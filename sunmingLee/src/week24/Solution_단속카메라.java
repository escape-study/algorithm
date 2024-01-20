import java.util.*;

class Solution_단속카메라 {

    public int solution(int[][] routes) {
        int answer = 0;
        Arrays.sort(routes, Comparator.comparingInt((int[] a) -> a[1]));    // 진출 지점 기준으로 오름차순
        int camera = -30_001;  // 가장 뒤에있는 진출지점
        for (int[] route : routes) {
            if (route[0] <= camera && camera <= route[1]) {   // 현재 카메라가 차량의 진입/진출지점 사이에 있음
                continue;
            }

            camera = route[1];  // 차량의 진출 지점으로 새로운 카메라 설치
            answer++;
        }

        return answer;
    }
}