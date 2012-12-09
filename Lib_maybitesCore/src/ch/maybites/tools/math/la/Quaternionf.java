/*
 * Mathematik
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * {@link http://www.gnu.org/licenses/lgpl.html}
 *
 * Copyright (c) 2012 Martin Fršhlich
 * 
 * derived from code by
 * Patrick Kochlik + Dennis Paul
 *
 */


package ch.maybites.tools.math.la;


public class Quaternionf {

	private float[] v_;


	/**
	 * Construct an empty Quaternion 
	 * 
	 * @param m  Array of 4 matrix elements, x, y, z, w
	 */
	public Quaternionf() {
		reset();
	}


	/**
	 * Construct a Quaternion with the specified element values.
	 * 
	 * @param m  Array of 4 imaginary elements: x, y, z, w
	 */
	public Quaternionf(float[] args) {
		reset();
		for(int i = 0; i < 4; i++)
			v_[i] = args[i];
	}

	/**
	 * Construct an empty Quaternion 
	 * 
	 * @param x  imaginary x value
	 * @param y  imaginary y value
	 * @param z  imaginary w value
	 * @param w  imaginary z value
	 */
	public Quaternionf(float theX, float theY, float theZ, float theW) {
		reset();
		v_[0] = theX;
		v_[1] = theY;
		v_[2] = theZ;
		v_[3] = theW;
	}

	public float magnitude(){
		return (float)Math.sqrt(v_[0] * v_[0] + v_[1] * v_[1] + v_[2] * v_[2] + v_[3] * v_[3]);
	}

	public void normalize(){
		float magnitude = magnitude();
		v_[0] /= magnitude;
		v_[1] /= magnitude;
		v_[2] /= magnitude;
		v_[3] /= magnitude;
	}

	public void reset() {
		v_ = new float[4];
		for (int i = 0; i < 4; i++)
			v_[i] = 0.0f;
	}

	public void setX(float x){
		v_[0] = x;
	}

	public void setY(float y){
		v_[1] = y;
	}

	public void setZ(float z){
		v_[2] = z;
	}

	public void setW(float w){
		v_[3] = w;
	}

	public void set(float theW, Vector3f theVector3f) {
		v_[0] = theVector3f.x();
		v_[1] = theVector3f.y();
		v_[2] = theVector3f.z();
		v_[3] = theW;
	}


	public void set(Quaternionf theQuaternion) {
		v_[0] = theQuaternion.x();
		v_[1] = theQuaternion.y();
		v_[2] = theQuaternion.z();
		v_[3] = theQuaternion.w();
	}

	public float x(){
		return v_[0];
	}

	public float y(){
		return v_[1];
	}

	public float z(){
		return v_[2];
	}

	public float w(){
		return v_[3];
	}

	/**
	 * Set the elements of the Quat to represent a rotation of angle
	 * (degrees) around the axis (x,y,z)
	 * 
	 */
	public void setRotate( float angle, float x, float y, float z ) {
		angle = angle/180.0f * 3.14159f;
		
		float epsilon = 0.0000001f;

		float length = (float)Math.sqrt( x * x + y * y + z * z );
		if (length < epsilon) {
			// ~zero length axis, so reset rotation to zero.
			reset();
			return;
		}

		float inversenorm  = 1.0f / length;
		float coshalfangle = (float)Math.cos( 0.5f * angle );
		float sinhalfangle = (float)Math.sin( 0.5f * angle );

		setX(x * sinhalfangle * inversenorm);
		setY(y * sinhalfangle * inversenorm);
		setZ(z * sinhalfangle * inversenorm);
		setW(coshalfangle);
	}

	public void setRotate(float angle, Vector3f vec) {
		setRotate( angle, vec.x(), vec.y(), vec.z() );
	}

	public void setRotate(float angle1, Vector3f axis1,
	                          float angle2, Vector3f axis2,
	                          float angle3, Vector3f axis3) {
	       Quaternionf q1 = new Quaternionf(); 
	       q1.setRotate(angle1,axis1);
	       Quaternionf q2 = new Quaternionf(); 
	       q2.setRotate(angle2,axis2);
	       Quaternionf q3 = new Quaternionf(); 
	       q3.setRotate(angle3,axis3);

	       setMultiply(q1, q2);
	       setMultiply(this, q3);
	}

	/**
	 * get the Quaternion values in an array 
	 * 
	 * @return 	float[] array
	 */
	public float[] get(){
		return v_;
	}
	
	/**
	 * Set this quaternion as a result of the multiplication of the two specified quaternions
	 * @param theA
	 * @param theB
	 */
	public void setMultiply(Quaternionf theA, Quaternionf theB) {
		v_[0] = theA.w() * theB.x() + theA.x() * theB.w() + theA.y() * theB.z() - theA.z() * theB.y();
		v_[1] = theA.w() * theB.y() + theA.y() * theB.w() + theA.z() * theB.x() - theA.x() * theB.z();
		v_[2] = theA.w() * theB.z() + theA.z() * theB.w() + theA.x() * theB.y() - theA.y() * theB.x();
		v_[3] = theA.w() * theB.w() - theA.x() * theB.x() - theA.y() * theB.y() - theA.z() * theB.z();
	}
	
	/**
	 * Multiply this quaternion with the specified quaternion
	 * @param theA
	 */
	public void multiply(Quaternionf theA) {
		setMultiply(this, theA);
	}
	
	/**
	 * Multiply this quaternion with the specified quaternion and return this instance
	 * @param theA
	 * @return this instance
	 */
	public Quaternionf getMultiplied(Quaternionf theA) {
		setMultiply(this, theA);
		return this;
	}
	
	/**
	 * Returns the result of the multiplication between this and the specified
	 * Quaternion without modifying this instance
	 * @param theA
	 * @return the new instance of a quaternion
	 */
	public Quaternionf makeMultiply(Quaternionf theA) {
		return clone().getMultiplied(theA);
	}

	/**
	 * get an exact copy of this quaternion
	 */
	public Quaternionf clone(){
		return new Quaternionf(x(), y(), z(), w());
	}
}
