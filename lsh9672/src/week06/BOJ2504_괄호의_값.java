package week06;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * 아이디어
 * 스택을 이용하는 문제
 * 괄호를 하나씩 스택에 넣고, 닫는 괄호면 스택안의 값과 비교해서 확인한다.
 * 숫자라면 연산후에 다시넣는다.
 * 완벽한 괄호라면 계산하고 나온 수를 다시 넣어서 다음 괄호 또는 최종연산에 사용한다.
 *
 * (수정)
 * 여는 괄호면 괄호에 맞게 2 또는 3을 변수에 곱해준다.(해당 변수의 초기값은 1)
 * 닫는 괄호가 나오면 이전에 저장해둔 괄호랑 비교해서 올바른 괄호라면, 저장해둔 값을 최종 출력 변수에 더해준다,
 * 이 때 중요한 것은, 여는 괄호가 나올때 마다 곱해줬으니, 닫는 괄호가 나와서 종료를 했으면 그 수만 큼 나눠줘야 다음 괄호가 해당 연산을 할 수 있다.
 */

public class BOJ2504_괄호의_값 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String bracketStr = br.readLine();

        //괄호처리할 스택 선언
        Stack<Character> bracketStack = new Stack<>();

        //최종적인 값을 반환할 값
        int resultValue = 0;
        //여는 괄호가 나올때 마다 누적해둘 변수
        int tempValue = 1;


        //반복문 돌면서 넣기
        loop:
        for(int i = 0; i < bracketStr.length(); i++){

            //문자열 하나 빼기.
            char temp = bracketStr.charAt(i);

            switch(temp){
                // 여는 소괄호라면
                case '(':
                    //2를 곱해서 누적시켜두고 스택에 넣음
                    tempValue *= 2;
                    bracketStack.push(temp);
                    break;
                //여는 대괄호 라면
                case '[':
                    //3를 곱해서 누적시켜두고 스택에 넣음
                    tempValue *= 3;
                    bracketStack.push(temp);
                    break;

                //닫는 소괄호라면
                case ')':
                    //스택의 마지막 값을 확인해야 함 - 맞는 괄호가 아니라면 0을 출력하도록 함.
                    if(bracketStack.isEmpty() || bracketStack.peek() != '('){
                        resultValue = 0;
                        break loop;
                    }

                    //맞는 괄호라면.

                    //최종 출력변수에 누적한 값 저장하고, 다음 연산을 위해 나누기2를 함.
                    //바로 이전값이 여는 괄호,일때만 계산.
                    if(bracketStr.charAt(i - 1) == '(') resultValue += tempValue;
                    tempValue /= 2;
                    bracketStack.pop();

                    break;

                //닫는 대괄호라면
                case ']':
                    if(bracketStack.isEmpty() || bracketStack.peek() != '['){
                        resultValue = 0;
                        break loop;
                    }

                    //맞는 괄호라면.
                    //최종 출력변수에 누적한 값 저장하고, 다음 연산을 위해 나누기3를 함.
                    if(bracketStr.charAt(i - 1) == '[') resultValue += tempValue;
                    tempValue /= 3;
                    bracketStack.pop();

                    break;
            }
        }

        if(!bracketStack.isEmpty()) resultValue = 0;
        System.out.println(resultValue);

    }
}
