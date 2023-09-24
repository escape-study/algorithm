package week07;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 연속된 배열의 합이므로 우선 누적합을 이용하는 방법을 생각했다.
 * 모든 경우를 다 해보는 수 밖에 없다.
 * 우선 각 배열의 누적합을 구한 후, 이 누적합 배열을 이용해서 나올 수 있는 모든 경우의 수를 리스트에 담는다.
 * 이렇게 만들어진 a,b 배열을 이용해서 조합을 해보고, T가 나오면 저장을 하는 식으로 한다.
 * 이때 단순 이중 반복문이 아닌 투 포인터를 이용해서 해결을 해준다.
 */
public class BOJ2143_두_배열의_합 {

    //누적값 구하는 메서드
    private static void makeSum(int[] array){

        for(int i = 1; i < array.length; i++){
            array[i] += array[i - 1];
        }
    }

    //구간 값 리스트에 넣는 메서드
    private static void allValueSave(int[] array, List<Integer> list){

        list.add(array[0]); //첫번째 값은 넣어두기.

        for(int i = 1; i < array.length; i++){
            list.add(array[i]);
            for(int j = 0; j < i; j++){

                list.add(array[i] - array[j]);
            }
        }
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st;

        //T저장 변수
        int T = Integer.parseInt(br.readLine());

        //n - a배열길이
        int n = Integer.parseInt(br.readLine());
        //a배열
        int[] aArray = new int[n];
        //a배열의 모든 구간 값을 저장할 리스트
        List<Integer> aSumList = new ArrayList<>();
        st = new StringTokenizer(br.readLine());

        for(int i = 0; i < n; i++){
            aArray[i] = Integer.parseInt(st.nextToken());
        }

        //m - b배열 길이
        int m = Integer.parseInt(br.readLine());
        //b배열
        int[] bArray = new int[m];
        st = new StringTokenizer(br.readLine());
        //b배열의 모든 구간 값을 저장할 리스트.
        List<Integer> bSumList = new ArrayList<>();

        for(int i = 0; i < m; i++){
            bArray[i] = Integer.parseInt(st.nextToken());
        }

        //누적합 구해두기.
        makeSum(aArray);
        makeSum(bArray);

        //각 배열의 모든 구간 값을 리스트에 저장.
        allValueSave(aArray, aSumList);
        allValueSave(bArray, bSumList);

        //투 포인터를 구하기 위해서 정렬
        Collections.sort(aSumList);
        Collections.sort(bSumList);

        long result = 0; //나올 수 있는 경우의 수.

        int left = 0; //a리스트의 처음 위치에서 시작.
        int right = bSumList.size() - 1; //b리스트의 가장 끝쪽에서 시작

        //두 값의 합이, T보다 크다면,right를 감소시켜서 값을 줄여봄
        //두 값의 합이, T보다 작다면, left값을 증가시켜서 값을 키움
        //두 값의 합이, T라면, 경우의 수를 증가시키고, 둘 중 하나의 값을 키우거나 줄여봄 - 중복값이 있을 수 있기 때문에 T가 아닐때 까지 값을 변경해봄.

        //인덱스를 벗어나지 않을때까지 반복.
        while(left < aSumList.size() && right >= 0){

            //두 값의 합.
            int sumValue = aSumList.get(left) + bSumList.get(right);

            //클 때
            if(sumValue > T){
                right--;
            }
            //작을떄,
            else if(sumValue < T){
                left++;
            }
            //같을때 - 중복값이 있기 떄문에 a,b리스트 모두 상한선,하한선까지 탐색필요.
            else{

                //현재 인덱스 값 임시 저장.
                int aTemp = aSumList.get(left);
                int bTemp = bSumList.get(right);

                //해당 하는 값의 개수를 누적해야됨 - 만들수 있는 수는, aTempCount * bTempCount이다.
                long aTempCount = 0;
                long bTempCount = 0;

                //a리스트쪽 인덱스를 증가시키면서 기존의 값과 동일한지 확인.
                while(left < aSumList.size() && aSumList.get(left) == aTemp){
                    aTempCount++;
                    left++;
                }

                //b리스트쪽 인덱스를 감소시키면서 기존의 값과 동일한지 확인.
                while(right >= 0 && bSumList.get(right) == bTemp){
                    bTempCount++;
                    right--;
                }


                result += aTempCount * bTempCount;
            }
        }
        System.out.println(result);
    }
}
