package ch.maybites.mxj.math.matrices;

import javax.vecmath.Vector3f;

import com.cycling74.max.Atom;
import com.cycling74.max.DataTypes;

public class Euler extends Transformations{

		private static final String[] INLET_ASSIST = new String[]{
			"inlet message"
		};
		private static final String[] OUTLET_ASSIST = new String[]{
			"outlet 1 help"
		};
		
		public static String MYMESSAGENAME = "matrices_euler";

		String matrix3, matrix4, vector, quaternion;	

		float[] myMatrix;
		Vector3f myEuler = new Vector3f();

		public Euler(Atom[] args)
		{
			declareInlets(new int[]{DataTypes.ALL});
			declareOutlets(new int[]{DataTypes.ALL});
			
			setInletAssist(INLET_ASSIST);
			setOutletAssist(OUTLET_ASSIST);

			myMatrix = new float[3];

		}
	    
		public void list(Atom a[]){
			if(a.length == 3){
				float[] quat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					if(a[i].isFloat()){
						quat[i] = a[i].getFloat();
					} else if(a[i].isInt()){
						quat[i] = (int)a[i].getInt();
					}else{
						post("Euler Error: only float or int values allowed");
					}
				}
				myMatrix = quat;
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}else{
				post("Euler Error: list needs 3 int or float values.");
			}
		}
		

		public void matrices_matrix4x4(Atom a[]){
			if(a.length == 16){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				post("Euler Error: matrix4x4 to euler not supported");
			}
		}
		
		public void matrices_matrix3x3(Atom a[]){
			if(a.length == 9){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				myMatrix = this.getEulerFromMatrix3x3(mat);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}
		
		public void matrices_quaternion(Atom a[]){
			if(a.length == 4){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				post("Euler Error: quaternion to euler not supported");
			}
		}
		
		public void matrices_axisangle(Atom a[]){
			if(a.length == 4){
				float[] mat = new float[a.length];
				for(int i = 0; i < a.length; i++){
					mat[i] = a[i].getFloat();
				}
				
				myMatrix = this.getEulerFromAxisAngle(mat);
				outlet(0, MYMESSAGENAME, Atom.newAtom(myMatrix));
			}
		}

				
		public void bang()
		{
		}
	    
	}



