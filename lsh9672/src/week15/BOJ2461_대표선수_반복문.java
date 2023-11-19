package week15;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 정렬후에 투포인터를 이용한다.
 * 모든 배열을 정렬하고 처음에는 전부 배열의 0번쨰 인덱스를 가리킨다.
 * 여기서 최대 값과 최소값을 구해서 저장한다.
 * 그다음에는 최소값이 있는 배열의 인덱스를 하나씩 증가시킨다.(최대 - 최소 값이 작아지기 위해서는 최소값이 점점 커저야 됨, 이를 위해서 미리 정렬을 해둠.)
 */
public class BOJ2461_대표선수_반복문 {

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        int N = Integer.parseInt(st.nextToken());
        int M = Integer.parseInt(st.nextToken());

        int[][] playerArray = new int[N][M];
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            for(int j = 0; j < M; j++){
                playerArray[i][j] = Integer.parseInt(st.nextToken());
            }
            //배열 정렬.
            Arrays.sort(playerArray[i]);
        }


        int result = Integer.MAX_VALUE;

        //각 학급의 인덱스를 저장하고 있을 배열.
        int[] indexArray = new int[N];
        //계속반복 - 단 0이면 종료.
        while(true){

            int tempMax = -1;
            int tempMin = Integer.MAX_VALUE;
            int tempMinIndex = 0;
            for(int i = 0; i < N; i++){

                int value = playerArray[i][indexArray[i]];

                //최대값 구하기
                if(tempMax < value){
                    tempMax = value;
                }

                //최소값 구하기
                if(tempMin > value){
                    tempMin = value;
                    tempMinIndex = i;
                }
            }

            result = Math.min(result, tempMax - tempMin);

            //0이면 이것보다 더 작은 차이는 존재하지 않음.
            if(result == 0) break;


            //최소값 인덱스를 증가시켜야 함. 최소값 인덱스를 더이상 증가시킬 수 없다면 종료.
            if(indexArray[tempMinIndex] == M - 1) break;

            indexArray[tempMinIndex]++;
        }


        System.out.println(result);
    }
}
