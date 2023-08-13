package BOJ;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class Main_21608 {
    static int[][] seat;
    static int N;
    static Set<Integer>[] likes;
    static int[] dr = {-1, 0, 1, 0};
    static int[] dc = {0, 1, 0, -1};


    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        N = Integer.parseInt(br.readLine());
        seat = new int[N][N];   // 학생들의 자리
        likes = new HashSet[N * N + 1];  // 인덱스번호의 학생이 좋아하는 학생들 집합
        for (int i = 1; i < likes.length; i++) {
            likes[i] = new HashSet<Integer>();
        }

        StringTokenizer st;
        for (int i = 0; i < N * N; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            int temp = Integer.parseInt(st.nextToken());    // 현재 자리에 앉힐 학생
            // temp가 좋아하는 학생들
            for (int j = 0; j < 4; j++) {
                likes[temp].add(Integer.parseInt(st.nextToken()));
            }

            int[][] tempSeat1 = new int[N][N];   // 임시배열1
            int[][] tempSeat2 = new int[N][N];   // 임시배열2
            int maxNear = findNearStudent(temp, tempSeat1); // 인접한 칸에 앉은 선호학생의 최대수
            if (maxNear != -1) {    // 1번 조건을 만족하는 자리가 여러개임
                maxNear = findNearSeat(maxNear, tempSeat1, tempSeat2);
                takeSeat(temp, maxNear, tempSeat2);
            }
        }

        // 만족도 구하기
        int answer = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                int temp = seat[i][j];
                int count = 0;
                for (int k = 0; k < dr.length; k++) {
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if (inBound(nr, nc) && likes[temp].contains(seat[nr][nc])) {
                        count++;
                    }
                }

                switch (count) {
                    case 1:
                        answer++;
                        break;
                    case 2:
                        answer += 10;
                        break;
                    case 3:
                        answer += 100;
                        break;
                    case 4:
                        answer += 1000;
                        break;
                    default:
                        break;
                }
            }
        }

        System.out.println(answer);
    }   // end of main


    /**
     * temp 학생을 조건이 만족하는 자리에 앉힘
     */
    private static void takeSeat(int temp, int maxNear, int[][] tempSeat) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tempSeat[i][j] == maxNear) {
                    seat[i][j] = temp;
                    return;
                }
            }
        }
    }   // end of takeSeat

    /**
     * 1번 조건을 만족하는 자리 중 인접한 칸 중에서 비어있는 칸의 개수 세기
     * return : 인접한 빈자리의 최대수
     */
    private static int findNearSeat(int maxNear, int[][] tempSeat1, int[][] tempSeat2) {
        int max = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (tempSeat1[i][j] != maxNear) {
                    tempSeat2[i][j] = -1; // 결과값이 0일때와 구분용
                    continue;
                }

                int sum = 0;  // 인접한 빈자리 개수
                // 인접한 자리 체크
                for (int k = 0; k < dr.length; k++) {
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if (inBound(nr, nc) && seat[nr][nc] == 0) {
                        sum++;
                    }
                }

                tempSeat2[i][j] = sum;
                if (max < tempSeat2[i][j]) {
                    max = tempSeat2[i][j];
                }
            }
        }

        return max;
    }   // end of findNearSeat

    /**
     * 해당칸에 인접한 좋아하는 친구의 수 찾기
     * return : 인접한 칸에 앉은 선호학생의 최대수
     */
    private static int findNearStudent(int temp, int[][] tempSeat) {
        int maxNear = 0;
        int count = 0;  // 1번 조건에 해당하는 칸의 수
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (seat[i][j] != 0) {
                    tempSeat[i][j] = -1;    // 결과값이 0일때와 구분용
                    continue;
                }

                // 인접한 자리 체크
                for (int k = 0; k < dr.length; k++) {
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if (inBound(nr, nc) && likes[temp].contains(seat[nr][nc])) {
                        tempSeat[i][j]++;
                    }
                }

                if (maxNear < tempSeat[i][j]) {
                    maxNear = tempSeat[i][j];
                    count = 1;
                } else if (maxNear == tempSeat[i][j]) {
                    count++;
                }
            }
        }

        // 1번 조건을 만족하는 자리가 하나뿐임
        if (count == 1) {
            takeSeat(temp, maxNear, tempSeat);
            return -1;
        }

        return maxNear;
    }   // end of findNearStudent

    private static boolean inBound(int nr, int nc) {
        if (nr < 0 || nr >= N || nc < 0 || nc >= N) {
            return false;
        }

        return true;
    }   // end of inBound
}
