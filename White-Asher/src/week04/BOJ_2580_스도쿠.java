/*
제목 : 스도쿠
알고리즘 유형 : #backtracking
플랫폼 : #BOJ
난이도 : G4
문제번호 : 2580
시간 : 70m
해결 : O
저장 : O
해결언어 : java
문제링크 : https://www.acmicpc.net/problem/2580
특이사항 : #esalgo-week04
접근방법
- 백트래킹을 곁들인 구현문제
- 경우의 수가 여러개 나올 수 있으므로 한개만 출력하는 것이 포인트
*/

import java.io.*;
import java.util.*;


public class BOJ_2580_스도쿠 {
    static int[][] arr = new int[9][9];
    static StringTokenizer st;
    // 빈칸 저장용 리스트
    static List<int[]> blank = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < 9; i++) {
            st = new StringTokenizer(br.readLine());
            for (int j = 0; j < 9; j++) {
                int input = Integer.parseInt(st.nextToken());
                arr[i][j] = input;
                if(input == 0) blank.add(new int[]{i, j});
            }
        } // input :: end

        // 0인곳 찾아서 숫자 넣고 탐색하기
        putNum(0);

    }

    public static void putNum(int idx){
        // 블랭크 사이즈 수 만큼 다 돌았다면 탐색할 수 없으니 리턴함.
        if(idx == blank.size()) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    sb.append(arr[i][j]).append(" ");
                }
                sb.append("\n");
            }
            System.out.println(sb);
            // 만족하는 경우의 수가 여러개일 수 있으므로 한개만 출력하고 프로그램 종료
            System.exit(0);
        }
        // 빈칸 좌표 뽑아서
        int[] cur = blank.get(idx);
        int tarY = cur[0];
        int tarX = cur[1];
        // 가능한 숫자 배열에 저장
        List<Integer> list = findNum(tarY, tarX);
        // 가능한 숫자들 탐색하기
        for (int i = 0; i < list.size(); i++) {
            arr[tarY][tarX] = list.get(i);
            // 해당 칸에 숫자를 넣을 수 있다면 다음 빈칸 탐색하러가기
            if(isPossible(tarY, tarX)) {
                putNum(idx+1);
            }
            // 원복
            arr[tarY][tarX] = 0;
        }

    }

    // 해당 칸에 숫자를 넣을 수 있는지 체크
    public static boolean isPossible(int y, int x){
        return rowCheck(x) && colCheck(y) && boxCheck(y, x);
    }

    // 넣을 수 있는 숫자 찾기
    public static List<Integer> findNum(int y, int x) {

        int[] checkarr = new int[10];
        // 세로
        for(int i = 0; i < 9; i++) {
            checkarr[arr[i][x]] = 1;
        }
        // 가로
        for(int i = 0; i < 9; i++) {
            checkarr[arr[y][i]] = 1;
        }
        // 박스
        int yidx = y / 3;
        int xidx = x / 3;
        for(int i = yidx *3; i < (yidx + 1) * 3; i++) {
            for(int j = xidx *3; j < (xidx + 1) * 3; j++) {
                checkarr[arr[i][j]] = 1;
            }
        }
        /*
        가로, 세로, 박스 체크를 하면서 0인 숫자를 뽑아서 리스트에 넣어버리는데
        여기서 0이라는 것은 가로 세로 박스칸에 넣을 수 있는 숫자의 여부를 뜻한다.
        만약 1 이상이라면 가로, 세로, 박스에 해당 숫자가 있으므로 해당칸에
        그 숫자를 넣을 수 없게 된다.
         */
        List<Integer> list = new ArrayList<>();
        for (int i = 1; i < 10; i++) {
            if(checkarr[i] == 0) list.add(i);
        }
        return list;
    }

    // 세로 체크
    public static boolean rowCheck(int x) {
        int[] temp = new int[10];
        for (int i = 0; i < 9; i++) {
            if(arr[i][x] == 0) continue;
            temp[arr[i][x]]++;
        }
        for (int i = 0; i < 9; i++) {
            if(temp[i] >= 2) return false;
        }
        return true;
    }

    // 가로체크
    public static boolean colCheck(int y) {
        int[] temp = new int[10];
        for (int i = 0; i < 9; i++) {
            if(arr[y][i] == 0) continue;
            temp[arr[y][i]]++;
        }
        for (int i = 0; i < 9; i++) {
            if(temp[i] >= 2) return false;
        }
        return true;
    }

    // 박스 체크
    public static boolean boxCheck(int y, int x) {
        int[] temp = new int[10];
        int yidx = y / 3;
        int xidx = x / 3;
        for(int i = yidx *3; i < (yidx + 1) * 3; i++) {
            for(int j = xidx *3; j < (xidx + 1) * 3; j++) {
                if(arr[i][j] == 0) continue;
                temp[arr[i][j]]++;
            }
        }
        for (int i = 0; i < 9; i++) {
            if(temp[i] >= 2) return false;
        }
        return true;
    }
}
