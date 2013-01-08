/*
 * Copyright (c) 2013 maybites.ch
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal 
 *  in the Software without restriction, including without limitation the rights 
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
 *  copies of the Software, and to permit persons to whom the Software is furnished
 *  to do so, subject to the following conditions:
 *  
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *  
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 *  WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN 
 *  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 *  
 *  Portion of the code based on ofNode Library from openFrameworks.org
 *  
 */

package ch.maybites.tools.threedee;

import ch.maybites.tools.math.la.Matrix4x4f;
import ch.maybites.tools.math.la.Quaternionf;
import ch.maybites.tools.math.la.Vector3f;

/**
 * 
 * @author Martin Fršhlich
 *
 * This class is heavily indebted by the openframeworks ofNode class. It has some minor improvements
 * in regards to performance
 *  
 */
public class Node {
	
	private Vector3f position;
	private Quaternionf orientation;
	private Vector3f scale;
	
	private Vector3f[] axis;
	
	private Matrix4x4f localTransfromMatrix;
	
	private Matrix4x4f worldTransfromMatrix;
	private Matrix4x4f inverseWorldTransfromMatrix;
	
	protected Node parent;
	
	protected boolean wasTransformed = false;
	
	public Node(){
		axis = new Vector3f[3];
		position = new Vector3f(.0f, .0f, .0f);
		orientation = new Quaternionf(.0f, .0f, .0f);
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
		createMatrix();
	}

	/**
	 * Uses the provided instance as the local position. 
	 * @param pos sets its as position
	 */
	public void setPosition(Vector3f pos){
		position = pos;
		createMatrix();
	}
	
	/**
	 * Sets the local orientation in euler angles (in degrees)
	 * @param euler
	 */
	public void setOrientation(Vector3f euler){
		Quaternionf q = new Quaternionf(euler.x(), euler.y(), euler.z());
		setOrientation(q);
	}
	
	/**
	 * Uses the provided Instance of a Quaternion as the local orientation
	 * @param _orientation
	 */
	public void setOrientation(Quaternionf _orientation){
		orientation = _orientation;
		createMatrix();
	}

	public void setScale(float _scale){
		scale = new Vector3f(_scale, _scale, _scale);
		createMatrix();
	}
	
	/**
	 * Uses the provided Instance of the Vector as the local scale factors for each axis
	 * @param _scale
	 */
	public void setScale(Vector3f _scale){
		scale = _scale;
		createMatrix();
	}

	/**
	 * stets this nodes parent node
	 * @param _parent
	 */
	public void setParent(Node _parent){
		parent = _parent;
	}

	public Vector3f getXAxis(){
		return axis[0];
	}

	public Vector3f getYAxis() {
		return axis[1];
	}

	public Vector3f getZAxis() {
		return axis[2];
	}

	public Vector3f getSideDir() {
		return getXAxis();
	}

	public Vector3f getLookAtDir() {
		return getZAxis().scaleMake(-1f);
	}

	public Vector3f getUpDir() {
		return getYAxis();
	}

	public float getPitch() {
		return getOrientationEuler().x();
	}

	public float getHeading(){
		return getOrientationEuler().y();
	}

	public float getRoll(){
		return getOrientationEuler().z();
	}

	public Quaternionf getOrientationQuat(){
		return orientation;
	}

	public Vector3f getOrientationEuler(){
	    return orientation.getEuler();
	}

	public Vector3f getScale(){
		return scale;
	}

	/** 
	 * Returns this node's parent node
	 * 
	 * @return parent node
	 */
	public Node getParent(){
		return parent;
	}
	
	/**
	 * clears this node's parent
	 */
	public void clearParent(){
		parent = null;
	}

	/**
	 * moves this node in direction to the provided local axis values
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(float x, float y, float z) {
		move(new Vector3f(x, y, z));
	}

	/**
	 * moves this node in direction to the provided local vector
	 * 
	 * @param offset
	 */
	public void move(Vector3f offset) {
		position.add(offset);
		localTransfromMatrix.setTranslation(position);
		setTransformed();
	}

	public void rotate(Quaternionf rotation){
		orientation.multiply(rotation);
		createMatrix();
	}

	public void rotate(float degrees, Vector3f v) {
		rotate(new Quaternionf(degrees, v));
	}

	public void truck(float amount) {
		move(getXAxis().scaleMake(amount));
	}

	public void boom(float amount) {
		move(getYAxis().scaleMake(amount));
	}

	public void dolly(float amount) {
		move(getZAxis().scaleMake(amount));
	}

	public void tilt(float degrees) {
		rotate(degrees, getXAxis());
	}

	public void pan(float degrees) {
		rotate(degrees, getYAxis());
	}

	public void roll(float degrees) {
		rotate(degrees, getZAxis());
	}

	
	
	public Matrix4x4f getLocalTransformationMatrix(){
		return localTransfromMatrix;
	}
	
	public Matrix4x4f getWorldTransformationMatrix(){
		makeGlobalTransformationMatrix();
		return worldTransfromMatrix;
	}
	
	public Matrix4x4f getInvWorldTransformationMatrix(){
		makeGlobalTransformationMatrix();
		return inverseWorldTransfromMatrix;
	}

	public Vector3f getWorldPosition(){
		return getWorldTransformationMatrix().getTranslation();
	}

	//----------------------------------------
	public Quaternionf getWorldOrientation()  {
		return getWorldTransformationMatrix().getRotation();
	}

	private void makeGlobalTransformationMatrix(){
		if(wasTransformed()){
			if(parent != null)
				worldTransfromMatrix = localTransfromMatrix.multiplyMake(parent.getWorldTransformationMatrix());
			else
				worldTransfromMatrix = localTransfromMatrix.clone();
			
			inverseWorldTransfromMatrix = worldTransfromMatrix.invertMake();
			
			resetTransformed();
		}
	}

	/**
	 * checks if a transformation was applied to this (and its parents) node
	 * 
	 * @return true if there was a transformation
	 */
	protected boolean wasTransformed(){
		return (wasTransformed || (parent != null && parent.wasTransformed()))? true: false;
	}
	
	private void setTransformed(){
		wasTransformed = true;
	}
	
	private void resetTransformed(){
		wasTransformed = false;
	}
	
	private void createMatrix() {
		//if(isMatrixDirty) {
		//	isMatrixDirty = false;
		localTransfromMatrix = new Matrix4x4f();
		localTransfromMatrix.scale(scale);
		localTransfromMatrix.rotate(orientation);
		localTransfromMatrix.translate(position);
				
		if(scale.x()>0){
			axis[0] = localTransfromMatrix.getRowAsVector(0).scale(1.0f / scale.x());
		}
		if(scale.y()>0){
			axis[1] = localTransfromMatrix.getRowAsVector(1).scale(1.0f / scale.y());
		}
		if(scale.z()>0){
			axis[2] = localTransfromMatrix.getRowAsVector(2).scale(1.0f / scale.z());
		}

		setTransformed();
	}

}
