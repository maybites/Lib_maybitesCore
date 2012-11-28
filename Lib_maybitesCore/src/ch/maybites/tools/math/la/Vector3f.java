package ch.maybites.tools.math.la;

import java.io.Serializable;
import java.util.Random;

/*
 * This code is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public 
 * License as published by the Free Software Foundation; either 
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This code is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License along with this program; if not, write to the Free 
 * Software Foundation, Inc., 59 Temple Place - Suite 330, Boston, 
 * MA  02111-1307, USA.
 */

/**
 * Implementation of a 3-element vector suited for use with
 * Matrix4x4
 */   
public class Vector3f
{

	
	  protected float[] v_;

	  /**
	   * threshold to maintain the minimal difference between two vectors before
	   * they are almost the same
	   */
	  protected static final float ALMOST_THRESHOLD = 0.001f;

	  /**
	   * Utility for random
	   */
	  protected static final Random generator = new Random();

	  protected void initialize()
	  {
	    v_ = new float[3];
	    for (int i = 0; i < 3; i++)
	      v_[i] = 0.0f;
	  }

	/**
	 * Create a default 3-element vector (all elements set to 0.0).
	 */
	public Vector3f()
	{
		initialize();
	}

	/**
	 * Create a 3-element vector with the specified values.
	 * 
	 * @param v1  1st element.
	 * @param v2  2nd element.
	 * @param v3  3rd element.
	 */
	public Vector3f (float v1, float v2, float v3)
	{
	    initialize();
	    set (v1, v2, v3);
	}

	/**
	 * Create a 3-element vector with the specified values.
	 * 
	 * @param args  3 element float array.
	 */
	public Vector3f (float[] args)
	{
	    initialize();
	    set (args[0], args[1], args[2]);
	}

	/**
	 * Construct a 3-element vector as a copy of the specified Vector.
	 * 
	 * @param Vector4d
	 */
	public Vector3f (Vector3f vector)
	{
	    initialize();
		set(vector);
	}

	 /**
	   * Set the elements of this vector.
	   * 
	   * @param v1  1st element.
	   * @param v2  2nd element.
	   * @param v3  3rd element.
	   */
	  public void set (float v1, float v2, float v3)
	  {
	    v_[0] = v1;
	    v_[1] = v2;
	    v_[2] = v3;
	  }


	/**
	 * Set the elements of this vector according to the specified vector.
	 * 
	 * @param vector  Vector to copy.
	 */
	public void set (Vector3f vector)
	{
		for (int i = 0; i < 3; i++)
			v_[i] = vector.v_[i];
	}


	  /**
	   * Return the i'th element of this point.
	   * 
	   * @param i  Index of element to get (first is 0).
	   * @return   i'th element of this point.
	   */
	  public float getElement (int i)
	  {
	    return v_[i];
	  }

	  /**
	   * Set the i'th element of this vector.
	   * 
	   * @param i  Index of element to set (first is 0).
	   * @param    Value to set.
	   */
	  public void setElement (int i, float value)
	  {
	    v_[i] = value;
	  }
	  
	  public final float x(){
		  return v_[0];
	  }
	  
	  public final float y(){
		  return v_[1];
	  }
	 
	  public final float z(){
		  return v_[2];
	  }
	  
	  public final void setX(float x){
		  v_[0] = x;
	  }
	  
	  public final void setY(float y){
		  v_[1] = y;
	  }
	 
	  public final void setZ(float z){
		  v_[2] = z;
	  }

	  public final float[] toArray() {
	      return v_;
	  }

	  public final boolean isNaN() {
	      if (Float.isNaN(v_[0]) || Float.isNaN(v_[1]) || Float.isNaN(v_[2])) {
	          return true;
	      } else {
	          return false;
	      }
	  }


	/**
	 * Returns a new Vector with the result of the addition of the 
	 * specified Vectors with this Vector. This instance will NOT be
	 * modified
	 * 
	 * Usage: Vector3f newVec = thisVector.add(otherVector)
	 * 
	 * @param theVector	the Vector to be added
	 * @return the addition
	 */
	public final Vector3f add(Vector3f theVector) {
		return new Vector3f(v_[0] + theVector.x(), v_[1] + theVector.y(), v_[2] + theVector.z());
	}

	/**
	 * Replace this Vector with the result of the addition of the two 
	 * specified Vectors. This method modifies this instance.
	 * 
	 * Usage 1: add the otherVector from thisVector and apply the result to thisVector:
	 * 	thisVector.sub(thisVector, otherVector);
	 * 
	 * Usage 2: add the otherVector from firstVector and apply the result to thisVector:
	 * 	thisVector.sub(firstVector, otherVector);
	 * 
	 * @param theVectorA	of class Vector3f
	 * @param theVectorB	of class Vector3f
	 */
	public final void add(Vector3f theVectorA, Vector3f theVectorB) {
		v_[0] = theVectorA.v_[0] + theVectorB.v_[0];
		v_[1] = theVectorA.v_[1] + theVectorB.v_[1];
		v_[2] = theVectorA.v_[2] + theVectorB.v_[2];
	}

