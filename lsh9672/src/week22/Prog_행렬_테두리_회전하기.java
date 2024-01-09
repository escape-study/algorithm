package week22;

import java.util.Arrays;

/**
 * 아이디어
 * 단순 구현
 * 시작점의 값을 저장할 변수를 두고, 이동할 다음위치의 값을 이전 위치에 저장한다.
 */

public class Prog_행렬_테두리_회전하기 {

    //하우상좌
    private final static int[] dx = {1, 0, -1, 0};
    private final static int[] dy = {0, 1, 0, -1};
    private static int[][] maps;


    //행렬 만드는 메서드
    private static void makeMaps(int rows, int columns){

        int count = 1;

        for(int i = 0; i < rows; i++){
            for(int j = 0; j < columns; j++){
                maps[i][j] = count++;
            }
        }
    }

    //범위를 벗어나는지 확인하는 메서드
    private static boolean check(int nextX, int nextY, int[] queries){
        return nextX >= (queries[0] - 1) && nextX <= (queries[2] - 1) &&
                nextY >= (queries[1] - 1) && nextY <= (queries[3] - 1);
    }

    //회전 메서드 - 회전하면서 최소값을 저장해둠.
    private static int rotate(int[] queries){


        int dir = 0;

        int currentX = queries[0] - 1;
        int currentY = queries[1] - 1;

        int minValue = maps[currentX][currentY];
        int startValue = maps[currentX][currentY];

        int nextX = -1;
        int nextY = -1;
        //총 4번 반복
        while(dir <= 3){

            nextX = currentX + dx[dir];
            nextY = currentY + dy[dir];

            //다음 값이 범위안에 있다면, 다음값 저장.
            if(check(nextX, nextY, queries)){
                minValue = Math.min(minValue, maps[currentX][currentY]);
                maps[currentX][currentY] = maps[nextX][nextY];
                currentX = nextX;
                currentY = nextY;
                continue;
            }

            //범위값이 아니라면 방향전환.
            dir++;
        }

        //처음에 저장해 둔 값 넣기
        maps[queries[0] - 1][queries[1]] = startValue;

        return minValue;
    }


    public int[] solution(int rows, int columns, int[][] queries) {
        int[] answer = new int[queries.length];
        maps = new int[rows][columns];
        makeMaps(rows, columns);

        for(int i = 0; i < queries.length; i++){
            answer[i] = rotate(queries[i]);
        }


        return answer;
    }

    public static void main(String[] args) {

        Prog_행렬_테두리_회전하기 s = new Prog_행렬_테두리_회전하기();

        int rows1 = 6;
        int columns1 = 6;
        int[][] queries1 = 	{{2,2,5,4},{3,3,6,6},{5,1,6,3}};
        System.out.println(Arrays.toString(s.solution(rows1,columns1, queries1)));

        int rows2 = 3;
        int columns2 = 3;
        int[][] queries2 = {{1,1,2,2},{1,2,2,3},{2,1,3,2},{2,2,3,3}};
        System.out.println(Arrays.toString(s.solution(rows2,columns2, queries2)));

        int rows3 = 100;
        int columns3 = 97;
        int[][] queries3 = {{1,1,100,97}};
        System.out.println(Arrays.toString(s.solution(rows3,columns3, queries3)));
    }
}
