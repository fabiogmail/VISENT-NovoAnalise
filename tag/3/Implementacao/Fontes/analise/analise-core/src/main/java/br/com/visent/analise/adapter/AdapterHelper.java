package br.com.visent.analise.adapter;

import java.time.Duration;

public class AdapterHelper {

	public static String durationToString(Duration duration) {
		long toHours = duration.toHours();
    	long toMinutes = duration.toMinutes();
    	return String.format("%02d:%02d:%02d",
    			duration.toHours(),
    			duration.minusHours(toHours).toMinutes(), 
    			duration.minusMinutes(toMinutes).getSeconds());
	}
	
	public static Duration stringToDuration(String durationString) {
		String[] durationParts = durationString.split(":");
    	return Duration.ZERO
    			.plusHours(Long.parseLong(durationParts[0]))
    			.plusMinutes(Long.parseLong(durationParts[1]))
    			.plusSeconds(Long.parseLong(durationParts[2]));
	}
	
}
