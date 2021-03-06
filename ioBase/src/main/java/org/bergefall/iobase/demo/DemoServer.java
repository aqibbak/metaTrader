package org.bergefall.iobase.demo;

import java.util.ArrayList;
import java.util.List;

import org.bergefall.base.strategy.IntraStrategyBeanMsg;
import org.bergefall.base.strategy.StrategyToken;
import org.bergefall.common.DateUtils;
import org.bergefall.common.config.MetaTraderConfig;
import org.bergefall.iobase.blp.BusinessLogicPipelineImpl;
import org.bergefall.iobase.blp.BusinessLogicPipline;
import org.bergefall.iobase.server.MetaTraderServerApplication;
import org.bergefall.protocol.metatrader.MetaTraderProtos.MetaTraderMessage;

public class DemoServer extends MetaTraderServerApplication {

	private static final String configFile = "../common/src/main/resources/ConfigExample.properties";

	public static void main(String[] args) throws InterruptedException {
		DemoServer ds = new DemoServer();
		ds.initServer(configFile);
		ds.startListening();
	}
	
	private static class SimpleBLP extends BusinessLogicPipelineImpl {

		public SimpleBLP(MetaTraderConfig config) {
			super(config);
		}

		@Override
		protected void handleMarketData(StrategyToken token, IntraStrategyBeanMsg intraMsg) {
			MetaTraderMessage msg = token.getTriggeringMsg();
			if ( msg.getSeqNo() % 10 == 0) {
				System.out.println("Handled MarketData msg with seqno: " + msg.getSeqNo() + " at: " +
						DateUtils.getCurrentTimeAsReadableDate() + msg);			
			}
		}

		@Override
		protected void handleAccounts(StrategyToken token, IntraStrategyBeanMsg intraMsg) {
			MetaTraderMessage msg = token.getTriggeringMsg();
			if ( msg.getSeqNo() % 10 == 0) {
				System.out.println("Handled Account msg with seqno: " + msg.getSeqNo() + " at: " +
						DateUtils.getCurrentTimeAsReadableDate() + msg);			
			}	
		}

		@Override
		protected void handleInstrument(StrategyToken token, IntraStrategyBeanMsg intraMsg) {

		}

		@Override
		protected void handleBeats(StrategyToken token, IntraStrategyBeanMsg intraMsg) {
			// TODO Auto-generated method stub
			
		}

		@Override
		protected void handleOrders(StrategyToken token, IntraStrategyBeanMsg intraMsg) {
			// TODO Auto-generated method stub
			
		}
		
	}


	@Override
	protected List<BusinessLogicPipline> getBLP(MetaTraderConfig config) {
		List<BusinessLogicPipline> list = new ArrayList<>();
		list.add(new SimpleBLP(config));
		list.add(new SimpleBLP(config));
		return list;
	}
}
