package ch.maybites.tools.math.la;

import ch.maybites.tools.threedee.Frustum;


/*
 * (C) 2012 - Maybites
 * 
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
 * Implementation of a 4x4 matrix 
 * it is suited for use in a 2D and 3D graphics rendering engine.
 * 
 * All transformations on this matrix, if not specified, are by definition
 * postMultiplication
 * 
 */   
public class Matrix4x4f 
{
	private float[]  m_;  // of 16



	/**
	 * Construct a 4x4 identity matrix.
	 */
	public Matrix4x4f()
	{
		initialize();
		setIdentity();
	}



	/**
	 * Construct a 4x4 matrix with the specified element values.
	 * 
	 * @param m  Array of 16 ROW-oriented matrix elements, m00, m01, etc.
	 */
	public Matrix4x4f (float[] m)
	{
		initialize();
		set (m);
	}



	/**
	 * Constrauct a 4x4 matrix as a copy of the specified matrix.
	 * 
	 * @param matrix  Matrix to copy.
	 */
	public Matrix4x4f (Matrix4x4f matrix)
	{
		initialize();
		set (matrix);
	}

	/**
	 * Construct a Rotation matrix from the specified quaternion.
	 * 
	 * @param quat  normalized Quaternion.
	 */
	public Matrix4x4f (Quaternionf quat)
	{
		initialize();
		set (quat);
	}
	
	/**
	 * Construct a Translation matrix from the specified Vector.
	 * 
	 * @param translation  Translation vector.
	 */
	public Matrix4x4f (Vector3f translation)
	{
		initialize();
		set(translation);
	}
	
	/**
	 * Construct a Scale matrix from the specified factors.
	 * 
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public Matrix4x4f (float scaleX, float scaleY, float scaleZ)
	{
		initialize();
		set(scaleX, scaleY, scaleZ);
	}
	
	/**
	 * Construct a 4x4 frustum matrix from the specified frustum.
	 * 
	 * @param frust  Frustum.
	 */
	public Matrix4x4f (Frustum frust)
	{
		initialize();
		if(frust.isOrtho())
			setOrthograficMatrix(frust.getLeft(),
				frust.getRight(), 
				frust.getBottom(), 
				frust.getTop(), 
				frust.getNear(), 
				frust.getFar());
		else
			setPerspectiveMatrix(frust.getLeft(),
					frust.getRight(), 
					frust.getBottom(), 
					frust.getTop(), 
					frust.getNear(), 
					frust.getFar());
			
	}


	/**
	 * Construct a 4x4 matrix with the specified values.
	 * 
	 * where m[row, column]
	 * 
	 * @param m00  Value of element m[0,0].
	 * @param m01  Value of element m[0,1].
	 * @param m02  Value of element m[0,2].
	 * @param m03  Value of element m[0,3].
	 * @param m10  Value of element m[1,0].
	 * @param m11  Value of element m[1,1].
	 * @param m12  Value of element m[1,2].
	 * @param m13  Value of element m[1,3].
	 * @param m20  Value of element m[2,0].
	 * @param m21  Value of element m[2,1].
	 * @param m22  Value of element m[2,2].
	 * @param m23  Value of element m[2,3].
	 * @param m30  Value of element m[3,0].
	 * @param m31  Value of element m[3,1].
	 * @param m32  Value of element m[3,2].
	 * @param m33  Value of element m[3,3].
	 */
	public Matrix4x4f (float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33)
	{
		initialize();
		set (m00, m01, m02, m03,
				m10, m11, m12, m13,
				m20, m21, m22, m23,
				m30, m31, m32, m33);
	}



	/**
	 * Initialize the matrix.
	 */
	private void initialize()
	{
		m_ = new float[16];
	}



	/**
	 * Make an identity matrix out of this 4x4 matrix.
	 */
	public void setIdentity()
	{
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
				m_[i*4 + j] = i == j ? 1.0f : 0.0f;
	}



	/**
	 * Set the value of this 4x4matrix according to the specified
	 * matrix
	 *
	 * @param matrix  Matrix to copy.
	 */
	public void set(Matrix4x4f matrix)
	{
		for (int i=0; i<16; i++)
			m_[i] = matrix.m_[i];
	}

	/**
	 * Set the values of this 4x4 matrix.
	 * 
	 * @param m  Array of 16 ROW-oriented matrix elements, m00, m01, etc.
	 * @throws   ArrayOutOfBoundsException
	 */
	public void set(float[] m)
	{
		for (int i=0; i<16; i++)
			m_[i] = m[i];
	}

	/**
	 * Set the values of this 4x4 matrix.
	 * 
	 * @param m  Array of 16 COLUMN-oriented matrix elements, m00, m10, etc.
	 * @throws   ArrayOutOfBoundsException
	 */
	public void setCol(float[] m)
	{
		for (int i=0; i<16; i++){
			m_[i/4 + i%4*4] = m[i];
		}
	}

