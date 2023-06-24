package gad.radix;

import java.util.Arrays;
import java.util.Random;

public final class BinaryRadixSort {

    private BinaryRadixSort() {
    }

    public static int key(int element, int binPlace) {
        return (element >> binPlace) & 1;
    }

    public static void kSort(BinaryBucket from, BinaryBucket to, int binPlace) {
        BinaryBucket tempBucket = new BinaryBucket(from.size());

        // Füge die Zahlen mit Ziffer 0 in den tempBucket ein
        for (int i = 0; i < from.size(); i++) {
            int number = from.get(i);
            int key = key(number, binPlace);

            if (key == 0) {
                tempBucket.insertLeft(number);
            }
        }

        // Füge die Zahlen mit Ziffer 1 in den tempBucket ein
        for (int i = 0; i < from.size(); i++) {
            int number = from.get(i);
            int key = key(number, binPlace);

            if (key == 1) {
                tempBucket.insertRight(number);
            }
        }

        // Kopiere die Zahlen aus dem tempBucket zurück in den from-Bucket
        for (int i = 0; i < tempBucket.size(); i++) {
            int number = tempBucket.get(i);
            from.insertLeft(number);
        }

        // Kopiere die Zahlen aus dem tempBucket in den to-Bucket
        for (int i = 0; i < tempBucket.size(); i++) {
            int number = tempBucket.get(i);
            to.insertRight(number);
        }
    }

    public static void lastSort(BinaryBucket from, int[] to) {
        for (int i = 0; i < to.length; i++){
            from.insertLeft(to[i]);
        }
        int size = from.size();

        // Negative Zahlen nach vorne in das Ergebnis-Array einsortieren
        int index = 0;
        for (int i = size - 1; i >= 0; i--) {
            int number = from.get(i);
            if (number < 0) {
                to[index++] = number;
            } else {
                break;
            }
        }

        // Positive Zahlen nach hinten in das Ergebnis-Array einsortieren
        for (int i = 0; i < size; i++) {
            int number = from.get(i);
            if (number >= 0) {
                to[index++] = number;
            } else {
                break;
            }
        }
    }

    public static void sort(int[] elements, Result result) {
        BinaryBucket bucket1 = new BinaryBucket(elements.length);
        BinaryBucket bucket2 = new BinaryBucket(elements.length);

        for (int binPlace = 0; binPlace < 32; binPlace++) {
            if (binPlace % 2 == 0) {
                kSort(bucket1, bucket2, binPlace);
                result.logArray(bucket2.toArray());
            } else {
                kSort(bucket2, bucket1, binPlace);
                result.logArray(bucket1.toArray());
            }
        }

        lastSort(bucket1, elements);
    }

    public static void main(String[] args) {
        int[] test = new int[10_000_000];
        Random random = new Random();
        for (int i = 0; i < test.length; i++) {
            test[i] = random.nextInt(Integer.MAX_VALUE);
        }
        int[] testTwo = Arrays.copyOf(test, test.length);

        long start = System.nanoTime();
        sort(test, ignored -> {
        });
        long binaryTime = System.nanoTime() - start;

        start = System.nanoTime();
        RadixSort.sort(testTwo, ignored -> {
        });
        long decimalTime = System.nanoTime() - start;

        System.out.println("Korrekt sortiert:" + Arrays.equals(test, testTwo));
        System.out.println("Binary: " + binaryTime / 1_000_000);
        System.out.println("Decimal: " + decimalTime / 1_000_000);
    }
}
