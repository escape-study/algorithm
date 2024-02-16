package week27;

import java.util.*;

public class Solution_택배배달과수거하기 {
    class House {
        int index;
        int box;

        public House(int index, int box) {
            this.index = index;
            this.box = box;
        }

        public void minusBox(int num) {
            this.box -= num;
        }
    }

    Queue<House> delivery, pickup;

    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        delivery = new ArrayDeque<>();  // 배달해야할 집
        pickup = new ArrayDeque<>();    // 수거해야할 집

        for (int i = n - 1; i >= 0; i--) {  // 거리가 먼 순으로 등록
            if (deliveries[i] != 0) {
                delivery.add(new House(i + 1, deliveries[i]));
            }

            if (pickups[i] != 0) {
                pickup.add(new House(i + 1, pickups[i]));
            }
        }

        long answer = 0;
        while (!delivery.isEmpty() || !pickup.isEmpty()) {
            int delivDist = drive(cap, delivery);
            int pickDist = drive(cap, pickup);

            answer += Math.max(delivDist, pickDist) * 2;    // 현재 남은 집들 중 가장 멀리있는곳에 방문
        }

        return answer;
    }   // end of solution

    private int drive(int cap, Queue<House> houses) {
        if (houses.isEmpty()) {
            return -1;
        }

        int res = houses.peek().index;
        int tempBox = cap;  // 담을 수 있는 상자 수
        while (tempBox != 0) {
            if (houses.isEmpty()) {
                break;
            }

            House cur = houses.peek();

            if (tempBox - cur.box < 0) {    // 일부만 배달 or 수거
                cur.minusBox(tempBox);
                break;
            } else {
                tempBox -= cur.box;
                houses.poll();
            }
        }

        return res;
    }   // end of drive
}
