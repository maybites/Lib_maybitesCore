package ch.maybites.mxj.math.matrices;

import com.cycling74.max.MaxObject;

public class Transformations extends MaxObject{
	
	public static double RADIANFACTOR = (Math.PI / 180.0);
	
	public static int QUATERNION_W = 0;
	public static int QUATERNION_X = 1;
	public static int QUATERNION_Y = 2;
	public static int QUATERNION_Z = 3;
	
	public static int AXISANGLE_A = 0;
	public static int AXISANGLE_X = 1;
	public static int AXISANGLE_Y = 2;
	public static int AXISANGLE_Z = 3;
	
	public static int M00 = 0;
	public static int M01 = 1;
	public static int M02 = 2;
	public static int M10 = 3;
	public static int M11 = 4;
	public static int M12 = 5;
	public static int M20 = 6;
	public static int M21 = 7;
	public static int M22 = 8;

	public static float[] getMatrix4x4FromEuler(float[] e){
		float angle_x = e[0];
		float angle_y = e[1];
		float angle_z = e[2];
		
		float A       = cos_deg(angle_x);
		float B       = sin_deg(angle_x);
		float C       = cos_deg(angle_y);
		float D       = sin_deg(angle_y);
		float E       = cos_deg(angle_z);
		float F       = sin_deg(angle_z);

		float AD      =   A * D;
		float BD      =   B * D;

		float[] mat = new float[16];
	    mat[0]  =   C * E;
	    mat[1]  =  -C * F;
	    mat[2]  =  -D;
	    mat[4]  = -BD * E + A * F;
	    mat[5]  =  BD * F + A * E;
	    mat[6]  =  -B * C;
	    mat[8]  =  AD * E + B * F;
	    mat[9]  = -AD * F + B * E;
	    mat[10] =   A * C;

	    mat[3]  =  mat[7] = mat[11] = mat[12] = mat[13] = mat[14] = 0;
	    mat[15] =  1;

	    return mat;

	}

	public static float sin_deg(float angle){
		return (float) Math.sin((double)angle * RADIANFACTOR);
	}
	
	public static float cos_deg(float angle){
		return (float) Math.cos((double)angle * RADIANFACTOR);
	}

	public static float[] normalizeQuaternion(float[] q){
		float t = sqrt(q[0]*q[0] + q[1]*q[1] + q[2]*q[2] + q[3]*q[3]);
		return new float[]{q[0]/t, q[1]/t, q[2]/t, q[3]/t};
	}
	
	public static float sqrt(float value){
		return (float) Math.sqrt((double)value);
	}
	
	public static float[] getMatrix4x4FromQuaternion(float[] q){
		float w = q[0]; 
		float x = q[1]; 
		float y = q[2]; 
		float z = q[3];
		float xx      = x * x;
		float xy      = x * y;
		float xz      = x * z;
		float xw      = x * w;

		float yy      = y * y;
		float yz      = y * z;
		float yw      = y * w;

		float zz      = z * z;
		float zw      = z * w;
		
		float[] mat = new float[16];

		
		mat[0]  = 1 - 2 * ( yy + zz );
	    mat[1]  =     2 * ( xy - zw );
	    mat[2]  =     2 * ( xz + yw );

	    mat[4]  =     2 * ( xy + zw );
	    mat[5]  = 1 - 2 * ( xx + zz );
	    mat[6]  =     2 * ( yz - xw );

	    mat[8]  =     2 * ( xz - yw );
	    mat[9]  =     2 * ( yz + xw );
	    mat[10] = 1 - 2 * ( xx + yy );

	    mat[3] = mat[7] = mat[11] = mat[12] = mat[13] = mat[14] = 0;
	    mat[15] = 1.0f;
	    
	    return mat;
		
	}

