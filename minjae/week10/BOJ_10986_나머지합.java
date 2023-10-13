package week10;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_10986_나머지합 {

    /* 1000000  / 1000000000  -> N^2  1000000000000
    *  1.배열의 구간합을 구한다.
    *  2. 나눗셈이 되는 구간을 구해야 하니까 M의 나머지를 저장
    *  3. 이중 배열을 돌며 값을 구한다??       --> 시간 초과
    *  ---------------------------------
    * 1. 미리 다음 값들이 몇개 있는지 저장 후에 계산
    * 2. M개의 배열에 저장하면서 result 계산
    * 3. 0인경우는 자신도 가능하니 포함
    * ---------------------------------
    * 1. 개수를 미리 세고 그중 2개 이상인 얘들을 조합으로 계산
    *
    * -----------------------------------------------
    * 1. 타입을 int로 하면 틀렸습니다.
    *
    *
    * */
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;
    static int N , M ;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        long result = 0;
        long[] check = new long[M];
        long sum = 0;
        for (int i = 0; i < N; i++) {
            sum =  (sum + Integer.parseInt(st.nextToken())) % M;
            check[(int)sum]++;
            if(sum == 0) result++;
        }

        for(int i = 0 ; i < M ;i++){
            result += (check[i]*(check[i]-1)/2);
        }

        System.out.println(result);

    }
}