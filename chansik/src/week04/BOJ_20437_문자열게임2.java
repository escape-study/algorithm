package chansik.src.week04;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_20437_문자열게임2 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        StringBuilder sb = new StringBuilder();
        int T = Integer.parseInt(st.nextToken());

        // 어떤 문자를 정확히 K개 포함하면서 가장 짧은것 구하기
        // 어떤 문자를 정확히 K개 포함하면서 문자열의 첫번째와 마지막이 해당 문자와 같으면서 가장 긴것 구하기
        for(int i=0;i<T;i++) {
            HashMap<Character, List<Integer>> map = new HashMap<>();
            char[] arr = bf.readLine().toCharArray();
            int k = Integer.parseInt(bf.readLine());
            for(int j=0;j<arr.length;j++) {
                List<Integer> list = map.getOrDefault(arr[j], new ArrayList<>());
                list.add(j);
                map.put(arr[j], list);
            }

            int small = isShortest(map, arr, k);
            int big = isLongest(map, arr, k);

            if (small == Integer.MAX_VALUE && big == -1) {
                sb.append(-1).append("\n");
            } else {
                sb.append(small).append(" ").append(big).append("\n");
            }
        }
        System.out.println(sb);
    }

    public static int isLongest(HashMap<Character, List<Integer>> map, char[] arr, int k) {
        int returnNum = -1;

        for (char key : map.keySet()) {
            int right = k - 1;
            List<Integer> list = map.get(key);
            for(int left=0;right+left<list.size();left++) {
                returnNum = Math.max(returnNum, list.get(right+left) - list.get(left) + 1);
            }
        }
        return returnNum;
    }

    public static int isShortest(HashMap<Character, List<Integer>> map, char[] arr, int k) {
        // 시작 범위 알파벳 빈도 구하기
        int returnNum = Integer.MAX_VALUE;

        for (char key : map.keySet()) {
            int right = k - 1;
            List<Integer> list = map.get(key);
            for(int left=0;right+left<list.size();left++) {
                returnNum = Math.min(returnNum, list.get(right+left) - list.get(left) + 1);
            }
        }
        return returnNum;
    }
}