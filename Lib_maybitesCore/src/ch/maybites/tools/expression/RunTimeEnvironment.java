/*
 * Copyright 2015 Martin Fröhlich
 * 
 * http://maybites.ch
 * 
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the
 * "Software"), to deal in the Software without restriction, including
 * without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to
 * permit persons to whom the Software is furnished to do so, subject to
 * the following conditions:
 * 
 * The above copyright notice and this permission notice shall be
 * included in all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 * 
 */

package ch.maybites.tools.expression;

import java.util.HashMap;
import java.util.Map;

public class RunTimeEnvironment {

	/**
	 * All defined variables with name and value.
	 */
	protected Map<String, MutableVariable> globalVars = new HashMap<String, MutableVariable>();

	/**
	 * All defined variables with name and value.
	 */
	protected Map<String, MutableVariable> localVars = new HashMap<String, MutableVariable>();

	protected RunTimeEnvironment() {
	}

	/**
	 * Sets a global variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setGlobalVariable(String variable, MutableVariable value) {
		if(globalVars.containsKey(value))
			globalVars.get(value).set(value);
		else
			globalVars.put(variable, value);
	}

	/**
	 * Sets a global variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setGlobalVariable(String variable, double value) {
		if(globalVars.containsKey(value))
			globalVars.get(value).setValue(value);
		else
			globalVars.put(variable, new MutableVariable(value));
	}

	/**
	 * Sets a global variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setGlobalVariable(String variable, String value) {
		if(globalVars.containsKey(value))
			globalVars.get(value).setValue(value);
		else
			globalVars.put(variable, new MutableVariable(value));
	}

	/**
	 * Sets a local variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setLocalVariable(String variable, MutableVariable value) {
		if(localVars.containsKey(value))
			localVars.get(value).set(value);
		else
			localVars.put(variable, value);
	}

	/**
	 * Sets a local variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setLocalVariable(String variable, double value) {
		if(localVars.containsKey(value))
			localVars.get(value).setValue(value);
		else
			localVars.put(variable, new MutableVariable(value));
	}

	/**
	 * Sets a local variable value.
	 * 
	 * @param variable
	 *            The variable name.
	 * @param value
	 *            The variable value.
	 */
	public void setLocalVariable(String variable, String value) {
		if(localVars.containsKey(value))
			localVars.get(value).setValue(value);
		else
			localVars.put(variable, new MutableVariable(value));
	}

}