	/**
	 * Set the values of this 4x4 matrix.
	 * 
	 * where m[row, column]
	 * 
	 * @param m00  Value of element m[0,0].
	 * @param m01  Value of element m[0,1].
	 * @param m02  Value of element m[0,2].
	 * @param m03  Value of element m[0,3].
	 * @param m10  Value of element m[1,0].
	 * @param m11  Value of element m[1,1].
	 * @param m12  Value of element m[1,2].
	 * @param m13  Value of element m[1,3].
	 * @param m20  Value of element m[2,0].
	 * @param m21  Value of element m[2,1].
	 * @param m22  Value of element m[2,2].
	 * @param m23  Value of element m[2,3].
	 * @param m30  Value of element m[3,0].
	 * @param m31  Value of element m[3,1].
	 * @param m32  Value of element m[3,2].
	 * @param m33  Value of element m[3,3].
	 */
	public void set (float m00, float m01, float m02, float m03,
			float m10, float m11, float m12, float m13,
			float m20, float m21, float m22, float m23,
			float m30, float m31, float m32, float m33)
	{
		m_[0]  = m00;
		m_[1]  = m01;
		m_[2]  = m02;
		m_[3]  = m03;  

		m_[4]  = m10;
		m_[5]  = m11;
		m_[6]  = m12;
		m_[7]  = m13;  

		m_[8]  = m20;
		m_[9]  = m21;
		m_[10] = m22;
		m_[11] = m23;  

		m_[12] = m30;
		m_[13] = m31;
		m_[14] = m32;
		m_[15] = m33;  
	}


	/**
	 * Sets this matrix as a perspective projection matrix
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param zNear
	 * @param zFar
	 */
	public void setPerspectiveMatrix(float left, float right,
			float bottom, float top,
			float zNear, float zFar){
		
		float A = (right+left)/(right-left);
		float B = (top+bottom)/(top-bottom);
		float C = -(zFar+zNear)/(zFar-zNear);
		float D = -2.0f*zFar*zNear/(zFar-zNear);
		initialize();
		setElement(0, 0, 2.0f*zNear/(right-left));
		setElement(1, 1, 2.0f*zNear/(top-bottom));
		setElement(2, 0, A);
		setElement(2, 1, B);
		setElement(2, 2, C);
		setElement(2, 3, -1.0f);
		setElement(3, 2, D);
	}
	
	/**
	 * Sets this matrix as a orthografic projection matrix
	 * @param left
	 * @param right
	 * @param bottom
	 * @param top
	 * @param zNear
	 * @param zFar
	 */
	public void setOrthograficMatrix(float left, float right,
			float bottom, float top,
			float zNear, float zFar){

	    float tx = -(right+left)/(right-left);
	    float ty = -(top+bottom)/(top-bottom);
	    float tz = -(zFar+zNear)/(zFar-zNear);
		initialize();
		setElement(0, 0, 2.0f/(right-left));
		setElement(1, 1, 2.0f/(top-bottom));
		setElement(2, 2, -2.0f/(zFar-zNear));
		setElement(3, 0, tx);
		setElement(3, 1, ty);
		setElement(3, 2, tz);
	}

	/**
	 * Sets a Translation Matrix from a Vector.
	 * 
	 * @param vec  Translation Vector.
	 */
	public void set(Vector3f vec){
		setIdentity();
		setElement (3, 0, vec.x());
		setElement (3, 1, vec.y());
		setElement (3, 2, vec.z());  
	}
	
	/**
	 * Sets the translation elements with the provided vector without changing
	 * the rest of the matrix
	 * 
	 * @param vec
	 */
	public void setTranslation(Vector3f vec){
		setElement (3, 0, vec.x());
		setElement (3, 1, vec.y());
		setElement (3, 2, vec.z());  
	}
	
	/**
	 * Sets a Scale Matrix from set factors
	 * 
	 * @param scaleX
	 * @param scaleY
	 * @param scaleZ
	 */
	public void set(float scaleX, float scaleY, float scaleZ){
		setIdentity();
		setElement (0, 0, scaleX);
		setElement (1, 1, scaleY);
		setElement (2, 2, scaleZ);  
	}
	
	/**
	 * Sets Rotation Matrix from a normalized Quaternion.
	 * 
	 * @param quat  normalized Quaternion4f.
	 */
	public void set(Quaternionf quat){
		//quat.normalize();
		float sqw = quat.w()*quat.w();
		float sqx = quat.x()*quat.x();
		float sqy = quat.y()*quat.y();
		float sqz = quat.z()*quat.z();


		float m00 = ( sqx - sqy - sqz + sqw); // since sqw + sqx + sqy + sqz =1/invs*invs
		float m11 = (-sqx + sqy - sqz + sqw);
		float m22 = (-sqx - sqy + sqz + sqw);

		float tmp1 = quat.x()*quat.y();
		float tmp2 = quat.z()*quat.w();
		float m01 = 2.0f * (tmp1 + tmp2);
		float m10 = 2.0f * (tmp1 - tmp2);
		tmp1 = quat.x()*quat.z();
		tmp2 = quat.y()*quat.w();
		float m02 = 2.0f * (tmp1 - tmp2);
		float m20 = 2.0f * (tmp1 + tmp2);
		tmp1 = quat.y()*quat.z();
		tmp2 = quat.x()*quat.w();
		float m12 = 2.0f * (tmp1 + tmp2);
		float m21 = 2.0f * (tmp1 - tmp2);      

		set (m00, m01, m02, 0,
				m10, m11, m12, 0,
				m20, m21, m22, 0,
				0, 0, 0, 1);
	}


