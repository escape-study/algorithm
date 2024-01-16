import java.util.*;

class Solution_숫자게임 {
    public int solution(int[] A, int[] B) {
        int answer = 0;
        Arrays.sort(A);
        Arrays.sort(B);

        int indexA = A.length - 1;
        int indexB = B.length - 1;
        while(indexA >= 0){
            if(A[indexA] < B[indexB]){
                answer++;
                indexB--;
            }

            indexA--;
        }
        return answer;
    }
}