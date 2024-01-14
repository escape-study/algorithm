/*
제목 : 도넛과 막대 그래프
알고리즘 유형 : #implementation
플랫폼 : #Programmers
난이도 : L2
문제번호 : 258711
시간 : INF
해결 : △
저장 : O
해결언어 : java
문제링크 : https://school.programmers.co.kr/learn/courses/30/lessons/258711
특이사항 : #2024-KAKAO-WINTER-INTERNSHIP , #esalgo-week23
접근방법
- BFS, DFS로 접근할 수 있지만 각 그래프의 규칙을 찾아서 접근하면 훨씬 더 간결하게 풀 수 있는 문제
- 정점은 무조건 출발간선이 두개 이상, 도착간선 0개를 만족함. 
- 막대 그래프의 경우 마지막 노드는 도착간선만 있음
- 8자 그래프의 경우 중심에 2개 간선이 들어오고, 2개의 간선이 나감을 알 수 있음
- 위 규칙을 통해 정점과 각 그래프의 개수를 구할 수 있음.
*/

import java.util.*;

class Solution {
    public int[] solution(int[][] edges) {
        int[] ans = new int[4];
        Map<Integer, int[]> node = new HashMap<>();
        
        // 간선정보 setting
        for(int[] e : edges) {
            int start = e[0];
            int end = e[1];
            if(!node.containsKey(start)) {
                node.put(start, new int[]{0,0});
            }
            if(!node.containsKey(end)) {
                node.put(end, new int[]{0,0});
            }
            node.get(start)[0]++; // start -> end 개수
            node.get(end)[1]++; // end -> start 개수
        }
        
        for (Map.Entry<Integer, int[]> entry : node.entrySet()) {
            int key = entry.getKey();
            int[] n = entry.getValue();
            
            // 출발간선 두개, 도착간선 0개라면 정점
            if(n[0] >= 2 && n[1] == 0) {
                ans[0] = key;
            } 
            // 막대그래프
            else if(n[0] == 0 && n[1] > 0) {
                ans[2]++;
            } 
            // 8자 그래프
            else if(n[0] >= 2 && n[1] >= 2) {
                ans[3]++;
            }
        }
        // 정점에 연결된 그래프 - 막대그래프 - 8자 그래프 = 도넛그래프
        ans[1] = node.get(ans[0])[0] - ans[2] - ans[3];
        
        return ans;
    }
}