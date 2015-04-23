package com.douChat.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectableChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ServerOpenThread implements Runnable {
	/**
	 * Use instance to ensure there is only one nio server run on JVM
	 */
	private static ServerOpenThread instance;
	
	private static final Logger logger = Logger.getLogger(ServerOpenThread.class.getName());

	private Selector selector = null;
	private boolean isStop = false;
	private int acceptCnt = 0;
	
	/**
	 * The ConnectionEventHandlers waiting to bind to ServerSocketChannel port
	 */
	private ConcurrentLinkedQueue<IConnectionEventHandler> connEventWaitList = new ConcurrentLinkedQueue<IConnectionEventHandler>();

	private ServerOpenThread() {
		try {
			selector = Selector.open();
		} catch (IOException e) {
			logger.log(Level.WARNING, "Exception during openning selector: {0}", e.getMessage());
			isStop = true;
		}
	}
	
	public static ServerOpenThread getInstance() {
		if (instance == null) {
			instance = new ServerOpenThread();
			Thread t = new Thread(instance);
			t.setName("ServerOpenThread");
			t.start();
		}
		return instance;
	}
	
	public void addConnectionEventHandler(IConnectionEventHandler connEventHandler) {
		connEventWaitList.offer(connEventHandler);
		selector.wakeup(); // This can cancel the block status of selector.select()
	}
	
	public void removeConnectionEventHandler(IConnectionEventHandler connEventHandler) {
		for(SelectionKey sk: selector.keys()) {
			if(sk == connEventHandler) {
				try {
					sk.cancel();
					SelectableChannel channel = sk.channel();
					channel.close();
				} catch (IOException e) {
					logger.log(Level.WARNING, "Exception during removing ConnectionEventHandler: ", e.getMessage());
				}
				break;
			}
		}
	}

	@Override
	public void run() {
		logger.log(Level.INFO, "Server thread start.");
		// init server
		while (!isStop) {
			try {
				System.out.println(000);
				selector.select();
				System.out.println(111);
				for (Iterator<SelectionKey> i = selector.selectedKeys().iterator(); i.hasNext();) {
					SelectionKey sk = i.next();
					System.out.println("Selected Key - Acceptable: " + sk.isAcceptable() + "; Readable: " + sk.isReadable() + "; Writable: " + sk.isWritable());
					i.remove();

					SocketChannel socketChannel = null;
					int port = 0;

					if ((sk.readyOps() & SelectionKey.OP_ACCEPT) != 0) {
						ServerSocketChannel nextReady = (ServerSocketChannel) sk.channel();
						port = nextReady.socket().getLocalPort();
						socketChannel = nextReady.accept();
						logger.log(Level.FINEST,"OP_ACCEPT");
					}
					if ((sk.readyOps() & SelectionKey.OP_CONNECT) != 0) {
						sk.cancel();
						socketChannel = (SocketChannel) sk.channel();
						logger.log(Level.FINEST,"OP_CONNECT");
					}
					
					if(socketChannel != null) {
						socketChannel.configureBlocking(false);
						socketChannel.socket().setSoLinger(false, 0);
						socketChannel.socket().setReuseAddress(true);
						logger.log(Level.FINER, "Registered new client socket: {0}", socketChannel);
						IConnectionEventHandler connEventHandler = (IConnectionEventHandler) sk.attachment();
						socketChannel.socket().setReceiveBufferSize(connEventHandler.getReceiveBufferSize());
						connEventHandler.handle(socketChannel);
					} else {
						logger.log(Level.INFO,"This cannot occured");
					}
					acceptCnt++;
				}
				addWaitingHandler();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void addWaitingHandler() {
		IConnectionEventHandler connEventHandler = null;

		while ((connEventHandler = connEventWaitList.poll()) != null) {
			
		}
	}

	public static void main(String[] args) {
		ServerOpenThread sot = ServerOpenThread.getInstance();
	}
}