	/**
	 * Return the values of this 4x4 matrix.
	 * 
	 * @return  Array ov values: m00, m01, etc.
	 */
	public float[] get()
	{
		return m_;
	}

	/**
	 * Return the values of this 4x4 matrix in COLUMN-oriented fashion.
	 * 
	 */
	public float[] getCol()
	{
		float[] ret = new float[16];
		for (int i=0; i<16; i++){
			ret[i] = m_[i/4 + i%4*4];
		}
		return ret;
	}

	/**
	 * Returns this 4x4 matrix in as a quaternion.<br>
	 * 
	 * This function will only result in a correct normalized Quaternion if
	 * the matrix is a pure rotation matrix
	 * 
	 * see discussion: http://www.euclideanspace.com/maths/geometry/rotations/conversions/matrixToQuaternion/
	 *
	 * @return        Quaternion4f
	 * 
	 */
	public Quaternionf getQuaternion(){
		float tr = m_[0] + m_[5] + m_[10];
		float qw, qx, qy, qz;

		if (tr > 0) { 
			float S = (float)Math.sqrt(tr+1.0) * 2.0f; // S=4*qw 
			qw = 0.25f * S;
			qx = (m_[6] - m_[9]) / S;
			qy = (m_[8] - m_[2]) / S; 
			qz = (m_[1] - m_[4]) / S; 
		} else if ((m_[0] > m_[5])&(m_[0] > m_[10])) { 
			float S = (float)Math.sqrt(1.0 + m_[0] - m_[5] - m_[10]) * 2; // S=4*qx 
			qw = (m_[6] - m_[9]) / S;
			qx = 0.25f * S;
			qy = (m_[1] + m_[4]) / S; 
			qz = (m_[8] + m_[2]) / S; 
		} else if (m_[5] > m_[10]) { 
			float S = (float)Math.sqrt(1.0 + m_[5] - m_[0] - m_[10]) * 2; // S=4*qy
			qw = (m_[8] - m_[2]) / S;
			qx = (m_[1] + m_[4]) / S; 
			qy = 0.25f * S;
			qz = (m_[6] + m_[9]) / S; 
		} else { 
			float S = (float)Math.sqrt(1.0 + m_[10] - m_[0] - m_[5]) * 2; // S=4*qz
			qw = (m_[1] - m_[4]) / S;
			qx = (m_[8] + m_[2]) / S;
			qy = (m_[6] + m_[9]) / S;
			qz = 0.25f * S;
		}

		Quaternionf ret = new Quaternionf(qx, qy, qz, qw);
		return ret;
	}

	/**
	 * Check if this 4x4 matrix equals the specified object.
	 * 
	 * @param object  Object to check.
	 * @return        True if the two are equal, false otherwise.
	 * @throws        ClassCastException if object is not of type Matrix4x4.
	 */
	public boolean equals (Object object)
	{
		Matrix4x4f matrix = (Matrix4x4f) object;

		for (int i=0; i<16; i++)
			if (m_[i] != matrix.m_[i]) return false;
		return true;
	}

	/**
	 * Transposes this matrix
	 */
	public void transpose(){
		Matrix4x4f trans = new Matrix4x4f(getCol());
		set(trans);
	}


	/**
	 * Return matrix element [i,j].
	 * 
	 * @param i  Row of element to get (first row is 0).
	 * @param j  Column of element to get (first column is 0).
	 * @return   Element at specified position.
	 * @throws   ArrayOutOfBoundsException
	 */
	public float getElement (int i, int j)
	{
		return m_[i*4 + j];  
	}

	/**
	 * Return Row as a vector.
	 * 
	 * @param row  Row to get (first row is 0).
	 * @return   Vector3f at specified position.
	 * @throws   ArrayOutOfBoundsException
	 */
	public Vector3f getRowAsVector(int row){
		return new Vector3f(getElement(row, 0), getElement(row, 1), getElement(row, 1));
	}

	/**
	 * Set specified matrix element.
	 * 
	 * @param i      Row of element to set (first row is 0).
	 * @param j      Column of element to set (first column is 0).
	 * @param value  New element value.
	 * @throws       ArrayOutOfBoundsException
	 */
	public void setElement (int i, int j, float value)
	{
		m_[i*4 + j] = value;
	}


	/**
	 * Add the specified 4x4 matrix to this matrix.
	 * 
	 * @param matrix  Matrix to add.
	 */
	public void add(Matrix4x4f matrix)
	{
		for (int i=0; i<4; i++)
			for (int j=0; j<4; j++)
				m_[i*4 + j] += matrix.m_[i*4 + j];
	}

	/**
	 * Add the specified 4x4 matrix to this matrix and return this instance
	 * 
	 * @param matrix  Matrix to add.
	 * @return this instance
	 */
	public Matrix4x4f getAdd(Matrix4x4f matrix)
	{
		add(matrix);
		return this;
	}

	/**
	 * Add the specified 4x4 matrix to this matrix and return a new instance.
	 * This method does NOT modify this instance
	 * 
	 * @param matrix  Matrix to add.
	 * @return this instance
	 */
	public Matrix4x4f makeAdd(Matrix4x4f matrix){
		return clone().getAdd(matrix);
	}

