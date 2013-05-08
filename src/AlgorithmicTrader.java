import org.joda.money.BigMoney;

import com.xeiam.xchange.Exchange;


public class AlgorithmicTrader {
	
	BigMoney maximumBTCTrade;
	int maximumTradesPerMinute;
	
	Exchange bTCEExchange;

	public AlgorithmicTrader(BigMoney maximumBTCTrade, int maximumTradesPerMinute, Exchange bTCEExchange){
		this.bTCEExchange = bTCEExchange;
	}
	
}
