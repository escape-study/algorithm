package week52;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * dp + 그리디
 * length, width, height를 각각 x,y,z로 잡는다면,
 * dp[x][y][z] = min(dp[x - n][y - n][z - n], dp[x][y][z]) 해당 공식이 성립한다.
 * 이때 중요한 것은 length,width, height가 각각 최대 10^6까지 나오기 때문에
 * 한칸씩 증가시키는 방법은 불가능 하다.
 *
 * (추가)
 * 초기에 생각한 dp로는 x,y,z 부분 크기가 크기 때문에 불가능하다.
 * 그리디를 이용해야 한다.
 * 우선 최소의 상자를 채우기 위해서는 가장 큰 것을 최대한 많이 채우고, 그다음 크기를 채우는 식으로 가는 것이 좋다.
 * 만약 상자가 2의 제곱꼴이 아니면 가장 큰 것을 넣는 경우보단 그보다 작은 크기를 넣는 경우가 더 적을수도 있지만,
 * 해당 문제에는 2의 제곱꼴이므로,주어진 상자에서 넣을 수 있는 크기를 먼저 넣고 시작하는 것이 좋다.
 * 큰것부터 넣으면 해당 값이 최소가 되기 때문에 해당문제는 그리디로 볼 수 있다.
 * x,y,z중 가장 작은 것을 기준으로 나눠본다.
 * 가령, 3*4*5라고 한다면 4*4*4로는 나눌 수 없고, 2*2*2로 해야 한다.
 *
 * (참고)
 * https://steady-coding.tistory.com/14
 * 주어진 상자를 2의 배수로 분할하는 아이디어까지는 접근했는데, 디테일한 구성(이전 갯수를 저장하고 하는등)은 구현을 못해서 참고했음.
 */

public class BOJ1493_박스채우기 {

	public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		int length = Integer.parseInt(st.nextToken());
		int width = Integer.parseInt(st.nextToken());
		int height = Integer.parseInt(st.nextToken());

		int n = Integer.parseInt(br.readLine());

		int[] numArray = new int[20]; // 상자 수, 인덱스는 제곱꼴의 지수

		for(int i = 0; i < n; i++){
			st = new StringTokenizer(br.readLine());

			int a = Integer.parseInt(st.nextToken());
			int b = Integer.parseInt(st.nextToken());

			numArray[a] = b;
		}

		long result = 0;//최종적으로 출력할 개수.
		long prevCount = 0;//이전 크기의 상자를 몇개 넣었는지 -> 2*2*2크기의 상자를 2개 넣었으면 다음 작은크기인 1*1*1로 분할할때, 해당 개수에 8곱하면 구할 수 있음.


		//큰것부터 상자를 채워보기
		//큰 것 부터 채우기 위해서, 주어진 상자를 2의 제곱수만큼 분할해본다(shift 연산 사용)
		for(int i = 19; i >= 0; i--){

			prevCount *= 8;

			//몇개의 상자로 분할 할 수 있는지 체크.(분할 하려고 하는 상자 크기가 더 크면 0이 나옴)
			long divideCount = (long) (length >> i) * (width >> i) * (height >> i) - prevCount;
			divideCount = Math.min(divideCount, numArray[i]); //분할한 개수와 해당 상자 수를 비교(더 적게 가지고 있을 수도 있음.);

			result += divideCount; //나눈 상자 수 저장.
			prevCount += divideCount; //현재 크기의 상자로 분할했을때의 갯수 추가.
		}

		//최종적으로 prevCount에는 부피가 누적되어있음,문제에서 주어진 상자의 부피와 같은지 확인
		if(prevCount != ((long)length * width * height)){
			result = -1;
		}

		System.out.println(result);

	}
}