	/**
	 * Add two matrices and return the result matrix.
	 * 
	 * @param m1  First matrix to add.
	 * @param m2  Second matrix to add.
	 * @return    Sum m1 + m2.
	 */
	public Matrix4x4f makeAdd(Matrix4x4f m1, Matrix4x4f m2)
	{
		Matrix4x4f m = new Matrix4x4f (m1);
		m.add (m2);
		return m;
	}



	/**
	 * Multiply this 4x4 matrix with the specified matrix and
	 * store the result in this 4x4 matrix.
	 * 
	 * Since this Matrix is Row-Major - it is a postMultiplication
	 * C = A x B where
	 *
	 * A = this instance
	 * B = matrix
	 * C = this instance
	 * 
	 * @param matrix  Matrix to multiply with.
	 */
	public void multiply(Matrix4x4f matrix){
		Matrix4x4f product = new Matrix4x4f();

		for (int i = 0; i < 16; i += 4) { //step through rows
			for (int j = 0; j < 4; j++) { //step through columns
				product.m_[i + j] = 0.0f; //set value of marixvector = 0
				for (int k = 0; k < 4; k++) // step though each Row (k) and each Column(k*4)
					product.m_[i + j] += m_[i + k] * matrix.m_[k*4 + j]; // and add the multiplied result
			}
		}

		set(product);
	}

	/**
	 * Multiply this 4x4 matrix with the specified matrix and
	 * store the result in this 4x4 matrix.
	 * 	 
	 * Since this Matrix is Row-Major - it is a postMultiplication
	 * C = A x B where
	 *
	 * A = this instance
	 * B = matrix
	 * C = this instance
	 *
	 * @param matrix  Matrix to multiply with.
	 * @return this instance
	 */
	public Matrix4x4f getMultiplied(Matrix4x4f matrix){
		multiply(matrix);
		return this;
	}


	/**
	 * Multiply two matrices and return the result matrix. This instance will
	 * NOT be modified
	 * 
	 * @param matrix  First matrix to multiply.
	 * @return Product m1 * m2.
	 */
	public Matrix4x4f makeMultiply (Matrix4x4f matrix){
		return clone().getMultiplied(matrix);
	}


	/**
	 * Multiply this 4x4 matrix with the specified vector.
	 * 
	 * @param vector4  Vector to multiply with.
	 * @return         Result of operation.
	 */
	public Vector4d multiply (Vector4d vector4)
	{
		Vector4d  product = new Vector4d();

		for (int i = 0; i < 4; i++) {
			float value = 0.0f;
			for (int j = 0; j < 4; j++)
				value += getElement(i, j) * vector4.getElement (j);
			product.setElement (i, value);
		}

		return product;
	}

	/**
	 * Transform (PreMultiply) a Point using this 4x4 matrix and return a new instance
	 * without modifying the provided instance
	 * 
	 * This Method is not a mathematically correct matrix4x4 * vector3 multiplication
	 * since it ignores the (usually unused i.e. 0) elements m03, m13 and m23
	 * 
	 * @param point  
	 * @return vector
	 */
	public Vector3f makeTransformPoint(Vector3f point)
	{
		return transformPoint(point.clone());
	}
	
	/**
	 * Transform (PreMultiply) a Point using this 4x4 matrix. It applies the result
	 * to the provided instance and also returns it.
	 * 
	 * This Method is not a mathematically correct matrix4x4 * vector3 multiplication
	 * since it ignores the (usually unused i.e. 0) elements m03, m13 and m23
	 * 
	 * @param point  
	 * @return point
	 */
	public Vector3f transformPoint(Vector3f point)
	{
		float x = point.x() * m_[0]  +
				point.y() * m_[4]  +
				point.z() * m_[8]  + m_[12];

		float y = point.x() * m_[1]  +
				point.y() * m_[5]  +
				point.z() * m_[9]  + m_[13];

		float z = point.x() * m_[2]   +
				point.y() * m_[6]   +
				point.z() * m_[10]  + m_[14];

		point.set(x, y, z);
		return point;
	}


	/**
	 * Transform one coordinate using this 4x4 matrix.
	 * 
	 * @param point  [x0,y0,z0]
	 * @return       Result of operation: [x0',y0',z0']
	 */
	public float[] transformPoint (float[] point)
	{
		float[]  result = new float[3];

		result[0] = point[0] * m_[0]  +
				point[1] * m_[4]  +
				point[2] * m_[8]  + m_[12];

		result[1] = point[0] * m_[1]  +
				point[1] * m_[5]  +
				point[2] * m_[9]  + m_[13];

		result[2] = point[0] * m_[2]   +
				point[1] * m_[6]   +
				point[2] * m_[10]  + m_[14];

		return result;
	}



	/**
	 * Transform a set of 3D coordinates using this 4x4 matrix.
	 * The result of the operation is put back in the original array.
	 * 
	 * @param points  Points to transform [x0,y0,z0,x1,y1,z1,...]
	 */
	public void transformPoints (float[] points)
	{
		for (int i = 0; i < points.length; i += 3) {
			float x = points[i + 0] * m_[0]  +
					points[i + 1] * m_[4]  +
					points[i + 2] * m_[8]  + m_[12];

			float y = points[i + 0] * m_[1]  +
					points[i + 1] * m_[5]  +
					points[i + 2] * m_[9]  + m_[13];

			float z = points[i + 0] * m_[2]   +
					points[i + 1] * m_[6]   +
					points[i + 2] * m_[10]  + m_[14];

			points[i + 0] = x;
			points[i + 1] = y;
			points[i + 2] = z;            
		}
	}



