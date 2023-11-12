package week14;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 단순하게 bfs로 탐색하면서 체크를 한다.
 * 두 그룹이므로 A그룹과 B그룹으로 나눈다.
 * 1번부터 탐색한다고 했을떄, 1번이 A그룹이라고 한다면, 인접 노드들은 B그룹이다.
 * 탐색하는데 인접노드가 이미 탐색되었고, 자신과 같은 그룹에 속하게 된다면 해당 그래프는 이분그래프가 될 수 없다.
 *
 * (주의점)
 * 모든 노드가 연결되어있지 않을수도 있음.
 * 그렇기 때문에 1을 시작노드로 탐색을 한번만 하면 되면 안됨.
 * 반복문을 돌면서 탐색안된 노드가 있으면 해당 노드를 시작노드로 해서 탐색을 돌려야 모든 노드 탐색이 가능함.
 */
public class BOJ1707_이분그래프 {

    //그래프
    private static Set<Integer>[] graph;

    //타입 바꿔주는 메서드
    private static char typeToggle(char type){

        return type == 'A' ? 'B' : 'A';
    }

    //bfs 탐색할 메서드
    private static String bfs(int startNode, char[] visited){

        visited[startNode] = 'A';

        Queue<Integer> needVisited = new ArrayDeque<>();
        needVisited.add(startNode);

        while(!needVisited.isEmpty()){

            int currentNode = needVisited.poll();

            char nextType = typeToggle(visited[currentNode]);
            for(int nextNode : graph[currentNode]){

                //이전에 방문하지 않았다면,
                if(visited[nextNode] != 'A' && visited[nextNode] != 'B'){
                    visited[nextNode] = nextType;
                    needVisited.add(nextNode);
                }
                //방문했었는데 다음노드 타입과 현재 노드 타입이 동일하다면.
                else if(visited[nextNode] == visited[currentNode]){
                    return "NO";
                }
            }
        }
        return "YES";
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        //최종적으로 출력을 할 스트링빌더 객체
        StringBuilder result = new StringBuilder();

        //테스트 케이스
        int T = Integer.parseInt(br.readLine());
        for(int testCase = 0; testCase < T; testCase++){

            st = new StringTokenizer(br.readLine());
            //그래프 정점의 개수
            int V = Integer.parseInt(st.nextToken());
            //간선 수
            int E = Integer.parseInt(st.nextToken());

            graph = new Set[V + 1];
            for(int i = 1; i <= V; i++){
                graph[i] = new HashSet<>();
            }

            for(int i = 0; i < E; i++){
                st = new StringTokenizer(br.readLine());

                int a = Integer.parseInt(st.nextToken());
                int b = Integer.parseInt(st.nextToken());
                graph[a].add(b);
                graph[b].add(a);

            }

            if(V == 1) result.append("NO").append("\n");

            else{

                char[] visited = new char[V + 1];

                String temp = null;
                for(int startNode = 1; startNode <= V; startNode++){

                    if(visited[startNode] != 'A' && visited[startNode] != 'B') {
                        temp = bfs(startNode, visited);
                    }

                    if(temp.equals("NO")) break;
                }

                result.append(temp).append("\n");
            }
        }

        System.out.println(result);
    }


}