	public static float[] resizeMatrix3x3To4x4(float[] mat){
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
	
	public static float[] resizeMatrix4x4To3x3(float[] mat){
		float[] ret = new float[9]; 
		ret[0] = mat[0];
		ret[1] = mat[1];
		ret[2] = mat[2];
		ret[3] = mat[4];
		ret[4] = mat[5];
		ret[5] = mat[6];
		ret[6] = mat[8];
		ret[7] = mat[9];
		ret[8] = mat[10];
		return ret;
	}

	public static float[] getQuaternionFromMatrix4x4(float[] mat){
		float w, x, y, z, s;
		float t = mat[0] + mat[5] + mat[10] + 1;
		if(t > 0){
		      s = 0.5f / sqrt(t);
		      w = 0.25f / s;
		      x = ( mat[9] - mat[6] ) * s;
		      y = ( mat[2] - mat[8] ) * s;
		      z = ( mat[4] - mat[1] ) * s;

		} else {
			if ((mat[0] > mat[5]) && (mat[0] > mat[10])) { 
		        s  = sqrt( 1.0f + mat[0] - mat[5] - mat[10] ) * 2;
		        x = 0.25f / s;
		        y = (mat[1] + mat[4] ) / s;
		        z = (mat[2] + mat[8] ) / s;
		        w = (mat[6] - mat[9] ) / s;

			} else if(mat[5] > mat[10]) { 
		        s  = sqrt( 1.0f + mat[5] - mat[0] - mat[10] ) * 2;
		        x = (mat[1] + mat[4] ) / s;
		        y = 0.25f / s;
		        z = (mat[6] + mat[9] ) / s;
		        w = (mat[2] - mat[8] ) / s;

			} else { 
				s  = sqrt( 1.0f + mat[10] - mat[0] - mat[5] ) * 2;
				x = (mat[2] + mat[8] ) / s;
				y = (mat[6] + mat[9] ) / s;
				z = 0.25f / s;
				w = (mat[1] - mat[4] ) / s;
			} 
		}
		return new float[]{w, x, y, z};
	}

	public static float[] getAxisAngleFromQuaternion(float[] mat){
		float[] n = mat;
		float ret[] = new float[4];
		if(mat[QUATERNION_W] > 1)
			n = normalizeQuaternion(mat);
		ret[AXISANGLE_A] = 2 * (float)Math.acos(n[QUATERNION_W]) / (float)RADIANFACTOR;
		float s = (float)Math.sqrt(1-n[QUATERNION_W]*n[QUATERNION_W]); // assuming quaternion normalised then w is less than 1, so term always positive.
		if (s < 0.001) { // test to avoid divide by zero, s is always positive due to sqrt
		     // if s close to zero then direction of axis not important
			ret[AXISANGLE_X] = n[QUATERNION_X]; // if it is important that axis is normalised then replace with x=1; y=z=0;
			ret[AXISANGLE_Y] = n[QUATERNION_Y];
			ret[AXISANGLE_Z] = n[QUATERNION_Z];
		} else {
			ret[AXISANGLE_X] = n[QUATERNION_X] / s; // normalise axis
			ret[AXISANGLE_Y] = n[QUATERNION_Y] / s;
			ret[AXISANGLE_Z] = n[QUATERNION_Z] / s;
		}
		return ret;
	}
	
	

	public static float[] getMatrix3x3FromAxisAngle(float[] axis) {
		 	float[] ret = new float[9];
		 
		    float c = cos_deg(axis[AXISANGLE_A]);
		    float s = sin_deg(axis[AXISANGLE_A]);
		    float t = (float)1.0 - c;
		    ret[M00] = c + axis[AXISANGLE_X]*axis[AXISANGLE_X]*t;
		    ret[M11] = c + axis[AXISANGLE_Y]*axis[AXISANGLE_Y]*t;
		    ret[M22] = c + axis[AXISANGLE_Z]*axis[AXISANGLE_Z]*t;


		    float tmp1 = axis[AXISANGLE_X]*axis[AXISANGLE_Y]*t;
		    float tmp2 = axis[AXISANGLE_Z]*s;
		    ret[M10] = tmp1 + tmp2;
		    ret[M01] = tmp1 - tmp2;

		    tmp1 = axis[AXISANGLE_X]*axis[AXISANGLE_Z]*t;
		    tmp2 = axis[AXISANGLE_Y]*s;
		    ret[M20] = tmp1 - tmp2;
		    ret[M02] = tmp1 + tmp2;

		    tmp1 = axis[AXISANGLE_Y]*axis[AXISANGLE_Z]*t;
		    tmp2 = axis[AXISANGLE_X]*s;
		    ret[M21] = tmp1 + tmp2;
		    ret[M12] = tmp1 - tmp2;
		    return ret;
	 }

	public static float[] getMatrix4x4FromVector(float[] vec){
		float[] ret = new float[16]; 
		ret[3] = vec[0];
		ret[7] = vec[1];
		ret[11] = vec[2];
		ret[0] = ret[5] = ret[10] = ret[15] = 1.0f;
		ret[1] = ret[2] = ret[4]	= ret[6] = ret[8] = 
			ret[9] = ret[12] = ret[13] = ret[14] = 0;
		return ret;

	}

	public static float[] getVectorFromMatrix4x4(float[] mat){
		float[] ret = new float[3];
		ret[0] = mat[3];
		ret[1] = mat[7];
		ret[2] = mat[11];
		return ret;
	}
	
	public float[] getEulerFromAxisAngle(float[] m) {
		float x = m[AXISANGLE_X];
		float y = m[AXISANGLE_Y];
		float z = m[AXISANGLE_Z];
		float a = m[AXISANGLE_A];
		
		
		float s= sin_deg(a);
		float c= cos_deg(a);
		float t=1-c;
		float heading;
		float attitude;
		float bank;
		if ((x*y*t + z*s) > 0.998) { // north pole singularity detected
			heading = 2*(float)Math.atan2(x*sin_deg(a/2),cos_deg(a/2))/(float)RADIANFACTOR;
			attitude = (float)Math.PI/2/(float)RADIANFACTOR;
			bank = 0;
			return new float[]{heading, attitude, bank};
		}
		if ((x*y*t + z*s) < -0.998) { // south pole singularity detected
			heading = -2*(float)Math.atan2(x*sin_deg(a/2),cos_deg(a/2))/(float)RADIANFACTOR;
			attitude = -(float)Math.PI/2/(float)RADIANFACTOR;
			bank = 0;
			return new float[]{heading, attitude, bank};
		}

		heading = (float)Math.atan2(y * s- x * z * t , 1 - (y*y+ z*z ) * t)/(float)RADIANFACTOR; 
		attitude = (float)Math.asin(x * y * t + z * s)/(float)RADIANFACTOR; 
		bank = (float)Math.atan2(x * s - y * z * t , 1 - (x*x + z*z) * t)/(float)RADIANFACTOR; 
		return new float[]{heading, attitude, bank};
	}
	
	public float[] getEulerFromMatrix3x3(float[]  m) {
		float heading, attitude, bank;
	    // Assuming the angles are in radians.
		if (m[M10] > 0.998) { // singularity at north pole
			heading = (float)Math.atan2(m[M02],m[M22])/(float)RADIANFACTOR;
			attitude = 90.0f;
			bank = 0;
			return new float[]{heading, attitude, bank};
		}
		if (m[M10] < -0.998) { // singularity at south pole
			heading = (float)Math.atan2(m[M02],m[M22])/(float)RADIANFACTOR;
			attitude = -90.0f;
			bank = 0;
			return new float[]{heading, attitude, bank};
		}
		heading = (float)Math.atan2(-m[M20],m[M00])/(float)RADIANFACTOR;
		bank = (float)Math.atan2(-m[M12],m[M11])/(float)RADIANFACTOR;
		attitude = (float)Math.asin(m[M10])/(float)RADIANFACTOR;
		return new float[]{heading, attitude, bank};
	}



}
