/*
 * Copyright (C) 2011 Martin Fršhlich
 *
 * This class is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This class is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * {@link http://www.gnu.org/licenses/lgpl.html}
 *
 */

package ch.maybites.tools;

public class Debugger {

	private static final int LEVEL_VERBOSE = 0;
	private static final int LEVEL_DEBUG = 1;
	private static final int LEVEL_INFO = 2;
	private static final int LEVEL_WARNING = 3;
	private static final int LEVEL_ERROR = 4;
	private static final int LEVEL_FATAL = 5;
	
	private static final String[] LEVEL_MESG = {	"Verbose: ",
													"Debug: ",
													"Info: ",
													"Warning: ",
													"Error: ",
													"Fatal: "};
	
	// methods and attributes for Singleton pattern
	private Debugger(int l) {
		myLevel = l;
		_showClassNames = true;
	}

	private int myLevel;
	private boolean _showClassNames;
	
	static private Debugger _instance = new Debugger(LEVEL_VERBOSE);

	static public void showClassNames(){
		
	}
	
	static public void setLevelToVerbose(){
		_instance.myLevel = LEVEL_VERBOSE;
	}
	
	static public void setLevelToDebug(){
		_instance.myLevel = LEVEL_DEBUG;
	}
	
	static public void setLevelToInfo(){
		_instance.myLevel = LEVEL_INFO;
	}

	static public void setLevelToWarning(){
		_instance.myLevel = LEVEL_WARNING;
	}
	
	static public void setLevelToError(){
		_instance.myLevel = LEVEL_ERROR;
	}
	
	static public void setLevelToFatal(){
		_instance.myLevel = LEVEL_FATAL;
	}
	
	/**
	 * Get the global Debugger instance
	 * @return
	 */
	static public Debugger getInstance() {
		return _instance;
	}
	
	
	private void message(Class o, String message, int level){
		System.out.print(LEVEL_MESG[level]);
		if(o != null && _showClassNames)
			System.out.print("from " + o.getName() + ": ");
		System.out.println(message);
	}
	
	private void messageErr(Class o, String message, int level){
		System.err.print(LEVEL_MESG[level]);
		if(o != null && _showClassNames)
			System.err.print("from " + o.getName() + ": ");
		System.err.println(message);
	}

	public void verboseMessage(Class o, String message){
		if(myLevel <= LEVEL_VERBOSE)
			message(o, message, LEVEL_VERBOSE);
	}
	
	/**
	 * Lowest Priority Message
	 * @param o
	 * @param message
	 */
	public static void verbose(Class o, String message){
		getInstance().verboseMessage(o, message);
	}

	public void debugMessage(Class o, String message){
		if(myLevel <= LEVEL_DEBUG)
			message(o, message, LEVEL_DEBUG);
	}
	
	/**
	 * 2nd Lowest Priority Message
	 * @param o
	 * @param message
	 */
	public static void debug(Class o, String message){
		getInstance().debugMessage(o, message);
	}

	public void infoMessage(Class o, String message){
		if(myLevel <= LEVEL_INFO)
			message(o, message, LEVEL_INFO);
	}

	/**
	 * 3rd Lowest Priority Message
	 * @param o
	 * @param message
	 */
	public static void info(Class o, String message){
		getInstance().infoMessage(o, message);
	}

	public void warningMessage(Class o, String message){
		if(myLevel <= LEVEL_WARNING)
			message(o, message, LEVEL_WARNING);
	}

	/**
	 * Warning Message - possibly severe
	 * @param o
	 * @param message
	 */
	public static void warning(Class o, String message){
		getInstance().warningMessage(o, message);
	}

	public void errorMessage(Class o, String message){
		if(myLevel <= LEVEL_ERROR)
			messageErr(o, message, LEVEL_ERROR);
	}
	
	/**
	 * Error Message - very severe
	 * @param o
	 * @param message
	 */
	public static void error(Class o, String message){
		getInstance().errorMessage(o, message);
	}

	public void fatalMessage(Class o, String message){
		if(myLevel <= LEVEL_FATAL)
			messageErr(o, message, LEVEL_FATAL);
	}

	/**
	 * Fatal Message - very severe - Application will probably crash
	 * @param o
	 * @param message
	 */
	public static void fatal(Class o, String message){
		getInstance().fatalMessage(o, message);
	}

}
