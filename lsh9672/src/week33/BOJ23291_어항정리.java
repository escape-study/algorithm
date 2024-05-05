package week33;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 개 빡구현
 * 각 어항을 배열로 두고 생각하면 된다.
 * 최대 배열 크기는 문제에서 주어진 N으로 잡으면 된다.
 * 어항의 개수가 최대 N이므로 배열로 만들어서 구한다 했을떄 N*N을 넘어가지 않는다.
 * 처리해야 할 로직의 순서는 아래와 같다.
 * 1. 물고기가 가장 적은 어항에 물고기 + 1(단, 여러개면 모두 +1씩)
 * 2. 가장 왼쪽에 있는 어항을 해당 어항의 오른쪽에 있는 어항위에 쌓기
 * 3. 2개 이상 쌓인 어항을 90도 회전후 바닥에 있는 어항에 쌓기(단, 회전해서 쌓을수 없을떄까지 반복한다 - 회전시켰을떄, 바닥에 있는 어항보다 쌓을 어항의 열 길이가 더 긴경우 못쌓음)
 * 4. 인접칸 물고기를 비교해서 물고기재배치(단, 모든 칸이 동시에 해야 한다.)
 * 5. 일렬로 어항정렬(단, 왼쪽 부터 정렬하고, 가장 밑바닥부터 위로, 왼쪽부터 오른쪽순으로 정렬한다.)
 * 6. 어항의 절반을 나눠서 왼쪽 어항을 180도 회전후에 오른쪽 어항위에 올린다.
 * 7. 5번의 규칙대로 일렬로 정렬한다.
 * 8. 정렬된 어항중에서 최대 물고기의 수와 최소 물고기 수 차이구하기
 * 9. 8번에서 구한 물고기수 차이가 K초과이면 1번부터 반복.
 *
 * 어항에는 최소 1마리의 물고기가 들어있기 때문에 0이면 없는 것으로 간주하면 된다.
 */
public class BOJ23291_어항정리 {

	//4방향
	private final static int[] dx = {-1, 1, 0, 0};
	private final static int[] dy = {0, 0, -1, 1};

	private static int N;//어항수

	private static int K;//목표 차이
	private static int[][] maps;//어항 - 2차원 배열

	//물고기가 가장 적은 어항에 물고기 수를 +1하는
	private static void addFish(){

		int minCount = Integer.MAX_VALUE;
		//최소 물고기수 구하기
		for(int i = 0; i < N; i++){
			if (maps[N - 1][i] >= minCount) continue;
			minCount = maps[N - 1][i];
		}

		//어항 돌면서 최소 물고기인 곳+1
		for(int i = 0; i < N; i++){
			if(maps[N - 1][i] != minCount) continue;
			maps[N - 1][i]++;
		}

	}


	//어항쌓기 - 처음에는 왼쪽꺼 한칸 쌓는 것으로 시작.
	private static void stackFishbowl(){

		int leftStart = 0;//왼쪽 시작점
		int rotateWidth = 1;//회전시킬 배열의 넓이
		int rotateHeight = 1;//회전시킬 배열의 높이
		int count = 0; //두번 쌓을때마다 회전시키는 배열의 넓이가 증가함. 이를 체그

		//반복문 돌면서 처리
		//leftStart + rotateWidth => 올리려고 하는 바닥에 있는 어항의 가장 왼쪽.
		while(leftStart + rotateWidth + rotateHeight - 1 < N){

			for(int i = N - 1; i > N - 1 - rotateHeight; i--){
				for(int j = leftStart; j < leftStart + rotateWidth; j++){
					int nextX = N - 1 - rotateWidth + j - leftStart;
					int nextY = leftStart + rotateWidth + N - 1 - i;
					maps[nextX][nextY] = maps[i][j];
					maps[i][j] = 0;
				}
			}

			leftStart += rotateWidth;

			//짝수면 높이만 증가.
			if(count % 2 == 0) rotateHeight++;
			//홀수번쨰일때는 넓이만 증가.
			else rotateWidth++;

			count++;
		}
	}

