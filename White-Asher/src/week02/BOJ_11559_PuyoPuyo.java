/*
제목 : PuyoPuyo
알고리즘 유형 : #implementation , #bfs
플랫폼 : #BOJ
난이도 : G4
문제번호 : 11559
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/11559
특이사항 : esalgo-week02
*/

import java.io.*;
import java.util.*;

public class BOJ_11559_PuyoPuyo {
    static StringTokenizer st;
    static String[][] arr;
    static boolean[][] check;
    static int[] dx = {-1,1,0,0};
    static int[] dy = {0,0,-1,1};

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        arr = new String[12][6];
        for (int i = 0; i < 12; i++) {
            String[] input = br.readLine().split("");
            for (int j = 0; j < 6; j++) {
                arr[i][j] = input[j];
            }
        }
        int breakCnt = 0;
        // 돌았는대 깰게 있다면?
        while(search()){
            // 카운트 추가하고 블럭깨기;
            breakCnt++;
            push();
        }
        System.out.println(breakCnt);
    }

    // 블럭 찾기
    public static boolean search() {
        check = new boolean[12][6];
        boolean isSearch = false;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                // 해당 블럭에 빈공간이 아니고 깰 곳이 아니라면?
                if(!arr[i][j].equals(".") && !check[i][j]) {
                    String tar = arr[i][j];
                    if(BFS(i, j, tar)){
                        isSearch = true;
                    }
                }
            }
        }
        return isSearch;
    }

    // 깨지는지 확인
    public static boolean BFS(int i, int j, String tar){
        boolean[][] visited = new boolean[12][6];
        Queue<int[]> queue = new ArrayDeque<>();
        queue.add(new int[]{i,j});
        visited[i][j] = true;
        int cnt = 1;

        while(!queue.isEmpty()) {
            int[] q = queue.poll();
            int qx = q[0];
            int qy = q[1];

            for (int d = 0; d < 4; d++) {
                int nx = qx + dx[d];
                int ny = qy + dy[d];
                if(nx < 0 || ny < 0 || nx >= 12 || ny >= 6) continue;
                if(visited[nx][ny]) continue;
                if(!arr[nx][ny].equals(tar)) continue;
                cnt++;
                visited[nx][ny] = true;
                queue.add(new int[]{nx, ny});
            }
        }
        // 깨질 수 있는 4개 이상 블럭 체크
        if(cnt >= 4){
            for (int k = 0; k < 12; k++) {
                for (int l = 0; l < 6; l++) {
                    if(visited[k][l]) {
                        check[k][l] = true;
                    }
                }
            }
            return true;
        } else {
            return false;
        }
    }

    // 전부 체크 된거 없애고 전부 아래로 내리기
    public static void push() {
        // 반복문 돌면서 깰 곳 없애고 아래로 내리기
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 6; j++) {
                if(check[i][j]){
                    arr[i][j] = ".";
                }
            }
        }

        for (int i = 0; i < 6; i++) {
            Queue<String> queue = new ArrayDeque<>();
            for (int j = 11; j >= 0; j--) {
                if(!arr[j][i].equals(".")){
                    queue.add(arr[j][i]);
                    arr[j][i] = ".";
                }
            }
            int start = 11;
            while(!queue.isEmpty()){
                arr[start--][i] = queue.poll();
            }
        }
    }
}