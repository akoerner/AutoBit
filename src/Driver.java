import java.math.BigDecimal;
import java.math.MathContext;
import java.util.List;

import org.joda.money.BigMoney;
import org.joda.money.CurrencyUnit;

import com.sun.media.sound.Toolkit;
import com.tictactec.ta.lib.Core;
import com.xeiam.xchange.currency.Currencies;
import com.xeiam.xchange.dto.marketdata.Ticker;
import com.xeiam.xchange.dto.trade.LimitOrder;
import com.xeiam.xchange.dto.trade.OpenOrders;
import com.xeiam.xchange.dto.trade.Wallet;


public class Driver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		//http://www.slideshare.net/mring33/using-java
		String apiKey = "";
		String apiSecret = "";
        BTCEExchangeAccessor bem = new BTCEExchangeAccessor(apiKey, apiSecret);
        MathContext mc = new MathContext(12);
        BigDecimal volumeChange = new BigDecimal(0);
        BigDecimal lastChange = new BigDecimal(0);
        Ticker tickerLast = null;
        
        Core tradingAlgorithmCore = new Core();
   
        
        while(true){
    
        	
        	try {
        		Thread.sleep(2000);
        		Ticker ticker = bem.getLatestTicker();
        		
        		
        		if(tickerLast != null){
        			volumeChange = tickerLast.getVolume().subtract(ticker.getVolume(), mc);
        			volumeChange = volumeChange.divide(tickerLast.getVolume(), mc);
        			volumeChange = volumeChange.multiply(new BigDecimal(100), mc);
        			
        			lastChange = tickerLast.getLast().getAmount().subtract(ticker.getLast().getAmount(), mc);
        			lastChange = lastChange.divide(tickerLast.getLast().getAmount(), mc);
        			lastChange = lastChange.multiply(new BigDecimal(100), mc);
        			
        		}
        		
        		if(lastChange.doubleValue() > 1){
        			java.awt.Toolkit.getDefaultToolkit().beep();

        		}else if(lastChange.doubleValue() < -1){
        			java.awt.Toolkit.getDefaultToolkit().beep();
        			java.awt.Toolkit.getDefaultToolkit().beep();
        		}
        		
        		System.out.println("Last:" + ticker.getLast().getAmount() 
        							+ " - " +
        							"High:" + ticker.getHigh().getAmount() 
        							+ " - " +
        							"Low:" + ticker.getLow().getAmount() 
        							+ " - " +
        							"Ask:" + ticker.getAsk().getAmount() 
        							+ " - " +
        							"Bid:" + ticker.getBid().getAmount() 
        							+ " - " +
        							ticker.getTimestamp() + " - " + ticker.getVolume()
        							+ " - " +
        							"Volume Change:" + volumeChange.doubleValue()*-1 + "%"
        							+ " - " +
        							"Last Change:" + lastChange.doubleValue()*-1+ "%"
        							);
        							
        							
        		
        		tickerLast = ticker;
        		//List<LimitOrder> openOrders = bem.getOpenOrders();
        		
        		//for(LimitOrder openOrder: openOrders){
        		//	System.out.println(openOrder.getId());
        		//	bem.cancelOrderById(openOrder.getId());
        		//}
        		
        		
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e){
				System.out.println("Connection Dead");
				e.printStackTrace();
			}
        }


        
   
       
         

	}

}
