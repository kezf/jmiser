package org.miser.socket.nio;

import java.io.Closeable;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

import org.miser.core.io.IOUtil;
import org.miser.socket.SocketRuntimeException;

/**
 * 基于NIO的Socket服务端实现
 * 
 * @author Oliver
 */
public class NioServer implements Closeable {

	private static final AcceptHandler ACCEPT_HANDLER = new AcceptHandler();

	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	private ChannelHandler handler;

	/**
	 * 构造
	 * 
	 * @param port 端口
	 */
	public NioServer(int port) {
		init(new InetSocketAddress(port));
	}

	/**
	 * 初始化
	 * 
	 * @param address 地址和端口
	 * @return this
	 */
	public NioServer init(InetSocketAddress address) {
		try {
			// 打开服务器套接字通道
			this.serverSocketChannel = ServerSocketChannel.open();
			// 设置为非阻塞状态
			this.serverSocketChannel.configureBlocking(false);
			// 绑定端口号
			this.serverSocketChannel.bind(address);

			// 打开一个选择器
			this.selector = Selector.open();
			// 服务器套接字注册到Selector中 并指定Selector监控连接事件
			this.serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		} catch (IOException e) {
			throw new SocketRuntimeException(e);
		}
		return this;
	}

	/**
	 * 设置NIO数据处理器
	 *
	 * @param handler {@link ChannelHandler}
	 * @return this
	 */
	public NioServer setChannelHandler(ChannelHandler handler) {
		this.handler = handler;
		return this;
	}

	/**
	 * 获取{@link Selector}
	 *
	 * @return {@link Selector}
	 */
	public Selector getSelector() {
		return this.selector;
	}

	/**
	 * 启动NIO服务端，即开始监听
	 *
	 * @see #listen()
	 */
	public void start() {
		listen();
	}

	/**
	 * 开始监听
	 */
	public void listen() {
		try {
			doListen();
		} catch (IOException e) {
			throw new SocketRuntimeException(e);
		}
	}

	/**
	 * 开始监听
	 * 
	 * @throws IOException IO异常
	 */
	private void doListen() throws IOException {
		while (this.selector.isOpen() && 0 != this.selector.select()) {
			// 返回已选择键的集合
			final Iterator<SelectionKey> keyIter = selector.selectedKeys().iterator();
			while (keyIter.hasNext()) {
				handle(keyIter.next());
				keyIter.remove();
			}
		}
	}

	/**
	 * 处理SelectionKey
	 * 
	 * @param key SelectionKey
	 */
	private void handle(SelectionKey key) {
		// 有客户端接入此服务端
		if (key.isAcceptable()) {
			ACCEPT_HANDLER.completed((ServerSocketChannel) key.channel(), this);
		}

		// 读事件就绪
		if (key.isReadable()) {
			final SocketChannel socketChannel = (SocketChannel) key.channel();
			try {
				handler.handle(socketChannel);
			} catch (Exception e) {
				IOUtil.close(socketChannel);
				e.printStackTrace();
			}
		}
	}

	@Override
	public void close() {
		IOUtil.close(this.selector);
		IOUtil.close(this.serverSocketChannel);
	}
}
