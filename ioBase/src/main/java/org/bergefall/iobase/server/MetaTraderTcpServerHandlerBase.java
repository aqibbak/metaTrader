package org.bergefall.iobase.server;

import java.util.List;

import org.bergefall.iobase.blp.BusinessLogicPipline;
import org.bergefall.protocol.metatrader.MetaTraderProtos.MetaTraderMessage;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class MetaTraderTcpServerHandlerBase extends SimpleChannelInboundHandler<MetaTraderMessage> {

	private List<BusinessLogicPipline> businessPipelines;
	
	public MetaTraderTcpServerHandlerBase(List<BusinessLogicPipline> blp) {
		super();
		businessPipelines = blp;
	}
	
  @Override
  protected void channelRead0(ChannelHandlerContext ctx, MetaTraderMessage msg)
      throws Exception {
	  for (BusinessLogicPipline pipeline : businessPipelines) {
		  pipeline.enqueue(msg);
	  }    
  }
  
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
      ctx.flush();
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
      cause.printStackTrace();
      ctx.close();
  }

}