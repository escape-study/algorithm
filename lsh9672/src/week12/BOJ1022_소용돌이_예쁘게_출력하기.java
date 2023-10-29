package week12;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 빡구현 문제
 * 주의 할 점은 좌표를 -3으로 줬다는 점이다.
 * 구현하기 편하게 0,0부터 시작하도록 하고, 입력받은 좌표를 0,0으로 옮긴 좌표계로 변환하는 메서드를 만들어서 대응 하면 된다.
 *
 * (추가)
 * r,c의 최대값으로 배열을 만들어서 저장하면 터진다.
 * 구하고자 하는 부분만 배열로 만들어서 저장하면 된다.
 * 소용돌이 모양으로 숫자를 1부터 돌리지만, 배열에는 저장하지 않고, 해당하는 r,c 범위일때만 저장하도록 한다.
 *
 */
public class BOJ1022_소용돌이_예쁘게_출력하기 {

    //소용돌이를 돌리기 위한 방향 - 우상좌하
    private static final int[] dx = {0, -1, 0, 1};
    private static final int[] dy = {1, 0, -1, 0};

    //좌표를 담는 객체.
    private static class Node{

        //소용 돌이가 각 방향으로 2번씩 같은 칸만큼 이동 - 11,22,33,44 ......
        //dirCount는 방향 회전 횟수 - 2회가 되면 전진 칸수가 증가됨.
        //count는 현재 전진 횟수.
        //totalCount는 총 전진해야 하는 횟수.
        int x,y,dir, totalCount, dirCount, count;

        public Node(int x, int y){
            this.x = x;
            this.y = y;
            this.dir = 0;
            this.count = 0;
            this.totalCount = 1;
            this.dirCount = 0;
        }

        //다음 위치로 이동.
        public void move(){

            //방향 전환 두번하면 전진 칸수 증가.
            if(this.dirCount == 2){
                this.dirCount = 0;
                this.totalCount++;
            }
            //같은 방향으로 전진칸수만큼 전진했으면 방향 회전
            if(count == totalCount){
                this.count = 0;
                this.dirCount++;
                this.dir = (this.dir + 1) % 4;
            }

            this.x += dx[dir];
            this.y += dy[dir];
            this.count++;
        }

        //결과 배열 범위에 들어왔는지 확인
        public boolean check(int r1, int r2, int c1, int c2){

            //범위 안에 들어오면 true
            if(x >= r1 && x <= r2 && y >= c1 && y <= c2) return true;

            return false;
        }
    }

    //시작 노드
    private static Node startNode;

    private static StringBuilder result;

    //숫자가 담긴 배열 - 최종적으로 출력할 배열.
    private static int[][] resultArray;


    //가장 큰 수의 길이 찾기 - 모든 수의 길이를 맞추기 위해서
    private static int maxLen(int num1, int num2, int num3, int num4){

        int maxNum = Math.max(Math.max(Math.max(num1, num2), num3), num4);

        return String.valueOf(maxNum).length();
    }

    //출력을 위해 스트링빌더 객체에 담기.
    private static void setBuilder(int r1, int r2, int c1, int c2){

        int len = maxLen(resultArray[0][0], resultArray[r2 - r1][0], resultArray[0][c2 - c1], resultArray[r2 - r1][c2 - c1]);

        for(int i = 0; i <= r2 - r1; i++){
            for(int j = 0; j <= c2 - c1; j++){
                result.append(String.format(
                        "%" + len + "s",
                        String.valueOf(resultArray[i][j])));

                if(j == c2 - c1) continue;
                result.append(" ");
            }

            if(i == r2 - r1) continue;
            result.append("\n");
        }

    }

    //좌표를 결과 배열에 맞게 변환하여 값 넣기.
    private static void insertValue(int r1, int r2, int c1, int c2, int number){

        int r = r1 * (-1) + startNode.x;
        int c = c1 * (-1) + startNode.y;

        resultArray[r][c] = number;
    }


    //연산을 해서 최종적으로 출력 배열을 만들 메서드
    private static void logic(int r1, int r2, int c1, int c2){
        //배열에 숫자가 다 담겼는지 확인하기 위한 변수
        int arrayCount = 0;

        //소용돌이 숫자
        int number = 1;

        //반복문을 돌면서 숫자 담기. - 결과 배열에 값을 다 담을 때까지 반복.
        while(arrayCount < (resultArray[0].length * resultArray.length)){

            //목표 범위내에 들어왔다면 - 결과 배열에 넣음
            if(startNode.check(r1,r2,c1,c2)){
                insertValue(r1,r2,c1,c2,number);
                arrayCount++;
            }

                //소용돌이 좌표 전진.
            startNode.move();
            number++;
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int r1 = Integer.parseInt(st.nextToken());
        int c1 = Integer.parseInt(st.nextToken());
        int r2 = Integer.parseInt(st.nextToken());
        int c2 = Integer.parseInt(st.nextToken());

        //실제 값을 저장할 배열 만들기
        resultArray = new int[r2 - r1 + 1][c2 - c1 + 1];

        //소용돌이 시작 노드
        startNode = new Node(0, 0);

        //소용돌이 돌리고 배열에 수 채우기
        logic(r1, r2, c1, c2);

        //출력을 위한 스트링 빌더
        result = new StringBuilder();
        setBuilder(r1,r2,c1,c2);

        System.out.println(result);

    }
}
