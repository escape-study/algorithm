package week11;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 그리디
 * 플러그가 꽉차 있다면, 앞으로 덜 사용할 것들을 먼저 뽑는 것이 효율적이다.
 * 가장 오랫동안 꽂혀있는 것을 뽑는 에이징 기법을 사용하면, 오래 꽂혀있지만, 앞으로 더 많이 사용할 제품이였다면 손해다
 * 실제로는 이러한 알고리즘을 구현하려면 미래를 알아야 하기 때문에 어렵지만. 문제에서는 앞으로 꽂을 순서도 알려주었다
 * 따라서 앞에서 순차적으로 꽃으면서, 플러그가 비어있다면 추가하고, 비어있지 않으면 매순간 가장 많이 꽂을 노드를 선택한다.
 *
 * (수정)
 * 앞으로 사용할 횟수가 많다고 하더라도, 아주 나중에 사용한다면 해당 노드를 선택하면 손해이다
 * 현재 기점으로부터 가장 오랫동안 쓰지 않을 노드를 선택하게 된다면, 다음 사용때 만큼 해당 위치는 사용할 수 있다는 뜻이된다.
 * 앞으로 가장 오랫동ㅇ안 사용하지 않을 노드를 선택해서 바꾸는 방식으로 변경한다.
 */
public class BOJ1700_멀티탭_스케줄링 {

    //멀티탭 구멍수
    private static int N;
    //총 사용 횟수
    private static int K;

    //전기용품 사용 순서
    private static int[] usingOrder;

    //플러그 뺴는 최소 횟수 구하기'
    private static int logic(){

        //멀티탭
        Set<Integer> multiTab = new HashSet<>();

        //플러그 빼는 횟수 누적
        int result = 0;

        //반복문 돌면서 플러그 판단
        for(int i = 0; i < K; i++){

            int currentProduct = usingOrder[i];

            //꽂을 수 있는 구멍이 없다면.
            if(multiTab.size() == N && !multiTab.contains(currentProduct)){
                //멀티탭에 꽂혀있는 제품들을 하나씩 반복문 돌면서, 앞으로 어떤 것이 제일 오랫동안 사용이 되지 않을지 선정.
                int tempNode = 0; //멀티탭에서 뽑을 노드.

                boolean[] visited = new boolean[K + 1];

                //반복문 돌리면서 파악
                for(int j = i+1; j < K; j++){
                    //멀티탭에 있는 값이고, 방문안했으면 추가.
                    if(multiTab.contains(usingOrder[j]) && !visited[usingOrder[j]]){
                        visited[usingOrder[j]] = true;
                        tempNode = usingOrder[j];
                    }
                }

                //방문 배열을 확인해서 멀티탭에는 포함되지만, 방문처리 안된 것을 선정(앞으로 한번도 쓰이지 않는다는 뜻)
                for(int j = 1; j <= K; j++){
                    if(multiTab.contains(j) && !visited[j]){
                        tempNode = j;
                        break;
                    }
                }

                //선정한 뽑을 노드 제거.
                multiTab.remove(tempNode);
                result++;
            }

            //현재 제품 멀티탭에 꽂기
            multiTab.add(currentProduct);
        }

        return result;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        st = new StringTokenizer(br.readLine());
        N = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        usingOrder = new int[K];
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < K; i++){

            usingOrder[i] = Integer.parseInt(st.nextToken());
        }

        System.out.println(logic());
    }
}
