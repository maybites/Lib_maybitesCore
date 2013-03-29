package ch.maybites.utils;

public class Timer {
	long time;
	
	public Timer(){
		
	}
	
	public void start(){
		time = System.currentTimeMillis();
	}
	
	public String estimation(int percent){
		long diff = System.currentTimeMillis() - time;
		return getTimeString(diff / percent * (100 - percent));
	}
	
	public String stop(){
		long diff = System.currentTimeMillis() - time;
		return getTimeString(diff);
	}
	
	private String getTimeString(long _time){
		StringBuffer timebandit = new StringBuffer();
		timebandit.insert(0, _time%1000 + "ms");
		_time /= 1000;
		if(_time >= 1)
			timebandit.insert(0, _time%60 + "s:");
		_time /= 60;
		if(_time >= 1)
			timebandit.insert(0, _time%60 + "m:");
		_time /= 60;
		if(_time >= 1)
			timebandit.insert(0, _time%24 + "h:");
		return timebandit.toString();			
	}
	
}