/*
 * maybites tools
 *
 * Copyright (C) 2011 Martin Fršhlich
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

package ch.maybites.tools;

public class Calc {

	public static float trim(float min, float value, float max) {
		if (value >= min) {
			if (value <= max) {
				return value;
			} else {
				return max;
			}
		} else {
			return min;
		}
	}

	public static float map(float val, float minVal, float maxVal,
			float minMap, float maxMap) {
		if (val > minVal) {
			if (val < maxVal) {
				return minMap + (val - minVal) / (maxVal - minVal)
						* (maxMap - minMap);
			} else {
				return maxMap;
			}
		}
		return minMap;
	}

	static public final float sq(float a) {
		return a * a;
	}

	static public final int min(int a, int b) {
		return (a < b) ? a : b;
	}

	static public final float min(float a, float b) {
		return (a < b) ? a : b;
	}

	/*
	 * static public final double min(double a, double b) { return (a < b) ? a :
	 * b; }
	 */

	static public final int min(int a, int b, int c) {
		return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
	}

	static public final float min(float a, float b, float c) {
		return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
	}

	static public final float dist(float x1, float y1, float x2, float y2) {
		return sqrt(sq(x2 - x1) + sq(y2 - y1));
	}

	static public final float dist(float x1, float y1, float z1, float x2,
			float y2, float z2) {
		return sqrt(sq(x2 - x1) + sq(y2 - y1) + sq(z2 - z1));
	}

	static public final float sqrt(float a) {
		return (float) Math.sqrt(a);
	}

}
