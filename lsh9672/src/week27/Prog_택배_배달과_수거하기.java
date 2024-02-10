package week27;

import java.util.Stack;

/**
 * 아이디어
 * 그리디
 * 배달할 집의 개수가 10만이므로 완탐불가(dp 등 전부 불가)
 * 그리디로 생각해서 해결해야 한다.
 * 담을수 있는 택배상자(cap)크기를 넘지 않게 이동해야 한다.
 * 전략은 다음과 같다.
 * 1. 가장 먼집부터 배달한다.
 * 2. 갈때는 배달만, 올때는 수거만 한다.
 * 3. 갈때는 배달 가능한 최대치를, 올때는 배달 가능한 최대치를 배달했으므로 최대 cap 크기만큼 수거해온다.
 */
public class Prog_택배_배달과_수거하기 {


    public long solution(int cap, int n, int[] deliveries, int[] pickups) {
        long answer = 0;

        Stack<Integer> deliveryStack = new Stack<>();//배달 스택
        Stack<Integer> pickupStack = new Stack<>();//수거 스택.

        //스택 넣기.
        for(int i = 0; i < n ; i++){

            if(deliveries[i] != 0) deliveryStack.add(i);

            if(pickups[i] != 0) pickupStack.add(i);
        }

        int deliveryCap = cap; //배달크기
        int pickupCap = cap; //수거 크기.

        int deliveryDistance = 0; //배달거리
        int pickupDistance = 0; //수거거리

        //배달 위치 구하기.
        while(!deliveryStack.isEmpty() || !pickupStack.isEmpty()){

            //배달, 수거 거리구하기 - 둘다 스택이 비어있지 않음을 확인하고, 저장해야 함.
            deliveryDistance = deliveryStack.isEmpty() ? 0 : deliveryStack.peek() + 1;
            pickupDistance = pickupStack.isEmpty() ? 0 : pickupStack.peek() + 1;



            //다음 배달 위치 꺼내기.
            while (!deliveryStack.isEmpty()){
                int nextDelivery = deliveryStack.pop();

                //한번에 배달 가능한 크기와 꺼낸위치의 배달 크기를 비교.
                //캡이 더 크다면 캡 크기에서 빼고 반복 -> 최대로 배달하기 위해 다음 배달크기도 봄.
                if(deliveries[nextDelivery] < deliveryCap) {

                    deliveryCap -= deliveries[nextDelivery];
                    continue;
                }
                //캡과 같다면, 더 꺼낼 필요 없음
                if(deliveries[nextDelivery] == deliveryCap){
                    deliveryCap -= deliveries[nextDelivery];
                    break;
                }

                //캡보다 큰 경우에는, 남은 캡만큼 빼고 다시 넣음.
                deliveries[nextDelivery] -= deliveryCap;
                deliveryStack.add(nextDelivery);
                deliveryCap = 0;

                break;
            }

            //수거 위치 구하기.
            while(!pickupStack.isEmpty()){
                int nextPickup = pickupStack.pop();


                if(pickups[nextPickup] < pickupCap) {

                    pickupCap -= pickups[nextPickup];
                    continue;
                }
                //캡과 같다면, 더 꺼낼 필요 없음
                if(pickups[nextPickup] == pickupCap){
                    pickupCap -= pickups[nextPickup];
                    break;
                }

                //캡보다 큰 경우에는, 남은 캡만큼 빼고 다시 넣음.
                pickups[nextPickup] -= pickupCap;
                pickupStack.add(nextPickup);
                pickupCap = 0;

                break;
            }


            //더 큰쪽값을 선택
            answer += (long) Math.max(deliveryDistance, pickupDistance) * 2;

            //다음 배달과 수거를 위해서 최대 캡 크기로 초기화.
            deliveryCap = cap;
            pickupCap = cap;

        }


        return answer;
    }

    public static void main(String[] args) {
        Prog_택배_배달과_수거하기 s = new Prog_택배_배달과_수거하기();

        int cap1 = 4;
        int n1 = 5;
        int[] deliveries1 = {1, 0, 3, 1, 2};
        int[] pickups1 = {0, 3, 0, 4, 0};
        System.out.println(s.solution(cap1, n1, deliveries1, pickups1));

        int cap2 = 2;
        int n2 = 7;
        int[] deliveries2 = {1, 0, 2, 0, 1, 0, 2};
        int[] pickups2 = {0, 2, 0, 1, 0, 2, 0};
        System.out.println(s.solution(cap2, n2, deliveries2, pickups2));
    }
}
