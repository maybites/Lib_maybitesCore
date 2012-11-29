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

	public Vector3f thePointA;

	public Vector3f direction;

	public Linef() {
		thePointA = new Vector3f();
		direction = new Vector3f();
	}

	public Linef(Vector3f theOrigin, Vector3f theDirection) {
		thePointA = new Vector3f(theOrigin);
		direction = new Vector3f(theDirection);
	}
	
	public Linef(Linef theOther) {
		thePointA = new Vector3f(theOther.thePointA);
		direction = new Vector3f(theOther.direction);
	}

	protected Vector3f getPointB(){
		return thePointA.add(direction);
	}

	
	/**
	 * This method sets thePointA closest to the origin.
	 */
	public void originize(){
		Vector3f unity = thePointA.cross(direction);
		Vector3f center = unity.cross(direction);
		center.normalize();
		center.scale(center.angle(thePointA)*thePointA.magnitude());
		thePointA = center;
	}
	
	public Vector3f getDistanceVector(Linef line){
		Vector3f dirVW = line.direction.cross(direction);
		Vector3f r0s = line.thePointA.sub(thePointA);
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
		Vector3f diff = lineB.thePointA.sub(lineA.thePointA);
		return diff;
	}
	
	public boolean isParallel(Linef line){
		Vector3f dirVW = direction.cross(line.direction);
		return (dirVW.magnitude() == 0.0f)? true: false;
	}
	
	public float getDistance(Linef line){
		return getDistanceVector(line).magnitude();
	}

	public Vector3f getDistanceVector(Vector3f point){
		Vector3f ret = point.sub(thePointA);
		Vector3f dir = new Vector3f(direction);
		float scale = dir.dot(ret) / (dir.magnitude() * dir.magnitude());
		dir.scale(scale);
		ret.sub(ret, dir);
		return ret;
	}

	public float getDistance(Vector3f point){
		Vector3f temp = point.sub(thePointA);
		temp.cross(temp, direction);
		return temp.magnitude() / direction.magnitude();
	}
	
	public String toString() {
		return "origin + " + thePointA + " / " + " direction " + direction;
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
