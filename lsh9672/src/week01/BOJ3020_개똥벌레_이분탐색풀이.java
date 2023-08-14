package week01;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

/**
 * 이분탐색 풀이는 아래 블로그를 참고해서 풀어봄
 * https://dkwjdi.tistory.com/149
 *
 * 높이를 지정하고, 해당 높이에 존재하는 장애물을 이분탐색을 이용해 빨리구하는 방식
 * lower_bound인 이유는 충돌하는 가장 작은 높이를 찾게 되면, 그것보다 큰 높이를 가진 장애물에는 반드시 충돌하게 된다.
 * 그렇기에 충돌하는 가장 작은 높이를 찾으려고 하는것이고, 이 값이 중복되어 존재할 수 있기 때문에 결정문제인 파라메트릭 서치이며 lower_bound문제가 된다.
 * -> 누적합을 이용하는 방식이 훨씬 빠르다고 판단됨 이분탐색의 경우, 각 높이를 logn으로 탐색해 나아간다.
 * -> 이것을 n번 반복하면,nlogn인데에 반해, 누적합은 한번 n보다 더 작은 m만큼 반복 돈 후에 각 높이에 대해서  O(1)의 속도로 접근이 가능하다.
 * -> 따라서 O(n0)의 속도를 가지는 누적합이 더 빠르다.
 */
public class BOJ3020_개똥벌레_이분탐색풀이 {

    private static int N;//동굴의 길이
    private static int H;//동굴의 높이

    //종유석 길이 정보를 저장할 배열 - 위에서 아래로 자람.
    private static int[] ceilArray;
    //석순 길이 정보를 저장할 배열. - 아래에서 위로 자람.
    private static int[] floorArray;

    private static int binarySearch(int left, int right, int height, int[] array){

        if(left >= right) return right;

        int mid = (left + right) / 2;

        //선택한 높이가 주어진 높이보다 크다면 인덱스를 줄여서 하한선을 찾음.
        if(array[mid] >= height){
            return binarySearch(left, mid, height, array);
        }
        else{
            return binarySearch(mid + 1, right, height, array);
        }

    }


    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        H = Integer.parseInt(st.nextToken());


        ceilArray = new int[N / 2];
        floorArray = new int[N / 2];

        //값 입력 - 석순, 종유석순으로
        for(int i = 0; i < N/2; i++){

            floorArray[i] = Integer.parseInt(br.readLine());
            ceilArray[i] = Integer.parseInt(br.readLine());
        }

        //이분 탐색을 통해서, 주어진 높이에서 충돌하는 장애물 개수를 빠르게 찾기 위해 정렬해준다.
        Arrays.sort(floorArray);
        Arrays.sort(ceilArray);

        //장애물의 최소값
        int minBlockCount = 200001;
        //구간의 수.
        int intervalCount = 0;

        for(int interval = 1; interval <= H; interval++){

            int floorCount = N / 2 - binarySearch(0,N / 2, interval, floorArray);
            int ceilCount = N / 2 - binarySearch(0,N / 2, H - interval + 1, ceilArray);

            int tempCount = floorCount + ceilCount;

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