	/**
	 * Transform a set of 2D (x,y) coordinates using this 4x4 matrix.
	 * The result of the operation is put back in the original array
	 * rounded to the nearest integer.
	 * 
	 * @param points  Points to transform [x0,y0,x1,y1,...].
	 */
	public void transformXyPoints (float[] points)
	{
		for (int i = 0; i < points.length; i += 2) {
			float x = points[i + 0] * m_[0]  +
					points[i + 1] * m_[4]  + m_[12];

			float y = points[i + 0] * m_[1]  +
					points[i + 1] * m_[5]  + m_[13];

			points[i + 0] = x;
			points[i + 1] = y;
		}
	}



	/**
	 * Transform a set of 3D coordinates using this 4x4 matrix.
	 * The result of the operation is put back in the original array.
	 * 
	 * @param points  Points to transform [x0,y0,z0,x1,y1,z1,...].
	 */
	public void transformPoints (int[] points)
	{
		for (int i = 0; i < points.length; i += 3) {
			float x = points[i + 0] * m_[0]  +
					points[i + 1] * m_[4]  +
					points[i + 2] * m_[8]  + m_[12];

			float y = points[i + 0] * m_[1]  +
					points[i + 1] * m_[5]  +
					points[i + 2] * m_[9]  + m_[13];

			float z = points[i + 0] * m_[2]  +
					points[i + 1] * m_[6]  +
					points[i + 2] * m_[10] + m_[14];

			points[i + 0] = (int) Math.round (x);
			points[i + 1] = (int) Math.round (y);
			points[i + 2] = (int) Math.round (z);            
		}
	}



	/**
	 * Transform a set of 2D (x,y) coordinates using this 4x4 matrix.
	 * The result of the operation is put back in the original array
	 * rounded to the nearest integer.
	 * 
	 * @param points  Points to transform [x0,y0,x1,y1,...].
	 */
	public void transformXyPoints (int[] points)
	{
		for (int i = 0; i < points.length; i += 2) {
			float x = points[i + 0] * m_[0] +
					points[i + 1] * m_[4] + m_[12];

			float y = points[i + 0] * m_[1]  +
					points[i + 1] * m_[5]  + m_[13];

			points[i + 0] = (int) Math.round (x);
			points[i + 1] = (int) Math.round (y);
		}
	}



	/**
	 * multiply the specified translation matrix with this matrix.
	 * 
	 * @param dx  x translation of translation matrix.
	 * @param dy  y translation of translation matrix.
	 * @param dz  z translation of translation matrix.
	 */
	public void translate(float dx, float dy, float dz)
	{
		translate(new Vector3f(dx, dy, dz));
	}

	/**
	 * multiply the specified translation matrix to this matrix.
	 * 
	 * @param vec  Vector3f  of translation matrix
	 */
	public void translate(Vector3f vec)
	{
		Matrix4x4f  translationMatrix = new Matrix4x4f(vec);
		multiply (translationMatrix);
	}



	/**
	 * Apply rotation around X axis to this matrix.
	 * 
	 * @param angle  Angle to rotate [radians].
	 */
	public void rotateX (float angle)
	{
		Matrix4x4f rotationMatrix = new Matrix4x4f();

		float cosAngle = (float)Math.cos (angle);
		float sinAngle = (float)Math.sin (angle);  

		rotationMatrix.setElement (1, 1,  cosAngle);
		rotationMatrix.setElement (1, 2,  sinAngle);
		rotationMatrix.setElement (2, 1, -sinAngle);
		rotationMatrix.setElement (2, 2,  cosAngle);

		multiply (rotationMatrix);
	}



	/**
	 * Apply rotation around Y axis to this matrix.
	 * 
	 * @param angle  Angle to rotate [radians].
	 */
	public void rotateY (float angle)
	{
		Matrix4x4f rotationMatrix = new Matrix4x4f();

		float cosAngle = (float)Math.cos (angle);
		float sinAngle = (float)Math.sin (angle);  

		rotationMatrix.setElement (0, 0,  cosAngle);
		rotationMatrix.setElement (0, 2, -sinAngle);
		rotationMatrix.setElement (2, 0,  sinAngle);
		rotationMatrix.setElement (2, 2,  cosAngle);

		multiply (rotationMatrix);
	}



	/**
	 * Apply rotation around z axis to this matrix.
	 * 
	 * @param angle  Angle to rotate [radians].
	 */
	public void rotateZ (float angle)
	{
		Matrix4x4f rotationMatrix = new Matrix4x4f();

		float cosAngle = (float)Math.cos (angle);
		float sinAngle = (float)Math.sin (angle);  

		rotationMatrix.setElement (0, 0,  cosAngle);
		rotationMatrix.setElement (0, 1,  sinAngle);
		rotationMatrix.setElement (1, 0, -sinAngle);
		rotationMatrix.setElement (1, 1,  cosAngle);

		multiply (rotationMatrix);
	}



