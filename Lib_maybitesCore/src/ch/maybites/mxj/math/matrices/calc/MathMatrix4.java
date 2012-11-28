package ch.maybites.mxj.math.matrices.calc;

import javax.vecmath.*;

import ch.maybites.mxj.math.matrices.Matrix3x3;
import ch.maybites.mxj.math.matrices.Matrix4x4;
import ch.maybites.mxj.math.matrices.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class MathMatrix4 extends MaxObject {

	private static String METHOD_ADD_MATRIX4_MATRIX4 = "add_matrix4_matrix4";
	private static String METHOD_ADD_SCALAR_MATRIX4 = "add_scalar_matrix4";
	private static String METHOD_ADD_SCALAR = "add_scalar";
	private static String METHOD_FLUSH = "flush";
	private static String METHOD_FLUSH_ALL = "flush";
	private static String METHOD_FLUSH_MATRIX3 = "flush_matrix3";
	private static String METHOD_FLUSH_VECTOR = "flush_vector";
	private static String METHOD_SET_MATRIX3 = "set_matrix3";
	private static String METHOD_SET_VECTOR = "set_vector";
	private static String METHOD_SET_MATRIX3_VECTOR = "set_vector_matrix3";
	private static String METHOD_INVERT = "invert";
	private static String METHOD_INVERT_MATRIX4 = "invert_matrix4";
	private static String METHOD_MUL_SCALAR = "mul_scalar";
	private static String METHOD_MUL_SCALAR_MATRIX4 = "mul_scalar_matrix4";
	private static String METHOD_MUL_MATRIX4 = "mul_matrix4";
	private static String METHOD_MUL_MATRIX4A_MATRIX4B = "mul_matrix4a_matrix4b";
	private static String METHOD_MUL_MATRIX4B_MATRIX4A = "mul_matrix4b_matrix4a";
	private static String METHOD_SUB_MATRIX4A_MATRIX4B = "sub_matrix4a_matrix4b";
	private static String METHOD_SUB_MATRIX4B_MATRIX4A = "sub_matrix4b_matrix4a";
	private static String METHOD_SUB_MATRIX4 = "sub_matrix4";
	private static String METHOD_TRANSFORM_VECTOR = "transform_vector";
	private static String METHOD_TRANSFORMVEC_VECTOR_MATRIX4 = "transformvec_vector_matrix4";
	private static String METHOD_TRANSFORMVEC_MATRIX4_VECTOR = "transformvec_matrix4_vector";
	
	
	private static final String[] INLET_ASSIST = new String[]{
		"method value 1",
		"method value 2",
		"method value 3",
		"init",
		
	};
	private static final String[] OUTLET_ASSIST = new String[]{
		"matrix4",
		"matrix3",
		"vector"	};
	
	private static int INITINLET = 3; // the forth inlet
	private static int INLETVALUES = 4; // has to be the count of inlets (4)
	
	private static int OUTLET_MATRIX4 = 0;
	private static int OUTLET_MATRIX3 = 1;
	private static int OUTLET_VECTOR = 2;
	
	String myMethod;
	Matrix4f myMatrix;
	private boolean flush = false;
	

	float[] inputFloat;
	Matrix4f[] inputMatrix4f;
	Matrix3f[] inputMatrix3f;
	Quat4f[] inputQuat4f;
	AxisAngle4f[] inputAxisAngle4f;
	Vector3f[] inputVector3f;

	public MathMatrix4(){
		this(METHOD_FLUSH , 0);
	}

	public MathMatrix4(String method){
		this(method, 0);
	}
		
	public MathMatrix4(String method, int f){
		myMethod = method;
		if(f==1)
			flush = true;
		declareInlets(new int[]{DataTypes.ALL, DataTypes.ALL, DataTypes.ALL, DataTypes.ALL});
		declareOutlets(new int[]{DataTypes.ALL, DataTypes.ALL, DataTypes.ALL});
		
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		
		myMatrix = new Matrix4f();
		inputFloat = new float[INLETVALUES];
		inputMatrix4f = new Matrix4f[INLETVALUES];
		inputMatrix3f = new Matrix3f[INLETVALUES];
		inputQuat4f = new Quat4f[INLETVALUES];
		inputAxisAngle4f = new AxisAngle4f[INLETVALUES];
		inputVector3f = new Vector3f[INLETVALUES];
		for(int i=0; i < INLETVALUES; i++){
			inputFloat[i] = 0;
			inputMatrix4f[i] = new Matrix4f();
			inputMatrix3f[i] = new Matrix3f();
			inputQuat4f[i] = new Quat4f();
			inputAxisAngle4f[i] = new AxisAngle4f();
			inputVector3f[i] = new Vector3f();
		}
		
	}
	
	public void matrices_matrix4x4(Atom a[]){
		if(a.length == 16){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputMatrix4f[getInlet()] = new Matrix4f(mat);
			if(getInlet() == INITINLET){
				myMatrix = inputMatrix4f[INITINLET];
			}  
			if(getInlet() == 0){
				leftInlet();
			}
		}
	}

	public void matrices_quaternion(Atom a[]){
		if(a.length == 4){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputQuat4f[getInlet()] = new Quat4f(mat);
			if(getInlet() == 0){
				leftInlet();
			}
		}
	}

	public void matrices_matrix3x3(Atom a[]){
		if(a.length == 9){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputMatrix3f[getInlet()] = new Matrix3f(mat);
			if(getInlet() == 0){
				leftInlet();
			}
		}
	}

	public void matrices_axisangle(Atom a[]){
		if(a.length == 4){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputAxisAngle4f[getInlet()] = new AxisAngle4f(mat);
			if(getInlet() == 0){
				leftInlet();
			}
		}
	}

	public void matrices_vector(Atom a[]){
		if(a.length == 3){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputVector3f[getInlet()] = new Vector3f(mat);
			if(getInlet() == 0){
				leftInlet();
			}
		}
	}

	public void inlet(float f){
		inputFloat[getInlet()] = f;
		if(getInlet() == 0){
			leftInlet();
		}
	}
	
	public void anything(String msg, Atom[] a){
		if(msg.equals(METHOD_FLUSH)){
			flushMatrix4(myMatrix);
		}else if(msg.equals(METHOD_FLUSH_ALL)){
			flushAll();
		}else if(msg.equals(METHOD_FLUSH_MATRIX3)){
			flushMatrix3(myMatrix);
		}else if(msg.equals(METHOD_FLUSH_VECTOR)){
			flushVector(myMatrix);
		}else{
			myMethod = msg;
		}
		
		if(getInlet() == 0){
			calculate();
		}
	}
	
	private void leftInlet(){
		calculate();
		if(flush){			
			flushMatrix4(myMatrix);
		}
	}

	private void flushAll(){
		flushMatrix4(myMatrix);
		flushMatrix3(myMatrix);
		flushVector(myMatrix);
	}
	
	private void flushMatrix4(Matrix4f m){
		outlet(OUTLET_MATRIX4, Matrix4x4.MYMESSAGENAME , Atom.newAtom(getfloat(m)));
	}
	
	private void flushMatrix3(Matrix4f m){
		Matrix3f n = new Matrix3f();
		m.getRotationScale(n);
		flushMatrix3(n);
	}
	
	private void flushMatrix3(Matrix3f m){
		outlet(OUTLET_MATRIX3, Matrix3x3.MYMESSAGENAME , Atom.newAtom(getfloat(m)));
	}
	
	private void flushVector(Matrix4f m){
		Vector3f v = new Vector3f();
		m.get(v);
		flushVector(v);
	}
	
	private void flushVector(Vector3f v){
		outlet(OUTLET_VECTOR, Vector.MYMESSAGENAME , Atom.newAtom(getfloat(v)));
	}
	
	private void calculate(){
		if(myMethod.equals(METHOD_ADD_MATRIX4_MATRIX4)){
			myMatrix.add(this.inputMatrix4f[0], this.inputMatrix4f[1]);
		}else if(myMethod.equals(METHOD_ADD_SCALAR_MATRIX4)){
			myMatrix.add(this.inputFloat[0], this.inputMatrix4f[1]);
		}else if(myMethod.equals(METHOD_ADD_SCALAR)){
			myMatrix.add(this.inputFloat[0]);
		}else if(myMethod.equals(METHOD_MUL_MATRIX4A_MATRIX4B)){
			myMatrix.mul(this.inputMatrix4f[0], this.inputMatrix4f[1]);
		}else if(myMethod.equals(METHOD_MUL_MATRIX4B_MATRIX4A)){
			myMatrix.mul(this.inputMatrix4f[1], this.inputMatrix4f[0]);
		}else if(myMethod.equals(METHOD_MUL_SCALAR_MATRIX4)){
			myMatrix.mul(this.inputFloat[0], this.inputMatrix4f[1]);
		}else if(myMethod.equals(METHOD_MUL_MATRIX4)){
			myMatrix.mul(this.inputMatrix4f[0]);
		}else if(myMethod.equals(METHOD_MUL_SCALAR)){
			myMatrix.mul(this.inputFloat[0]);
		}else if(myMethod.equals(METHOD_INVERT)){
			myMatrix.invert();
		}else if(myMethod.equals(METHOD_INVERT_MATRIX4)){
			myMatrix.invert(this.inputMatrix4f[0]);
			flushMatrix4(myMatrix);
		}else if(myMethod.equals(METHOD_SUB_MATRIX4A_MATRIX4B)){
			myMatrix.sub(this.inputMatrix4f[0], this.inputMatrix4f[1]);
		}else if(myMethod.equals(METHOD_SUB_MATRIX4B_MATRIX4A)){
			myMatrix.sub(this.inputMatrix4f[1], this.inputMatrix4f[0]);
		}else if(myMethod.equals(METHOD_SUB_MATRIX4)){
			myMatrix.sub(this.inputMatrix4f[0]);
		}else if(myMethod.equals(METHOD_TRANSFORM_VECTOR)){
			Vector3f v = new Vector3f(this.inputVector3f[0]);
			myMatrix.transform(v);
			flushVector(v);
		}else if(myMethod.equals(METHOD_TRANSFORMVEC_MATRIX4_VECTOR)){
			Vector3f v = new Vector3f(this.inputVector3f[1]);
			myMatrix = this.inputMatrix4f[0];
			myMatrix.transform(v);
			flushVector(v);
		}else if(myMethod.equals(METHOD_TRANSFORMVEC_VECTOR_MATRIX4)){
			Vector3f v = new Vector3f(this.inputVector3f[0]);
			myMatrix = this.inputMatrix4f[1];
			myMatrix.transform(v);
			flushVector(v);
		}else if(myMethod.equals(METHOD_SET_MATRIX3_VECTOR)){
			myMatrix.set(this.inputMatrix3f[0], this.inputVector3f[1], 0);
		}else if(myMethod.equals(METHOD_SET_MATRIX3)){
			myMatrix.set(this.inputMatrix3f[0]);
		}else if(myMethod.equals(METHOD_SET_VECTOR)){
			myMatrix.set(this.inputVector3f[0]);
		}
	}

	private float[] getfloat(Matrix4f m){
		return new float[]{
			m.m00, m.m01, m.m02, m.m03,
			m.m10, m.m11, m.m12, m.m13,
			m.m20, m.m21, m.m22, m.m23,
			m.m30, m.m31, m.m32, m.m33,
		};
	}

	private float[] getfloat(Matrix3f m){
		return new float[]{
			m.m00, m.m01, m.m02,
			m.m10, m.m11, m.m12,
			m.m20, m.m21, m.m22
		};
	}

	private float[] getfloat(Vector3f m){
		return new float[]{
			m.x, m.y, m.z
		};
	}

	private float[] getfloat(Quat4f m){
		return new float[]{
			m.w, m.x, m.y, m.z
		};
	}

	
}
