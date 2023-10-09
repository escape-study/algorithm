package week9;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1644_소수의연속합 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static int N;

    public static void main(String[] args) throws IOException {

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());

        boolean[] prime = new boolean[N+1];

        prime[0] = true;
        prime[1] = true;

        List<Integer> list = new ArrayList<>();
        for (int i = 2 ; i <= N ; i++){
            if(!prime[i]){
                list.add(i);

                int num = 2;
                while (i*num <= N){
                    prime[i*num] = true;
                    num++;
                }

            }
        }

        int result = 0;

        int right = list.size()-1;
        int left = list.size()-1;

        int num = list.get(right);

        while (true){

            if(num == N){
                result++;
                num -= list.get(right);
                right--;
            }else if (num < N){
                left--;
                if(left < 0) break;
                num += list.get(left);
            }else{
                num -= list.get(right);
                right--;
            }
        }

        System.out.println(result);


    }


}