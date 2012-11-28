/*
 * Copyright (C) 2011 Martin Fršhlich & Others
 *
 * This class is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This class is distributed in the hope that it will be useful,
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

import java.util.Random;

public class MayRandom extends Random {

	Random internalRandom;

	public MayRandom() {
	}

	/**
	 * Return a random number in the range [0, howbig).
	 * <P>
	 * The number returned will range from zero up to (but not including)
	 * 'howbig'.
	 */
	public final float create(float howbig) {
		// for some reason (rounding error?) Math.random() * 3
		// can sometimes return '3' (once in ~30 million tries)
		// so a check was added to avoid the inclusion of 'howbig'

		// avoid an infinite loop
		if (howbig == 0)
			return 0;

		// internal random number object
		if (internalRandom == null)
			internalRandom = new Random();

		float value = 0;
		do {
			// value = (float)Math.random() * howbig;
			value = internalRandom.nextFloat() * howbig;
		} while (value == howbig);
		return value;
	}

	/**
	 * Return a random number in the range [howsmall, howbig).
	 * <P>
	 * The number returned will range from 'howsmall' up to (but not including
	 * 'howbig'.
	 * <P>
	 * If howsmall is >= howbig, howsmall will be returned, meaning that
	 * random(5, 5) will return 5 (useful) and random(7, 4) will return 7 (not
	 * useful.. better idea?)
	 */
	public final float create(float howsmall, float howbig) {
		if (howsmall >= howbig)
			return howsmall;
		float diff = howbig - howsmall;
		return create(diff) + howsmall;
	}

}
