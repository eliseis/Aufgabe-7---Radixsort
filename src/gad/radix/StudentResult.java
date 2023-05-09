package gad.radix;

import java.util.Arrays;

public class StudentResult implements Result {
	@Override
	public void logArray(int[] array) {
		System.out.println(Arrays.toString(array));
	}
}