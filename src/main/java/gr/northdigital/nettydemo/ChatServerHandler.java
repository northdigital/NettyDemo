package gr.northdigital.nettydemo;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {
  private static final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  public void handlerAdded(ChannelHandlerContext ctx) {
    Channel incomingChannel = ctx.channel();
    for(Channel channel : channels) {
      channel.write("SERVER - " + incomingChannel.remoteAddress() + " has joined!\r\n");
      channel.flush();
    }
    channels.add(incomingChannel);
  }

  @Override
  public void handlerRemoved(ChannelHandlerContext ctx) {
    Channel incomingChannel = ctx.channel();
    for(Channel channel : channels) {
      channel.write("SERVER - " + incomingChannel.remoteAddress() + " has left!\r\n");
      channel.flush();
    }
    channels.add(incomingChannel);
  }

  @Override
  protected void channelRead0(ChannelHandlerContext channelHandlerContext, String msg) {
    Channel incomingChannel = channelHandlerContext.channel();
    for(Channel channel : channels) {
      if(channel != incomingChannel) {
        channel.write(("[" + incomingChannel.remoteAddress() + "] " + msg + "\r\n"));
        channel.flush();
      }
    }
  }
}
