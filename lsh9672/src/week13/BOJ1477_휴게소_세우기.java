package week13;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 그리디 + pq
 * 휴게소가 없는 구간, 즉 휴게소간 거리가 최소가 되게 하기 위해서는, 휴게소간 거리가 가장 긴 구간의 중간에 휴게소를 세우면 된다.
 * 이를 pq를 이용해서 처리한다.
 * 휴게소의 수와, 추가할 휴게소 수가 많지 않다
 * pq를 이용한다면 초기에 nlogn이 사용된다.
 * 여기에 매번 휴게소가 추가될때마다 정렬이 필요하기 때문에 mlogm이 된다.
 * 최대값으로 봤을때 m > n 이므로, mlogm + nlogn => mlogm이 된다.
 *
 * (참고)
 * 위의 풀이대로 하면 틀린다.
 * 이분탐색을 이용해서 휴게소 간격을 구하고, 해당 간격으로 m개 딱맞게 가능한지 판단하는 식으로 찾아야 한다.
 */

public class BOJ1477_휴게소_세우기 {

    //휴게소 수
    private static int N;
    //더 지으려는 휴게소 개수
    private static int M;
    //고속도로 총 길이.
    private static int L;

    private static int[] temp;

    //해당 간격이 가능한지 확인하는 메서드
    private static boolean check(int mid){

        int count = 0;

        for(int i = 0; i < N + 1; i++){

            count += (temp[i + 1] - temp[i] - 1) / mid;
        }

        if(count > M) return true;

        return false;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        L = Integer.parseInt(st.nextToken());

        temp = new int[N+2];
        temp[0] = 0;
        temp[N + 1] = L;


        st = new StringTokenizer(br.readLine());
        for(int i = 1; i <= N; i++){
            temp[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(temp);

        //이분탐색으로 휴게소 간격 구하기
        int left = 1;
        int right = L;

        while(left <= right){

            int mid = (left + right) / 2;

            if(check(mid)) left = mid + 1;

            else right = mid - 1;
        }

        System.out.println(left);

    }
}
