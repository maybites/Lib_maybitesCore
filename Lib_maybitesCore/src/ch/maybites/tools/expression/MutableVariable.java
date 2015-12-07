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

import java.math.MathContext;
import java.math.RoundingMode;

public class MutableVariable {
	public static final MutableVariable ONE = new MutableVariable(1);
	public static final MutableVariable ZERO = new MutableVariable(0);
	
	private double dValue;
	private String sValue;
	
	public boolean isNumber = false;
	
	public MutableVariable(double value){
		this.dValue = value;
		isNumber = true;
	}
	
	public MutableVariable(String value){
		setValue(value);
	}

	public void set(MutableVariable val){
		if(val.isNumber){
			this.dValue = val.getNumberValue();
			this.isNumber = true;
		} else {
			this.sValue = val.getStringValue();
			this.isNumber = false;
		}
	}

	public void setValue(String val){
		try{
			this.dValue = Double.parseDouble(val);
			isNumber = true;
		} catch (NumberFormatException e){
			this.sValue = val;
			isNumber = false;
		}
	}
	
	public void setValue(double val){
		this.dValue = val;
		isNumber = true;
	}
	
	public double getNumberValue(){
		if(isNumber)
			return dValue;
		return 0;
	}
	
	public String getStringValue(){
		if(!isNumber)
			return sValue;
		return ""+ dValue;
	}

	public MutableVariable add(MutableVariable v2) {
		if(isNumber && v2.isNumber)
			return new MutableVariable(this.dValue + v2.getNumberValue());
		else
			return new MutableVariable(getStringValue() + v2.getStringValue());			
	}

	public MutableVariable subtract(MutableVariable v2) {
		return new MutableVariable(getNumberValue() - v2.getNumberValue());
	}

	public MutableVariable subtract(double v2) {
		return new MutableVariable(getNumberValue() - v2);
	}


	public MutableVariable multiply(MutableVariable v2) {
		return new MutableVariable(getNumberValue() * v2.getNumberValue());
	}
	
	public MutableVariable multiply(int v2) {
		return new MutableVariable(getNumberValue() * (double)v2);
	}
	
	public MutableVariable multiply(double v2) {
		return new MutableVariable(getNumberValue() * v2);
	}

	public MutableVariable divide(MutableVariable v2) {
		return new MutableVariable(getNumberValue() / v2.getNumberValue());
	}
	
	public MutableVariable remainder(MutableVariable v2) {
		return new MutableVariable(getNumberValue() % v2.getNumberValue());
	}
	
	public MutableVariable remainder(double v2) {
		return new MutableVariable(getNumberValue() % v2);
	}

	public int signum() {
		return (int)Math.signum(getNumberValue());
	}

	public double doubleValue() {
		return getNumberValue();
	}

	public int intValueExact() {
		return (int)dValue;
	}
	
	public int intValue() {
		return (int)dValue;
	}

	public MutableVariable pow(int v2) {
		return new MutableVariable(Math.pow(getNumberValue(), v2));
	}

	public int compareTo(MutableVariable v2) {
		return (getNumberValue() == v2.getNumberValue())? 0: (getNumberValue() > v2.getNumberValue())? 1: -1;
	}

	public MutableVariable abs() {
		return new MutableVariable(Math.abs(getNumberValue()));
	}

	public MutableVariable round() {
		return new MutableVariable(Math.round(getNumberValue()));
	}
	
	public MutableVariable floor() {
		return new MutableVariable(Math.floor(getNumberValue()));
	}

	public MutableVariable ceil() {
		return new MutableVariable(Math.ceil(getNumberValue()));
	}

	public MutableVariable sqrt() {
		return new MutableVariable(Math.sqrt(getNumberValue()));
	}

	public String toString(){
		return getStringValue();
	}
	
}
