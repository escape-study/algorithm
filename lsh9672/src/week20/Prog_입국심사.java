package week20;

import java.util.Arrays;

/**
 * 아이디어
 * 이분탐색
 * 완전탐색으로 돌리기에는 심사관 수와 입국심새 대기자 수가 너무 많다.
 * 따라서 이분탐색을 이용해서 시간을 줄여가며, 체크한다.
 * 시간의 경우에 최대로 걸리는 시간을 구해야, 해당 시간부터 절반단위로 줄여나갈수 있다.
 * 가장 오래 걸리는 시간은 처리시간이 가장 긴 심사관이 모든 대기자를 처리하는 경우이다.
 */

public class Prog_입국심사 {

    //해당 시간에 몇명의 사람을 처리가 가능한지 구하는 메서드
    private static long processPeople(int[] times, long selectTime){

        long result = 0;

        //반복문으로 돌면서 각 심사관이 처리가능한 시간을 모든 사람이 심사 받는데 걸리는 시간으로 나눈 목을 더해준다.
        //가령 7시간이 걸린다 하면 총 시간이 15라고 했을떄, 해당 심사관은 15/7 = 2 만큼 처리가 가능하다.
        for(int time : times){
            result += selectTime / time;
        }

        return result;
    }

    //이분탐색을 하는 메서드
    //시간을 줄여보면서, n명이상의 사람을 처리 가능하다면 조금씩 더 줄여가는 식으로 구해봄.
    private static long binarySearch(int n, int[] times, long start, long end){

        while(start < end){
            long mid = (start + end) / 2;

            //해당 시간에 몇명 처리가 가능한지 구하기.
            long peopleCount = processPeople(times, mid);

            //해당 처리 인원이 실제로 처리해야 하는 인원보다 작다면 시간을 늘려야 됨.
            if(peopleCount < n){
                start = mid + 1;
            }
            //처리해야 하는 인원과 같거나, 더 많은 인원 처리가 가능하다면 시간을 좀 더 줄여봄
            else{
                end = mid;
            }
        }

        return start;
    }


    public long solution(int n, int[] times){

        Arrays.sort(times);

        return binarySearch(n, times ,times[0], (long)times[times.length - 1] * (long)n);
        
    }

    public static void main(String[] args) {

        int n = 6;
        int[] times = {7, 10};
        Prog_입국심사 s = new Prog_입국심사();
        System.out.println(s.solution(n,times));
    }


}
