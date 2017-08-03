package com.lamsade.alternativecriteria;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Utils {
	
	public List<Double> generateScale(double minValue, double maxValue, int cuts){
		
		List<Double> scale = new ArrayList<>();
		
		double subrange_length = (maxValue - minValue)/(cuts-1);
		double current_start = minValue;
		
		for (int i = 0; i < cuts; ++i) {
			if(i == 0){
				scale.add(minValue);
			}else if(i==(cuts-1)){
				scale.add(maxValue);
			}else{
				scale.add(current_start);
			}
			current_start += subrange_length;
		}
		
		Collections.sort(scale);
		return scale;
	}
	
}