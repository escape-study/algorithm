package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 위상정렬
 * 위상정렬과 동일하게 진입차수를 저장한다.
 * 이때 건설시간이 있기 떄문에 해당 노드에 건설시간을 누적해둔다.
 * 기존에 저장된 값보다 더 큰 값이 오면 해당 값으로 업데이트 한다.
 * 진입차수가 0이 되면 다음 노드로 탐색할 수 있도록 큐에 넣어서 탐색한다.
 */
public class BOJ1005_ACM_Craft {


    //건물수 - 노드수
    private static int N;
    //건물간 건설순서 규칙 - 간선수
    private static int K;
    //건설해야 할 노드번호.
    private static int W;

    //각 노드의 진입 차수 카운트
    private static int[] inDegree;

    //그래프
    private static List<Integer>[] graph;

    //건물 만드는데 걸리는 시간.
    private static int[] buildTime;

    private static void init(){

        inDegree = new int[N + 1];
        buildTime = new int[N + 1];
        graph = new List[N + 1];

        for(int i = 1; i <= N; i++){
            graph[i] = new ArrayList<>();
        }
    }

    //bfs 탐색
    private static int bfs(){

        //해당 위치까지 오는데 빌드 시간이 가장 큰 값을 저장.
        int[] visited = new int[N + 1];

        Queue<Integer> needVisited = new ArrayDeque<>();

        //진입차수가 0인 값들만 찾아서 넣음
        for(int i = 1; i <= N; i++){

            if(inDegree[i] != 0) continue;
            visited[i] = buildTime[i];
            needVisited.add(i);
        }

        while(!needVisited.isEmpty()){

            int currentNode = needVisited.poll();

            for(int nextNode: graph[currentNode]){

                inDegree[nextNode]--;
                visited[nextNode] = Math.max(visited[nextNode], buildTime[nextNode] + visited[currentNode]);

                if(inDegree[nextNode] == 0){
                    needVisited.add(nextNode);
                }
            }
        }

        return visited[W];
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        StringBuilder result = new StringBuilder();

        int T = Integer.parseInt(br.readLine());

        for(int testCase = 0; testCase < T; testCase++){
            st = new StringTokenizer(br.readLine());

            N = Integer.parseInt(st.nextToken());
            K = Integer.parseInt(st.nextToken());

            init();

            //각 건물 빌드 시간.
            st = new StringTokenizer(br.readLine());
            for(int i = 1; i <= N; i++){
                buildTime[i] = Integer.parseInt(st.nextToken());
            }

            //간선
            for(int i = 0; i < K; i++){
                st = new StringTokenizer(br.readLine());
                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());

                graph[a].add(b);
                inDegree[b]++;
            }
            //건설해야 할 건물번호.
            W = Integer.parseInt(br.readLine());

            result.append(bfs()).append("\n");

        }

        System.out.println(result);

    }
}
