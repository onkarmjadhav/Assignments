package com.test;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class ZipCodeRange {

	static Map<Integer, Integer> zipMap = new TreeMap<>();

	public static void main(String[] args) {
		// String zipCodes = "[94500,94599] [94140,94210] [94210,94501]
		// [94200,94299] [94600,94699] [94650,94689] [94343,94454]";
		// String zipCodes = "[94133,94133] [94200,94299] [94226,94399]";
		String zipCodes = "[94133,94133] [94200,94299] [94600,94699]";
		arrangeZipCodes(zipCodes);
	}

	public static void arrangeZipCodes(String zipCodes) {

		String[] zipCodeArray = zipCodes.trim().split(" ");
		for (int i = 0; i < zipCodeArray.length; i++) {
			String zipCodeBoundary = zipCodeArray[i];
			String zipCodeLower = null;
			String zipCodeHigher = null;
			try {
				if (zipCodeBoundary.charAt(0) == '[' && zipCodeBoundary.lastIndexOf('[') == 0
						&& zipCodeBoundary.charAt(zipCodeBoundary.length() - 1) == ']'
						&& zipCodeBoundary.lastIndexOf(']') == zipCodeBoundary.length() - 1) {//Validate input
					

					zipCodeLower = zipCodeBoundary.substring(1, 6);
					zipCodeHigher = zipCodeBoundary.substring(7, 12);

					validateZipCodes(Integer.parseInt(zipCodeLower), Integer.parseInt(zipCodeHigher));

				} else {
					System.out.println("Invalid Input");
				}
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Invalid Input : String is not valid at Index : " + i);
			} catch (NumberFormatException e) {
				System.out.println("Invalid Input");
			} catch (Exception e) {
				System.out.println("Invalid Input");
			}

		}

		displayManipulatedZipCodes();

	}

	/**
	 * Display final result
	 */
	private static void displayManipulatedZipCodes() {
		Set<Entry<Integer, Integer>> zipEntrySet = zipMap.entrySet();

		for (Entry<Integer, Integer> entry : zipEntrySet) {
			System.out.print("[" + entry.getKey() + "," + entry.getValue() + "] ");
		}

	}

	/**
	 * This method validate each zipcode with the upper & lower range stored in Map
	 * @param zipCodeLower
	 * @param zipCodeHigher
	 */
	private static void validateZipCodes(int zipCodeLower, int zipCodeHigher) {

		if (!zipMap.isEmpty()) {
			Set<Entry<Integer, Integer>> zipEntrySet = zipMap.entrySet();
			Iterator<Map.Entry<Integer, Integer>> itr = zipEntrySet.iterator();
			while (itr.hasNext()) {
				Map.Entry<Integer, Integer> entry = itr.next();
				int low = entry.getKey();
				int high = entry.getValue();
				if (zipCodeLower >= low && zipCodeLower <= high) {
					zipCodeLower = low;
					if (zipCodeHigher < high)
						zipCodeHigher = high;
				} else if (zipCodeHigher >= low && zipCodeHigher <= high) {
					itr.remove();
					zipCodeHigher = high;
				} else if (zipCodeLower < low) {
					itr.remove();
					if (zipCodeHigher < high)
						zipCodeHigher = high;
				}
			}

			zipMap.put(zipCodeLower, zipCodeHigher);

		} else {
			zipMap.put(zipCodeLower, zipCodeHigher);
		}
	}
}
