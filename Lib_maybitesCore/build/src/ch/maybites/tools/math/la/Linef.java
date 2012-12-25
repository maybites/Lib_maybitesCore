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

package ch.maybites.tools.math.la;

public class Linef{

	public Vector3f theOrigin;

	public Vector3f direction;

	public Linef() {
		theOrigin = new Vector3f();
		direction = new Vector3f();
	}

	/**
	 * Creates a line in theOrigin with its direction
	 * 
	 * @param	theOrigin	the reference point of that line
	 * @param	theDirection	the direction vector of that line
	 */
	public Linef(Vector3f theOrigin, Vector3f theDirection) {
		theOrigin = new Vector3f(theOrigin);
		direction = new Vector3f(theDirection);
	}
	
	/**
	 * Creates a copy of a line
	 * 
	 * @param	theOther	the line to be copied
	 */
	public Linef(Linef theOther) {
		theOrigin = new Vector3f(theOther.theOrigin);
		direction = new Vector3f(theOther.direction);
	}
	
	/**
	 * sets the line defined by two point: theOrigin and another point
	 * 
	 * @param	theOrigin		the reference point in that line
	 * @param	theOtherPoint	the other point of that line
	 */
	public void set(Vector3f theOrigin, Vector3f theOtherPoint) {
		theOrigin = new Vector3f(theOrigin);
		direction = theOtherPoint.makeSub(theOrigin);
	}

	/**
	 * returns another point on this line
	 * it is the result of a vector addition of theOrigin and the direction
	 * 
	 * @return	theOtherPoint	the other point on that line
	 */
	protected Vector3f theOtherPoint(){
		return theOrigin.makeAdd(direction);
	}

	
	/**
	 * This method sets theOrigin closest to the space origin.
	 */
	public void originize(){
		Vector3f unity = theOrigin.makeCross(direction);
		Vector3f center = unity.makeCross(direction);
		center.normalize();
		center.scale(center.angle(theOrigin)*theOrigin.magnitude());
		theOrigin = center;
	}
	
	/**
	 * returns the shortest distance between to lines
	 * 
	 * @param	line	the other line
	 * @return	distance vector
	 */
	public Vector3f getDistanceVector(Linef line){
		Vector3f dirVW = line.direction.makeCross(direction);
		Vector3f r0s = line.theOrigin.makeSub(theOrigin);
		if(dirVW.magnitude() != 0.0f){
			dirVW.normalize();
			dirVW.scale((float)Math.abs(dirVW.dot(r0s)));
			return dirVW;
		}
		// if they are parallel
		Linef lineA = new Linef(this);
		Linef lineB = new Linef(line);
		lineA.originize();
		lineB.originize();
		Vector3f diff = lineB.theOrigin.makeSub(lineA.theOrigin);
		return diff;
	}
	
	/**
	 * checks if the specified line is parallel to this line
	 * 
	 * @param	otherLine	the other line
	 * @return	true if parallel
	 */
	public boolean isParallel(Linef otherLine){
		Vector3f dirVW = direction.makeCross(otherLine.direction);
		return (dirVW.magnitude() == 0.0f)? true: false;
	}
	
	/**
	 * returns the shortest distance between to lines
	 * 
	 * @param	otherLine	the other line
	 * @return	distance
	 */
	public float getDistance(Linef otherLine){
		return getDistanceVector(otherLine).magnitude();
	}

	/**
	 * returns the shortest distance vector between this line and the specified point
	 * 
	 * @param	point
	 * @return	distance vector
	 */
	public Vector3f getDistanceVector(Vector3f point){
		Vector3f ret = point.makeSub(theOrigin);
		Vector3f dir = new Vector3f(direction);
		float scale = dir.dot(ret) / (dir.magnitude() * dir.magnitude());
		dir.scale(scale);
		ret.setSub(ret, dir);
		return ret;
	}

	/**
	 * returns the shortest distance between this line and the specified point
	 * 
	 * @param	point
	 * @return	distance
	 */
	public float getDistance(Vector3f point){
		Vector3f temp = point.makeSub(theOrigin);
		temp.setCross(temp, direction);
		return temp.magnitude() / direction.magnitude();
	}

	/**
	 * checks if this line and the specified line intersect
	 * 
	 * @param	otherLine
	 * @return	true if they intersect
	 */
	public boolean intersect(Vector3f otherLine){
		return (getDistance(otherLine) == 0.0f)? true: false;
	}

	public String toString() {
		return "origin + " + theOrigin + " / " + " direction " + direction;
	}
	
	public static void main(String[] args) {
	        /* multiplying matrices */
		
		Linef line = new Linef(new Vector3f(-2, 2, 2), new Vector3f(-1, 1, 1));
		
		Linef crossline = new Linef(new Vector3f(2, 2, 1), new Vector3f(1, 1, 1));
		
		boolean parallel = line.isParallel(crossline);

		float distance = line.getDistance(crossline);
		
		Vector3f distanceV = line.getDistanceVector(crossline);
		
		System.out.println(distance);

	 }

}
