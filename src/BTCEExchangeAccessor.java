import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.xeiam.xchange.Exchange;
import com.xeiam.xchange.ExchangeFactory;
import com.xeiam.xchange.ExchangeSpecification;
import com.xeiam.xchange.btce.BTCEExchange;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.Order;
import com.xeiam.xchange.dto.account.AccountInfo;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.service.account.polling.PollingAccountService;
import com.xeiam.xchange.service.marketdata.polling.PollingMarketDataService;


public class BTCEExchangeAccessor {
	
	Exchange bTCEExchange;

	public BTCEExchangeAccessor(String apiKey, String apiSecret){
		 TrustManager[] trustAllCerts = new TrustManager[]{
                 new X509TrustManager() {

                     public java.security.cert.X509Certificate[] getAcceptedIssuers()
                     {
                         return null;
                     }
                     public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType)
                     {
                         //No need to implement.
                     }
                     public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType)
                     {
                         //No need to implement.
                     }
                 }
         };

         // Install the all-trusting trust manager
         try 
         {
             SSLContext sc = SSLContext.getInstance("SSL");
             sc.init(null, trustAllCerts, new java.security.SecureRandom());
             HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         } 
         catch (Exception e) 
         {
             System.out.println(e);
         }
		
         ExchangeSpecification exSpec = new ExchangeSpecification(BTCEExchange.class);
         exSpec.setSecretKey(apiSecret);
         exSpec.setApiKey(apiKey);
         exSpec.setUri("https://btc-e.com");
         this.bTCEExchange = ExchangeFactory.INSTANCE.createExchange(exSpec);
         
		
		
	}
	
	public Ticker getLatestTicker(){
        PollingMarketDataService marketDataService;
        marketDataService = bTCEExchange.getPollingMarketDataService();
        Ticker ticker = marketDataService.getTicker(Currencies.BTC, Currencies.USD);
        return ticker;
	}

	public String getMinimumTradeFee(){
		System.out.println(this.bTCEExchange.getDefaultExchangeSpecification().getParameter("Fee"));
		
		return "";
	}
	
	public Double getTradeFeePercent(){
		return 0.002;//this.bTCEExchange.getExchangeSpecification().getTradeFeePercent();
	}
	
	public List<LimitOrder> getOpenOrders(){
		return this.bTCEExchange.getPollingTradeService().getOpenOrders().getOpenOrders();
	}
	
	public void cancelOrderById(String id){
		this.bTCEExchange.getPollingTradeService().cancelOrder(id);
	}
	
	public AccountInfo getAccountInfo(){

		
		PollingAccountService accountService = this.bTCEExchange.getPollingAccountService();

		AccountInfo accountInfo = accountService.getAccountInfo();
		return accountInfo;
	}
	
	public BigMoney getAccountCurrentUSD(){
		return this.getAccountInfo().getBalance(CurrencyUnit.USD);
	}

	public BigMoney getAccountCurrentBTC(){
		return this.getAccountInfo().getBalance(CurrencyUnit.getInstance("BTC"));	
	}
}
