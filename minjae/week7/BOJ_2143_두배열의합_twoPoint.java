package week7;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_2143_두배열의합_twoPoint {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int T, N , M;
    public static void main(String[] args) throws IOException {

        T = Integer.parseInt(br.readLine());
        N = Integer.parseInt(br.readLine());
        int arrA[] = new int[N];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < N; i++) {
            arrA[i] = Integer.parseInt(st.nextToken());
        }

        M = Integer.parseInt(br.readLine());
        int arrB[] = new int[M];
        st = new StringTokenizer(br.readLine());

        for (int i = 0; i < M; i++) {
            arrB[i] = Integer.parseInt(st.nextToken());
        }

        int aSize = N*(N+1)/2;
        long[] aSum = new long[aSize];
        int idx=0;
        for(int i=0; i<N; i++) {
            int av=0;
            for(int j=i; j<N; j++) {
                av+=arrA[j];
                aSum[idx++] = av;
            }
        }



        int bSize = M*(M+1)/2;
        long[] bSum = new long[bSize];
        idx=0;
        for(int i=0; i<M; i++) {
            int bv=0;
            for(int j=i; j<M; j++) {
                bv+=arrB[j];
                bSum[idx++] = bv;
            }
        }

        Arrays.sort(aSum);
        Arrays.sort(bSum);

        int left = 0;
        int right = bSize-1;
        long cnt = 0;

        while (left < aSize && right >= 0){

            long sum = aSum[left] + bSum[right];

            if(sum == T){
                long a = aSum[left];
                long b = bSum[right];

                long aCnt =0;
                long bCnt = 0;

                while (left < aSize && aSum[left] == a){
                    aCnt++;
                    left++;
                }
                while (right >=0 && bSum[right] == b){
                    bCnt++;
                    right--;
                }
                cnt += aCnt * bCnt;
            }else if(sum < T){
                left++;
            }else{
                right--;
            }

        }
        System.out.println(cnt);


    }
}