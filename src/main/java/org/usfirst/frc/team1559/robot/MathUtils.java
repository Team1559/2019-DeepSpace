package org.usfirst.frc.team1559.robot;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MathUtils {

	/**
	 * Maps a value from one range to another.
	 * @param value Value to be mapped.
	 * @param minI Lower bound of initial range.
	 * @param maxI Upper bound of initial range.
	 * @param minF Lower bound of final range.
	 * @param maxF Upper bound of final range.
	 * @return Mapped value.
	 */
	public static double mapRange(double value, double minI, double maxI, double minF, double maxF) {
		return (value - minI) / (maxI - minI) * (maxF - minF) + minF;
	}

	/**
	 * Applies a function to every element in an array.
	 * @param function Function to be applied.
	 * @param array Array for function to be mapped on.
	 * @return List containing mapped elements.
	 */
	public static <T, R> List<R> map(Function<T, R> function, T[] array) {
		return Arrays.asList(array).stream().map(function).collect(Collectors.toList());
	}

	public static double sum(List<? extends Number> list) {
		double sum = 0;
		int n = list.size();
		for (int i = 0; i < n; i++) {
			sum += list.get(i).doubleValue();
		}
		return sum;
	}
	
	public static double average(Number... values) {
		return average(Arrays.asList(values));
	}

	public static double average(List<? extends Number> list) {
		double sum = 0;
		int n = list.size();
		for (int i = 0; i < n; i++) {
			sum += list.get(i).doubleValue();
		}
		return sum / n;
	}
	
	public static double median(List<? extends Number> list) {
		Number[] array = new Number[list.size()];
		list.toArray(array);
		Arrays.sort(array);
		if (array.length % 2 == 0) {
			return array[array.length / 2].doubleValue() + array[array.length / 2 - 1].doubleValue() / 2.0;
		} else {
			return array[array.length / 2].doubleValue();
		}
	}
}
