package gad.radix;

public class BinaryBucket {

	private int[] bucket;
	private int size;
	private int mid;

	public BinaryBucket(int size) {
		this.bucket = new int[size];
		this.size = 0;
		this.mid = size;

	}

	public void insertLeft(int number) {
		bucket[--mid] = number;
		size++;

	}

	public void insertRight(int number) {
		bucket[size++] = number;
	}

	public int getMid() {
		return mid;
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

