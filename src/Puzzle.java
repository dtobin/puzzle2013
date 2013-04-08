import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import com.google.common.base.Stopwatch;
import com.google.common.collect.Lists;


public class Puzzle {
	
	private static Random random = new Random();
	
	private static String PRICES = "prices.txt";
	private static String PRICES_FIXED = "prices_fixed.txt";
	
	public static void main(String[] argv) {
		
		//printSumsFromList();
		//List<Long> prices = getPrices();
		//printSolutions(KnapsackChecker.solve(prices, 17545));
		
		List<Long> prices = Lists.newArrayList(PriceList.fromFile(PRICES_FIXED).getPrices());
//		tryAddPrimes(prices, 100, 30, 2);
		tryAddPrimes(prices, 173, 1, 1);
		tryAddPrimes(prices, 223, 1, 1);
		System.out.println(prices.get(prices.size() - 1));
		System.out.println(prices.get(prices.size() - 2));
		
//		tryAddPrimes(prices, 6500, 50, 1);
		tryAddPrimes(prices, 6863, 1, 1);
		System.out.println(prices.get(prices.size() - 1));

//		tryAddComposites(prices, 200, 100, 1);
		tryAddComposites(prices, 220, 1, 1);
		System.out.println(prices.get(prices.size() - 1));
		
//		tryAddComposites(prices, 200, 100, 1);
		tryAddComposites(prices, 242, 1, 1);
		System.out.println(prices.get(prices.size() - 1));

//		tryAddCompositeInRange(prices, 5790, 1000);
		tryAddCompositeInRange(prices, 5950, 1);
		System.out.println(prices.get(prices.size() - 1));
		
//		tryAddComposites(prices, 880, 500, 1);
		tryAddComposites(prices, 1202, 1, 1);
//		tryAddComposites(prices, 880, 500, 1);
		tryAddComposites(prices, 1335, 1, 1);
		
		System.out.println("time for chaff");
		
		for (int i = 0; i < 10; i++) {
			tryAddComposites(prices, 300, 10000, 1);
			System.out.println(prices.get(prices.size() - 1));
		}
		
		new PriceList(prices).toFile(PRICES);
	}
	
	private static void tryAddPrimes(List<Long> prices, int start, int variance, int n) {
		while(true) {
			NumberPicker.addPrimes(prices, start, variance, n);
			if (KnapsackChecker.solve(prices, 17545).size() == 2) {
				break;
			} else {
				for (int i = 0; i < n; i++) {
					prices.remove(prices.size() - 1);
				}
			}
		}
	}
	
	private static void tryAddComposites(List<Long> prices, int start, int variance, int n) {
		while(true) {
			NumberPicker.addComposites(prices, start, variance, n);
			if (KnapsackChecker.solve(prices, 17545).size() == 2) {
				break;
			} else {
				for (int i = 0; i < n; i++) {
					prices.remove(prices.size() - 1);
				}
			}
		}
	}
	public static void tryAddCompositeInRange(List<Long> prices, int start, int range) {
		for (int i = 0; i < range; i++) {
			long l = start + i;
			if (NumberPicker.hasSmallFactor(l) && !prices.contains(l)) {
				prices.add(l);
				if (KnapsackChecker.solve(prices, 17545).size() == 2) {
					return;
				}
				prices.remove(prices.size() - 1);
			}
		}
		throw new RuntimeException("couldn't find it, sorry");
	}
	
	private static void tryForAWhile() {
		while (true) {
			//NumberPicker.pickNumbers();
			List<Long> prices = getPrices();
//			List<Long> prices = PriceList.fromFile(PRICES_FIXED).getPrices();
			List<List<Long>> solutions = KnapsackChecker.solve(prices, 17545);
			System.out.println(solutions.size());
			if (solutions.size() == 2) {
				printSolutions(solutions);
				break;
			}
		}
	}
	
	private static void printSumsFromList() {
		List<Long> prices = getPrices();
		
		Stopwatch timer = new Stopwatch();
		timer.start();
		
		KnapsackChecker checker = new KnapsackChecker();
		Map<Long, Long> sums = checker.getSums(prices);
		
		timer.stop();
		System.out.println("time to compute sums: " + timer.elapsedMillis() + " ms");
		
		List<Long> uniques = getWithCount(sums, 2);
		Collections.sort(uniques);
		System.out.println(join(uniques, "\n"));
	}
	
	
	
	private static List<Long> getUniques(Map<Long, Long> sums) {
		return getWithCount(sums, 1L);
	}
		
	private static List<Long> getWithCount(Map<Long, Long> sums, long count) {
		List<Long> uniques = Lists.newArrayList();
		for (Entry<Long, Long> e : sums.entrySet()) {
			if (e.getValue().longValue() == count) {
				uniques.add(e.getKey());
			}
		}
		return uniques;
	}
	
	private static List<Long> getPrices() {
		return PriceList.fromFile("prices.txt").getPrices();
	}
	
	private static List<Long> getRandomPrices(int numItems) {
		List<Long> prices = Lists.newArrayList();
		for (int i = 0; i < numItems; i++) {
			long l = random.nextInt(10000) + 200;
			prices.add(l);
		}
		return prices;
	}
	
	private static void savePrices(List<Long> prices) {
		new PriceList(prices).toFile(PRICES);
	}
	
	private static void printSolutions(List<List<Long>> solutions) {
		for (List<Long> solution : solutions) {
			System.out.println(join(solution, " "));
		}
	}
	
	private static String join(List<Long> list, String joiner) {
		if (list.size() == 0) return "";
		String result = "" + list.get(0).toString();
		for(int i = 1; i < list.size(); i++) {
			result += (joiner + list.get(i).toString());
		}
		return result;
	}
}
