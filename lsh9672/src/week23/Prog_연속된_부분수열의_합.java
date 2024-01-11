package week23;

import java.util.Arrays;

/**
 * 아이디어
 * 투포인터
 */

public class Prog_연속된_부분수열의_합 {


    public int[] solution(int[] sequence, int k) {

        int[] answer = new int[2];
        int startIndex = 0;
        int endIndex = 0;
        int sum = sequence[startIndex];

        int minIndex = 1_000_001;
        int minSize = Integer.MAX_VALUE;

        while(startIndex <= endIndex){


            //부분수열의 합보다 작다면, endIndex를 증가 시킴.
            if(k > sum) {
                if(++endIndex >= sequence.length) break;
                sum += sequence[endIndex];
            }

            //부분수열의 합보다 크다면, startIndex를 증가시킴.
            else if(k < sum) {
                if(++startIndex >= sequence.length) break;
                sum -= sequence[startIndex - 1];
            }
            else{
                //k인 경우라면 더 짧은 경우에 업데이트 함.
                if(minSize > endIndex - startIndex){
                    minSize = endIndex - startIndex;
                    minIndex = startIndex;
                }

                if(++endIndex >= sequence.length) break;
                sum += sequence[endIndex];
                sum -= sequence[startIndex++];
            }
        }


        answer[0] = minIndex;
        answer[1] = minIndex + minSize;
        return answer;
    }

    public static void main(String[] args) {
        Prog_연속된_부분수열의_합 s = new Prog_연속된_부분수열의_합();

        int[] sequence1 = {1, 2, 3, 4, 5};
        int k1 = 7;
        System.out.println(Arrays.toString(s.solution(sequence1, k1)));

        int[] sequence2 = {1, 1, 1, 2, 3, 4, 5};
        int k2 = 5;
        System.out.println(Arrays.toString(s.solution(sequence2, k2)));

        int[] sequence3 = {2, 2, 2, 2, 2};
        int k3 = 6;
        System.out.println(Arrays.toString(s.solution(sequence3, k3)));
    }
}
