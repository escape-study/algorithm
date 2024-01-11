package week23;

public class PROG_연속하는펄스수열 {
    public long solution(int[] sequence) {
        int mul = 1;

        long plus = 0;
        long minus = 0;

        long max = Long.MIN_VALUE;
        for (int i = 0; i < sequence.length; i++) {
            plus += mul*sequence[i];
            if(plus < 0){
                plus = 0;  
            }else{
                max = Math.max(max, plus);
            }
            
            mul = -mul;
            
            minus += mul * sequence[i];
            if(minus < 0){
                minus = 0;
            }else{
                max = Math.max(max, minus);
            }
        }
        
        long answer = max;
        return answer;
    }
}