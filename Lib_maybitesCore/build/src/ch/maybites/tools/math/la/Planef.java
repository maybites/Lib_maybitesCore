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


public class Planef{

	private Vector3f normal;

	private float p;

	/**
	 * Creates a plane in the origin with a normal pointing to the 
	 * positive z-axis
	 */
	public Planef() {
		normal = new Vector3f(0, 0, 1);
		update(new Vector3f());
	}

	/**
	 * Creates a plane in theOrigin with a normal pointing in the direction of
	 * the theOrigin
	 * 
	 * @param	theOrigin	the reference point in that plane
	 */
	public Planef(Vector3f theOrigin) {
		normal = new Vector3f(theOrigin);
		update(theOrigin);
	}

	/**
	 * Creates a plane in theOrigin and its normal
	 * 
	 * @param	theOrigin	the reference point in that plane
	 * @param	theNormal	the normal vector of that plane
	 */
	public Planef(Vector3f theOrigin, Vector3f theNormal) {
		normal = new Vector3f(theNormal);
		update(theOrigin);
	}
	
	/**
	 * Creates a plane defined by two lines. If the lines don't intersect each other,
	 * then the origin of the plane will lie in the middle between the two lines origins 
	 * 
	 * @param	theFirstLine	
	 * @param	theSecondLine	
	 */
	public Planef(Linef theFirstLine, Linef theSecondLine) {
		normal = theFirstLine.direction.makeCross(theSecondLine.direction);
		Vector3f origin = theFirstLine.theOrigin.makeAdd(theSecondLine.theOrigin);
		origin.scale(.5f);
		update(origin);
	}

	/**
	 * Creates a plane in theOrigin and two other points in that plane
	 * 
	 * @param	theOrigin	the reference point in that plane
	 * @param	thePointA	pointA that lies in that plane
	 * @param	thePointB	pointB that lies in that plane
	 */
	public Planef(Vector3f theOrigin,
			Vector3f thePointA,
			Vector3f thePointB) {
		normal = thePointA.makeSub(theOrigin).makeCross(thePointB.makeSub(theOrigin));
		update(theOrigin);
	}

	/**
	 * sets a plane in theOrigin and two vectors in that plane
	 * 
	 * @param	theOrigin	the reference point in that plane
	 * @param	theVectorA	VectorA that defines that plane
	 * @param	theVectorB	VectorB that lies in that plane
	 */
	public void set(Vector3f theOrigin, Vector3f theVectorA, Vector3f theVectorB) {
		normal = theVectorA.makeCross(theVectorB);
		update(theOrigin);
	}

	private void update(Vector3f origin) {
		normal.normalize();
		p = normal.dot(origin);
	}

	/**
	 * returns the planes normal vector
	 * 
	 * @return	the normal vector
	 */
	public Vector3f getNormal() {
		return normal;
	}

	/**
	 * returns the planes p-value
	 * 
	 * @return	the p-value
	 */
	public float getP() {
		return p;
	}

	/**
	 * returns the origin point of this plane. Its not necessary the one specified
	 * when this plane was constructed. Its the result of this planes normal-vector
	 * scaled with factor p
	 * 
	 * @return	Vector3f	origin point
	 */
	public Vector3f getOrigin(){
		Vector3f orig = new Vector3f(normal);
		orig.scale(p);
		return orig;
	}

	/**
	 * returns the intersection point of the specified line with this plane
	 * 
	 * @param	line
	 * @return	Vector3f	intersection point. returns null if line is parallel to plane
	 */
	public Vector3f getIntersection(Linef line){
		// first check if line is parallel to plane:
		if(!intersects(line))
			return null;

		Vector3f vector = new Vector3f(line.direction);
		vector.scale((p - normal.dot(line.theOrigin))/normal.dot(vector));
		return line.theOrigin.makeAdd(vector);
	}

	/**
	 * returns the intersection line of the specified plane with this plane
	 * 
	 * @param	thePlane
	 * @return	intersection line. returns null if thePlane is parallel to this plane
	 */
	public Linef getIntersection(Planef thePlane){
		// first check if thePlane is parallel to plane:
		if(!intersects(thePlane)){
			return null;
		}
		Vector3f direction = thePlane.getNormal().makeCross(getNormal());
		direction.normalize();
		Vector3f point = thePlane.getOrigin().makeAdd(getOrigin());
		return new Linef(point, direction);
	}

	/**
	 * checks if the specified plane is intersecting this plane
	 * 
	 * @param	thePlane	
	 * @return	true if thePlane intersects with this plane
	 */
	public boolean intersects(Planef thePlane){
		return (thePlane.getNormal().makeCross(getNormal()).magnitude() == 0.0f)? false: true;
	}

	/**
	 * checks if the specified line is intersecting this plane
	 * 
	 * @param	line	
	 * @return	boolean		true if it intersects with this plane
	 */
	public boolean intersects(Linef line){
		return (line.direction.dot(normal) == 0.0f)? false: true;
	}

	/**
	 * returns the angle in rad between the specified line
	 * and this plane
	 * 
	 * @param	line	
	 * @return	float	intersection-angle in rad with this plane
	 */
	public float angle(Linef line){
		if(!intersects(line)){
			return 0.0f;
		}
		float div = Math.abs(normal.dot(line.direction)) / (normal.magnitude() * line.direction.magnitude());
		return (float)Math.asin((div > 1.0f)? 1.0: div);
	}

	/**
	 * returns the shortest distance of the specified point to this plane
	 * the distance is positive if the point lies on that side of the plane where
	 * its normal is pointing to. otherwise its negative.
	 * 
	 * @param	point	point
	 * @return	float	direct distance to this point. 
	 */
	public float getDistance(Vector3f point){
		return normal.dot(point) - p;
	}

	public static void main(String[] args) {
		/* multiplying matrices */

		Linef line = new Linef(new Vector3f(2, 2, 2), new Vector3f(1, 1, 1));

		Linef crossline = new Linef(new Vector3f(2, 2, 1), new Vector3f(1, 1, 1));

		//boolean parallel = line.isParallel(crossline);

		Vector3f point = new Vector3f(0, 0, 2);

		//Vector3f distanceV = line.getDistanceVector(crossline);

		Vector3f normal = new Vector3f(0, 0, 1);

		Vector3f pOrigin = new Vector3f(0, 0, 0);

		Planef plane = new Planef(pOrigin, normal);

		Planef otherplane = new Planef();

		float distance = plane.getDistance(point);
		
		point.setZ(-1);
		
		distance = plane.getDistance(point);

		boolean inter = plane.intersects(otherplane);

		Linef newLine = plane.getIntersection(otherplane);

		float angle = plane.angle(line) / 3.14159f * 180.0f;

		//System.out.println(distance);

	}

}
