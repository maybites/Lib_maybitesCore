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
		position = new Vector3f(.0f, .0f, .0f);
		orientation = new Quaternionf(.0f, .0f, .0f);
		scale = new Vector3f(1.0f, 1.0f, 1.0f);
		createMatrix();
	}

	/**
	 * Uses the provided instance as the local position. 
	 * @param pos sets its as position
	 */
	public void setPosition(Vector3f pos){
		position = pos;
		createMatrix();
	}
	
	/**
	 * Sets the local orientation in euler angles (in degrees)
	 * @param euler
	 */
	public void setOrientation(Vector3f euler){
		Quaternionf q = new Quaternionf(euler.x(), euler.y(), euler.z());
		setOrientation(q);
	}
	
	/**
	 * Uses the provided Instance of a Quaternion as the local orientation
	 * @param _orientation
	 */
	public void setOrientation(Quaternionf _orientation){
		orientation = _orientation;
		createMatrix();
	}
	
	/**
	 * Uses the provided Instance of the Vector as the local scale factors for each axis
	 * @param _scale
	 */
	public void setScale(Vector3f _scale){
		scale = _scale;
		createMatrix();
	}

	/**
	 * stets this nodes parent node
	 * @param _parent
	 */
	public void setParent(Node _parent){
		parent = _parent;
	}
	
	/** 
	 * Returns this node's parent node
	 * 
	 * @return parent node
	 */
	public Node getParent(){
		return parent;
	}
	
	/**
	 * clears this node's parent
	 */
	public void clearParent(){
		parent = null;
	}

	/**
	 * moves this node in direction to the provided local axis values
	 * 
	 * @param x
	 * @param y
	 * @param z
	 */
	public void move(float x, float y, float z) {
		move(new Vector3f(x, y, z));
	}


	/**
	 * moves this node in direction to the provided local vector
	 * 
	 * @param offset
	 */
	public void move(Vector3f offset) {
		position.add(offset);
		localTransfromMatrix.setTranslation(position);
	}


	public Matrix4x4f getLocalTransformationMatrix(){
		return localTransfromMatrix;
	}
	
	public Matrix4x4f getGlobalTransfromationMatrix(){
		if(parent != null)
			// is this correct? 
			//return parent.getGlobalTransfromationMatrix().makeMultiply(localTransfromMatrix);
			return localTransfromMatrix.makeMultiply(parent.getGlobalTransfromationMatrix());
		else
			return localTransfromMatrix;
	}
	
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