	/**
	 * Apply rotation around an arbitrary axis.
	 *
	 * Ref: http://www.swin.edu.au/astronomy/pbourke/geometry/rotate/
	 * (but be aware of errors, corrected here)
	 *
	 * @param angle  Angle to rotate [radians]
	 * @param p0     First point defining the axis (x,y,z)
	 * @param p1     Second point defining the axis (x,y,z)
	 */
	public void rotate (float angle, float[] p0, float[] p1)
	{
		// Represent axis of rotation by a unit vector [a,b,c]
		float a = p1[0] - p0[0];
		float b = p1[1] - p0[1];
		float c = p1[2] - p0[2];  

		float length = (float)Math.sqrt (a*a + b*b + c*c);

		a /= length;
		b /= length;
		c /= length;  

		float d = (float)Math.sqrt (b*b + c*c);

		// Coefficients used for step 2 matrix
		float e = d == 0.0f ? 1.0f : c / d;
		float f = d == 0.0f ? 0.0f : b / d;  

		// Coefficients used for the step 3 matrix
		float k = d;
		float l = a;

		// Coefficients for the step 5 matrix (inverse of step 3)
		float m = d / (a*a + d*d);
		float n = a / (a*a + d*d);  

		// Coefficients for the step 4 matrix
		float cosAngle = (float)Math.cos (angle);
		float sinAngle = (float)Math.sin (angle);  

		//
		// Step 1
		//
		Matrix4x4f  step1 = new Matrix4x4f();
		step1.setElement (3, 0, -p0[0]);
		step1.setElement (3, 1, -p0[1]);
		step1.setElement (3, 2, -p0[2]);

		//
		// Step 2
		//
		Matrix4x4f  step2 = new Matrix4x4f();
		step2.setElement (1, 1,  e);
		step2.setElement (1, 2,  f);
		step2.setElement (2, 1, -f);
		step2.setElement (2, 2,  e);      

		//
		// Step 3
		//
		Matrix4x4f  step3 = new Matrix4x4f();
		step3.setElement (0, 0,  k);
		step3.setElement (0, 2,  l);
		step3.setElement (2, 0, -l);
		step3.setElement (2, 2,  k);

		//
		// Step 4
		//
		Matrix4x4f  step4 = new Matrix4x4f();
		step4.setElement (0, 0,  cosAngle);
		step4.setElement (0, 1,  sinAngle);
		step4.setElement (1, 0, -sinAngle);
		step4.setElement (1, 1,  cosAngle);

		//
		// Step 5 (inverse of step 3)
		//
		Matrix4x4f  step5 = new Matrix4x4f();
		step5.setElement (0, 0,  m);
		step5.setElement (0, 2, -n);
		step5.setElement (2, 0,  n);
		step5.setElement (2, 2,  m);

		//
		// Step 6 (inverse of step 2)
		//
		Matrix4x4f  step6 = new Matrix4x4f();
		step6.setElement (1, 1,  e);
		step6.setElement (1, 2, -f);
		step6.setElement (2, 1,  f);
		step6.setElement (2, 2,  e);      

		//
		// Step 7 (inverse of step 1)
		//
		Matrix4x4f  step7 = new Matrix4x4f();
		step7.setElement (3, 0, p0[0]);
		step7.setElement (3, 1, p0[1]);
		step7.setElement (3, 2, p0[2]);

		multiply (step1);
		multiply (step2);
		multiply (step3);
		multiply (step4);
		multiply (step5);
		multiply (step6);
		multiply (step7);
	}

	/**
	 * Apply rotation with a quaternion.
	 *
	 * @param quat   normalized Quaternion
	 */
	public void rotate (Quaternionf quat){
		multiply(new Matrix4x4f(quat));
	}

	/**
	 * Apply scaling (relative to origo) to this 4x4 matrix.
	 * 
	 * @param xScale  Scaling in x direction.
	 * @param yScale  Scaling in y direction.
	 * @param zScale  Scaling in z direction.
	 */
	public void scale (float xScale, float yScale, float zScale)
	{
		Matrix4x4f  scalingMatrix = new Matrix4x4f(xScale, yScale, zScale);
		multiply (scalingMatrix);
	}

	/**
	 * Apply scaling-vector (relative to origo) to this 4x4 matrix.
	 * 
	 * @param scale  Scaling Vector3f.
	 */
	public void scale (Vector3f scale)
	{
		scale(scale.x(), scale.y(), scale.z());
	}
	
	/**
	 * Apply scaling relative to a fixed point to this 4x4 matrix.
	 * 
	 * @param xScale      Scaling in x direction.
	 * @param yScale      Scaling in y direction.
	 * @param zScale      Scaling in z direction.
	 * @param fixedPoint  Scaling origo.
	 */
	public void scale (float xScale, float yScale, float zScale,
			Vector3f fixedPoint)
	{
		translate(fixedPoint.makeScale(-1.0f));
		scale(xScale, yScale, zScale);
		translate(fixedPoint);
	}



