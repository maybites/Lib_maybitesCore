package ch.maybites.utils;

public interface DebugLogger {
	
	public void printInfo(boolean _showTime, String _level, String _className, String _message);

	public void printError(boolean _showTime, String _level, String _className, String _message);

}
