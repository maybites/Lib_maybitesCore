package ch.maybites.alpha.image;


import processing.core.*;

/*
 * Created on 14.05.2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author mf
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ImageLoader extends PApplet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String myName;
	String myExt;
	boolean loop;
	int myStellen;
	int starts, ends;
	int counter;
	PImage myImg;

	public ImageLoader(String name, String ext, int stellen, int startswith, int endswidth){
		myName = name;
		myExt = ext;
		myStellen = stellen;
		starts = startswith;
		ends = endswidth;
		counter = starts;
		loop = true;
		myImg = null;
		next();
	}

	public ImageLoader(String name, String ext, int stellen, int startswith){
		this(name,  ext,  stellen,  startswith,  -1);
	}
		
	
	public boolean next(){
			if(counter == ends && loop){
				counter = starts;
			}
			try{
				myImg = loadImage(myName + getNumberString(counter++, myStellen) + "." + myExt);
			}catch (Exception e){
				counter = starts;
				return false;
			}
		return true;
	}
	
	public PImage getImage(){
		return myImg;
	}
	
	public int getPosition(){
		return counter;
	}
	
	public void reset(){
		counter=starts;
		next();
	}
	
	private String getNumberString(int number, int size){
		String ret = "";
		if(Math.pow(10, size) > number){
			for (int i = 0; i < size; i++ ){
				ret = (int)(number / Math.pow(10, (double)i))% 10 + ret;
			}
		}else{
			ret = "" + number;
		}
		return ret;
	}
	
}
