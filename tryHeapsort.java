//Muzi Seloisa stud no. 4569906
//18 March 2026 Practical 6

import java.io.*;
import java.util.*;

public class tryHeapsort {

    static class Node {
        String word;
        Node(String w) {
            word = w;
        }
    }

    static class NodeHeap {
        Node[] heap;
        int size;

        NodeHeap(int capacity) {
            heap = new Node[capacity];
            size = 0;
        }

        void insert(Node N) {
            heap[size] = N;
            int i = size;
            size++;
            while (i > 0) {
                int parent = (i - 1) / 2;
                if (heap[i].word.compareTo(heap[parent].word) > 0) {
                    Node temp = heap[i];
                    heap[i] = heap[parent];
                    heap[parent] = temp;
                    i = parent;
                } else break;
            }
        }

        Node removeMax() {
            Node root = heap[0];
            heap[0] = heap[size - 1];
            size--;
            heapifyNode(0);
            return root;
        }

        void heapifyNode(int i) {
            int largest = i;
            int left = 2*i + 1;
            int right = 2*i + 2;

            if (left < size && heap[left].word.compareTo(heap[largest].word) > 0)
                largest = left;
            if (right < size && heap[right].word.compareTo(heap[largest].word) > 0)
                largest = right;
            if (largest != i) {
                Node temp = heap[i];
                heap[i] = heap[largest];
                heap[largest] = temp;
                heapifyNode(largest);
            }
        }
    }

    static String[] readWordsFromFile(String filename) {
        ArrayList<String> words = new ArrayList<>();
        try {
            Scanner sc = new Scanner(new File(filename));
            while (sc.hasNext()) {
                String word = sc.next();
                word = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
                if (!word.isEmpty()) words.add(word);
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }
        return words.toArray(new String[0]);
    }

    static void swap(String[] arr, int i, int j) {
        String temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    static void heapify(String[] arr, int n, int i) {
        int largest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;

        if (left < n && arr[left].compareTo(arr[largest]) > 0) largest = left;
        if (right < n && arr[right].compareTo(arr[largest]) > 0) largest = right;

        if (largest != i) {
            swap(arr, i, largest);
            heapify(arr, n, largest);
        }
    }

    static void buildHeapBottomUp(String[] arr) {
        for (int i = arr.length/2 - 1; i >= 0; i--) heapify(arr, arr.length, i);
    }

    static void heapSort(String[] arr) {
        int n = arr.length;
        buildHeapBottomUp(arr);
        for (int i = n-1; i > 0; i--) {
            swap(arr, 0, i);
            heapify(arr, i, 0);
        }
    }

    static String[] heapSortTopDown(String[] arr) {
        NodeHeap h = new NodeHeap(arr.length);
        for (String word : arr) h.insert(new Node(word));

        String[] sorted = new String[arr.length];
        for (int i = arr.length - 1; i >= 0; i--) {
            sorted[i] = h.removeMax().word;
        }
        return sorted;
    }

    static void printArray(String[] arr, int limit) {
        for (int i = 0; i < limit && i < arr.length; i++) System.out.print(arr[i] + " ");
        System.out.println();
    }

    public static void main(String[] args) {

        // SMALL TEST (c)
        String[] test = {"pear","apple","orange","banana","grape","kiwi","melon","plum","peach","berry"};

        System.out.println("Before:");
        printArray(test, 20);

        heapSort(test);
        System.out.println("Bottom-Up Sorted:");
        printArray(test, 20);

        String[] testTop = heapSortTopDown(test.clone());
        System.out.println("Top-Down Sorted:");
        printArray(testTop, 20);

        String[] words = readWordsFromFile("anagrams.txt"); // updated filename
        String[] bottom = words.clone();
        String[] top = words.clone();

        long t1 = System.nanoTime();
        heapSort(bottom);
        long t2 = System.nanoTime();

        long t3 = System.nanoTime();
        top = heapSortTopDown(top);
        long t4 = System.nanoTime();

        System.out.println("\nBottom-Up Time: " + (t2 - t1) + " ns");
        System.out.println("Top-Down Time: " + (t4 - t3) + " ns");

        System.out.println("\nFirst 20 words:");
        printArray(bottom, 20);
    }
}
