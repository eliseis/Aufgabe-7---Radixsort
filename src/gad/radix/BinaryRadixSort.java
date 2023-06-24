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
        for (int i = 0; i < from.getMid(); i++) {
            int number = from.get(i);
            int key = key(number, binPlace);
            if (key == 0) {
                to.insertLeft(number);
            }
            else{
                to.insertRight(number);
            }
        }

        // Füge die Zahlen mit Ziffer 1 in den tempBucket ein
        for (int i = from.size() - 1; i >= from.getMid(); i--){
            int number = from.get(i);
            int key = key(number, binPlace);
            if (key == 1) {
                to.insertRight(number);
            }
            else {
                to.insertLeft(number);
            }
        }
    }

    public static void lastSort(BinaryBucket from, int[] to) {
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

        for (int element : elements) {
            bucket1.insertLeft(element);
        }

        for (int i = 0; i < 32; i++) {
            kSort(bucket1, bucket2, i);
            result.logArray(bucket2.toArray());
            BinaryBucket temp = bucket1;
            bucket1 = bucket2;
            bucket2 = temp;
            bucket2.clear();
        }

        // Führe lastSort direkt in das ursprüngliche Array elements aus
        lastSort(bucket1, elements);
    }

    public static void main(String[] args) {
        int[] test = new int[6];
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
