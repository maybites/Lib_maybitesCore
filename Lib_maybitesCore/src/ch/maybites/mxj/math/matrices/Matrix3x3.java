package ch.maybites.mxj.math.matrices;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

public class Matrix3x3 extends Transformations{

		private static final String[] INLET_ASSIST = new String[]{
			"inlet message"
		};
		private static final String[] OUTLET_ASSIST = new String[]{
			"outlet 1 help"
		};
		
		public static String MYMESSAGENAME = "matrices_matrix3x3";

		String matrix3, matrix4, vector, quaternion;	
		
		float[] myMatrix;

		public Matrix3x3(Atom[] args)
		{
			declareInlets(new int[]{DataTypes.ALL});
			declareOutlets(new int[]{DataTypes.ALL});
			
			setInletAssist(INLET_ASSIST);
			setOutletAssist(OUTLET_ASSIST);
			
			myMatrix = new float[3 * 3];

		}
	    
		public void list(Atom a[]){
			if(a.length == 9){
				float[] quat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					if(a[i].isFloat()){
						quat[i] = a[i].getFloat();
					} else if(a[i].isInt()){
						quat[i] = (int)a[i].getInt();
					}else{
						post("Matrix3x3 Error: only float or int values allowed");
					}
				}
				myMatrix = quat;
					outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}else{
				post("Matrix3x3 Error: list needs 9 int or float values");
			}
		}
		
		public void matrices_quaternion(Atom a[]){
			if(a.length == 4){
				float[] quat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					quat[i] = a[i].getFloat();
				}
				quat = normalizeQuaternion(quat);
				float mtx[] = getMatrix4x4FromQuaternion(quat);
				myMatrix = resizeMatrix4x4To3x3(mtx);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}

		public void matrices_matrix4x4(Atom a[]){
			if(a.length == 16){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = resizeMatrix4x4To3x3(mat);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}

		public void matrices_matrix3x3(Atom a[]){
			if(a.length == 9){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = mat;
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}
		
		public void matrices_euler(Atom a[]){
			if(a.length == 3){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				
				float tmp[] = getMatrix4x4FromEuler(mat);
				myMatrix = resizeMatrix4x4To3x3(tmp);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}
		
		public void matrices_axisangle(Atom a[]){
			if(a.length == 4){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = getMatrix3x3FromAxisAngle(mat);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}

								
		public void bang()
		{
		}
	    
	}



