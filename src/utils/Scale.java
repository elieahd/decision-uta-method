package com.lamsade.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Scale{

	public static void main(String[] args) {
		int minValue, maxValue, scaleInterval;
		
		try{minValue = Integer.parseInt(args[0]);}catch(Exception e){minValue = 10;}
		try{maxValue = Integer.parseInt(args[1]);}catch(Exception e){maxValue = 20;}
		try{scaleInterval = Integer.parseInt(args[2]);}catch(Exception e){scaleInterval = 3;}
		
		System.out.println("[" + minValue + "," + maxValue +"] ["+ scaleInterval +"] => " + getScale(minValue, maxValue, scaleInterval));
	}

	private static List<Double> getScale (int maxValue, int minValue, int scaleInterval){
		List<Double> scale = new ArrayList<Double>();
		double total_length = maxValue - minValue;
		double subrange_length = total_length/(scaleInterval-1);

		double current_start = minValue;
		for (int i = 0; i < scaleInterval; ++i) {
			if(i == 0){
				scale.add(Double.parseDouble(minValue+""));
			}else if(i==(scaleInterval-1)){
				scale.add(Double.parseDouble(maxValue+""));
			}else{
				scale.add(current_start);
			}
			current_start += subrange_length;
		}
		Collections.sort(scale);
		return scale;
	}

}