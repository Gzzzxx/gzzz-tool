package tool.gzzz;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;

@SpringBootTest
public class SortTest {

    /**
     * 冒泡排序
     */
    @Test
    void testBubbleSort() {
        int[] arr = new int[]{1,2,3,4,19,46,7,10,1};
        bubbleSort(arr);
        System.out.println("\033[33m" + Arrays.toString(arr) + "\033[0m");
    }

    void bubbleSort(int[] arr) {
        for (int i = 0; i < arr.length - 1; i++) {
            boolean flag = true;
            for (int j = 0; j < arr.length - 1 - i; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    flag = false;
                }
            }
            if (flag) {
                break;
            }
        }
    }

    /**
     * 选择排序
     */
    @Test
    void testSelectionSort() {
        int[] arr = new int[]{5,4,3,2,1,5};
        selectionSort(arr);
        System.out.println("\033[33m" + Arrays.toString(arr) + "\033[0m");
    }

    void selectionSort(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            // 记录最小值的索引，与后面的元素依次比较，大于待比较的值，则记录下该索引
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[min] > arr[j]) {
                    min = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[min];
            arr[min] = temp;
        }
    }

    /**
     * 插入排序
     */
    @Test
    void testInsertSort() {
        int[] arr = new int[]{2,6,2,55,1,67,44,25};
        insertSort(arr);
        System.out.println("\033[33m" + Arrays.toString(arr) + "\033[0m");
    }

    void insertSort(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            // 记录待插入的元素，在已排好序的序列中从后向前比较
            int value = arr[i], j = i;
            while (j > 0 && value < arr[j - 1]) {
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = value;
        }
    }

    /**
     * 归并排序
     */
    @Test
    void testMergeSort() {
        int[] arr = new int[]{1,5,3,10,34,2,4,1};
        int[] a = mergeSort(arr, 0, arr.length - 1);
        System.out.println("\033[33m" + Arrays.toString(a) + "\033[0m");
    }

    public int[] mergeSort(int[] arr, int l, int h) {
        // 只剩一个元素，直接返回
        if (l == h) {
            return new int[]{arr[l]};
        }
        int mid = l + (h - l) / 2;
        int[] left = mergeSort(arr, l, mid);
        int[] right = mergeSort(arr, mid + 1, h);
        int[] newArr = new int[left.length + right.length];
        int m = 0, i = 0, j = 0;
        while (i < left.length && j < right.length) {
            newArr[m++] = left[i] < right[j] ? left[i++] : right[j++];
        }
        // 一侧数组的值已经取空了，直接从从另一个数组取值
        while (i < left.length) {
            newArr[m++] = left[i++];
        }
        while (j < right.length) {
            newArr[m++] = right[j++];
        }
        return newArr;
    }

    /**
     * 快速排序
     */
    @Test
    void quickSort() {
        int[] arr = new int[]{3,6,1,33,14,23,99,66,47,66};
        quickSort(arr, 0 ,arr.length - 1);
        System.out.println("\033[33m" + Arrays.toString(arr) + "\033[0m");
    }

    public void quickSort(int[] arr, int left, int right) {
        // 递归到最后，数组只剩一个元素，排序完成
        if (left < right) {
            int pivot = partition(arr, left, right);
            quickSort(arr, left, pivot - 1);
            quickSort(arr, pivot + 1, right);
        }
    }
    public int partition(int[] arr, int left, int right) {
        // 记录基准元素
        int value = arr[left];
        while (left < right) {
            // 从右侧遍历，直到比基准小
            while (left < right && arr[right] >= value) {
                right--;
            }
            arr[left] = arr[right];
            // 从左侧遍历，直到比基准大
            while (left < right && arr[left] < value) {
                left++;
            }
            arr[right] = arr[left];
        }
        // left=right遍历结束，将基准元素插入到腾空的位置
        arr[left] = value;
        return left;
    }

}
