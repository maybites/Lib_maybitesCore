package ch.maybites.tools.math.la;

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
 * Implementation of a 4-element vector suited for use with
 * Matrix4x4
 */   
public class Vector4d
{
	private double[] v_;

	public static int X = 0;
	public static int Y = 1;
	public static int Z = 2;
	public static int W = 3;

	private void initialize()
	{
		v_ = new double[4];
		for (int i = 0; i < 4; i++)
			v_[i] = 0.0;
	}



	/**
	 * Create a default 4-element vector (all elements set to 0.0).
	 */
	public Vector4d()
	{
		initialize();
	}



	/**
	 * Create a 4-element vector with the specified values.
	 * 
	 * @param v1  1st element.
	 * @param v2  2nd element.
	 * @param v3  3rd element.
	 * @param v4  4th element
	 */
	public Vector4d (double v1, double v2, double v3, double v4)
	{
		initialize();
		set (v1, v2, v3, v4);
	}



	/**
	 * Construct a 4-element vector as a copy of the specified vector.
	 * 
	 * @param Vector4d
	 */
	public Vector4d (Vector4d Vector4d)
	{
		initialize();
		set (Vector4d);
	}



	/**
	 * Set the elements of this vector.
	 * 
	 * @param v1  1st element.
	 * @param v2  2nd element.
	 * @param v3  3rd element.
	 * @param v4  4th element
	 */
	public void set (double v1, double v2, double v3, double v4)
	{
		v_[0] = v1;
		v_[1] = v2;
		v_[2] = v3;
		v_[3] = v4;
	}



	/**
	 * Set the elements of this vector according to the specified vector.
	 * 
	 * @param vector  Vector to copy.
	 */
	public void set (Vector4d vector)
	{
		for (int i = 0; i < 4; i++)
			v_[0] = vector.v_[i];
	}


	/**
	 * Check if this 4-element vector equals the specified object.
	 * 
	 * @param object
	 * @return  TRue if the two equals, false otherwise.
	 */
	public boolean equals(Object object){
		Vector4d vector = (Vector4d) object;

		return v_[0] == vector.v_[0] &&
				v_[1] == vector.v_[1] &&
				v_[2] == vector.v_[2] &&
				v_[3] == vector.v_[3];
	}

	/**
	 * Return the i'th element of this vector.
	 * 
	 * @param i  Index of element to get (first is 0).
	 * @return   i'th element of this vector.
	 */
	public double getElement (int i)
	{
		return v_[i];
	}



	/**
	 * Set the i'th element of this vector.
	 * 
	 * @param i  Index of element to set (first is 0).
	 * @param value  Value to set.
	 */
	public void setElement (int i, double value)
	{
		v_[i] = value;
	}



	/**
	 * Create a string representation of this vector.
	 * 
	 * @return  String representing this vector.
	 */
	public String toString()
	{
		return ("Vector4d: [" + 
				v_[0] + "," + v_[1] + "," + v_[2] + "," + v_[3] + "]");
	}
}

