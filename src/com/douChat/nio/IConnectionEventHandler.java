package com.douChat.nio;

import java.nio.channels.SocketChannel;

public interface IConnectionEventHandler {

	int getReceiveBufferSize();

	void handle(SocketChannel socketChannel);

}
