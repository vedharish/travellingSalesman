import java.util.Arrays;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class permute{
    public static void permute(int[] array, int k, BufferedWriter output) throws IOException{
        for(int i=k; i<array.length; i++){
            int temp;
            temp = array[i];
            array[i] = array[k];
            array[k] = temp;
            permute(array, k+1, output);
            int temp2;
            temp2 = array[i];
            array[i] = array[k];
            array[k] = temp2;
        }
        if (k == array.length-1){
            for(int iter=0; iter<array.length; iter++){
                output.write(array[iter]+"|");
            }
            output.write("\n");
        }
    }
    public static void main(String[] args) throws IOException{
        BufferedWriter output = new BufferedWriter(new FileWriter("permutations.txt"));
        for(int num=1; num<11; num++){
            int[] arr = new int[num];
            for(int iter=1; iter<=num; iter++){
                arr[iter-1] = iter;
            }
            permute(arr, 0, output);
        }
        output.flush();
    }
}
