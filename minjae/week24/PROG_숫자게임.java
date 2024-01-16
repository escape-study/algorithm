package week24;


import java.util.*;

    public class PROG_숫자게임 {
        public int solution(int[] A, int[] B) {

            Arrays.sort(A);

            Arrays.sort(B);

            int a = 0;
            int b = 0;
            int sum =0;
            while(a < A.length && b < B.length){
                if(A[a] >= B[b]){
                    b++;
                }else{
                    a++;
                    b++;
                    sum++;
                }
            }
            return sum;
        }
}