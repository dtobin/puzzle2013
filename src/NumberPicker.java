import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;


public class NumberPicker {

	private static String PRICES = "prices.txt";
	private static String PRICES_FIXED = "prices_fixed.txt";
	private static Random random = new Random();
	
	public static void main(String[] args) {
		// pickPrimes();
		
		pickComposites();
	}
	
	public static void pickNumbers() {
		List<Long> prices = Lists.newArrayList(PriceList.fromFile(PRICES_FIXED).getPrices());
		
		addPrimes(prices, 100, 30, 2);
		addPrimes(prices, 6500, 50, 1);
		addComposites(prices, 200, 100, 2);
		addComposites(prices, 5050, 50, 1);
		addComposites(prices, 880, 500, 2);
		
		new PriceList(prices).toFile(PRICES);
	}
	
	private static void pickComposites() {
		List<Long> prices = Lists.newArrayList();
			
		addComposites(prices, 200, 100, 5);
		addComposites(prices, 5100, 100, 1);
		addComposites(prices, 5400, 200, 3);
		addComposites(prices, 8800, 500, 2);
		
		Collections.sort(prices);
		savePrices(prices);
	}
	
	public static void addComposites(List<Long> prices, long start, int variance, int n) {
		for (int i = 0; i < n; i++) {
			long l;
			do {
				l = random.nextInt(variance) + start; 
			} while (!hasSmallFactor(l) || prices.contains(l));
			prices.add(l);
		}
	}
	
	public static void addPrimes(List<Long> prices, int start, int variance, int n) {
		for(int i = 0; i < n; i++) {
			long prime;
			do {
				prime = Primes.nthPrimeFrom(random.nextInt(variance), start);
			} while (prices.contains(prime));
			prices.add(prime);
		}
	}

	public static boolean hasSmallFactor(long l) {
		return containsFactor(l, ImmutableList.of(2L, 3L, 5L, 7L));
	}
	
	private static boolean containsFactor(long l, List<Long> factors) {
		for (Long factor : factors) {
			if (l % factor == 0) {
				return true;
			}
		}
		return false;
	}
	
	private static void pickPrimes() {
		List<Long> prices = Lists.newArrayList();
		
		// aim for 10 primes
		addPrimes(prices, 100, 30, 5);
		addPrimes(prices, 5000, 200, 5);
		Collections.sort(prices);
		
		savePrices(prices);
	}
	
	private static void savePrices(List<Long> prices) {
		new PriceList(prices).toFile(PRICES);
	}
}
