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
	 * upper Left Corner of viewport
	 */
	public float posX;
	/**
	 * upper Left Corner of viewport
	 */
	public float posY;

	/**
	 * Create Viewport with default settings
	 */
	public Viewport(){
		width = 320;
		height = 240;
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
	 * @param _posX upper left corner
	 * @param _posY upper left corner
	 * @param _width
	 * @param _height
	 */
	public Viewport(float _posX, float _posY, float _width, float _height){
		posX = _posX;
		posY = _posY;
		width = _width;
		height = _height;
	}
}
