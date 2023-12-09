package week18;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 플로이드 워셜 or 유니온파인드
 * 플로이드 워셜로 풀어도 200^3이라서 충분히 가능하지만 유니온 파인으로 처리해보도록 한다.
 *
 * 완전탐색을 하는 것이 아닌 유니온 파인드로 그래프를 합치는 것이다.
 * 경로에 ㅣ있는 노드들이 최대 루트를 찾았을때 동일하면 두 노드는 연결되어있다.
 * 그래프 연결정보를 받아서 유니온 파인드로 그래프를 합치고 경로 압축으로 루트를 찾아서 저장한다.
 *
 */
public class BOJ1976_여행가자 {

    private static int N;
    private static int M;
    private static int[] parents;

    //경로 압축하면서 부모찾기
    private static int find(int currentNode){

        if(parents[currentNode] == currentNode)
            return currentNode;

        return parents[currentNode] = find(parents[currentNode]);
    }

    //두 그래프 합치기.
    private static void union(int a, int b){

        int aNode = find(a);
        int bNode = find(b);

        if(aNode < bNode){
            parents[bNode] = aNode;
            return;
        }

        parents[aNode] = bNode;
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        M = Integer.parseInt(br.readLine());

        //부모를 저장할 배열
        parents = new int[N + 1];
        for(int i = 1; i <= N; i++){
            parents[i] = i;
        }

        //유니온 파인드로 합치기
        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 1; j <= N; j++){
                int node = Integer.parseInt(st.nextToken());
                //1이면 연결되어있기 떄문에 union으로 두 그래프를 합쳐야 됨.
                if(node == 1) union(i, j);
            }
        }

        //입력받은 경로 검증 - 첫번째노드의 루트를 찾아서 나머지 노드들과 비교.
        st = new StringTokenizer(br.readLine());
        int root = find(Integer.parseInt(st.nextToken()));
        for(int i = 0; i < M - 1; i++){
            int node = Integer.parseInt(st.nextToken());

            //
            if(root != find(node)){
                System.out.println("NO");
                return;
            }
        }

        System.out.println("YES");

    }
}
