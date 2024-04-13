/*
제목 : 상어초등학교
알고리즘 유형 : #implementation
플랫폼 : #BOJ
난이도 : G5
문제번호 : 21608
시간 : -
해결 : -
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/21608
특이사항 : #esalgo-week01
*/

import java.util.*;
import java.io.*;

/*
1. 비어있는 칸 중에서 좋아하는 학생이 인접한 칸에 가장 많은 칸으로 자리를 정한다.
2. 1을 만족하는 칸이 여러 개이면, 인접한 칸 중에서 비어있는 칸이 가장 많은 칸으로 자리를 정한다.
3. 2를 만족하는 칸도 여러 개인 경우에는 행의 번호가 가장 작은 칸으로, 그러한 칸도 여러 개이면 열의 번호가 가장 작은 칸으로 자리를 정한다.
*/

public class BOJ_21608_상어초등학교 {
    static StringTokenizer st;
    static int N;
    static List<Integer>[] list;
    static int[][] desk;
    static int[] dy = new int[]{-1,1,0,0};
    static int[] dx = new int[]{0,0,-1,1};
    public static void main(String[] args) throws Exception{
        // start :: input
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        list = new ArrayList[N*N+1];
        desk = new int[N][N];
        for (int i = 0; i < N*N; i++) {
            st = new StringTokenizer(br.readLine());
            int student = Integer.parseInt(st.nextToken());
            list[student] = new ArrayList<>();
            for (int j = 0; j < 4; j++) {
                list[student].add(Integer.parseInt(st.nextToken()));
            }
            // end :: input
            batch(student);
        }

        // 계산로직
        int answer = 0;
        for (int y = 0; y < N; y++) {
            for (int x = 0; x < N; x++) {
                int likeCnt = 0;
                for (int d = 0; d < 4; d++) {
                    int ny = y + dy[d];
                    int nx = x + dx[d];
                    if (ny < 0 || nx < 0 || ny >= N || nx >= N) continue;
                    int student = desk[y][x];
                    int tarStudent = desk[ny][nx];
                    for (int i = 0; i < 4; i++) {
                        if (tarStudent == list[student].get(i)) {
                            likeCnt++;
                        }
                    }
                }
                answer += (int) Math.pow(10, likeCnt-1);
            }
        }

        System.out.println(answer);

    }

    // 자리 배치 메서드
    public static void batch(int student) {
//        System.out.println("student method : " + student);
        // 전체 탐색
        int maxLike = 0; // 가장 앉고 싶은 자리. (좋아하는 학생)
        int maxEmpty = 0; // 가장 앉고 싶은 자리. (비어있는 곳)
        List<Location> locationList = new ArrayList<>();
        for (int y = N - 1; y >= 0; y--) {
            for (int x = N - 1; x >= 0; x--) {
                if(desk[y][x] != 0) continue;
                int emptyArea = 0; // 주변 비어있는 카운트
                int likeArea = 0; // 주변 좋아하는 학생 카운트
                // 4방면 탐색
                for (int d = 0; d < 4; d++) {
                    int ny = y + dy[d];
                    int nx = x + dx[d];
                    // 배열 밖을 나가면 탐색하지 않음
                    if (ny < 0 || nx < 0 || ny >= N || nx >= N) continue;
                    // 탐색 칸 앉은 사람이 좋아하는 학생인지 판별
                    for (int i = 0; i < 4; i++) {

//                        System.out.println("desk[ny][nx] : " + desk[ny][nx] + " list[student].get(i) : " + list[student].get(i));

                        if (desk[ny][nx] == list[student].get(i)) {
                            likeArea++;
                        }
                    }
                    // 탐색 칸 이 비어있다면 카운트 추가
                    if (desk[ny][nx] == 0) {
                        emptyArea++;
                    }
                }
//                System.out.println("likearea : " + likeArea +" emptyArea : " + emptyArea);

                // 가장 앉고 싶은 자리 갱신된다면 기존 리스트 값 초기화 후 새로운 값 add
                if(likeArea > maxLike) {
                    locationList.clear();
                    locationList.add(new Location(y, x, emptyArea, likeArea));
                    maxLike = likeArea;
                    maxEmpty = emptyArea;
                }
                // 가장 좋아하는 자리가 여러개라면 리스트에 넣기
                if (likeArea == maxLike) {
                    // 좋아하는 자리중 빈 자리 많은 곳 갱신되면
                    if(emptyArea > maxEmpty) {
                        // 기존 리스트 날리고 새로운 값 구겨넣기
                        locationList.clear();
                        locationList.add(new Location(y, x, emptyArea, likeArea));
                        maxEmpty = emptyArea;
                    } else if(emptyArea < maxEmpty) {
                        // 좋아하는 자리 동일하고 빈자리도 동일하면 리스트 넣기
                        continue;
                    } else {
                        locationList.add(new Location(y, x, emptyArea, likeArea));
                    }

                }
            }
        }

        // 전부 탐색하고 나서 리스트 정렬
        locationList.sort((o1, o2) -> {
            if(o2.y == o1.y) return o1.x - o2.x;
            else return o1.y - o2.y;
        });
//        System.out.println("locationList size : " + locationList.size());
//        for (int i = 0; i < locationList.size(); i++) {
//            System.out.println(locationList.get(i));
//        }
        Location target = locationList.get(0);
//        System.out.println("tarY : " + target.y);
//        System.out.println("tarX : " + target.x);

        desk[target.y][target.x] = student;

//        for (int i = 0; i < N; i++) {
//            for (int j = 0; j < N; j++) {
//                System.out.print(desk[i][j]);
//            }
//            System.out.println();
//        }
//        System.out.println("==================\n");
    }

    public static class Location {
        int y;
        int x;
        int emptyArea;
        int likeArea;

        public Location(int y, int x, int emptyArea, int likeArea) {
            this.y = y;
            this.x = x;
            this.emptyArea = emptyArea;
            this.likeArea = likeArea;
        }

        @Override
        public String toString() {
            return "Location{" +
                    "y=" + y +
                    ", x=" + x +
                    ", emptyArea=" + emptyArea +
                    ", likeArea=" + likeArea +
                    '}';
        }
    }

}
