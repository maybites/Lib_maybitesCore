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
	 * get the Quaternion values in an array 
	 * 
	 * @return 	float[] array
	 */
	public float[] get(){
		return v_;
	}
	
	public void multiply(Quaternionf theA, Quaternionf theB) {
		v_[0] = theA.w() * theB.x() + theA.x() * theB.w() + theA.y() * theB.z() - theA.z() * theB.y();
		v_[1] = theA.w() * theB.y() + theA.y() * theB.w() + theA.z() * theB.x() - theA.x() * theB.z();
		v_[2] = theA.w() * theB.z() + theA.z() * theB.w() + theA.x() * theB.y() - theA.y() * theB.x();
		v_[3] = theA.w() * theB.w() - theA.x() * theB.x() - theA.y() * theB.y() - theA.z() * theB.z();
	}


	/*
    public Vector4d getVectorAndAngle() {
        final Vector4d theResult = new Vector4d();

        float s = (float) Math.sqrt(1.0f - w * w);
        if (s < Mathematik.EPSILON) {
            s = 1.0f;
        }

        theResult.w = (float) Math.acos(w) * 2.0f;
        theResult.x = x / s;
        theResult.y = y / s;
        theResult.z = z / s;

        return theResult;
    }
	 */

}
