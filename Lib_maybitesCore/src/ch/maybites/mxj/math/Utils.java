package ch.maybites.mxj.math;

import java.lang.reflect.*;

public class Utils {

	static final String ERROR_MIN_MAX =
		"Cannot use min() or max() on an empty array.";


	static public final int min(int a, int b) {
		return (a < b) ? a : b;
	}

	static public final float min(float a, float b) {
		return (a < b) ? a : b;
	}

	static public final int min(int a, int b, int c) {
		return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
	}

	static public final float min(float a, float b, float c) {
		return (a < b) ? ((a < c) ? a : c) : ((b < c) ? b : c);
	}

	/**
	 * Find the minimum value in an array.
	 * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
	 * @param list the source array
	 * @return The minimum value
	 */
	static public final int min(int[] list) {
		if (list.length == 0) {
			throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
		}
		int min = list[0];
		for (int i = 1; i < list.length; i++) {
			if (list[i] < min) min = list[i];
		}
		return min;
	}


	/**
	 * Find the minimum value in an array.
	 * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
	 * @param list the source array
	 * @return The minimum value
	 */
	static public final float min(float[] list) {
		if (list.length == 0) {
			throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
		}
		float min = list[0];
		for (int i = 1; i < list.length; i++) {
			if (list[i] < min) min = list[i];
		}
		return min;
	}

	static public final int max(int a, int b) {
		return (a > b) ? a : b;
	}

	static public final float max(float a, float b) {
		return (a > b) ? a : b;
	}

	static public final int max(int a, int b, int c) {
		return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
	}

	static public final float max(float a, float b, float c) {
		return (a > b) ? ((a > c) ? a : c) : ((b > c) ? b : c);
	}


	/**
	 * Find the maximum value in an array.
	 * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
	 * @param list the source array
	 * @return The maximum value
	 */
	static public final int max(int[] list) {
		if (list.length == 0) {
			throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
		}
		int max = list[0];
		for (int i = 1; i < list.length; i++) {
			if (list[i] > max) max = list[i];
		}
		return max;
	}

	/**
	 * Find the maximum value in an array.
	 * Throws an ArrayIndexOutOfBoundsException if the array is length 0.
	 * @param list the source array
	 * @return The maximum value
	 */
	static public final float max(float[] list) {
		if (list.length == 0) {
			throw new ArrayIndexOutOfBoundsException(ERROR_MIN_MAX);
		}
		float max = list[0];
		for (int i = 1; i < list.length; i++) {
			if (list[i] > max) max = list[i];
		}
		return max;
	}

	static public final int constrain(int amt, int low, int high) {
		return (amt < low) ? low : ((amt > high) ? high : amt);
	}

	static public final float constrain(float amt, float low, float high) {
		return (amt < low) ? low : ((amt > high) ? high : amt);
	}

	/**
	 * Convenience function to map a variable from one coordinate space
	 * to another. Equivalent to unlerp() followed by lerp().
	 */
	static public final float map(float value,
			float istart, float istop,
			float ostart, float ostop) {
		return ostart + (ostop - ostart) * ((value - istart) / (istop - istart));
	}

	  static public boolean[] concat(boolean a[], boolean b[]) {
		    boolean c[] = new boolean[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public byte[] concat(byte a[], byte b[]) {
		    byte c[] = new byte[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public char[] concat(char a[], char b[]) {
		    char c[] = new char[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public int[] concat(int a[], int b[]) {
		    int c[] = new int[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public float[] concat(float a[], float b[]) {
		    float c[] = new float[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public String[] concat(String a[], String b[]) {
		    String c[] = new String[a.length + b.length];
		    System.arraycopy(a, 0, c, 0, a.length);
		    System.arraycopy(b, 0, c, a.length, b.length);
		    return c;
		  }

		  static public Object concat(Object a, Object b) {
		    Class<?> type = a.getClass().getComponentType();
		    int alength = Array.getLength(a);
		    int blength = Array.getLength(b);
		    Object outgoing = Array.newInstance(type, alength + blength);
		    System.arraycopy(a, 0, outgoing, 0, alength);
		    System.arraycopy(b, 0, outgoing, alength, blength);
		    return outgoing;
		  }

	
}