	/**
	 * Invert this 4x4 matrix.
	 */
	public void invert()
	{
		float[] tmp = new float[12];
		float[] src = new float[16];
		float[] dst = new float[16];  

		// Transpose matrix
		for (int i = 0; i < 4; i++) {
			src[i +  0] = m_[i*4 + 0];
			src[i +  4] = m_[i*4 + 1];
			src[i +  8] = m_[i*4 + 2];
			src[i + 12] = m_[i*4 + 3];
		}

		// Calculate pairs for first 8 elements (cofactors) 
		tmp[0] = src[10] * src[15];
		tmp[1] = src[11] * src[14];
		tmp[2] = src[9]  * src[15];
		tmp[3] = src[11] * src[13];
		tmp[4] = src[9]  * src[14];
		tmp[5] = src[10] * src[13];
		tmp[6] = src[8]  * src[15];
		tmp[7] = src[11] * src[12];
		tmp[8] = src[8]  * src[14];
		tmp[9] = src[10] * src[12];
		tmp[10] = src[8] * src[13];
		tmp[11] = src[9] * src[12];

		// Calculate first 8 elements (cofactors)
		dst[0]  = tmp[0]*src[5] + tmp[3]*src[6] + tmp[4]*src[7];
		dst[0] -= tmp[1]*src[5] + tmp[2]*src[6] + tmp[5]*src[7];
		dst[1]  = tmp[1]*src[4] + tmp[6]*src[6] + tmp[9]*src[7];
		dst[1] -= tmp[0]*src[4] + tmp[7]*src[6] + tmp[8]*src[7];
		dst[2]  = tmp[2]*src[4] + tmp[7]*src[5] + tmp[10]*src[7];
		dst[2] -= tmp[3]*src[4] + tmp[6]*src[5] + tmp[11]*src[7];
		dst[3]  = tmp[5]*src[4] + tmp[8]*src[5] + tmp[11]*src[6];
		dst[3] -= tmp[4]*src[4] + tmp[9]*src[5] + tmp[10]*src[6];
		dst[4]  = tmp[1]*src[1] + tmp[2]*src[2] + tmp[5]*src[3];
		dst[4] -= tmp[0]*src[1] + tmp[3]*src[2] + tmp[4]*src[3];
		dst[5]  = tmp[0]*src[0] + tmp[7]*src[2] + tmp[8]*src[3];
		dst[5] -= tmp[1]*src[0] + tmp[6]*src[2] + tmp[9]*src[3];
		dst[6]  = tmp[3]*src[0] + tmp[6]*src[1] + tmp[11]*src[3];
		dst[6] -= tmp[2]*src[0] + tmp[7]*src[1] + tmp[10]*src[3];
		dst[7]  = tmp[4]*src[0] + tmp[9]*src[1] + tmp[10]*src[2];
		dst[7] -= tmp[5]*src[0] + tmp[8]*src[1] + tmp[11]*src[2];

		// Calculate pairs for second 8 elements (cofactors)
		tmp[0]  = src[2]*src[7];
		tmp[1]  = src[3]*src[6];
		tmp[2]  = src[1]*src[7];
		tmp[3]  = src[3]*src[5];
		tmp[4]  = src[1]*src[6];
		tmp[5]  = src[2]*src[5];
		tmp[6]  = src[0]*src[7];
		tmp[7]  = src[3]*src[4];
		tmp[8]  = src[0]*src[6];
		tmp[9]  = src[2]*src[4];
		tmp[10] = src[0]*src[5];
		tmp[11] = src[1]*src[4];

		// Calculate second 8 elements (cofactors)
		dst[8]   = tmp[0] * src[13]  + tmp[3] * src[14]  + tmp[4] * src[15];
		dst[8]  -= tmp[1] * src[13]  + tmp[2] * src[14]  + tmp[5] * src[15];
		dst[9]   = tmp[1] * src[12]  + tmp[6] * src[14]  + tmp[9] * src[15];
		dst[9]  -= tmp[0] * src[12]  + tmp[7] * src[14]  + tmp[8] * src[15];
		dst[10]  = tmp[2] * src[12]  + tmp[7] * src[13]  + tmp[10]* src[15];
		dst[10] -= tmp[3] * src[12]  + tmp[6] * src[13]  + tmp[11]* src[15];
		dst[11]  = tmp[5] * src[12]  + tmp[8] * src[13]  + tmp[11]* src[14];
		dst[11] -= tmp[4] * src[12]  + tmp[9] * src[13]  + tmp[10]* src[14];
		dst[12]  = tmp[2] * src[10]  + tmp[5] * src[11]  + tmp[1] * src[9];
		dst[12] -= tmp[4] * src[11]  + tmp[0] * src[9]   + tmp[3] * src[10];
		dst[13]  = tmp[8] * src[11]  + tmp[0] * src[8]   + tmp[7] * src[10];
		dst[13] -= tmp[6] * src[10]  + tmp[9] * src[11]  + tmp[1] * src[8];
		dst[14]  = tmp[6] * src[9]   + tmp[11]* src[11]  + tmp[3] * src[8];
		dst[14] -= tmp[10]* src[11 ] + tmp[2] * src[8]   + tmp[7] * src[9];
		dst[15]  = tmp[10]* src[10]  + tmp[4] * src[8]   + tmp[9] * src[9];
		dst[15] -= tmp[8] * src[9]   + tmp[11]* src[10]  + tmp[5] * src[8];

		// Calculate determinant
		float det = src[0]*dst[0] + src[1]*dst[1] + src[2]*dst[2] + src[3]*dst[3];

		// Calculate matrix inverse
		det = 1.0f / det;
		for (int i = 0; i < 16; i++)
			m_[i] = dst[i] * det;
	}