	/**
	 * Replace this Vector with the result of the subtraction of the two 
	 * specified Vectors). This method modifies this instance.
	 * 
	 * Usage 1: substract the otherVector from thisVector and apply the result to thisVector:
	 * 	thisVector.sub(thisVector, otherVector);
	 * 
	 * Usage 2: substract the otherVector from firstVector and apply the result to thisVector:
	 * 	thisVector.sub(firstVector, otherVector);
	 * 
	 * @param theVectorA	of class Vector3f
	 * @param theVectorB	of class Vector3f
	 */
	public final void sub(Vector3f theVectorA, Vector3f theVectorB) {
		v_[0] = theVectorA.v_[0] - theVectorB.v_[0];
		v_[1] = theVectorA.v_[1] - theVectorB.v_[1];
		v_[2] = theVectorA.v_[2] - theVectorB.v_[2];
	}


	/**
	 * Returns a new Vector with the result of the subtraction of the 
	 * specified Vectors from this Vector. This instance will NOT be
	 * modified
	 * 
	 * Usage: Vector3f newVec = thisVector.sub(otherVector)
	 * 
	 * @param theVector	the Vector to be substracted
	 * @return the subtraction
	 */
	public final Vector3f sub(Vector3f theVector) {
		return new Vector3f(v_[0] - theVector.x(), v_[1] - theVector.y(), v_[2] - theVector.z());
	}


	/**
	 * Use this method to negate a vector. The result of the negation is vector
	 * with the same magnitude but opposite direction. Mathematically the
	 * negation is the additive inverse of the vector. The sum of a value and
	 * its additive inerse is always zero.
	 *
	 * @shortdesc Use this method to negate a vector.
	 * @related scale ( )
	 */
	public final void negate() {
		scale( -1);
	}


	/**
	 * Use this method to scale a vector. To scale a vector each of its
	 * coordinates is multiplied with the given scalar. The result is a vector
	 * that is parallel with its origin, with a different length and possibly
	 * opposite direction.<br>
	 * You can also scale a vector with another vector, in this case each coord
	 * of the vector is multiplied with related coord of the given vector.<br>
	 * Another possibillity is to set and scale the vector, this means the
	 * vector is set to the given vector multiplied with the given scalar.
	 *
	 * @param theScalar
	 *            float or int: the value the vector is scaled with
	 * @related divide ( )
	 * @related negate ( )
	 */
	public final void scale(final float theScalar) {
		v_[0] *= theScalar;
		v_[1] *= theScalar;
		v_[2] *= theScalar;
	}


	/**
	 *
	 * @param theVector
	 *            Vector3f: vector with the value each coord is scaled with
	 */
	public final void scale(final Vector3f theVector) {
		v_[0] *= theVector.v_[0];
		v_[1] *= theVector.v_[1];
		v_[2] *= theVector.v_[2];
	}


	/**
	 *
	 * @param theX float
	 * @param theY float
	 * @param theZ float
	 */
	public final void scale(float theX, float theY, float theZ) {
		v_[0] *= theX;
		v_[1] *= theY;
		v_[2] *= theZ;
	}


	/**
	 * @param theScalar
	 *            float or int: value the given vector is scaled with
	 * @param theVector
	 *            Vector3f: vector the vector is set to
	 */
	public final void scale(final float theScalar, final Vector3f theVector) {
		v_[0] = theScalar * theVector.v_[0];
		v_[1] = theScalar * theVector.v_[1];
		v_[2] = theScalar * theVector.v_[2];
	}

	/**
	 * Dividing is nearly the the same as scaling, except
	 *
	 * @param theDivisor
	 */
	public final void divide(final float theDivisor) {
		scale(1f / theDivisor);
	}


	public final void divide(final Vector3f theVector) {
		v_[0] /= theVector.v_[0];
		v_[1] /= theVector.v_[1];
		v_[2] /= theVector.v_[2];
	}

	public final float lengthSquared() {
		return v_[0] * v_[0] + v_[1] * v_[1] + v_[2] * v_[2];
	}

	/**
	 * Use this method to calculate the length of a vector, the length of a
	 * vector is also known as its magnitude. Vectors have a magnitude and a
	 * direction. These values are not explicitly expressed in the vector so
	 * they have to be computed.
	 *
	 * @return float: the length of the vector
	 * @shortdesc Calculates the length of the vector.
	 */
	public final float length() {
		return (float) Math.sqrt(lengthSquared());
	}

