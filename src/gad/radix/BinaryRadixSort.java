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
        int size = from.size();

        // Zähler für die Anzahl der Zahlen mit 0 an der angegebenen Stelle
        int countZero = 0;

        // Zahlen mit 0 an der angegebenen Stelle nach vorne in to sortieren
        for (int i = 0; i < size; i++) {
            int number = from.get(i);
            int k = key(number, binPlace);
            if (k == 0) {
                to.insertLeft(number);
                countZero++;
            }
        }

        // Zahlen mit 1 an der angegebenen Stelle nach hinten in to sortieren
        for (int i = 0; i < size; i++) {
            int number = from.get(i);
            int k = key(number, binPlace);
            if (k == 1) {
                to.insertRight(number);
            }
        }

        // Clear the from bucket
        from.clear();

        // Fehlende 0en in from einfügen
        for (int i = 0; i < countZero; i++) {
            from.insertLeft(to.get(i));
        }

        // Clear the to bucket
        to.clear();
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
