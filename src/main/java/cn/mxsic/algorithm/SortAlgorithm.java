package cn.mxsic.algorithm;

import org.junit.Test;

import static jdk.nashorn.internal.objects.Global.print;

/**
 * Function: SortAlgorithm <br>
 *
 * @author: siqishangshu <br>
 * @date: 2018-03-15 08:54:00
 */
public class SortAlgorithm {

    public static void quickSort(int arr[], int low, int high) {
        int l = low;
        int h = high;
        int k = arr[low];
        while (l < h) {
            while (l < h && arr[h] >= k)
                h--;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                l++;
            }


            while (l < h && arr[l] <= k)
                l++;
            if (l < h) {
                int temp = arr[h];
                arr[h] = arr[l];
                arr[l] = temp;
                h--;
            }
        }
        print(arr);
        System.out.println("l="+(l+1)+"h="+(h+1)+"k="+k+"\n");
        if(l>low)
            quickSort(arr,low,l-1);
        if(h<high)
            quickSort(arr,l+1,high);
    }


    @Test
    public void quik(){

    }
}
