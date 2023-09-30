package week01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 개수가 많기 때문에 누적합을 이용한다.
 * 석순과 종유석을 따로 계산하는 두 배열을 두고 누적합을 계산한다.
 * 입력
 */
public class BOJ3020_개똥벌레_누적합풀이 {

    private static int N;//동굴의 길이
    private static int H;//동굴의 높이

    //종유석 길이 정보를 저장할 배열 - 위에서 아래로 자람.
    private static int[] ceilArray;
    //석순 길이 정보를 저장할 배열. - 아래에서 위로 자람.
    private static int[] floorArray;

    //누적합 구하는 배열
    private static void setCumulativeSum(){
        for(int i = H - 1; i >= 1; i--){

            floorArray[i] += floorArray[i + 1];
            ceilArray[i] += ceilArray[i + 1];

        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());


        //석순과 종유석의 길이는 h보다 작음.
        ceilArray = new int[H + 1];
        floorArray = new int[H + 1];

        //값 입력 - 석순, 종유석순으로
        for(int i = 0; i < N/2; i++){

            floorArray[Integer.parseInt(br.readLine())]++;
            ceilArray[Integer.parseInt(br.readLine())]++;
        }

        //누적합 구하기.
        setCumulativeSum();

        //장애물의 최소값
        int minBlockCount = 200001;
        //구간의 수.
        int intervalCount = 0;

        for(int interval = 1; interval <= H; interval++){

            int tempCount = floorArray[interval] + ceilArray[H - interval + 1];//총 장애물 수.

            //현재 구한 장애물의 수가 더 적으면 업데이트
            if(minBlockCount > tempCount){
                minBlockCount = tempCount;
                intervalCount = 1;
            }
            //같다면 구간 개수 증가.
            else if(minBlockCount == tempCount){
                intervalCount++;
            }
        }


        System.out.println(minBlockCount + " " + intervalCount);
    }
}
