package gr.northdigital.nettydemo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
    System.out.println("Message received: " + msg);
  }
}
