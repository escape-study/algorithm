package week24;

import java.util.Arrays;

/**
 * 아이디어
 * 투포인터
 * 두 수를 먼저 정렬한다.
 * A,B모두 정렬후에 가장 큰수의 위치(맨마지막) 인덱스를 잡는다.
 * B에서 제일 큰수로 이길수 있는 A의 제일 큰수를 선택한다.
 * A가 B의 수와 크거나 같다면, A쪽인덱스를 하나씩 줄여가며 찾는다.
 * 시간복잡도 : 2 * nlogn(정렬 2회) + n(투포인터로 최대) = 2 * nlogn + n = O(nlogn)
 */
public class Prog_숫자게임 {

    public int solution(int[] A, int[] B) {
        int answer = 0;

        //두 수 배열 각각 정렬
        Arrays.sort(A);
        Arrays.sort(B);

        //투포인터로 비교를 위해서 두 배열의 인덱스 설정
        int indexA = A.length - 1;
        int indexB = B.length - 1;

        while(indexA >= 0){

            //A가 더 크거나 같다면 B쪽 인덱스를 하나씩 줄여봄
            if(A[indexA] >= B[indexB]){
                indexA--;
            }
            //B가 더 크다면 둘다 줄이고 승점 추가.
            else{
                indexA--;
                indexB--;
                answer++;
            }
        }

        return answer;
    }
    public static void main(String[] args) {

        Prog_숫자게임 s = new Prog_숫자게임();
        int[] A1 = {5,1,3,7};
        int[] B1 = {2,2,6,8};
        System.out.println(s.solution(A1,B1));

        int[] A2 = {2,2,2,2};
        int[] B2 = {1,1,1,1};
        System.out.println(s.solution(A2,B2));
    }
}
