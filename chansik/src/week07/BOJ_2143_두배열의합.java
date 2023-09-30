package chansik.src.week07;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2143_두배열의합 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        HashMap<Integer, Integer> map = new HashMap<>();
        int t = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        int n = Integer.parseInt(st.nextToken());
        int[] arr = new int[n];
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<n;i++) arr[i] = Integer.parseInt(st.nextToken());
        st = new StringTokenizer(bf.readLine());
        int m = Integer.parseInt(st.nextToken());
        int[] brr = new int[m];
        st = new StringTokenizer(bf.readLine());
        long count = 0;
        for(int i=0;i<m;i++) brr[i] = Integer.parseInt(st.nextToken());

        List<int[]> aList = new ArrayList<>();
        List<int[]> bList = new ArrayList<>();
        int[] aSum = new int[n+1];
        int[] bSum = new int[m+1];
        for(int i=1;i<=n;i++) aSum[i] = aSum[i-1] + arr[i-1];
        for(int i=1;i<=m;i++) bSum[i] = bSum[i-1] + brr[i-1];

        for(int i=1;i<=n;i++) {
            for (int j = 0; j < i; j++) {
                int number = aSum[i] - aSum[j];
                map.put(number, map.getOrDefault(number, 0) + 1);
            }
        }

        for (int key : map.keySet()) aList.add(new int[]{key, map.get(key)});
        map.clear();

        for(int i=1;i<=m;i++) {
            for (int j = 0; j < i; j++) {
                int number = bSum[i] - bSum[j];
                map.put(number, map.getOrDefault(number, 0) + 1);
            }
        }

        for (int key : map.keySet()) bList.add(new int[]{key, map.get(key)});

        Collections.sort(aList, (o1, o2) -> o1[0] - o2[0]);
        Collections.sort(bList, (o1, o2) -> o1[0] - o2[0]);
        for(int i=0;i<aList.size();i++) {
            int[] frInfo = aList.get(i);
            int frNum = frInfo[0]; int frCnt = frInfo[1];
            int left = 0; int right = bList.size() - 1;
            int middle = -1;
            while(true) {
                if (left > right) break;
                middle = (right + left) / 2;

                int[] reInfo = bList.get(middle);
                int reNum = reInfo[0]; int reCnt = reInfo[1];

                if (frNum + reNum > t) {
                    right = middle - 1;
                } else {
                    left = middle + 1;
                    if (frNum + reNum == t) {
                        count += ((long) frCnt * reCnt);
                        break;
                    }
                }

            }
        }

        System.out.println(count);
    }
}