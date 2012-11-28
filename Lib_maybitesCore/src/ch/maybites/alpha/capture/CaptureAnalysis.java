/*
 * Created on 14.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package ch.maybites.alpha.capture;

import processing.core.PImage;

/**
 * @author mf
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CaptureAnalysis {
	
	public PImage m_Img; 
	private int m_PixCount;
	public boolean m_init;
	private int threshold;
	
	public CaptureAnalysis(){
		m_init = false;
		threshold = 0;
	}

	public void setThreshold(int pThresh){
		threshold = pThresh;
	}
	
	public void init(PImage img){
		m_Img = img;
		m_PixCount = img.height * img.width;
		m_init = true;
	}
	
	public PImage nextDiff(PImage img){
		int newPixCount = img.width * img.height;
		PImage retImg = new PImage(img.width, img.height);
		PImage saveImg = new PImage(img.width, img.height);
		
		if(m_init == false){
			init(img);
		}
		
		// check ImageSizes
		if(newPixCount == m_PixCount){
			for(int i=0; i<m_PixCount; i++){
				saveImg.pixels[i] = img.pixels[i];
				int r = (red(img.pixels[i]));
				int g = (green(img.pixels[i]));
				int b = (blue(img.pixels[i]));
				int rc = (int)abs(r-red(m_Img.pixels[i]));
				int gc = (int)abs(g-green(m_Img.pixels[i]));
				int bc = (int)abs(b-blue(m_Img.pixels[i]));
				int change = rc+gc+bc;
				
				if(change > threshold){
					retImg.pixels[i] = color(change, change, change);
				}else{
					retImg.pixels[i] = color(0, 0, 0);
				}
			}
		}
		m_Img = saveImg;
		
		return retImg;
	}

	private  int red(int what) {
	    return what >> 16 & 0xFF;
	}
	
	private int green(int what){
	    return what >> 8 & 0xFF;
	}
	
	private int blue(int what){
	    return what & 0xFF;
	}
	
	public int brightness(int pColor){
		return red(pColor) + green(pColor) + blue(pColor);
	}
	
	private int color(int r, int g, int b){
		return (r<<16 | g<<8 | b) | ~0xffffff;
	}
	
	private float abs(float n) {
		return (n < 0) ? -n : n;
	}
	
}
