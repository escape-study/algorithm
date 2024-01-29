package week25;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 아이디어
 * 재귀를 이용한 완탐
 * 앞에서부터 괄호를 치는 경우와, 치지 않는 경우로 나눠서 재귀호출하여, 모든 경우를 다 체크한다.
 * 숫자와 수식을 분리해서 저장한다.
 * 수식인덱스와 수식인덱스+1이 연산하는 방법으로 구성할 수 있다.(수식이 0번째이면, 숫자에서 0,1번쨰를 연산하는 식으로)
 * 중요한것은 중첩괄호가 없다는 것이다(중첩괄호가 있었으면 귀찮아졌음)
 * 앞쪽부터 탐색하되, 괄호를 치는 경우가 생기면, 앞에 있는 것들은 전부 연산하면 된다.
 *
 * 3 + 8 * 7 - 9 * 2 이 예시에서  3 + (8 * 7) 이 경우를 생각하면, 그대로 다 연산하면 된다.
 * 만약 중첩괄호가 있으면 바로 연산하면 안되는게. 3 + ((8 * 7) - 9) 이러한 케이스가 있기 때문에 3은 마지막에 연산이 되어야 한다.
 * 하지만 중첩괄호가 없기 때문에 해당경우는 생길수 없고, 3 + (8 * 7) - (9 * 2) 이런케이스만 발생 할 수 있다.
 * 그러면 앞에서 8 * 7에 괄호를 치게 되면, 3 + (8 * 7)전부 연산후에 재귀로 넘겨줘도 된다.
 *
 */

public class BOJ16637_괄호_추가하기 {


    //수식의 길이
    private static int N;

    //최대값
    private static int result;

    //숫자 리스트
    private static List<Integer> numList;

    //연산자 리스트.
    private static List<Character> operationList;

    //수식이 문자열이므로, 연산자와 수를 받아서 연산하고 결과를 수행하는 메서드
    private static int operation(int a, int b, char operation){

        int result = 0;

        switch(operation){
            case '+':
                result = a + b;
                break;
            case '-':
                result = a - b;
                break;
            case '*':
                result = a * b;
                break;
        }

        return result;
    }

    //재귀호출하면서 모든 경우를 다 계산해볼 메서드
    private static void recursive(int index, int totalCal){

        //배열의 최대치에 도달하면, 값을 저장하고 종료.
        if(index >= operationList.size()){
            result = Math.max(result, totalCal);
            return;
        }

        //해당 위치에 괄호를 치는 경우 - 앞에 연산식먼저 계산하는 경우.
        recursive(index + 1, operation(totalCal, numList.get(index + 1), operationList.get(index)));

        //해당 위치에 괄호를 치지 않는 경우 - 뒤에 연산먼저 계산하는 경우./
        if(index + 2 <= operationList.size())
            recursive(index + 2, operation(totalCal, operation(numList.get(index + 1), numList.get(index + 2), operationList.get(index + 1)), operationList.get(index)));

    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        N = Integer.parseInt(br.readLine());
        String opStr = br.readLine();
        result = Integer.MIN_VALUE;

        numList = new ArrayList<>();
        operationList = new ArrayList<>();

        //리스트에 값 넣기
        for(int i = 0; i < opStr.length(); i++){
            //짝수면 수
            if(i % 2 == 0) numList.add(Character.getNumericValue(opStr.charAt(i)));

            //홀수면 연산자
            else operationList.add(opStr.charAt(i));
        }

        recursive(0, numList.get(0));

        System.out.println(result);
    }
}
