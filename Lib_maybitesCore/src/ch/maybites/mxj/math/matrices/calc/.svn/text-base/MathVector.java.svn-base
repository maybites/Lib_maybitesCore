package ch.maybites.mxj.math.matrices.calc;

import javax.vecmath.*;

import ch.maybites.mxj.math.matrices.Matrix3x3;
import ch.maybites.mxj.math.matrices.Matrix4x4;
import ch.maybites.mxj.math.matrices.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class MathVector extends MaxObject {

	private static String METHOD_FLUSH = "flush";
	private static String METHOD_ANGLERAD_VECTOR_VECTOR = "anglerad_vector_vector";
	private static String METHOD_ANGLERAD_VECTOR = "anglerad_vector";
	private static String METHOD_ANGLEDEG_VECTOR_VECTOR = "angledeg_vector_vector";
	private static String METHOD_ANGLEDEG_VECTOR = "angledeg_vector";
	private static String METHOD_CROSS_VECTORA_VECTORB = "cross_vectora_vectorb";
	private static String METHOD_CROSS_VECTORB_VECTORA = "cross_vectorb_vectora";
	private static String METHOD_DOT_VECTOR = "dot_vector";
	private static String METHOD_DOT_VECTOR_VECTOR = "dot_vector_vector";
	private static String METHOD_LENGTH = "length";
	private static String METHOD_LENGTH_VECTOR = "length_vector";
	private static String METHOD_NORMALIZE = "normalize";
	private static String METHOD_NORMALIZE_VECTOR = "normalize_vector";
	private static String METHOD_ADD_VECTOR_VECTOR = "add_vector_vector";
	private static String METHOD_SUB_VECTORA_VECTORB = "sub_vectora_vectorb";
	private static String METHOD_SUB_VECTORB_VECTORA = "sub_vectorb_vectora";
	private static String METHOD_SCALE_VECTOR_FLOAT = "scale_vector_float";
	private static String METHOD_SCALE_FLOAT_VECTOR = "scale_float_vector";
	
	
	private static final String[] INLET_ASSIST = new String[]{
		"method value 1",
		"method value 2"
	};
	
	private static final String[] OUTLET_ASSIST = new String[]{
		"vector",
		"float"
	};
	
	private static int INITINLET = 2; // the third inlet
	private static int INLETVALUES = 3; // has to be the count of inlets (4)
	
	private static int OUTLET_VECTOR = 0;
	private static int OUTLET_FLOAT = 1;
	
	private static float RAD2DEG_FACTOR = 180.0f / (float)Math.PI;
	
	String myMethod;
	Vector3f myVector;

	float[] inputFloat;
	Vector3f[] inputVector3f;

	public MathVector(){
		this(METHOD_FLUSH);
	}
		
	public MathVector(String method){
		myMethod = method;
		declareInlets(new int[]{DataTypes.ALL, DataTypes.ALL, DataTypes.ALL});
		declareOutlets(new int[]{DataTypes.ALL, DataTypes.FLOAT});
		
		setInletAssist(INLET_ASSIST);
		setOutletAssist(OUTLET_ASSIST);
		
		myVector = new Vector3f();
		inputFloat = new float[INLETVALUES];
		inputVector3f = new Vector3f[INLETVALUES];
		for(int i=0; i < INLETVALUES; i++){
			inputFloat[i] = 0;
			inputVector3f[i] = new Vector3f();
		}
		
	}

	public void matrices_vector(Atom a[]){
		if(a.length == 3){
			float[] mat = new float[a.length];
			for(int i = 0; i < a.length; i++){
				mat[i] = a[i].getFloat();
			}
			inputVector3f[getInlet()] = new Vector3f(mat);
			if(getInlet() == INITINLET){
				myVector = inputVector3f[getInlet()];
			}
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
			flushVector(myVector);
		}else{
			myMethod = msg;
		}
		
		if(getInlet() == 0){
			calculate();
		}
	}
	
	private void leftInlet(){
		calculate();
	}
		
	private void flushVector(Vector3f v){
		outlet(OUTLET_VECTOR, Vector.MYMESSAGENAME , Atom.newAtom(getfloat(v)));
	}
	
	private void flushFloat(float v){
		outlet(OUTLET_FLOAT, Atom.newAtom(v));
	}
	
	private void calculate(){
		if(myMethod.equals(METHOD_ANGLERAD_VECTOR)){
			float a = myVector.angle(this.inputVector3f[0]);
			flushFloat(a);
		}else if(myMethod.equals(METHOD_ANGLERAD_VECTOR_VECTOR)){
			myVector = this.inputVector3f[0];
			float a = myVector.angle(this.inputVector3f[1]);
			flushFloat(a);
		}else if(myMethod.equals(METHOD_ANGLEDEG_VECTOR)){
			float a = myVector.angle(this.inputVector3f[0]);
			flushFloat(a * RAD2DEG_FACTOR);
		}else if(myMethod.equals(METHOD_ANGLEDEG_VECTOR_VECTOR)){
			myVector = this.inputVector3f[0];
			float a = myVector.angle(this.inputVector3f[1]);
			flushFloat(a * RAD2DEG_FACTOR);
		}else if(myMethod.equals(METHOD_CROSS_VECTORA_VECTORB)){
			myVector.cross(this.inputVector3f[0], this.inputVector3f[1]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_CROSS_VECTORB_VECTORA)){
			myVector.cross(this.inputVector3f[1], this.inputVector3f[0]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_DOT_VECTOR_VECTOR)){
			myVector = this.inputVector3f[0];
			float d = myVector.dot(this.inputVector3f[1]);
			flushFloat(d);
		}else if(myMethod.equals(METHOD_DOT_VECTOR)){
			float d = myVector.dot(this.inputVector3f[0]);
			flushFloat(d);
		}else if(myMethod.equals(METHOD_LENGTH)){
			float l = myVector.length();
			flushFloat(l);
		}else if(myMethod.equals(METHOD_LENGTH_VECTOR)){
			myVector = this.inputVector3f[0];
			float l = myVector.length();
			flushFloat(l);
		}else if(myMethod.equals(METHOD_NORMALIZE)){
			myVector.normalize();
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_NORMALIZE_VECTOR)){
			myVector.normalize(this.inputVector3f[0]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_ADD_VECTOR_VECTOR)){
			myVector = this.inputVector3f[0];
			myVector.add(inputVector3f[1]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_SUB_VECTORA_VECTORB)){
			myVector.sub(inputVector3f[0], inputVector3f[1]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_SUB_VECTORB_VECTORA)){
			myVector.sub(inputVector3f[1], inputVector3f[0]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_SCALE_VECTOR_FLOAT)){
			myVector = this.inputVector3f[0];
			myVector.scale(inputFloat[1]);
			flushVector(myVector);
		}else if(myMethod.equals(METHOD_SCALE_FLOAT_VECTOR)){
			myVector = this.inputVector3f[1];
			myVector.scale(inputFloat[0]);
			flushVector(myVector);
		}
		
	}

	private float[] getfloat(Vector3f m){
		return new float[]{
			m.x, m.y, m.z
		};
	}
	
}
