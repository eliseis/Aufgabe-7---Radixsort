package gad.radix;

public class BinaryBucket {

	private int[] bucket;
	private int size;

	public BinaryBucket(int size) {
		this.bucket = new int[size];
		this.size = 0;

	}

	public void insertLeft(int number) {
		bucket[size++] = number;

	}

	public void insertRight(int number) {
		for (int i = size; i > 0; i--) {
			bucket[i] = bucket[i - 1];
		}
		bucket[0] = number;
		size++;

	}

	public int getMid() {
		return bucket[size / 2];
	}

	public void logArray(Result result) {
		result.logArray(bucket);
	}

	public int get(int index) {
		return bucket[index];
	}

	public int size() {
		return size;
	}

	public void clear() {
		size = 0;
	}
	public int[] toArray() {
		int[] array = new int[size];
		System.arraycopy(bucket, 0, array, 0, size);
		return array;
	}
}

