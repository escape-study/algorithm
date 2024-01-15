package week23;

        import java.util.Arrays;

/**
 * 아이디어
 * 슬라이딩 윈도우
 * 연속 부분 수열의 합과 동일하다
 * 단지 펄스 부분 수열이므로 두번 해야 한다.
 * -1, 1, -1, 1...이 곱해진 수열에서의 연속부분 수열의 합.
 * 1, -1, 1, -1...이 곱해진 수열에서의 연속 부분 수열의 합
 * 결국 각각의 수가 나올 수 있는 경우는 펄스가 곱해진 두가지 경우이므로, 계산후에 슬라이딩 윈도우로 구해주면 된다.
 * 수가 오름차순 정렬되어있는 것이 아니므로, 투포인터로는 구할 수 없다.
 *
 * => 슬라이딩 윈도우는 n^2으로 시간초과가 난다.
 */
public class Prog_연속_펄스_부분_수열의_합 {

    //펄스 수열 구하기.
    private static int[] pulseSequence(int[] sequence, int std){

        int[] tempSequence = new int[sequence.length];

        for(int i = 0; i < sequence.length; i++){
            tempSequence[i] = sequence[i] * std;

            std = (std == 1 ? -1 : 1);
        }

        return tempSequence;

    }


    public long solution(int[] sequence) {
        long answer = 0;

        //각각의 펄스 수열 구하기.
        int[] aPulse = pulseSequence(sequence, 1); // 1, -1, 1....
        int[] bPulse = pulseSequence(sequence, -1); // -1, 1, -1...

        //누적한 최대값을 저장할 dp배열
        long[] aDp = new long[sequence.length];
        long[] bDp = new long[sequence.length];

        //초기값
        aDp[0] = aPulse[0];
        bDp[0] = bPulse[0];

        answer = Math.max(aDp[0], bDp[0]);

        for(int i = 1; i < sequence.length; i++){
            aDp[i] = Math.max(aDp[i - 1] + aPulse[i], aPulse[i]);
            bDp[i] = Math.max(bDp[i - 1] + bPulse[i], bPulse[i]);

            answer = Math.max(answer, Math.max(aDp[i], bDp[i]));
        }



        return answer;
    }


    public static void main(String[] args) {
        Prog_연속_펄스_부분_수열의_합 s = new Prog_연속_펄스_부분_수열의_합();
        int[] sequence = {2,3,-6,1,3,-1,2,4};
        System.out.println(s.solution(sequence));
    }
}
