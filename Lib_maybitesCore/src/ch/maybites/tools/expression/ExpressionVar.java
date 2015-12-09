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

import java.util.ArrayList;

import ch.maybites.tools.expression.Expression.ExpressionException;
import ch.maybites.tools.expression.RunTimeEnvironment.Operation;

public class ExpressionVar {
	public static final ExpressionVar ONE = new ExpressionVar(1);
	public static final ExpressionVar ZERO = new ExpressionVar(0);
	
	private double dValue;
	private String sValue;
	
	private Operation operation; 
	private ArrayList<ExpressionVar> params;
	
	public boolean isNumber = false;
	
	/**
	 * Creates an ExpressionVar with the nummeric value of 
	 * @param value
	 */
	public ExpressionVar(double value){
		this.dValue = value;
		isNumber = true;
	}
	
	/**
	 * Creates an ExpressionVar with the String value of 
	 * @param value
	 */
	public ExpressionVar(String value){
		setValue(value);
	}

	/**
	 * Creates an ExpressionVar with an Evaluation Tree 
	 * @param value
	 */
	protected ExpressionVar(Operation op, ArrayList<ExpressionVar> p){
		operation = op;
		params = p;
		this.dValue = 0;
		isNumber = true;
	}

	/**
	 * Set this instance with the values of the passed ExpressionVar
	 * @param val
	 * @return this instance
	 */
	public ExpressionVar set(ExpressionVar val){
		if(val.isNumber){
			this.dValue = val.getNumberValue();
			this.isNumber = true;
		} else {
			this.sValue = val.getStringValue();
			this.isNumber = false;
		}
		return this;
	}

	/**
	 * Mutate this instance and set it with a String Value 
	 * @param val
	 * @return this instance
	 */
	public ExpressionVar setValue(String val){
		try{
			this.dValue = Double.parseDouble(val);
			isNumber = true;
		} catch (NumberFormatException e){
			this.sValue = val;
			isNumber = false;
		}
		return this;
	}
	
	/**
	 * Mutate this instance and set it with a double Value 
	 * @param val
	 * @return this instance
	 */
	public ExpressionVar setValue(double val){
		this.dValue = val;
		isNumber = true;
		return this;
	}
	
	/**
	 * Get the numeric value of this instance.
	 * @return the numeric value. If it is a String var, it returns 0
	 */
	public double getNumberValue(){
		if(isNumber)
			return dValue;
		return 0;
	}
	
	/**
	 * Get the String value of this instance
	 * @return the String. If it is a numeric var, the number returned as a String
	 */
	public String getStringValue(){
		if(!isNumber)
			return sValue;
		return ""+ dValue;
	}
	
	/**
	 * Evaluates the Expression Tree (if there is one).
	 * If this instance is returned by the parse() function of 
	 * Expression, this function should be called before you
	 * attempt do get its value.
	 * @return this instance
	 */
	public ExpressionVar eval() throws ExpressionException{
		if(operation != null){
			for(ExpressionVar exp: params)
				exp.eval();
			set(operation.eval(params));
		}
		return this;
	}
	
	/**
	 * Adds the provided Var to this Var.
	 * If one of them is a String var, the returned var is a String var too.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar add(ExpressionVar v2) {
		if(isNumber && v2.isNumber)
			return new ExpressionVar(this.dValue + v2.getNumberValue());
		else
			return new ExpressionVar(getStringValue() + v2.getStringValue());			
	}

	/**
	 * Substracts the provided Var from this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar subtract(ExpressionVar v2) {
		return new ExpressionVar(getNumberValue() - v2.getNumberValue());
	}

	/**
	 * Substracts the provided value from this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar subtract(double v2) {
		return new ExpressionVar(getNumberValue() - v2);
	}


	/**
	 * Multiplies the provided Var with this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar multiply(ExpressionVar v2) {
		return new ExpressionVar(getNumberValue() * v2.getNumberValue());
	}
	
	/**
	 * Multiplies the provided value with this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar multiply(int v2) {
		return new ExpressionVar(getNumberValue() * (double)v2);
	}
	
	/**
	 * Multiplies the provided value with this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar multiply(double v2) {
		return new ExpressionVar(getNumberValue() * v2);
	}

	/**
	 * Divides the provided Var from this Var.
	 * @param v2 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar divide(ExpressionVar v2) {
		return new ExpressionVar(getNumberValue() / v2.getNumberValue());
	}
	
	/**
	 * gets the modulo from this Var.
	 * @param v2 modulo
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar remainder(ExpressionVar v2) {
		return new ExpressionVar(getNumberValue() % v2.getNumberValue());
	}
	
	/**
	 * gets the modulo from this Var.
	 * @param v2 modulo
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar remainder(double v2) {
		return new ExpressionVar(getNumberValue() % v2);
	}

	/**
	 * gets the sign of the value of this instance
	 * -1 if it is below zero
	 * 0 if it is zero
	 * +1 if it is above zero
	 * @return the signum
	 */
	protected int signum() {
		return (int)Math.signum(getNumberValue());
	}

	/**
	 * Get the numeric value of this instance
	 * @return double value
	 */
	public double doubleValue() {
		return getNumberValue();
	}

	/**
	 * Get the numeric value of this instance
	 * @return int value
	 */
	public int intValueExact() {
		return (int)dValue;
	}
	
	/**
	 * Get the numeric value of this instance
	 * @return int value
	 */
	public int intValue() {
		return (int)dValue;
	}

	/**
	 * Calculates this instance raised to the power of the argument.
	 * @param v2
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar pow(ExpressionVar v2) {
		return new ExpressionVar(Math.pow(getNumberValue(), v2.getNumberValue()));
	}

	/**
	 * Compares the numeric value of this instance with the argument.
	 * -1 if this instance is < than the argument
	 * 0 if both are the same
	 * +1 if this instance is > than the argument
	 * @param v2
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected int compareTo(ExpressionVar v2) {
		return (getNumberValue() == v2.getNumberValue())? 0: (getNumberValue() > v2.getNumberValue())? 1: -1;
	}

	/**
	 * Gets the absolute value of this instance
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar abs() {
		return new ExpressionVar(Math.abs(getNumberValue()));
	}

	/**
	 * Returns the closest long to the argument, with ties rounding to positive infinity.
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar round() {
		return new ExpressionVar(Math.round(getNumberValue()));
	}
	
	/**
	 * Returns the largest (closest to positive infinity) double 
	 * value that is less than or equal to the argument and is 
	 * equal to a mathematical integer. 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar floor() {
		return new ExpressionVar(Math.floor(getNumberValue()));
	}

	/**
	 * Returns the smallest (closest to negative infinity) double value 
	 * that is greater than or equal to the argument and is equal 
	 * to a mathematical integer.
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar ceil() {
		return new ExpressionVar(Math.ceil(getNumberValue()));
	}

	/**
	 * Returns the correctly rounded positive square root of a double value. 
	 * @return a new instance of an ExpressionVar containing the result
	 */
	protected ExpressionVar sqrt() {
		return new ExpressionVar(Math.sqrt(getNumberValue()));
	}

	/**
	 * Returns a String representation of this instance
	 */
	public String toString(){
		return getStringValue();
	}
	
}