	/**
	 * See length()
	 *
	 * @return float: the length of the vector
	 * @shortdesc Calculates the length of the vector.
	 */
	public final float magnitude() {
		return length();
	}

	/**
	 * Replace this Vector with the result of the crossproduct of the two 
	 * specified Vectors. This method modifies this instance.
	 * 
	 * Usage 1: cross the otherVector with thisVector and apply the result to thisVector:
	 * 	thisVector.cross(thisVector, otherVector);
	 * 
	 * Usage 2: cross the otherVector with firstVector and apply the result to thisVector:
	 * 	thisVector.cross(firstVector, otherVector);
	 * 
	 * @param theVectorA
	 * @param theVectorB
	 */
	public final void cross(final Vector3f theVectorA, final Vector3f theVectorB) {
		set(theVectorA.cross(theVectorB));
	}

	/**
	 * Returns a new Vector with the result of the addition of the 
	 * specified Vectors with this Vector. This instance will NOT be
	 * modified
	 * 
	 * Usage: Vector3f newVec = thisVector.add(otherVector)
	 * 
	 * @param theVector	the Vector to be added
	 */

	/**
	 * Returns the cross product of two vectors. The cross product returns a
	 * vector standing vertical on the two vectors. This instance will NOT be
	 * modified
	 * 
	 * Usage: Vector3f newVec = thisVector.cross(otherVector)
	 * 
	 * @param theVector	the other vector
	 * @return the cross product
	 */
	public Vector3f cross(final Vector3f theVector) {
		return new Vector3f(v_[1] * theVector.v_[2] - v_[2] * theVector.v_[1],
				v_[2] * theVector.v_[0] - v_[0] * theVector.v_[2],
				v_[0] * theVector.v_[1] - v_[1] * theVector.v_[0]);
	}

	/**
	 * Returns the dot product of two vectors. The dot product is the cosinus of
	 * the angle between two vectors
	 *
	 * @param theVector,
	 *            the other vector
	 * @return float, dot product of two vectors
	 */
	public final float dot(Vector3f theVector) {
		return v_[0] * theVector.v_[0] + v_[1] * theVector.v_[1] + v_[2] * theVector.v_[2];
	}


	/**
	 * Sets the vector to the given one and norms it to the length of 1
	 *
	 */
	public final void normalize(Vector3f theVector) {
		set(theVector);
		normalize();
	}


	/**
	 * Norms the vector to the length of 1
	 *
	 */
	public final void normalize() {
		float inverseMag = 1.0f / magnitude();
		v_[0] *= inverseMag;
		v_[1] *= inverseMag;
		v_[2] *= inverseMag;
	}



	/**
	 * Interpolates between this vector and the given vector by a given blend
	 * value. The blend value has to be between 0 and 1. A blend value 0 would
	 * change nothing, a blend value 1 would set this vector to the given one.
	 *
	 * @param blend
	 *            float, blend value for interpolation
	 * @param i_vector
	 *            Vector3f, other vector for interpolation
	 */
	public void interpolate(final float blend, final Vector3f i_vector) {
		v_[0] = v_[0] + blend * (i_vector.v_[0] - v_[0]);
		v_[1] = v_[1] + blend * (i_vector.v_[1] - v_[1]);
		v_[2] = v_[2] + blend * (i_vector.v_[2] - v_[2]);
	}


	/**
	 * Sets a position randomly distributed inside a sphere of unit radius
	 * centered at the origin. Orientation will be random and length will range
	 * between 0 and 1
	 */
	public void randomize() {
		v_[0] = generator.nextFloat() * 2.0F - 1.0F;
		v_[1] = generator.nextFloat() * 2.0F - 1.0F;
		v_[2] = generator.nextFloat() * 2.0F - 1.0F;
		normalize();
	}


	public final float angle(Vector3f theVector) {
		float d = dot(theVector) / (length() * theVector.length());
		/** @todo check these lines. */
		if (d < -1.0f) {
			d = -1.0f;
		}
		if (d > 1.0f) {
			d = 1.0f;
		}
		return (float) Math.acos(d);
	}


	public final float distanceSquared(Vector3f theVector) {
		float dx = v_[0] - theVector.v_[0];
		float dy = v_[1] - theVector.v_[1];
		float dz = v_[2] - theVector.v_[2];
		return dx * dx + dy * dy + dz * dz;
	}


	public final float distance(Vector3f theVector) {
		return (float) Math.sqrt(distanceSquared(theVector));
	}

	public final void min(Vector3f theMin) {
		if (v_[0] < theMin.v_[0]) {
			v_[0] = theMin.v_[0];
		}
		if (v_[1] < theMin.v_[1]) {
			v_[1] = theMin.v_[1];
		}
		if (v_[2] < theMin.v_[2]) {
			v_[2] = theMin.v_[2];
		}
	}


