package week24;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * 아이디어
 * pq를 이용한 풀이.
 * 키는 나가는 시간을 기준으로 오름차순 정렬해서 넣는 것.
 * 큐에서 하나씩 빼서, 이전에 뺀 나가는 시간과, 새로 뺀 차량의 진입 시간을 비교한다.
 * 새로 뺀 차량의 진입시간이 이전에 나간 차량의 시간보다 작다면, 이전에 나간 차량의 시간에 감시카메라를 설치하면 커버가 된다.
 * 반면, 새로 뺸 차량의 진입시간이 이전에 나간 차량의 시간보다 크다면 커버가 불가능하기 때문에 해당 시간에 카메라를 더 설치 해야 한다.
 * 진출 시간 기준으로 오름차순 정렬하는 이유는, 최소한 진출 시간까지는 카메라가 설치되어야 해당 차량이 카메라에 걸리게 된다.
 * 카메라를 최소한으로 설치하는 경우는, 차량이 머무는 시간 중 가장 끝시간에 걸치게 해서, 최대한 많은 차량이 걸리게 하는 것이다.
 *
 * (추가)
 * pq에 넣어서 처리해도 되지만, 배열로 주어졌기 때문에 pq로 만들라면 n번만큼 반복해야 한다.
 * 그렇기 떄문에 주어진 배열을 정렬해서 처리하는 것이 좀 더 효율적이다.
 * 시간복잡도 상으로는 차이가 없겠지만, 코드길이가 훨씬 길어지게 된다.
 *
 * 시간복잡도 => 정렬(nlogn) + 반복(n) => O(nlogn), n이 10,000이므로 대략 14만으로 문제 없이 처리가 가능하다.
 */
public class Prog_단속카메라 {
    public int solution(int[][] routes) {
        int answer = 0;//카메라 수.

        Arrays.sort(routes, (route1, route2) -> route1[1] - route2[1]);


        //초기 진출시간(초기 카메라 설치 위치.)
        int outTime = routes[0][1];
        answer++;

        for(int i = 1; i < routes.length; i++){

            //이전에 진출 시간보다 진입시간이 더 작다면, 감시카메라를 설치할 필요 없음.
            if(outTime >= routes[i][0]) continue;

            outTime = routes[i][1];//진출시간 업데이트.
            answer++;//카메라 설치.
        }

        return answer;
    }

    public static void main(String[] args) {

        Prog_단속카메라 s = new Prog_단속카메라();
        int[][] routes = {{-20,-15}, {-14,-5}, {-18,-13}, {-5,-3}};
        System.out.println(s.solution(routes));

    }
}
