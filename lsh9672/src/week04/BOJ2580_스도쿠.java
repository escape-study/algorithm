package week04;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 전형적인 백트래킹
 * 9*9 형태의 배열이라서 단순 완탐을 하면 터진다
 * 중간에 필요없는 부분은 가지치기를 통해서 처리가 필요하다.
 * 0인 좌표들을 리스트에 담아서 하나씩 뽑아서 수를 1~9까지 배치해본다.
 * 배치가 가능하다면, 다음 위치를 뽑아서 이동한다.
 */

public class BOJ2580_스도쿠 {

    //좌표를 저장할 객체
    private static class Node{
        private int x, y;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }
    }

    //결과를 출력할 스트링 빌더
    private static StringBuilder result;

    //스도쿠 담을 배열
    private static int[][] maps;

    //결과를 스트링 빌더에 담을 메서드
    private static void resultPrint(){

        for(int i = 0; i < 9; i++){

            for(int j = 0; j < 9; j++){
                result.append(maps[i][j]);

                if(j == 8) continue;
                result.append(" ");
            }

            if(i == 8) continue;
            result.append("\n");
        }
    }

    //수를 놓을 좌표가 담긴 리스트
    private static List<Node> numList;

    //해당 수를 놓을 수 있는지 확인하는 메서드
    private static boolean check(Node node) {

        int x = node.getX();
        int y = node.getY();

        //해당 열 체크
        for(int i = 0; i < 9; i++){

            if(i == y) continue;

            if(maps[x][i] == maps[x][y]) return false;
        }

        //해당 행 체크
        for(int i = 0; i < 9; i++){

            if(i == x) continue;

            if(maps[i][y] == maps[x][y]) return false;
        }

        //3*3 칸 확인.
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){

                if(((i + ((x / 3) * 3)) == x) && ((j + ((y / 3) * 3)) == y)) continue;

                if(maps[i + ((x / 3) * 3)][j + ((y / 3) * 3)] == maps[x][y]) return false;
            }
        }


        return true;

    }

    //재귀 돌면서 수 놓아보는 메서드
    private static boolean recursive(int index){


        //모든 칸을 다 채웠으면 저장
        if(index == numList.size()){

            resultPrint();
            return true;
        }


        Node node = numList.get(index);
        for(int num = 1; num <= 9; num++){

            maps[node.getX()][node.getY()] = num;
            if(check(node)){
                if(recursive(index + 1)) return true;
            }

            maps[node.getX()][node.getY()] = 0;
        }


        return false;
    }
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        result = new StringBuilder();
        numList = new ArrayList<>();

        maps = new int[9][9];

        for(int i = 0; i < 9; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < 9; j++){
                maps[i][j] = Integer.parseInt(st.nextToken());

                if(maps[i][j] == 0){
                    numList.add(new Node(i,j));
                }
            }
        }

        recursive(0);

        System.out.println(result);


    }


}
