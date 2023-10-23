package chansik.src.week11;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1700_멀티탭스케줄링 {
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        HashSet<Integer> multiTab = new HashSet<>();
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        int answer = 0;
        int[] arr = new int[k];
        st = new StringTokenizer(bf.readLine());
        for(int i=0;i<k;i++) arr[i] = Integer.parseInt(st.nextToken());
        int cursor = 0;

        /**
         * 멀티탭에 꼽을 수 있는 만큼 전자기기를 꼽는다.
         */
        while (multiTab.size() != n) {
            if (cursor == k) break;
            multiTab.add(arr[cursor++]);
        }

        /**
         * cursor 는 현재 탐색할 위치를 나타내는 변수
         */

        HashMap<Integer, List<Integer>> scheduler = new HashMap<>();
        /**
         * 스케줄러를 생성하는 작업
         * [전자기기 번호, 전자기기가 나타나는 시간]
         */
        for(int i=cursor;i<k;i++) {
            if (!scheduler.containsKey(arr[i])) {
                List<Integer> list = new ArrayList<>();
                list.add(i);
                scheduler.put(arr[i], list);
            } else {
                scheduler.get(arr[i]).add(i);
            }
        }

        for(int i=cursor;i<k;i++) {
            // 해당 전자기기가 멀티탭에 이미 꼿혀있다면 스케줄러에서 해당 전자기기의 작업을 하나 제거한다.
            if (multiTab.contains(arr[i])) {
                scheduler.get(arr[i]).remove(0);
                continue;
            }
            int outProduct = 0;
            int step = -1;
            for (int product : multiTab) {
                // 멀티탭에 꼽혀있는 전자기기중 스케줄러에 작업이 더이상 존재하지 않는다면 뽑을 전자기기로 선정한다.
                if (scheduler.getOrDefault(product, new ArrayList<>()).isEmpty()) {
                    outProduct = product;
                    break;
                }
                int nextStep = scheduler.get(product).get(0);
                // 멀티탭에 꼽혀있는 전자기기중 가장 나중에 사용되는 전자기기를 뽑을 전자기기로 선정한다.
                if (step < nextStep) {
                    step = nextStep;
                    outProduct = product;
                }
            }
            if (!scheduler.get(arr[i]).isEmpty()) scheduler.get(arr[i]).remove(0);
            multiTab.remove(outProduct);
            multiTab.add(arr[i]);
            answer++;
        }
        System.out.println(answer);
    }
}
