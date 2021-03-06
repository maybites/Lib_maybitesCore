/*
 * Copyright (c) 2013 maybites.ch / openframeworks.org
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
 * derived from code by
 * Patrick Kochlik + Dennis Paul
 * Openframeworks
 *
 */

package ch.maybites.tools.math.la;

import ch.maybites.tools.Const;

/**
 * 
 * @author Martin Fr�hlich
 *
 * This class is heavily indebted by the openframeworks Quaternion class and code from
 * Patrick Kochlik + Dennis Paul. And others...
 * 
 */
public class Quaternionf {

	private float[] v_;

	/**
	 * Construct an empty Quaternion 
	 */
	public Quaternionf() {
		reset();
	}

	/**
	 * Construct a Quaternion with the specified element values.
	 * 
	 * @param args  Array of 4 imaginary elements: x, y, z, w
	 */
	public Quaternionf(float[] args) {
		reset();
		for(int i = 0; i < 4; i++)
			v_[i] = args[i];
	}

	/**
	 * Construct a Quaternion with the specified euler angles.
	 * @param bank
	 * @param heading
	 * @param attitude
	 */
	public Quaternionf(float bank, float heading, float attitude) {
		reset();
		setEuler(bank, heading, attitude);
	}

	/**
	 * Construct a Quaternion from a rotation around a vector
	 * @param angle
	 * @param vector
	 */
	public Quaternionf(float angle, Vector3f vector) {
		reset();
		setRotate(angle, vector);
	}

