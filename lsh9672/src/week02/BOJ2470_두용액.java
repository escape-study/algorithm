package week02;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 투포인터를 이용하여 해결
 * 수를 정렬해두고, 왼쪽, 오른쪽 인덱스를 왼쪽끝, 오른쪽 끝 인덱스에 둔다.
 * 0보다 작다면, 왼쪽 인덱스를 증가시켜서 더하는 수를 증가시켜준다.
 * 0보다 크다면, 오른쪽 인덱스를 감소시켜서 더하는 수를 감소시켜준다.
 * 이와같이 0에 가깝게 만들어주다가, 0인 값을 찾게 되면, 두 수를 출력하면 된다.
 */

public class BOJ2470_두용액 {
    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        int N = Integer.parseInt(br.readLine());

        int[] numArray = new int[N];
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < N; i++){
            numArray[i] = Integer.parseInt(st.nextToken());
        }

        Arrays.sort(numArray);

        int startIndex = 0;
        int endIndex = N - 1;

        int minGap = Integer.MAX_VALUE; //두 용액의 차이의 절대값을 저장. - 두 용액차의 절대값이 가장 작은 값을 선정
        int minValueSmall = 0; // 두 용액중 작은쪽 저장
        int minValueBig = 0; // 두 용액중 큰쪽 저장.

        //왼쪽 인덱스가 오른쪽 인덱스를 넘어갈때까지 반복.
        while(startIndex < endIndex){

            //두 용액의 차이 구하기.
            int tempGap = numArray[startIndex] + numArray[endIndex];

            //두 용액의 차이의 절대값을 이용해서 기존값과 비교해서 저장.
            if(Math.abs(tempGap) < minGap){
                minGap = Math.abs(tempGap);
                minValueSmall = numArray[startIndex];
                minValueBig = numArray[endIndex];
            }

            //인덱스 업데이트 - 용액의 차이에 따라 업데이트.
            if(tempGap < 0){
                startIndex++;
            }
            else if(tempGap > 0){
                endIndex--;
            }
            else{
                break;
            }
        }

        System.out.println(minValueSmall + " " + minValueBig);

    }
}