	//어항을 절반으로 나눠서 처리 -> 어항 접기로 지칭.
	private static void foldFishbowl(){
		//뽑아야 할것들.
		List<Integer> tempFishList = new ArrayList<>();

		int leftStart = 0; //왼쪽 시작점


		//두번 반복
		for(int count = 1 ; count <= 2; count++){

			int tempNextX = N - 1 - count * 2 + 1;//옮겨야 할 위치 행.
			//처음에는 한줄, 두번째 반복에서는 두줄을 옮겨야 한다.
			for(int i = N - 1; i > N - 1 - count; i--){
				tempFishList.clear();//이전값을 초기화 하기 위해 clear함
				//옮길 값을 리스트에 담기
				for(int j = leftStart; j < leftStart + ((N - leftStart)/2); j++){
					tempFishList.add(maps[i][j]);
					maps[i][j] = 0;
				}

				//리스트에 담긴 값 옮기기 - 180도 회전했기 때문에 뒤에서부터 값을 넣어야 함.
				for(int j = 0; j < tempFishList.size(); j++){
					maps[tempNextX][N - 1 - j] = tempFishList.get(j);
				}
				tempNextX++;

			}
			leftStart += N / 2;
		}



	}

	//배열 벗어나는지 체크
	private static boolean check(int nextX, int nextY){
		return nextX >= 0 && nextX < N &&
			nextY >= 0 && nextY < N;
	}
	//어항 안 물고기 뿌리기
	private static void spreadFish(){
		//동시에 발생해야하기 때문에 별도의 배열을 만들어 처리.
		int[][] spreadMaps = new int[N][N];

		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++){

				if(maps[i][j] == 0) continue;

				for(int dir = 0; dir < 4; dir++){
					int nextX = i + dx[dir];
					int nextY = j + dy[dir];

					if(!check(nextX, nextY) || maps[nextX][nextY] == 0) continue;

					int gap = (maps[i][j] - maps[nextX][nextY]) / 5;

					if(gap <= 0) continue;

					//더 큰쪽에서 작은쪽으로 옮긴다 했는데,(i,j)를 기준으로 옮길수 있는지 못옮기는지만 보면됨.
					//반대의 경우는 다음 탐색에서 처리가능.
					spreadMaps[i][j] -= gap;
					spreadMaps[nextX][nextY] += gap;

				}

			}
		}

		//이동시킬 물고기를 한번에 업데이트
		for(int i = 0; i < N; i++){
			for(int j = 0; j < N; j++) {
				maps[i][j] += spreadMaps[i][j];
			}
		}

	}

	//어항 일렬로 정렬.
	private static void sortFishbowl(){

		//이동시킬 물고기 리스트
		List<Integer> tempFishList = new ArrayList<>();

		//왼쪽 아래부터 위로 올라가면서 0이 아닌 어항이 있으면 리스트에 저장
		for(int j = 0; j < N; j++){
			for(int i = N - 1; i >= 0; i--){

				if(maps[i][j] == 0) break;

				tempFishList.add(maps[i][j]); //이동시킬 물고기 저장
				maps[i][j] = 0; //해당칸 초기화.
			}
		}

		//리스트 돌면서 채우기
		for(int i = 0; i < tempFishList.size(); i++){
			maps[N - 1][i] = tempFishList.get(i);
		}

	}

	//최대 물고기와 최소 물고기 수 차이와 K비교.
	private static boolean checkFishCount(){

		int minCount = Integer.MAX_VALUE;
		int maxCount = -1;

		for(int i = 0; i < N; i++){

			if(minCount > maps[N - 1][i]){
				minCount = maps[N - 1][i];
			}

			if(maxCount < maps[N - 1][i]){
				maxCount =  maps[N - 1][i];
			}
		}

		return (maxCount - minCount) <= K;
	}

	private static int logic(){

		int cleanCount = 0;//어항 정리를 몇번했는지

		while(!checkFishCount()){
			cleanCount++;
			addFish();//물고기 추가

			stackFishbowl();//어항 쌓기
			spreadFish();//물고기 뿌리기(재배치.)


			sortFishbowl();//어항 정렬

			foldFishbowl();//어항접기

			spreadFish();//물고기 뿌리기(재배치.)
			sortFishbowl();//어항 정렬




		}

		return cleanCount;
	}


	public static void main(String[] args) throws Exception{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());

		N = Integer.parseInt(st.nextToken());
		K = Integer.parseInt(st.nextToken());

		maps = new int[N][N];

		//어항 입력받기.
		st = new StringTokenizer(br.readLine());
		for(int i = 0; i < N; i++){
			maps[N - 1][i] = Integer.parseInt(st.nextToken());
		}

		System.out.println(logic());

	}
}

