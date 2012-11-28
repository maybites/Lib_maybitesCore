package ch.maybites.mxj.math.matrices.calc;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;
import com.cycling74.max.MaxObject;

public class VectorAddition extends MaxObject{

		private static final String[] INLET_ASSIST = new String[]{
			"inlet vector",
			"inlet matrix"
		};
		private static final String[] OUTLET_ASSIST = new String[]{
			"outlet 1 help"
		};
		
		static String MYMESSAGENAME = "matrices_vector";

		String matrix3, matrix4, vector, quaternion;	

		float[] myLeftMatrix;
		float[] myRightMatrix;

		public VectorAddition(Atom[] args)
		{
			declareInlets(new int[]{DataTypes.ALL, DataTypes.ALL});
			declareOutlets(new int[]{DataTypes.ALL});
			
			setInletAssist(INLET_ASSIST);
			setOutletAssist(OUTLET_ASSIST);
			myLeftMatrix = myRightMatrix = new float[4];
		}
		

		public void matrices_vector(Atom a[]){
			if(a.length == 3){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				if(getInlet() == 0){
					myLeftMatrix = mat;
					float[] v = addVector(myLeftMatrix, myRightMatrix);
					v[3] = 1.0f; // vectors need this special correction.
					outlet(0, MYMESSAGENAME, Atom.newAtom(v));
				}else if (getInlet() == 1){
					myRightMatrix = mat;
				}
			}
		}

		private float[] addVector(float[] l, float[] r){
			float[] ret = new float[r.length];
			for(int i = 0; i < r.length; i++){
				ret[i] = l[i] + r[i];
			}
			return ret;
		}
		
	}



