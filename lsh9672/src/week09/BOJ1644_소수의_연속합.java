package week09;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 *아이디어
 * 에라토스테네스의 체 + 슬라이딩 윈도우
 *
 * 디피를 고민했지만, 연속된 합이고,중복해서 사용할 수 없기 때문에,
 * 주어진 수보다 작은 소수를 에라토스테네스의 체를 이용하여 빠르게 구하고, 크기순으로 정렬한다.
 * 시작인덱스와 끝인덱스를 동일하게 0으로 두고,시작한다.
 * 해당 범위의 수의 합이, 주어진 수보다 작으면 끝 인덱스를 하나 증가시킨다.
 * 해당 범위의 수의 합이, 주어진 수보다 크면, 시작인덱스를 하나 증가시킨다.
 * 시작 인덱스가 끝인덱스보다 커지거나, 끝 인덱스가 소수배열의 크기를 벗어나면 종료한다.
 * 주의할 점은 여러개의 경우의 수 이므로 목표 수가 나오더라도 계속 이동해야 한다.(같은 값이 나오면 둘다 이동시킴.)
 */

public class BOJ1644_소수의_연속합 {

    //자연수
    private static int N;

    //경우의 수 저장
    private static int result;

    //소수 저장할 리스트
    private static List<Integer> primeNumList;

    //소수 구하는 메서드 - 에라토스테네스의 체
    private static void makePrimeNum(){

        boolean[] primeCheck = new boolean[N + 1];

        for(int i = 2; i <= Math.sqrt(N); i++){

            if(primeCheck[i]) continue;

            for(int j = i*2; j <= N; j += i){
                primeCheck[j] = true;
            }
        }


        for(int i = 2; i <= N; i++){
            if(primeCheck[i]) continue;

            primeNumList.add(i);
        }

    }

    //슬라이딩 윈도우를 수행하면서 경우의 수 구하기
    private static void countNum(){

        int start = 0;
        int end = 0;

        int sum = primeNumList.get(0);

        while(start <= end || end < primeNumList.size()){

            //수의 합이 목표 값이면
            if(sum == N){
                //해당 케이스 저장하고, 윈도우 크기 유지하고 이동
                result++;

                start++;
                end++;

                if(end >= primeNumList.size()) break;

                sum -= primeNumList.get(start - 1);
                sum += primeNumList.get(end);


            }
            //목표값 보다 작을 때
            else if(sum < N){
                //end 값 증가.
                end++;
                if(end >= primeNumList.size()) break;
                sum += primeNumList.get(end);
            }
            //목표값 보다 클때,
            else {
                start++;
                sum -= primeNumList.get(start - 1);
            }
        }

    }

    public static void main(String[] args) throws Exception{

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        result = 0;

        primeNumList = new ArrayList<>();

        if(N == 1){
            System.out.println(0);
            return;
        }

        makePrimeNum();
        countNum();

        System.out.println(result);
    }
}
