package chansik.src.week01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class BOJ_3020_개똥벌레 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int h = Integer.parseInt(st.nextToken());
        int[] up = new int[h+1];
        int[] down = new int[h+1];
        int[] sum = new int[h+1];
        for(int i=0;i<n/2;i++) {
            up[Integer.parseInt(bf.readLine())]++;
            down[Integer.parseInt(bf.readLine())]++;
        }

        for(int i=h-1;i>0;i--) {
            up[i] += up[i+1];
            down[i] += down[i+1];
        }

        int min = 500001;
        int count = 0;
        for(int i=1;i<=h;i++) {
            sum[i] = up[i] + down[h-i+1];
            min = Math.min(sum[i], min);
        }

        for(int i=1;i<=h;i++) count = sum[i] == min ? count + 1 : count;
        System.out.println(min + " " + count);
    }
}
