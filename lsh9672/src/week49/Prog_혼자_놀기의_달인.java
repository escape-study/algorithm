package week49;

/**
 * 아이디어
 * 완탐
 * 재귀를 이용하여 모든 경우를 탐색.
 * 시작점을 잡고, 해당 점부터 재귀호출을 이용하여 연결된 모든 경우를 다 확인한다.
 * 이때 1번그룹 상자수, 2번그룹 상자수를 저장 할 변수 두개를 같이 넘기고, 이 두 변수가 최대가 되도록 한다.
 * 두수의 곱이 최대가 되기 위해서는 두수가 최대가 되어야 한다
 * 따라서 상자를 탐색한 후에 같은 상자를 만나면, 누적된 상자를 곱할 두 변수중 작은쪽과 비교해서 업데이트 한다.
 *
 * (추가)
 * 처음에는 시작점에 따라서 그룹의 상자 개수가 달라지지 않을까 생각했다.
 * 1 - 2 - 3 - 4 - 5 - 2 이런 사이클이 있다면 2에서 시작했을때와 1에서 시작했을때가 다르지 않을까 생각했는데,
 * 문제를 다시보니 수 중복이 없다, 즉 1 -> 2로 갔다면 5 -> 2이거는 나올수 없다는 뜻이다.
 * 따라서 재귀로 할필요 없이, 반복문으로 처리해도 된다.
 */
public class Prog_혼자_놀기의_달인 {

	public int solution(int[] cards) {

		int result = -1;
		boolean[] visited = new boolean[cards.length];

		int firstValue = 0;
		int secondValue = 0;

		for(int i = 0; i < cards.length; i++){
			//이미 방문한 곳이면 패스
			if(visited[i]) continue;

			visited[i] = true;

			int count = 1;
			//반복돌면서 방문했던곳으로 돌아올떄까지 확인
			int nextIndex = cards[i] - 1;
			while(!visited[nextIndex]){

				visited[nextIndex] = true;
				nextIndex = cards[nextIndex] - 1;
				count++;

			}

			if(firstValue < secondValue){
				firstValue = Math.max(firstValue, count);
			}
			else{
				secondValue = Math.max(secondValue, count);
			}
		}


		return firstValue * secondValue;
	}
	public static void main(String[] args) {

		Prog_혼자_놀기의_달인 s = new Prog_혼자_놀기의_달인();
		int[] cards1 = {8,6,3,7,2,5,1,4};
		System.out.println(s.solution(cards1));

	}
}
