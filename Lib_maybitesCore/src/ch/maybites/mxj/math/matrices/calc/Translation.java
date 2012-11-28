package ch.maybites.mxj.math.matrices.calc;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class Translation extends MaxObject{

		private static final String[] INLET_ASSIST = new String[]{
			"inlet vector",
			"inlet matrix"
		};
		private static final String[] OUTLET_ASSIST = new String[]{
			"outlet 1 help"
		};
		
		static String MYMESSAGENAME = "matrices_vector";

		String matrix3, matrix4, vector, quaternion;	

		float[] myMatrix, myVector;

		public Translation(Atom[] args)
		{
			declareInlets(new int[]{DataTypes.ALL, DataTypes.ALL});
			declareOutlets(new int[]{DataTypes.ALL});
			
			setInletAssist(INLET_ASSIST);
			setOutletAssist(OUTLET_ASSIST);

			myMatrix = new float[16];
			myVector = new float[4];

		}
	    
		public void matrices_matrix4x4(Atom a[]){
			if(a.length == 16){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = mat;
				if(getInlet() == 0){
					outlet(0, MYMESSAGENAME, Atom.newAtom(getVector(myVector, myMatrix)));
				}
			}
		}
		
		public void matrices_matrix3x3(Atom a[]){
			if(a.length == 9){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = resizeMatrix3x3To4x4(mat);
				if(getInlet() == 0){
					outlet(0, MYMESSAGENAME, Atom.newAtom(getVector(myVector, myMatrix)));
				}
			}
		}

		public void matrices_vector(Atom a[]){
			if(a.length == 4){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myVector = mat;
				if(getInlet() == 0){
					outlet(0, MYMESSAGENAME, Atom.newAtom(getVector(myVector, myMatrix)));
				}
			}
		}

		private float[] resizeMatrix3x3To4x4(float[] mat){
			float[] ret = new float[16]; 
			ret[0] = mat[0];
			ret[1] = mat[1];
			ret[2] = mat[2];
			ret[4] = mat[3];
			ret[5] = mat[4];
			ret[6] = mat[5];
			ret[8] = mat[6];
			ret[9] = mat[7];
			ret[10] = mat[8];
			ret[15] = 1.0f;
			ret[3] = ret[7] = ret[11] = ret[12] = ret[13] = ret[14] = 0;
			return ret;
		}

		private float[] getVector(float[] v, float[] m){
			float[] ret = new float[4];
			ret[0] = m[0] * v[0] + m[1] * v[1] + m[2] * v[2] + m[3] * v[3];
			ret[1] = m[4] * v[0] + m[5] * v[1] + m[6] * v[2] + m[7] * v[3];
			ret[2] = m[8] * v[0] + m[9] * v[1] + m[10] * v[2] + m[11] * v[3];
			ret[3] = m[12] * v[0] + m[13] * v[1] + m[14] * v[2] + m[15] * v[3];
			return ret;
		}

		public void bang()
		{
		}
	    
	}



