package ch.maybites.tools.threedee;

public class Viewport {

	/**
	 * Width of viewport
	 */
	public float width;
	
	/**
	 * Height of viewport
	 */
	public float height;
	
	/**
	 * lower Left Corner of viewport
	 */
	public float posX;
	
	/**
	 * lower Left Corner of viewport
	 */
	public float posY;

	/**
	 * Create Viewport with normalized values
	 */
	public Viewport(){
		width = 1.f;
		height = 1.f;
	}

	/**
	 * Create Viewport
	 * 
	 * @param _width
	 * @param _height
	 */
	public Viewport(float _width, float _height){
		width = _width;
		height = _height;
	}
	
	/**
	 * Create Viewport
	 * 
	 * @param _posX lower left corner
	 * @param _posY lower left corner
	 * @param _width
	 * @param _height
	 */
	public Viewport(float _posX, float _posY, float _width, float _height){
		posX = _posX;
		posY = _posY;
		width = _width;
		height = _height;
	}
	
	/**
	 * Viewport coordinates in form of a float array, which sets the 
	 * left(=xPos), bottom(=yPos), width, and height of the viewport
	 * @param _viewport
	 */
	public Viewport(float[] _viewport){
		posX = _viewport[0];
		posY = _viewport[1];
		width = _viewport[2];
		height = _viewport[3];
	}

	public Viewport clone(){
		return new Viewport(posX, posY, width, height);
	}
}
