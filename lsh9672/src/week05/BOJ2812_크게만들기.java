package week05;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Stack;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 스택을 이용한 그리디
 * 그리디인 이유는 수를 가장 크게 만드려면 맨 앞의 수가 가장 커야한다.
 * 예를 들어 8911 이 있고, 이중 수를 하나만 지워서 가장 큰 수를 만들어야 된다고 했을때,
 * 8을 지우게 되면 911로 가장 큰수가 나온다.
 * 지울수 있는 갯수에 맞게 최대한 앞에 작은 수를 지워주면된다,
 * 현재 수를 선택하고 그것보다 앞선 수와 비교해서 더 크다면, 앞선 수를 지우는 식으로 해결해 나간다
 * 모든경우를 다해보는 것이 아니라, 가장 큰 수를 구하기 위해서는 현재 상황에서 가장 알맞는 방법을 선택하는 식이므로 그리디이다.
 */
public class BOJ2812_크게만들기 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        //숫자 자리수
        int N = Integer.parseInt(st.nextToken());
        //삭제할 숫자.
        int K = Integer.parseInt(st.nextToken());

        String strNum = br.readLine();

        //숫자를 저장할 스택
        Stack<Integer> numStack = new Stack<>();

        //수를 출력할 스트링 빌더
        StringBuilder sb = new StringBuilder();

        //삭제된 숫자체크
        int deleteNumCount = 0;

        //반복문 돌면서 이전에 저장된 수와 비교하면서 스택 채우기
        for(int i = 0; i < N; i++){

            int tempNum = Character.getNumericValue(strNum.charAt(i));

            //스택에 값이 없거나, 이전에 들어간 값이 크거나 같다면 저장.
            //기존에 스택에 들어있는 값이, 더 작다면 제거후 저장.
            while(true){

                if(!numStack.isEmpty() && deleteNumCount < K && numStack.peek() < tempNum){
                    numStack.pop();
                    deleteNumCount++;
                }
                else break;

            }

            numStack.push(tempNum);
        }


        //최종적으로 스택을 스트링 빌더에 저장해서 출력
        //처음 자리수에서 수를 삭제한 후 개수만큼만 출력 -> 모든 수가 같다면 위의 과정에서 pop하지 않음.
        for(int i = 0; i < N - K; i++){
            sb.append(numStack.get(i));
        }

        System.out.println(sb);

    }
}
