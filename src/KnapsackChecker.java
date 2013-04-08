import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;


public class KnapsackChecker {
	
	/**
	 * Keys: sums that can be created by a subset of the items
	 * Values: the count of ways that sum can be generated, >= 1
	 * 
	 * TODO: can switch to iterative generation of sums if too slow
	 * 
	 * 10 items: 350 ms
	 * 11 items: 2800 ms
	 */
	public Map<Long, Long> getSumsSlow(List<Long> prices) {
		Map<Long, Long> workingSums = Maps.newHashMap();
		long sum = 0;
		for (Long l : prices) {
			sum += l;
		}
		internalGetSums(prices, sum, workingSums);
		return workingSums;
	}
	
	private void internalGetSums(List<Long> prices, long listSum, Map<Long, Long> workingSums) {
		if (prices.size() == 0) return;
		insertCount(workingSums, listSum);
		for (int i = 0; i < prices.size(); i++) {
			Long item = prices.remove(i);
			listSum -= item;
			internalGetSums(prices, listSum, workingSums);
			prices.add(i, item);
			listSum += item;
		}
	}
	
	/**
	 * 10 -> 6 ms
	 * 11 -> 8 ms
	 * 15 -> 25 ms
	 * 20 -> 72 ms
	 * 25 -> 1201 ms
	 * 26 -> 2362 ms
	 * 27 -> 4380 ms
	 * 28 -> 8817 ms
	 */
	public Map<Long, Long> getSums(List<Long> prices) {
		Map<Long, Long> sums = Maps.newHashMap();
		
		long sum = 0;
		boolean[] bits = new boolean[prices.size()];
		// start all false, work up to all true
		
		while(true) {
			int i = 0;
			for(; i < bits.length; i++) {
				if(bits[i]) {
					bits[i] = false;
					sum -= prices.get(i);
				} else {
					bits[i] = true;
					sum += prices.get(i);
					break;
				}
			}
			if (i == bits.length) break;
			insertCount(sums, sum);
		}
		
		return sums;	
	}
	
	public static List<List<Long>> solve(List<Long> prices, long target) {	
		List<List<Long>> solutions = Lists.newArrayList();
		
		long sum = 0;
		boolean[] bits = new boolean[prices.size()];
		// start all false, work up to all true
		
		while(true) {
			int i = 0;
			for(; i < bits.length; i++) {
				if(bits[i]) {
					bits[i] = false;
					sum -= prices.get(i);
				} else {
					bits[i] = true;
					sum += prices.get(i);
					break;
				}
			}
			if (i == bits.length) break;
			if (sum == target) {
				solutions.add(select(prices, bits));
			}
		}
		
		return solutions;
	}
	
	private static List<Long> select(List<Long> prices, boolean[] bits) {
		List<Long> ret = Lists.newArrayList();
		for (int i = 0; i < bits.length; i++) {
			if (bits[i]) {
				ret.add(prices.get(i));
			}
		}
		return ret;
	}
	
	private void insertCount(Map<Long, Long> map, Long sum) {
		Long count = map.get(sum);
		if (count != null) {
			map.put(sum, count + 1);
		} else {
			map.put(sum, 1L);
		}
	}
}
