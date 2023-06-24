package gad.radix;

public class BinaryBucket {

	private int[] bucket;
	private int size;
	private int mid;
	private int s;

	public BinaryBucket(int size) {
		this.bucket = new int[size];
		this.size = 0;
		this.mid = 0;
		this.s = bucket.length - 1;

	}

	public void insertLeft(int number) {
		bucket[mid++] = number;
		size++;

	}

	public void insertRight(int number) {
		bucket[s--] = number;
		size++;
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
		mid = 0;
		size = 0;
		s = bucket.length - 1;
	}
	public void setsize(int a){
		size = a;
	}
	public void sets(int a){
		s = a;
	}
	public void setmid(int a){
		mid = a;
	}
	public int[] toArray() {
		int[] array = new int[size];
		System.arraycopy(bucket, 0, array, 0, size);
		return array;
	}
	public int[] getBucket() {
		return this.toArray();
	}
}

