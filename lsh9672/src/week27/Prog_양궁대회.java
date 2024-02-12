package week27;



import java.util.Arrays;

/**
 * 아이디어
 * 중복조합
 * 라이언이 과녁을 맞추는 모든 경우를 중복조합으로 뽑고 어피치와 비교한다,.
 *
 */
public class Prog_양궁대회 {


    //최대 점수.
    private static int maxScore;
    //최대 점수일때 과녁 상태.
    private static int[] maxScoreState;

    //최대 점수일때 상태 저장을 위해 배열을 복사할 메서드
    private static int[] copyArray(int[] lionState){
        int[] copy = new int[lionState.length];

        System.arraycopy(lionState,0, copy,0, copy.length);

        return copy;
    }

    //어피치의 과녁 상태와 비교해서 점수를 뽑아낼 메서드 => -1이면 라이언이 짐.
    private static int getScore(int[] apeach, int[] lion){

        int lionScore = 0;
        int apeachScore = 0;

        for(int i = 0; i < apeach.length; i++){

            if(apeach[i] == 0 && lion[i] == 0) continue;
            //어피치가 점수를 가져가는 경우.
            if(apeach[i] >= lion[i]){
                apeachScore += 10 - i;
            }
            //라이언이 점수를 가져가는 경우.
            else{
                lionScore += 10 - i;
            }
        }

        //라이언 점수가 더 높을때만 우승.
        if(lionScore > apeachScore) return lionScore - apeachScore;

        return -1;
    }
    //점수가 같을때, 이전에 저장된 상태와 현재 상태를 비교해서 더 낮은 쪽을 가져옴. - false : 이전값 그대로, true : 새로운 값으로 업데이트.
    private static boolean compareState(int[] newLion){

        for(int i = newLion.length - 1; i >= 0; i--){

            //이전에 저장된 상태보다 새로운 상태의 낮은 점수가 더 많으면 true리턴
            if(maxScoreState[i] < newLion[i]) return true;
            else if(maxScoreState[i] > newLion[i]) return false;
        }

        return false;
    }

    //재귀돌면서 중복조합으로 뽑아낼 메서드.
    private static void recursive(int index, int[] info, int[] lionState, int count, int n){

        //화살을 n번 쐈으면 계산
        if(count == n){
            int score = getScore(info, lionState); //비교해서 점수 뽑기.
            //점수가 -1이면 라이언이 졌음.
            if(score == -1) return;


            //라이언이 이겼으면, 기존의 값보다 큰경우, 같고, 낮은점수를 많이 쏜 경우에는 업데이트.
            if(maxScore < score || (maxScore == score && compareState(lionState))){
                //이전에 저장된 값 보다 큰경우는 그냥 업데이트.
                maxScore = score;
                maxScoreState = copyArray(lionState);
            }
            return;
        }

        //각 점수에 화살 쏘기.
        for(int i = index; i < info.length; i++){

            lionState[i]++;
            recursive(i, info, lionState, count + 1, n);
            lionState[i]--;
        }

    }

    public int[] solution(int n, int[] info) {
        int[] answer;

        maxScore = -1;

        recursive(0, info, new int[info.length], 0, n);

        answer = maxScoreState;

        //점수가 변동없이 -1이면, 라이언이 한번도 이기지 못헀다는 뜻으로, -1을 배열에 담아 리턴.
        if(maxScore == -1) answer = new int[]{-1};

        return answer;
    }

    public static void main(String[] args) {
        Prog_양궁대회 s = new Prog_양궁대회();

        int n1 = 5;
        int[] info1 = {2,1,1,1,0,0,0,0,0,0,0};
        System.out.println(Arrays.toString(s.solution(n1,info1)));
//
//        int n2 = 1;
//        int[] info2 = {1,0,0,0,0,0,0,0,0,0,0};
//        System.out.println(Arrays.toString(s.solution(n2,info2)));
//
//        int n3 = 9;
//        int[] info3 = {0,0,1,2,0,1,1,1,1,1,1};
//        System.out.println(Arrays.toString(s.solution(n3,info3)));
//
//        int n4 = 10;
//        int[] info4 = {0,0,0,0,0,0,0,0,3,4,3};
//        System.out.println(Arrays.toString(s.solution(n4,info4)));
    }
}
