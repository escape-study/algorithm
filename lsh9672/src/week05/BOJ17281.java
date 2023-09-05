package week05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 완탐을 해야되는 문제
 * 타순을 정해야 하고, 하기 떄문에 순서에 따라 달라지는 순열이다
 * 타순이 한번 정해지면 이닝마다 이를 반복해야 하기 때문에, 매 반복마다 타석에 설 사람을 뽑는 것이 아닌,
 * 미리 타순에 설 사람을 뽑아야 한다.
 */
public class BOJ17281 {

    //이닝 수 저장하는 변수
    private static int N;

    //최대 점수를 구할 변수
    private static int maxScore;

    //각 이닝별 얻게 되는 점수를 저장한 배열.
    private static int[][] inning;

    //재귀돌리면서 선수를 뽑았는지 체크하는 배열
    private static boolean[] visited;

    //선수 순번을 가지고 있을 배열
    private static int[] playerOrder;

    //각 타석별 결과에 따라 야구 베이스 상황을 업데이드할 메서드
    public static int updateBase(int[] baseStatus, int status){

        //얻게 될 점수.
        int returnScore = 0;

        //홈 베이스에 타자 배치
        baseStatus[0] = 1;

        //status 크기만큰 3루부터 1루까지 역순으로 돌면서 이동 - 4루에 도달하면 점수 +1
        for(int i = 3; i >= 0; i--){

            //해당 베이스에 사람이 없으면 패스.
            if(baseStatus[i] == 0) continue;

            int nextBase = i + status;

            //더한 값이 4 이상이면 점수가 난 것.
            if(nextBase >= 4) {
                //해당 베이스 비우기
                baseStatus[i] = 0;
                //점수 추가
                returnScore++;
            }
            //4 미만이라면 해당 베이스에 사람위치 시키기.
            else{
                baseStatus[i] = 0;
                baseStatus[nextBase] = 1;
            }
        }
        return returnScore;
    }

    //야구 베이스 상황을 크기 4 배열로 나타내고, 해당 배열을 초기화 할 메서드 - 이닝 시작시 초기화 해야됨.
    public static void initBase(int[] baseStatus){

        for(int i = 0; i < 4; i ++){
            baseStatus[i] = 0;
        }
    }

    //타순 정보가 들어있는 배열을 받아서 이닝을 반복 - 3아웃이면 이닝 변경, 득점한 점수 반환.
    private static int playGame(){

        //순번 인덱스
        int orderIndex = 1;

        //최종 점수
        int totalScore = 0;

        //베이스 상태
        int[] baseStatus = new int[4];

        for(int i = 1; i <= N; i++){

            int outCount = 0;
            //이닝별 플레이어 상태 - 안타를 치는지 아웃을 당하는지.
            int[] playerStatus = inning[i];

            //새 이닝에는 베이스 초기화
            initBase(baseStatus);

            //아웃 카운트가 3이 될때까지 반복
            while(outCount < 3){

                //해당 순번의 선수
                int player = playerOrder[orderIndex];

                //해당 선수가 타석에서 한 행동
                int status = playerStatus[player];

                //아웃이면 아웃카운트 증가.
                if(status == 0) outCount++;

                //아웃이 아닌 경우에는 베이스상태를 업데이트 하고 점수를 반환.
                else totalScore += updateBase(baseStatus, status);

                orderIndex++;
                if(orderIndex == 10) orderIndex = 1;
            }


        }

        return totalScore;

    }

    //순열로 타순 정하기 - 타순이 전부 정해지면 게임을 진행하는 메서드를 실행, count는 모든 선수의 타순을 정했는지 체크하는 변수.
    private static void recursive(int index){

        //9명의 선수 모두 순번을 정하면, 게임을 진행
        if(index > 9){
            maxScore = Math.max(maxScore, playGame());
            return;
        }

        for(int player = 2; player <= 9; player++){

            //이미 선별한 선수라면 패스
            if(visited[player]) continue;

            visited[player] = true;
            playerOrder[index] = player;

            int nextIndex = index + 1;
            //다음 순번이 4번이면 패스하도록 +2함(4번은 항상 고정)
            if(nextIndex == 4) nextIndex = index + 2;

            //재귀 호출
            recursive(nextIndex);

            //원상복귀 - 선수 순번 배열은 초기화 필요 없음, 어차피 인덱스에 의해서 결정되는 것.
            visited[player] = false;

        }

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());
        maxScore = 0;

        inning = new int[N+1][10];
        visited = new boolean[10];

        for(int i = 1; i <= N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 1; j <= 9; j++){
                inning[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //플레이어 순번을 지정할 배열.
        playerOrder = new int[10];
        playerOrder[4] = 1;
        visited[1] = true;

        recursive(1);

        System.out.println(maxScore);

    }


}