	/**
	 * Construct an empty Quaternion 
	 * 
	 * @param theX  imaginary x value
	 * @param theY  imaginary y value
	 * @param theZ  imaginary w value
	 * @param theW  imaginary z value
	 */
	public Quaternionf(float theX, float theY, float theZ, float theW) {
		reset();
		set(theX, theY, theZ, theW);
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
		v_[3] = 1.0f;
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

	/**
	 * Sets this Quaternion according to the euler angles in degrees
	 * 
	 * calc based on from http://www.euclideanspace.com/maths/geometry/rotations/conversions/eulerToQuaternion/index.htm
	 * 
	 * @param bank (x)
	 * @param heading (y)
	 * @param attitude (z)
	 */
	public void setEuler(float bank, float heading, float attitude) {
		reset();
		float angleX = bank * (float)Const.DEG_TO_RAD;
		float angleY = heading * (float)Const.DEG_TO_RAD;
		float angleZ = attitude * (float)Const.DEG_TO_RAD;
		float c1 = (float)Math.cos(angleY);
		float s1 = (float)Math.sin(angleY);
		float c2 = (float)Math.cos(angleZ);
		float s2 = (float)Math.sin(angleZ);
		float c3 = (float)Math.cos(angleX);
		float s3 = (float)Math.sin(angleX);
	    v_[3] = (float)Math.sqrt(1.0f + c1 * c2 + c1*c3 - s1 * s2 * s3 + c2*c3) / 2.0f;
	    float w4 = (4.0f * v_[3]);
	    v_[0] = (c2 * s3 + c1 * s3 + s1 * s2 * c3) / w4 ;
	    v_[1] = (s1 * c2 + s1 * c3 + c1 * s2 * s3) / w4 ;
	    v_[2] = (-s1 * s3 + c1 * s2 * c3 +s2) / w4 ;
	}

	public void set(float theW, Vector3f theVector3f) {
		v_[0] = theVector3f.x();
		v_[1] = theVector3f.y();
		v_[2] = theVector3f.z();
		v_[3] = theW;
	}

	public void set(float theX, float theY, float theZ, float theW) {
		v_[0] = theX;
		v_[1] = theY;
		v_[2] = theZ;
		v_[3] = theW;
	}

	public void set(Quaternionf theQuaternion) {
		v_[0] = theQuaternion.x();
		v_[1] = theQuaternion.y();
		v_[2] = theQuaternion.z();
		v_[3] = theQuaternion.w();
	}

	/**
	 * Set the elements of the Quat to represent a rotation of angle
	 * (degrees) around the axis (x,y,z)
	 * @param angle
	 * @param x
	 * @param y
	 * @param z
	 */
	public void setRotate( float angle, float x, float y, float z ) {
		angle = angle * (float)Const.DEG_TO_RAD;
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

	/**
	 * Set the elements of the Quat to represent a rotation of angle
	 * (degrees) around the axis vector
	 * 
	 * @param angle
	 * @param vec
	 */
	public void setRotate(float angle, Vector3f vec) {
		setRotate( angle, vec.x(), vec.y(), vec.z() );
	}

	/**
	 * Set the elements of the Quat to represent a rotation of 3 angles
	 * (degrees) around the three axis vectors
	 * 
	 * @param angle1
	 * @param axis1
	 * @param angle2
	 * @param axis2
	 * @param angle3
	 * @param axis3
	 */
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
	       multiply(q3);
	}
	
	/**
	 * This method returns the imaginary values (x, y, z) inside Vector
	 * @return
	 */
	public Vector3f getImaginaryQuaternion(){
		return new Vector3f(x(), y(), z());
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
		v_[1] = theA.w() * theB.y() - theA.x() * theB.z() + theA.y() * theB.w() + theA.z() * theB.x();
		v_[2] = theA.w() * theB.z() + theA.x() * theB.y() - theA.y() * theB.x() + theA.z() * theB.w();
		v_[3] = theA.w() * theB.w() - theA.x() * theB.x() - theA.y() * theB.y() - theA.z() * theB.z();
	}
		
	/**
	 * Multiply this quaternion with the specified quaternion and return this instance
	 * @param theA
	 * @return this instance
	 */
	public Quaternionf multiply(Quaternionf theA) {
		setMultiply(clone(), theA);
		return this;
	}
	
	/**
	 * Returns the result of the multiplication between this and the specified
	 * Quaternion without modifying this instance
	 * @param theA
	 * @return the new instance of a quaternion
	 */
	public Quaternionf multiplyMake(Quaternionf theA) {
		return clone().multiply(theA);
	}

	/**
	 * Scales this quaternion and returns this instance
	 * @param scale
	 * @return
	 */
	public Quaternionf scale(float scale){
		v_[0] *= scale;
		v_[1] *= scale;
		v_[2] *= scale;
		v_[3] *= scale;
		return this;
	}
	
	/**
	 * Conjugates this Quaternion (changes the sign of the x, y and z values)
	 * 
	 * @return this instance
	 */
	public Quaternionf conjugate(){
		v_[0] *= -1;
		v_[1] *= -1;
		v_[2] *= -1;
		return this;
	}
	
	/**
	 * Conjugates this Quaternion (changes the sign of the x, y and z values) and returns 
	 * the result as a new Instance without modifying this instance
	 * 
	 * @return new instance
	 */
	public Quaternionf conjugateMake(){
		return clone().conjugate();
	}
	
	public float length2(){
		return x() * x() + y() * y() + z() * z() + w() * w();
	}
	
	public float length(){
		return (float)Math.sqrt(length2());
	}
	
	public Quaternionf inverse(){
		conjugate().scale(length2());
		return this;
	}
	
	public Quaternionf inverseMake(){
		return clone().inverse();
	}
	
	/**
	 * Returns the Euler angles inside a vector in degrees
	 * 
	 * math based on http://www.euclideanspace.com/maths/geometry/rotations/conversions/quaternionToEuler/index.htm
	 * 
	 * @return vector with euler angles [bank, heading, attitude]
	 */
	public Vector3f getEuler() {
		float heading, attitude, bank;
	    float sqw = w()*w();
	    float sqx = x()*x();
	    float sqy = y()*y();
	    float sqz = z()*z();
	    // if normalised is one, otherwise is correction factor
	    float unit = sqx + sqy + sqz + sqw; 
	    float test = x()*y() + z()*w();
		if (test > 0.499*unit) { // singularity at north pole
			heading = 2.0f * (float)Math.atan2(x(),w());
			attitude = (float)Const.HALF_PI;
			bank = 0;
		}else if (test < -0.499*unit) { // singularity at south pole
			heading = -2.0f * (float)Math.atan2(x(),w());
			attitude = -(float)Const.HALF_PI;
			bank = 0;
		}else{
		    heading = (float)Math.atan2(2.0f*y()*w()-2.0f*x()*z() , sqx - sqy - sqz + sqw);
			attitude = (float)Math.asin(2*test/unit);
			bank = (float)Math.atan2(2.0*x()*w()-2.0*y()*z() , -sqx + sqy - sqz + sqw);
		}
		return new Vector3f(bank * (float)Const.RAD_TO_DEG, heading * (float)Const.RAD_TO_DEG, attitude * (float)Const.RAD_TO_DEG);
	}
	
	/**
	 * get an exact copy of this quaternion
	 */
	public Quaternionf clone(){
		return new Quaternionf(x(), y(), z(), w());
	}

	 /**
	  * Create a string representation of this vector.
	  * 
	  * @return  String representing this vector.
	  */
	 public String toString()
	 {
		 return ("Quaternionf: [x=" + 
				 v_[0] + ", y=" + v_[1] + ", z=" + v_[2] + ", w=" + v_[3] + "]");
	 }

	public static void main(String[] args) {
        /* multiplying matrices */
	
		Quaternionf q = new Quaternionf(0, 0, 30);
		
		System.out.println();

	}

}
