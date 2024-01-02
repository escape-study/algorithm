package week21;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 아이디어
 * 그리디
 * 꽃이 최소가 되기 위해서는 이전에 심은 꽃이 지는 날짜 보다 피는 날짜가 작거나 같은 경우이면서 지는 날짜가 가장 길어야 한다.
 * 꽃이 피고 지는 구간에 가장 적게 들어가기 위한 방법은 한번 피고 지는 시간이 길어서 최대한 많은 범위를 커버하면 된다.
 * 초기 기준점을 시작점인 0301로 잡는다.
 * 꽃이 피는 시작점이 기준점보다 작거나 같으면 후보군이고, 이러한 꽃들중에서 지는 시기가 가장 나중인 꽃을 가져온다.
 * 해당 꽃의 지는 시기를 새로운 기준점으로 업데이트 한다.
 * 꽃이 지는 시기가 1201을 넘어가면 종료한다.
 */
public class BOJ2457_공주님의정원 {


    //날짜 객체
    private static class Node{
        int date;

        public Node(int date){
            this.date = date;
        }

    }

    //꽃 객체
    private static class Flower{

        int startDate, endDate;
        boolean isUse;

        public Flower(int startDate, int endDate){
            this.startDate = startDate;
            this.endDate = endDate;
            this.isUse = false;
        }

        public Node startNode(){
            return new Node(startDate);
        }
        public Node endNode(){
            return new Node(endDate);
        }

        public void setUse(){
            this.isUse = false;
        }
    }

    //꽃 개수
    private static int N;

    //기준 점.
    private static Node standardDay;

    //종료 목표점
    private static Node finishDay;

    //꽃 리스트
    private static List<Flower> flowerList;

    //두 날짜 비교 메서드 - day1 > day2 : true, day1 <= day2 : false
    private static boolean compareDay(Node date1, Node date2){
        //월비교
        if(date1.date > date2.date){
            return true;
        }
        return false;
    }

    //실제 로직 - 꽃 개수
    private static int logic(){
        int flowerCount = 0;


        //flower를 정렬해서 피는 시간이 기준점보다 작거나 같으면서 지는 시간이 가장 큰 값을 꺼냄.
        Collections.sort(flowerList, (flower1, flower2) -> {

            return flower2.endDate - flower1.endDate;
        });


        while(true){

            boolean flag = false;
            for(Flower flower : flowerList){

                //이미 사용한 꽃이면 패스
                if(flower.isUse) continue;

                //시작점이 기준 점 보다 크면 패스.
                if(compareDay(flower.startNode(), standardDay)) continue;

                //종료 시점이 기준점 보다 작거나 같으면.
                if(!compareDay(flower.endNode(), standardDay)) continue;

                //기준점 업데이트
                standardDay = flower.endNode();

                //꽃 개수 추가.
                flowerCount++;

                flag = true;
            }


            //기준점이 1201보다 같거나 크면 끝.
            //꽃이 추가된 적이 없으면 불가능 한것
            if(standardDay.date >= 1201) break;

            if(!flag) return 0;

        }

        return flowerCount;
    }


    //월일을 입력받아 자리수 맞추고 숫자로 바꾸는 메서드
    private static int getDate(int month, int day){
        String strMonth = String.format("%02d", month);
        String strDay = String.format("%02d", day);

        return Integer.parseInt(strMonth + strDay);
    }

    public static void main(String[] args) throws Exception{
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = null;

        N = Integer.parseInt(br.readLine());
        flowerList = new ArrayList<>();
        standardDay = new Node(getDate(3,1));
        finishDay = new Node(getDate(11,30));

        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());
            flowerList.add(new Flower(
                    getDate(Integer.parseInt(st.nextToken()),Integer.parseInt(st.nextToken())),
                    getDate(Integer.parseInt(st.nextToken()), Integer.parseInt(st.nextToken()))
            ));
        }

        System.out.println(logic());
    }
}
