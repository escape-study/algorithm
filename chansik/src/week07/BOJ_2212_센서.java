package chansik.src.week07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.StringTokenizer;

public class BOJ_2212_센서 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        HashSet<Integer> set = new HashSet<>();
        int n = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        int k = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<n;i++) set.add(Integer.parseInt(st.nextToken()));
        int[] arr = new int[set.size()];
        int idx = 0;
        for (int num : set) arr[idx++] = num;
        Arrays.sort(arr);
        int[] dist = new int[arr.length-1];
        for(int i=1;i<arr.length;i++) dist[i-1] = arr[i] - arr[i-1];
        Arrays.sort(dist);
        int sum = Arrays.stream(dist).sum();
        for(int index=dist.length-1, count=k-1;count>0;count--,index--) {
            if (index < 0) break;
            sum -= dist[index];
        }
        System.out.print(sum);
    }
}