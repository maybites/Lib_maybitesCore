package ch.maybites.mxj.math.matrices.calc;

import ch.maybites.mxj.math.matrices.Matrix3x3;
import ch.maybites.mxj.math.matrices.Matrix4x4;
import ch.maybites.mxj.math.matrices.Vector;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class Scalar extends MaxObject{

		private static final String[] INLET_ASSIST = new String[]{
			"inlet vector",
			"inlet matrix"
		};
		private static final String[] OUTLET_ASSIST = new String[]{
			"outlet 1 help"
		};
		
		static String MYMESSAGENAME = "matrices_vector";

		String matrix3, matrix4, vector, quaternion;	

		float[] myMatrix;
		float myScalar;
		String myMessageName;

		public Scalar(){
			this(1.0f);
		}
		
		public Scalar(float s)
		{
			declareInlets(new int[]{DataTypes.ALL, DataTypes.ALL});
			declareOutlets(new int[]{DataTypes.ALL});
			
			setInletAssist(INLET_ASSIST);
			setOutletAssist(OUTLET_ASSIST);
			myMatrix = new float[16];
			myMessageName = Matrix4x4.MYMESSAGENAME;
			myScalar = s;
		}
		
		public void inlet(float s){
			myScalar = s;
			if(getInlet() == 0){
				outlet(0, myMessageName, Atom.newAtom(getMatrix(myScalar, myMatrix)));
			}
		}
	    
		public void matrices_matrix4x4(Atom a[]){
			if(a.length == 16){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = mat;
				myMessageName = Matrix4x4.MYMESSAGENAME;
				if(getInlet() == 0){
					outlet(0, myMessageName, Atom.newAtom(getMatrix(myScalar, myMatrix)));
				}
			}
		}
		
		public void matrices_matrix3x3(Atom a[]){
			if(a.length == 9){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = mat;
				myMessageName = Matrix3x3.MYMESSAGENAME;
				if(getInlet() == 0){
					outlet(0, MYMESSAGENAME, Atom.newAtom(getMatrix(myScalar, myMatrix)));
				}
			}
		}

		public void matrices_vector(Atom a[]){
			if(a.length == 3){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = mat;
				myMessageName = Vector.MYMESSAGENAME;
				if(getInlet() == 0){
					float[] v = getMatrix(myScalar, myMatrix);
					outlet(0, MYMESSAGENAME, Atom.newAtom(v));
				}
			}
		}

		private float[] getMatrix(float s, float[] m){
			float[] ret = new float[m.length];
			for(int i = 0; i < m.length; i++){
				ret[i] = m[i] * s;
			}
			return ret;
		}
		
	}



