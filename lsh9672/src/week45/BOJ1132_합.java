package week45;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Comparator;

/**
 * 아이디어(참고함)
 * 그리디
 * 각 자리별로 가중치를 두어 총 가중치가 큰 알파벳 부터 큰 수를 할당한다
 * 가령
 * AB
 * B
 * 이와 같이 주어졌다면, 10*A, 1B + 1B해서 A에 큰수를 할당하는 것이 더 큰수를 만든다
 * 이때 주의할 점은, 맨앞에 0이 와서는 안된다는 것이다.
 * 0위치에 0일수 없는 알파벳이 왔다면, 0이어도 되는 알파벳중 가중치가 낮은 것을 해당위치로 보내고, 나머지는 수의 위치를 하나씩 땡겨준다
 * 즉 ABCD인데 D가 0이 될수 없고 나머지는 0이 되어도 된다면, 0이 되어도 되는 것중 가중치가 낮은 C를 0으로 보내고, D는 앞으로 한칸 당겨주면 된다.
 */
public class BOJ1132_합 {

	private static class Alpha implements Comparable<Alpha>{
		int index;
		long weight;
		boolean isZero; //0이어도 되는지 여부

		public Alpha(int index){
			this.index = index;
			this.isZero = true;
		}

		@Override
		public int compareTo(Alpha alpha) {

			if(alpha.weight > this.weight) return 1;
			else if(alpha.weight == this.weight) return 0;

			return -1;
		}

		@Override
		public String toString() {
			return "Alpha{" +
				"index=" + (char) (index + 'A') +
				", weight=" + weight +
				", isZero=" + isZero +
				'}';
		}
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

		int N = Integer.parseInt(br.readLine());

		int alphaLen = 'J' - 'A' + 1;
		Alpha[] alphaArray = new Alpha[alphaLen]; //알파벳 별 가중치, 위치, 0여부를 저장
		String[] inputStr = new String[N];//입력으로 주어지는 문자열 저장 - 정답계산할떄 필요함.
		//초기 객체 저장 -> 가중치를 계속해서 더해야 하기 떄문에 미리 객체 생성해서 저장
		for(int i = 0; i < alphaLen; i++){
			alphaArray[i] = new Alpha(i);
		}


		for(int i = 0; i < N; i++){
			String tempAlpha = br.readLine();

			inputStr[i] = tempAlpha;

			long count = 1;

			for(int j = tempAlpha.length() - 1; j >= 0; j--){

				alphaArray[tempAlpha.charAt(j) - 'A'].weight += (long) count;

				//맨앞에 오는 알파벳이면 0일수 없음.
				if(j == 0) alphaArray[tempAlpha.charAt(j) - 'A'].isZero = false;

				count *= 10;
			}
		}

		//가중치 순으로 내림차순 정렬
		Arrays.sort(alphaArray);

		//가중치가 0이 아닌 위치, 즉, 탐색해야 할 마지막 인덱스 찾기.
		int lastIndex = 0;
		for(int i = alphaLen - 1; i >= 0; i--){

			if(alphaArray[i].weight == 0) continue;

			lastIndex = i;
			break;
		}




		//마지막 위치가 0이고, 0위치에 0이면 안되는 수가 왔으면, 0이 아니여도 되는 가장 작은 수를 찾아서 0위치에 두고 나머지는 앞으로 한칸씩 당기기.
		if(lastIndex == alphaLen - 1 && !alphaArray[lastIndex].isZero){

			for(int i = lastIndex - 1; i >= 0; i--){

				//false이면 패스.
				if(!alphaArray[i].isZero) continue;

				Alpha temp = alphaArray[i];
				for(int j = i + 1; j < alphaLen; j++){
					alphaArray[j - 1] = alphaArray[j];
				}

				alphaArray[alphaLen - 1] = temp;
				break;
			}
		}

		//최종적으로 알파벳을 숫자로 변환
		int[] numArray = new int[alphaLen];
		int num = 9;
		for(Alpha alpha : alphaArray){
			numArray[alpha.index] = num;
			num--;
		}

		long result = 0;
		//숫자로 변환해서 더하기.
		for(String str : inputStr){
			long count = 1;

			for(int i = str.length() - 1; i >= 0; i--){

				result += (long) numArray[str.charAt(i) - 'A'] * count;
				count *= 10;
			}
		}

		System.out.println(result);

	}
}
