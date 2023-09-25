package week05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 *
 * 완탐문제이다.
 * 가장 왼쪽의 계란을 드는 것은 정해져있다.
 * 이 계란을 들고, 꺠지지 않은 계란중에서 어떤것을 먼저 칠지 고른다
 * 하나를 고르면 재귀를 호출해서 또 그 다음 것을 깨보고 하는 식으로 진행한다.
 * 계란을 치는 순서에 따라서 더 많이 깰 수 있기 때문에 모든 경우를 다해보는 완전탐색이 필요하다.
 */
public class BOJ16987_계란으로_계란치기 {

    //계란 객체 - 내구도와 무게를 저장한다.
    private static class Egg{
        int S, W;//내구도와 무게

        public Egg(int S, int W){
            this.S = S;
            this.W = W;
        }

        //계란을 쳤을때 내구도 감소하는 메서드
        public int updateS(int weight){
            this.S -= weight;

            //0이거나 0보다 작으면 깨진것으로 0으로 만듦.
            if(this.S <= 0){
                this.S = 0;
                return 1;
            }
            return 0;
        }

        public Egg newEgg(){
            return new Egg(S,W);
        }
    }

    //계란수를 저장할 변수이다.
    private static int N;

    //깰 수 있는 계란의 최대 개수 저장.
    private static int maxBreak;

    //계란 객체 배열 - 계란을 순서대로 나열한 배열이다.
    private static Egg[] eggArray;

    //모든 경우를 재귀를 돌면서 체크할 메서드 - eggIndex 손에 든 계란번호, nextIndex : 다음에 칠 계란 인덱스.
    private static void recursive(int eggIndex, int currentBreakCount){


        //누적된 깨버린 계란수 업데이트
        maxBreak = Math.max(maxBreak, currentBreakCount);

        //가장 오른쪽 계란이라면 종료
        if(eggIndex == N ) return;

        //해당 계란이 깨졌으면 재귀 다음 값 호출.
        if(eggArray[eggIndex].S == 0){
            recursive(eggIndex + 1, currentBreakCount);
        }

        else{
            //반복문을 돌면서 계란을 쳐 본다.
            for(int i = 0; i < N; i++){

                if(i == eggIndex) continue;

                //다음에 칠 계란
                Egg temp = eggArray[i].newEgg();

                //다음에 칠 계란이 깨졌으면 패스.
                if(temp.S == 0) continue;

                Egg origin = eggArray[eggIndex].newEgg();

                //계란 깨기
                int breakCount = 0;
                breakCount += eggArray[eggIndex].updateS(eggArray[i].W);
                breakCount += eggArray[i].updateS(eggArray[eggIndex].W);

                recursive(eggIndex + 1, currentBreakCount + breakCount);

                //원상복귀.
                eggArray[i] = temp;
                eggArray[eggIndex] = origin;

            }
        }
    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        N = Integer.parseInt(br.readLine());

        maxBreak = Integer.MIN_VALUE;
        eggArray = new Egg[N];

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            int S = Integer.parseInt(st.nextToken());
            int W = Integer.parseInt(st.nextToken());

            eggArray[i] = new Egg(S,W);
        }

        recursive(0, 0);

        System.out.println(maxBreak);

    }
}
