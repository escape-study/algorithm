package week28;

import java.util.*;

public class Solution_주차요금계산 {
    public static void main(String[] args) {
        int[] fees = {180, 5000, 10, 1000};
        String[] records = {"05:59 0000 IN", "05:59 1111 IN"};
        Solution_주차요금계산 sol = new Solution_주차요금계산();
        System.out.println(Arrays.toString(sol.solution(fees, records)));

//        int[] res = {95000, 95000};
//        System.out.println(Arrays.toString(res));
    }

    Map<String, String> parkinglot;
    Map<String, Integer> cars;

    public int[] solution(int[] fees, String[] records) {
        parkinglot = new HashMap<>();   // key : 차량번호, value : 입차시각
        cars = new HashMap<>();    // key : 차량번호, value : 누적 주차 시간

        StringTokenizer st;
        for (String record : records) {
            st = new StringTokenizer(record, " ");
            String time = st.nextToken();
            String carNum = st.nextToken();
            String history = st.nextToken();

            switch (history) {
                case "IN":  // 입차
                    parkinglot.put(carNum, time);
                    break;
                case "OUT": // 출차
                    exitCar(carNum, parkinglot.get(carNum), time);
                    parkinglot.remove(carNum);  // 주차장에서 없앰
                    break;
            }
        }

        // 주차장에 남은 차 정산
        for (String carNum : parkinglot.keySet()) {
            exitCar(carNum, parkinglot.get(carNum), "23:59");
        }

        String[] carNumList = cars.keySet().toArray(new String[cars.size()]);   // 차량번호 배열
        Arrays.sort(carNumList);    // 차량번호가 작은 순으로 정렬
        int[] answer = new int[carNumList.length];
        for (int i = 0; i < carNumList.length; i++) {
            answer[i] = fees[1];    // 기본 요금 청구
            int parkingTime = cars.get(carNumList[i]);
            if (parkingTime > fees[0]) {   // 누적 주차 시간이 기본 시간 초과
                int left = (int) Math.ceil((double) (parkingTime - fees[0]) / fees[2]);  // 추가로 정산할 시간
                answer[i] += left * fees[3];
            }
        }

        return answer;
    }   // end of solution

    /**
     * carNum 번호 차량의 주차 시간 누적
     */
    private void exitCar(String carNum, String inTime, String outTime) {
        int parkingTime = calcTime(outTime) - calcTime(inTime);   // 주차시간
        cars.put(carNum, cars.getOrDefault(carNum, 0) + parkingTime);   // 주차시간 누적
    }

    /**
     * 문자열로 된 시각을 분으로 환산하여 반환
     */
    private int calcTime(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }   // end of calcTime
}
