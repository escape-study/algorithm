package week22;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 아이디어
 * 구현
 * 해당 블록의 가장 위에 오늘 블럭은 해당 수의 최대 약수이다.
 * 약수는 수의 제곱근까지 나오기 때문에 제곱근까지 반복해준다.
 *
 * 이때 주의할 점은, 나올수 있는 블럭은 10_000_000까지이다.
 * 즉, 나눠떨어졌을때, 몫이 해당수를 넘지 않는지 확인해야 한다.
 * 넘지 않았다면 그대로 리턴하고, 넘었다면 계속 약수를 구했을떄 최대가 되는 수가 약수이다
 * 단, 주의할 점은 수가 10_000_000을 넘는데 소수이면, 여기에 맞는 블럭은 1말고는 없다는 점이다.
 *
 */
public class Prog_숫자블록 {

    //나올 수 있는 수의 최대 치 - 블럭의 최대치
    private final static int MAX_BLOCK_NUM = 10_000_000;


    public int[] solution(long begin, long end) {
        int[] answer = new int[(int)(end - begin) + 1];

        //수의 뒤부터 반복함.
        int index = (int)(end - begin);
        for(int num = (int)end; num >= (int)begin; num--){


            //수를 소수들로 나눠봄
            //해당 수가 1이라면 답은 0
            if(num == 1){
                answer[index--] = 0;
                continue;
            }


            int divisor = -1; //최대 약수 저장.
            for(int j = 2; j <= (int) Math.sqrt(num); j++) {


                if (num % j != 0) continue;

                if(num / j <= MAX_BLOCK_NUM){
                    divisor = num / j;
                    break;
                }

                divisor = j;
            }

            //나눌수 있는 모든 수를 나눠봤는데. 나눠지지 않았다면.
            if(divisor == -1){
                divisor = 1;
            }

            answer[index--] = divisor;
        }

        return answer;
    }

    public static void main(String[] args) {

        Prog_숫자블록 s = new Prog_숫자블록();

        long begin = 100000014L;
        long end = 100000016L;
        System.out.println(Arrays.toString(s.solution(begin, end)));


    }
}
