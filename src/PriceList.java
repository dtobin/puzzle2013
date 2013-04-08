import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;


public class PriceList {
	
	private List<Long> prices;
	
	public PriceList(List<Long> prices) {
		this.prices = ImmutableList.copyOf(prices);
	}
	
	public List<Long> getPrices() {
		return prices;
	}
	
	public void toFile(String filename) {
		try {
			PrintStream out = new PrintStream(new FileOutputStream(filename));
			for (Long l : prices) {
				out.println(l);
			}
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
	
	public static PriceList fromFile(String filename) {
		try {
			List<Long> prices = Lists.newArrayList();
			
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			while (true) {
				String line = reader.readLine();
				if (line == null) break;
				try {
					Long l = Long.parseLong(line.trim());
					if (l != null) {
						prices.add(l);
					}
				} catch(NumberFormatException ex) {
					// ignore it
				}
			}
			return new PriceList(prices);
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}
}
