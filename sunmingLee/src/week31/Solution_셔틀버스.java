package week31;

import java.util.*;

public class Solution_셔틀버스 {
    public String solution(int n, int t, int m, String[] timetable) {
        String answer = "";
        Queue<Integer> line = new ArrayDeque<>();
        Arrays.sort(timetable); // 시간순 정렬
        for (String time : timetable) {
            line.add(timeToInt(time));
        }

        boolean hasSeat = false;    // 셔틀 도착 시각에 줄을 서도 되면 true
        int shuttle = 9 * 60;   // 셔틀 시각(분)
        loop:
        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < m; j++) {
                if (line.isEmpty()) { // 기다리는 크루가 없으면 가장 마지막 셔틀 시간에 줄서기
                    shuttle += (n - i - 1) * t;
                    hasSeat = true;
                    break loop;
                }

                if (line.peek() > shuttle) {  // 이번 셔틀을 탈 수 없음
                    break;
                }

                line.poll();
            }

            shuttle += t;   // 다음 셔틀 시각으로 변경
        }

        int latest = line.peek();   // 가장 마지막에 셔틀을 탈 사람의 줄 선 시각
        if (!hasSeat) {   // 마지막 셔틀 체크
            for (int i = 0; i < m; i++) {
                if (line.isEmpty() || line.peek() > shuttle) {
                    hasSeat = true;
                    break;
                }

                latest = line.poll();
            }
        }

        return hasSeat ? timeToStr(shuttle) : timeToStr(latest - 1);
    }   // end of solution

    /**
     * 분 -> HH:MM 형식으로 환산
     */
    private String timeToStr(int time) {
        int hour = time / 60;
        int min = time % 60;
        return (hour < 10 ? "0" : "") + hour + ":" + (min < 10 ? "0" : "") + min;
    }   // end of timeToStr

    /**
     * HH:MM -> 분으로 환산
     */
    private Integer timeToInt(String time) {
        String[] times = time.split(":");
        return Integer.parseInt(times[0]) * 60 + Integer.parseInt(times[1]);
    }   // end of timeToMin

}
