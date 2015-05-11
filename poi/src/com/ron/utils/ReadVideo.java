package com.ron.utils;

import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.MultimediaInfo;

import java.io.File;

public class ReadVideo {
	
	public static long getTime(String filePath){
		long ls = 0;
        File source = new File(filePath);
        Encoder encoder = new Encoder();
        try {
             MultimediaInfo m = encoder.getInfo(source);
             ls = m.getDuration();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ls;
	}

}