	public final void min(float theX, float theY, float theZ) {
		if (v_[0] < theX) {
			v_[0] = theX;
		}
		if (v_[1] < theY) {
			v_[1] = theY;
		}
		if (v_[2] < theZ) {
			v_[2] = theZ;
		}
	}


	public final void max(Vector3f theMax) {
		if (v_[0] > theMax.v_[0]) {
			v_[0] = theMax.v_[0];
		}
		if (v_[1] > theMax.v_[1]) {
			v_[1] = theMax.v_[1];
		}
		if (v_[2] > theMax.v_[2]) {
			v_[2] = theMax.v_[2];
		}
	}


	public final void max(float theX, float theY, float theZ) {
		if (v_[0] > theX) {
			v_[0] = theX;
		}
		if (v_[1] > theY) {
			v_[1] = theY;
		}
		if (v_[2] > theZ) {
			v_[2] = theZ;
		}
	}

	public final boolean equals(final Vector3f theVector) {
		if (v_[0] == theVector.v_[0] && v_[1] == theVector.v_[1] && v_[2] == theVector.v_[2]) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Check if this 3-element vector equals the specified object.
	 * 
	 * @return  TRue if the two equals, false otherwise.
	 */
	 public final boolean equals(final Object theVector) {
		 if (! (theVector instanceof Vector3f)) {
			 return false;
		 }

		 return equals( (Vector3f) theVector);
	 }

	 public final boolean almost(final Vector3f theVector) {
		 if (Math.abs(v_[0] - theVector.v_[0]) < ALMOST_THRESHOLD
				 && Math.abs(v_[1] - theVector.v_[1]) < ALMOST_THRESHOLD
				 && Math.abs(v_[2] - theVector.v_[2]) < ALMOST_THRESHOLD) {
			 return true;
		 } else {
			 return false;
		 }
	 }


	 /**
	  * rotate the vector around its Z Axis
	  */
	 public final void rotZ(double r){
		 float x1 = v_[0] * (float)Math.cos(r) + v_[1] * (float)Math.sin(r);
		 float y1 = v_[1] * (float)Math.cos(r) - v_[0] * (float)Math.sin(r);
		 v_[0] = x1;
		 v_[1] = y1;
	 }

	 /**
	  * rotate the vector around its Y Axis
	  */
	 public final void rotY(double r){
		 float x1 = v_[0] * (float)Math.cos(r) - v_[2] * (float)Math.sin(r);
		 float z1 = v_[2] * (float)Math.cos(r) + v_[0] * (float)Math.sin(r);
		 v_[0] = x1;
		 v_[2] = z1;
	 }

	 /**
	  * rotate the vector around its X Axis
	  */
	 public  final void rotX(double r){
		 float y1 = v_[1] * (float)Math.cos(r) + v_[2] * (float)Math.sin(r);
		 float z1 = v_[2] * (float)Math.cos(r) - v_[1] * (float)Math.sin(r);
		 v_[1] = y1;
		 v_[2] = z1;
	 }

	 public Vector3f clone() {
		 Vector3f clone = new Vector3f(v_[0], v_[1], v_[2]);
		 return clone;
	 }


	  public static final int X = 0;

	  public static final int Y = 1;

	  public static final int Z = 2;

	  public static final int LENGTH = 3;

	  public static int COMPARE_TYPE = LENGTH;


	 public int compareTo(Vector3f theVector3f) {
		 if (COMPARE_TYPE == LENGTH) {
			 final float myLengthSquared = lengthSquared();
			 final float myOtherLengthSquared = theVector3f.lengthSquared();
			 return myLengthSquared > myOtherLengthSquared ? 1 : (myLengthSquared < myOtherLengthSquared ? -1 : 0);
		 } else if (COMPARE_TYPE == X) {
			 return v_[0] > theVector3f.v_[0] ? 1 : (v_[0] < theVector3f.v_[0] ? -1 : 0);
		 } else if (COMPARE_TYPE == Y) {
			 return v_[1] > theVector3f.v_[1] ? 1 : (v_[1] < theVector3f.v_[1] ? -1 : 0);
		 } else if (COMPARE_TYPE == Z) {
			 return v_[2] > theVector3f.v_[2] ? 1 : (v_[2] < theVector3f.v_[2] ? -1 : 0);
		 } else {
			 return 0;
		 }
	 }


	 /**
	  * Create a string representation of this vector.
	  * 
	  * @return  String representing this vector.
	  */
	 public String toString()
	 {
		 return ("Vector3f: [" + 
				 v_[0] + "," + v_[1] + "," + v_[2] + "]");
	 }
}