	/**
	 * Invert this 4x4 matrix and return this instance.
	 * @return this instance
	 */
	public Matrix4x4f getInvert(){
		invert();
		return this;
	}

	/**
	 * Return the inverse of the specified matrix.
	 * 
	 * @return        new Instance of the inverse of this matrix.
	 */
	public Matrix4x4f makeInverse(){
		return clone().getInvert();
	}



	/**
	 * Solve the A x = b equation, where A is this 4x4 matrix, b is the
	 * specified result vector and the returned vector is the unknown x.
	 *
	 * @param vector  Result vector
	 * @return        Unknown vector.
	 */
	public Vector4d solve (Vector4d vector)
	{
		Matrix4x4f inverse = new Matrix4x4f (this);
		inverse.invert();
		Vector4d result = inverse.multiply (vector);
		return result;
	}



	/**
	 * Make this 4x4 matrix a world-2-device transformation matrix.
	 * <p>
	 * The world system is defined as follows:
	 *
	 * <pre>
	 *        w2 o 
	 *           |
	 *           |
	 *           |
	 *        w0 o-------o w1
	 * <pre>
	 * <p>
	 * Each point is defined with x,y,z so this system may in effect be
	 * arbitrary oriented in space, and may include sharing.
	 * <p>
	 * The device system is defined as follows:
	 *
	 * <pre>
	 *             width
	 *     x0,y0 o-------o
	 *           |
	 *    height |
	 *           |
	 *           o
	 * </pre>
	 * <p>
	 * The matrix maps w2 to (x0,y0), w0 to the lower left corner of the
	 * device rectangle, and w1 to the lower right corner of the device
	 * rectangle.
	 *
	 * @param w0      x,y,z coordinate of first world position.
	 * @param w1      x,y,z coordinate of second world position.
	 * @param w2      x,y,z coordinate of third world position.
	 * @param x0      X coordinate of upper left corner of device.
	 * @param y0      Y coordinate of upper left corner of device.
	 * @param width   Width of device
	 * @param height  Height of device.
	 */
	public void setWorld2DeviceTransform (float[] w0, float[] w1, float[] w2,
			int x0, int y0, int width, int height)
	{
		setIdentity();

		float[] x = new float[4];
		float[] y = new float[4];
		float[] z = new float[4];

		// Make direction vectors for new system
		x[0] = w2[0];          y[0] = w2[1];          z[0] = w2[2];
		x[1] = w1[0] - w0[0];  y[1] = w1[1] - w0[1];  z[1] = w1[2] - w0[2];
		x[2] = w0[0] - w2[0];  y[2] = w0[1] - w2[1];  z[2] = w0[2] - w2[2];

		x[3] = y[1]*z[2] - z[1]*y[2];
		y[3] = z[1]*x[2] - x[1]*z[2];
		z[3] = x[1]*y[2] - y[1]*x[2];

		// Normalize new z-vector, in case someone needs
		// new z-value in addition to device coordinates */
		float length = (float)Math.sqrt (x[3]*x[3] + y[3]*y[3] + z[3]*z[3]); 
		x[3] /= length;
		y[3] /= length;
		z[3] /= length;

		// Translate back to new origin                                
		translate (-x[0], -y[0], -z[0]);

		// Multiply with inverse of definition of new coordinate system
		float a = y[2]*z[3] - z[2]*y[3];
		float b = z[1]*y[3] - y[1]*z[3];
		float c = y[1]*z[2] - z[1]*y[2];

		float det = x[1]*a + x[2]*b + x[3]*c;

		float[] m = new float[16];

		m[0]  = a / det; 
		m[1]  = b / det; 
		m[2]  = c / det; 
		m[3]  = 0.0f;

		m[4]  = (x[3]*z[2] - x[2]*z[3]) / det;   
		m[5]  = (x[1]*z[3] - x[3]*z[1]) / det; 
		m[6]  = (z[1]*x[2] - x[1]*z[2]) / det;               
		m[7]  = 0.0f;

		m[8]  = (x[2]*y[3] - x[3]*y[2]) / det;  
		m[9]  = (y[1]*x[3] - x[1]*y[3]) / det;  
		m[10] = (x[1]*y[2] - y[1]*x[2]) / det;
		m[11] = 0.0f;

		m[12] = 0.0f; 
		m[13] = 0.0f; 
		m[14] = 0.0f; 
		m[15] = 1.0f;

		Matrix4x4f matrix = new Matrix4x4f (m);
		multiply (matrix);

		// Scale according to height and width of viewport
		matrix.setIdentity();
		matrix.setElement (0, 0, width);
		matrix.setElement (1, 1, height);
		multiply (matrix);

		// Translate according to origin of viewport
		matrix.setIdentity();
		matrix.setElement (3, 0, x0);
		matrix.setElement (3, 1, y0);
		multiply (matrix);
	}


	public Matrix4x4f clone(){
		return new Matrix4x4f(this);
	}

	/**
	 * Create a string representation of this matrix.
	 * 
	 * @return  String representing this matrix.
	 */
	public String toString()
	{
		String string = new String();

		for (int i=0; i<4; i++) {
			for (int j=0; j<4; j++)
				string += getElement(i,j) + " ";
			string += '\n';
		}

		return string;
	}
}

