package chansik.src.week13;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1477_휴게소세우기 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());
        int l = Integer.parseInt(st.nextToken());
        int answer = Integer.MAX_VALUE;
        int[] arr = new int[n+2];
        arr[0] = 0;
        arr[n+1] = l;
        if (n > 0) {
            st = new StringTokenizer(bf.readLine());
            for (int i = 1; i < n + 1; i++) arr[i] = Integer.parseInt(st.nextToken());
        }
        Arrays.sort(arr);

        int left = 0;
        int right = l;
        int middle = 0;
        int sum = 0;
        while(true) {
            if (left > right) break;
            sum = 0;
            middle = (left + right) / 2;

            System.out.println("[" + left + ", " + right + "]");
            for(int i=1;i<n+2;i++) sum += (arr[i] - arr[i-1] - 1) / middle;


            if (sum > m) left = middle + 1;
            else right = middle - 1;
        }
        System.out.println(left);
    }
}
