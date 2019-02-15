package sort;

public class BucketSort {
    public static int[] BucketSort(int[] theArray) {
        int []firstArray = new int[100];
        int []lastArray = new int[theArray.length];
        int m = 0;
        for(int i=0; i<theArray.length; i++) {
            if (firstArray[theArray[i]] == 0) {
                firstArray[theArray[i]] = 1;
            }else {
                firstArray[theArray[i]] +=1 ;
            }
        }
        for(int j=0; j<firstArray.length; j++) {
            if(firstArray[j]>0) {
                for(int k=0; k<firstArray[j]; k++) {
                    lastArray[m] = j;
                    m++;
                }
            }
        }
        return lastArray;
    }
    public static void main(String[] args) {
        int []the_array = {10,1,18,30,23,12,7,5,18,17};
        System.out.print("之前的排序：");
        for(int i = 0; i < the_array.length; i++) {
            System.out.print(the_array[i] + " ");
        }

        int []result_array = BucketSort(the_array);

        System.out.print("桶排序：");
        for(int i = 0; i < result_array.length; i++) {
            System.out.print(result_array[i] + " ");
        }
    }
}