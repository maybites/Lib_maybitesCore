package ch.maybites.tools.threedee;

import ch.maybites.tools.math.la.Matrix4x4f;
import ch.maybites.tools.math.la.Quaternionf;
import ch.maybites.tools.math.la.Vector3f;

public class Node {
	
	private Vector3f position;
	private Quaternionf orientation;
	private Vector3f scale;
	
	private Vector3f[] axis;
	
	private Matrix4x4f localTransfromMatrix;
	
	protected Node parent;
	
	public Node(){
		axis = new Vector3f[3];
		setPosition(new Vector3f(.0f, .0f, .0f));
		setOrientation(new Vector3f(.0f, .0f, .0f));
		setScale(new Vector3f(1.0f, 1.0f, 1.0f));
	}
	
	/**
	 * Takes this instance of a vector and uses it as its position vector. 
	 * 
	 */
	public void setPosition(Vector3f pos){
		position = pos;
		createMatrix();
	}
	
	public void setOrientation(Vector3f euler){
		Quaternionf q = new Quaternionf(euler.x(), euler.y(), euler.z());
		setOrientation(q);
	}
	
	public void setOrientation(Quaternionf _orientation){
		orientation = _orientation;
		createMatrix();
	}
	
	public void setScale(Vector3f _scale){
		scale = _scale;
		createMatrix();
	}

	//----------------------------------------
	private void createMatrix() {
		//if(isMatrixDirty) {
		//	isMatrixDirty = false;
		localTransfromMatrix = new Matrix4x4f();
		localTransfromMatrix.scale(scale);
		localTransfromMatrix.rotate(orientation);
		localTransfromMatrix.translate(position);
		
		if(scale.x()>0){
			axis[0] = localTransfromMatrix.getRowAsVector(0).getScaled(1.0f / scale.x());
		}
		if(scale.y()>0){
			axis[1] = localTransfromMatrix.getRowAsVector(1).getScaled(1.0f / scale.y());
		}
		if(scale.z()>0){
			axis[2] = localTransfromMatrix.getRowAsVector(2).getScaled(1.0f / scale.z());
		}
	}

}
