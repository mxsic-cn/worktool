package cn.mxsic.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * Function: PlainNioServer <br>
 *
 * @author: siqishangshu <br>
 * @date: 2019-07-18 11:17:00
 */
public class PlainNioServer {
    public void serve(int port) throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.configureBlocking(false);
        ServerSocket ss = serverChannel.socket();
        InetSocketAddress address = new InetSocketAddress(port);
        ss.bind(address);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        final ByteBuffer msg = ByteBuffer.wrap("hi \r\n".getBytes(Charset.defaultCharset()));
        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                break;
            }
            Set<SelectionKey> readKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        SocketChannel channel = serverSocketChannel.accept();
                        channel.configureBlocking(false);
                        channel.register(selector, SelectionKey.OP_WRITE |
                                SelectionKey.OP_READ, msg.duplicate());
                        System.out.println("Accepted connection from" + channel);
                    }
                    if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        while (buffer.hasRemaining()) {
                            if (channel.write(buffer) == 0) {
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    key.cancel();
                    e.printStackTrace();
                    key.channel().close();
                }

            }
        }
    }
}
