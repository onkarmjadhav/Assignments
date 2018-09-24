package com.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

public class ZipCodeRestrictions {
	
	static Map<Integer, Integer> zipMap = new LinkedHashMap<>();

	public static void main(String[] args) {
		String zipCodes = "[94500,94599] [94140,94210] [94210,94500] [94200,94299] [94600,94699] [94650,94689] [94343,94454]";
		arrangeZipCodes(zipCodes);
	}
	
	public static void arrangeZipCodes(String zipCodes){
		
		String []zipCodeArray = zipCodes.split(" ");
		for (int i = 0; i < zipCodeArray.length; i++) {
			String zipCodeBoundary = zipCodeArray[i];
			String zipCodeLower = zipCodeBoundary.substring(1, 6);
			String zipCodeHigher = zipCodeBoundary.substring(7, 12);
			
			validateZipCodes(Integer.parseInt(zipCodeLower), Integer.parseInt(zipCodeHigher));
		}
		
		displayManipulatedZipCodes();
	}

	private static void displayManipulatedZipCodes() {
		Set<Entry<Integer, Integer>> zipEntrySet = zipMap.entrySet();
		
		for(Entry<Integer, Integer> entry : zipEntrySet){
			System.out.print("[" + entry.getKey() + "," + entry.getValue() + "] ");
		}
		
	}

	private static void validateZipCodes(int zipCodeLower, int zipCodeHigher) {
		
		if(!zipMap.isEmpty()){
			Set<Entry<Integer, Integer>> zipEntrySet = zipMap.entrySet();
			int i = 0;
			boolean visited = false;
			for(Entry<Integer, Integer> entry : zipEntrySet){
				i++;
				int low = entry.getKey();
				int high = entry.getValue();
				
				if(zipCodeLower < low && zipCodeHigher > low && zipCodeHigher < high){
					zipMap.remove(low);
					zipMap.put(zipCodeLower, high);
					visited = true;
					continue;
				}else if(zipCodeLower > low && zipCodeHigher < high){
					visited = true;
					continue;
				}else if(zipCodeHigher > high && zipCodeLower > low && zipCodeLower <= high){
					zipMap.replace(low, zipCodeHigher);
					visited = true;
					continue;
				}else if(zipCodeLower < low && zipCodeHigher > high){
					zipMap.remove(low);
					zipMap.put(zipCodeLower, zipCodeHigher);
					visited = true;
					continue;
				}else{
					if(i == zipMap.size() && !visited){
						zipMap.put(zipCodeLower, zipCodeHigher);
					}
				}
			}
			
		}else{
			zipMap.put(zipCodeLower, zipCodeHigher);
		}
		
		
	}

}
