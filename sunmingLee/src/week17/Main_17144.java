import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main_17144 {
    static final int[] dr = {-1, 1, 0, 0};
    static final int[] dc = {0, 0, -1, 1};
    static int width, height, time;
    static int[] airCleaner;
    static int[][] room;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine(), " ");
        height = Integer.parseInt(st.nextToken());
        width = Integer.parseInt(st.nextToken());
        time = Integer.parseInt(st.nextToken());

        room = new int[height][width];
        for (int i = 0; i < height; i++) {
            st = new StringTokenizer(br.readLine(), " ");
            for (int j = 0; j < width; j++) {
                room[i][j] = Integer.parseInt(st.nextToken());

            }
        }

        airCleaner = new int[2];  // 공기청정기가 위치한 행
        for (int i = 0; i < height; i++) {
            if (room[i][0] == -1) {
                airCleaner[0] = i;
                break;
            }
        }
        airCleaner[1] = airCleaner[0] + 1;

        // 1초씩 반복
        for (int i = 0; i < time; i++) {
            spread();   // 미세먼지 확산
            activateCleaner();   // 공기청정기 작동
        }

        System.out.println(countDust());
    }   // end of main

    /**
     * 방 내 먼지량 반환
     */
    private static int countDust() {
        int count = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (room[i][j] > 0) {
                    count += room[i][j];
                }
            }
        }

        return count;
    }   // end of countDust

    /**
     * 공기청정기 작동
     */
    private static void activateCleaner() {
        // 위쪽 공기청정기
        int r = airCleaner[0], c = width - 1;
        for (int i = r - 1; i > 0; i--) {
            room[i][0] = room[i - 1][0];
        }

        for (int j = 0; j < c; j++) {
            room[0][j] = room[0][j + 1];
        }

        for (int i = 0; i < r; i++) {
            room[i][c] = room[i + 1][c];
        }

        for (int j = c; j > 1; j--) {
            room[r][j] = room[r][j - 1];
        }
        room[r][1] = 0; // 공기 청정기에서 나온 공기


        // 아래쪽 공기청정기
        r = airCleaner[1];
        int rMax = height - 1;
        for (int i = r + 1; i < rMax; i++) {
            room[i][0] = room[i + 1][0];
        }

        for (int j = 0; j < c; j++) {
            room[rMax][j] = room[rMax][j + 1];
        }

        for (int i = rMax; i > r; i--) {
            room[i][c] = room[i - 1][c];
        }

        for (int j = c; j > 1; j--) {
            room[r][j] = room[r][j - 1];
        }
        room[r][1] = 0; // 공기 청정기에서 나온 공기
    }   // end of airCleaner

    /**
     * 미세먼지 확산
     */
    private static void spread() {
        int[][] tempRoom = new int[height][width];  // 확산된 미세먼지 계산을 위한 임시 배열
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (room[i][j] < 1) {
                    continue;
                }

                int dustAmount = room[i][j] / 5;    // 확산될 먼지량
                int num = 0;    // 확산된 칸의 수
                for (int k = 0; k < 4; k++) {
                    int nr = i + dr[k];
                    int nc = j + dc[k];

                    if (inBound(nr, nc) && room[nr][nc] != -1) {    // 칸이 있고 공기청정기가 없는곳으로 확산
                        tempRoom[nr][nc] += dustAmount;
                        num++;
                    }
                }

                tempRoom[i][j] += room[i][j] - dustAmount * num;
            }
        }

        copyArray(tempRoom);    // 모든 먼지 확산 후, 원본 배열에 상태 업데이트

    }   // end of spread


    private static void copyArray(int[][] tempRoom) {
        for (int i = 0; i < height; i++) {
            room[i] = Arrays.copyOf(tempRoom[i], width);
        }

        // 공기청정기 위치 등록
        room[airCleaner[0]][0] = room[airCleaner[1]][0] = -1;
    }   // end of copyArray

    private static boolean inBound(int nr, int nc) {
        return nr >= 0 && nr < height && nc >= 0 && nc < width;
    }   // end of inBound

    /**
     * 방 상태 출력
     */
    private static void printArray() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                System.out.print(room[i][j] + " ");
            }
            System.out.println();
        }
    }
}