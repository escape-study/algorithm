package week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 플로이드 워셜
 * 프로그래머스에 있는 경기 승패를 보고 해당 선수를 이길수 있는지 없는지, 알수 없는지 파악하는 것과 유사.
 * bfs를 이용해서 주어진 사건 쌍을 a,b라고 하면, 한번은 a를 시작, b를 목표로 두고 탐색하고 반대로 한번더 해서 구분하려고 했다.
 * 하지만 간선의 수가 5만개이고, 노드 400개, 주어지는 사건쌍은 5만개이다.
 * bfs의 시간 복잡도는 50000 + 400, 이 동작을 5만 * 2번 반복해야 하기 때문에 시간초과가 난다.
 *
 * 반면 플로이드 워셜을 이용해서, 특정 위치에서 해당위치로 갈 수 있는지 체크하는 식으로 하면 N^3 - 대략 1600만정도로 충분하다
 * 단방향이므로, 갈수 있으면 -1로 표기하고, 갈수 없으면 0, 가는 것은 가능한데, 해당 노드가 시작점이 아닌 진입하는 도착 노드면 1로 표기 한다.
 */

public class BOJ1613_역사 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //노드수
        int n = Integer.parseInt(st.nextToken());
        //간선수
        int k = Integer.parseInt(st.nextToken());

        //인접행렬
        int[][] graph = new int[n + 1][n + 1];

        for(int i = 0; i < k; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            graph[a][b] = -1;
            graph[b][a] = 1;
        }

        //플로이드 워셜로 구성.
        for(int x = 1; x <= n; x++){
            for(int i = 1; i <= n; i++){

                if(i == x) continue;

                for(int j = 1; j <= n; j++){

                    if(i == j) continue;

                    if(graph[i][x] == -1 && graph[x][j] == -1){
                        graph[i][j] = -1;
                        graph[j][i] = 1;
                    }
                }
            }
        }

        StringBuilder sb = new StringBuilder();
        int s = Integer.parseInt(br.readLine());
        //쿼리 입력
        for(int i = 0; i < s; i++){
            st = new StringTokenizer(br.readLine());

            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());

            sb.append(graph[a][b]).append("\n");
        }

        System.out.println(sb);



    }
}
