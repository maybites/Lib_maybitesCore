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

	protected Vector3f getPointB(){
		return thePointA.add(direction);
	}

	public Vector3f getDistanceUnityVector(Linef line){
		Vector3f dirW = new Vector3f(line.direction);
		Vector3f ret = direction.cross(line.direction);
		ret.divide(ret.magnitude());
		return ret;
	}
	
	public Vector3f getDistanceVector(Linef line){
		Vector3f ret = getDistanceUnityVector(line);
		ret.scale(getDistance(line));
		return ret;
	}
	
	public float getDistance(Linef line){
		Vector3f dirVW = direction.cross(line.direction);
		Vector3f r0s = line.thePointA.sub(thePointA);
		return (float)(Math.abs(dirVW.dot(r0s)) / dirVW.magnitude());
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
		
		Linef line = new Linef(new Vector3f(0, 0, 0), new Vector3f(1, 0, 0));
		
		Linef crossline = new Linef(new Vector3f(3, 0, 0), new Vector3f(1, 0, 0));

		float distance = line.getDistance(crossline);
		
		System.out.println(distance);

	 }

}
