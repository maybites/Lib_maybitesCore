package ch.maybites.tools.threedee;

/*
 * Mathematik
 *
 * Copyright (C) 2012 Martin Fršhlich
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * {@link http://www.gnu.org/licenses/lgpl.html}
 *
 */

import ch.maybites.tools.math.la.Matrix4x4f;
import ch.maybites.tools.Const;

public class Frustum {
	
	private float left, right, bottom, top, zNear, zFar;
	
	private float fovy, aspectRatio;
	
	private boolean isOrtho = false;

	/**
	 * Creates a standard Frustum
	 */
	public Frustum(){
		reset();
	}

	/**
	 * Creates a Perspective Frustum Instance based on the provided value array
	 * 
	 * @param float array [_left, _right, _bottom, _top, _zNear, _zFar]
	 */
	public Frustum(float[] _values){
		left = _values[0];
		right = _values[1];
		bottom = _values[2];
		top = _values[3];
		zNear = _values[4];
		zFar = _values[5];	
		updateFovy();
	}

	/**
	 * Creates a Frustum Instance based on the provided frustum values
	 * 
	 * @param _isOrtho	if true sets this Frustum orthogonal
	 * @param _left
	 * @param _right
	 * @param _bottom
	 * @param _top
	 * @param _zNear
	 * @param _zFar
	 */
	public Frustum(boolean _isOrtho, float _left, float _right,
			float _bottom, float _top,
			float _zNear, float _zFar){
		isOrtho = _isOrtho;
		left = _left;
		right = _right;
		bottom = _bottom;
		top = _top;
		zNear = _zNear;
		zFar = _zFar;	
		updateFovy();
	}
	
	/**
	 * Creates a perspective frustum instance based on fov and aspectratio
	 * @param _fovy
	 * @param _aspectRatio
	 * @param _zNear
	 * @param _zFar
	 */
	public Frustum(float _fovy, float _aspectRatio,
			float _zNear, float _zFar){
		fovy = _fovy;
		aspectRatio = _aspectRatio;
		zNear = _zNear;
		zFar = _zFar;	
		update();
	}
	
	/**
	 * Creates a frustum instance based on a projection matrix. If the matrix is not a valid
	 * projection matrix it will be set to a standard frustum
	 * @param fm
	 */
	public Frustum(Matrix4x4f fm){
	    if (fm.getElement(0, 3)!=0.0 || 
	    		fm.getElement(1, 3)!=0.0 || 
	    		fm.getElement(2, 3)!=-1.0 || 
	    		fm.getElement(3, 3)!=0.0) {
		    reset();
	    } else {
	    	// only othografical projection matrices have this fields set
	    	if(fm.getElement(3, 0) != 0.0 || fm.getElement(3, 1) != 0.0){
		        zNear = (fm.getElement(3, 2)+1.0f) / fm.getElement(2, 2);
		        zFar = (fm.getElement(3, 2)-1.0f) / fm.getElement(2, 2);

		        left = -(1.0f+fm.getElement(3, 0)) / fm.getElement(0, 0);
		        right = (1.0f-fm.getElement(3, 0)) / fm.getElement(0, 0);

		        bottom = -(1.0f+fm.getElement(3, 1)) / fm.getElement(1, 1);
		        top = (1.0f-fm.getElement(3, 1)) / fm.getElement(1,1);
		        
		        isOrtho = true;
	    	} else {
			    zNear = fm.getElement(3, 2) / (fm.getElement(2, 2)-1.0f);
			    zFar = fm.getElement(3, 2) / (1.0f+fm.getElement(2, 2));

			    left = zNear * (fm.getElement(2, 0)-1.0f) / fm.getElement(0,0);
			    right = zNear * (1.0f+fm.getElement(2,0)) / fm.getElement(0,0);

			    top = zNear * (1.0f+fm.getElement(2, 1)) / fm.getElement(1,1);
			    bottom = zNear * (fm.getElement(2,1)-1.0f) / fm.getElement(1,1);
			    updateFovy();
	    	}
	    }
	}
	
	public boolean isOrtho(){
		return isOrtho;
	}


	/**
	 * Returns the Frustum values in form of an float array
	 * 
	 * return float array [_left, _right, _bottom, _top, _zNear, _zFar]
	 */
	public float[] get(){
		float[] _values = new float[6];
		_values[0] = left;
		_values[1] = right;
		_values[2] = bottom;
		_values[3] = top;
		_values[4] = zNear;
		_values[5] = zFar;	
		return _values;

	}
	
	public float getLeft(){
		return left;
	}
	
	public float getRight(){
		return right;
	}

	public float getBottom(){
		return bottom;
	}

	public float getTop(){
		return top;
	}

	public float getNear(){
		return zNear;
	}

	public float getFar(){
		return zFar;
	}

	public float getFOV(){
		return fovy;
	}
	
	public float getAspectRatio(){
		return aspectRatio;
	}
	
	public Matrix4x4f getProjectionMatrix(){
		return new Matrix4x4f(this);
	}

	private void reset(){
		left = -0.05f;
		right = 0.05f;
		bottom = -0.05f;
		top = 0.05f;
		zNear = .1f;
		zFar = 100.0f;	
		updateFovy();
	}
	
	private void update(){
	    // calculate the appropriate left, right etc.
	    float tan_fovy = (float)Math.tan(fovy*0.5f*Const.DEG_TO_RAD);
	    right  =  tan_fovy * aspectRatio * zNear;
	    left   = -right;
	    top    =  tan_fovy * zNear;
	    bottom =  -top;
	}
	
	private void updateFovy(){
		fovy = (float)((Math.atan(top/zNear)-Math.atan(bottom/zNear))*Const.RAD_TO_DEG);
	    aspectRatio = (right-left)/(top-bottom); 
	}

}
