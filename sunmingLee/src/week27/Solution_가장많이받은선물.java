package week27;

import java.util.*;

public class Solution_가장많이받은선물 {
    class Friend {
        int index;
        String name;
        int giftScore;

        public Friend(int index, String name) {
            this.index = index;
            this.name = name;
            this.giftScore = 0;
        }

        public void addGiftScore(int score) {
            this.giftScore += score;
        }
    }

    int[][] share;
    Friend[] info;

    public int solution(String[] friends, String[] gifts) {
        Map<String, Integer> friendsMap = new HashMap();    // 이름별 인덱스 번호 저장
        info = new Friend[friends.length];
        for (int i = 0; i < friends.length; i++) {
            friendsMap.put(friends[i], i);
            info[i] = new Friend(i, friends[i]);
        }

        share = new int[friends.length][friends.length];
        for (String temp : gifts) {
            String[] gift = temp.split(" ");
            int sender = friendsMap.get(gift[0]);
            int receiver = friendsMap.get(gift[1]);
            share[sender][receiver]++;
            info[sender].addGiftScore(1);
            info[receiver].addGiftScore(-1);
        }

        int answer = 0;
        for (int i = 0; i < friends.length; i++) {
            int giftSum = 0;
            for (int j = 0; j < friends.length; j++) {
                if (i == j) {
                    continue;
                }

                if (canGetGift(i, j)) {
                    giftSum++;
                }
            }

            if (answer < giftSum) {
                answer = giftSum;
            }
        }

        return answer;
    }   // end of solution

    private boolean canGetGift(int sender, int receiver) {
        int send = share[sender][receiver];
        int receive = share[receiver][sender];

        if (send > receive) {   // 준 선물이 더 많은 경우
            return true;
        }

        if (send == receive || send == 0 && receive == 0) {   // 주고 받은 선물이 같거나 주고받은적이 없는 경우
            if (info[sender].giftScore > info[receiver].giftScore) {  // 선물 지수가 더 높아야 선물을 받음
                return true;
            }
        }

        return false;
    }   // end of canGetGift

    public static void main(String[] args) {
        String[] friends = {"muzi", "ryan", "frodo", "neo"};
        String[] gifts = {"muzi frodo", "muzi frodo", "ryan muzi", "ryan muzi", "ryan muzi", "frodo muzi", "frodo ryan", "neo muzi"};
        Solution_가장많이받은선물 a = new Solution_가장많이받은선물();
        System.out.println(a.solution(friends, gifts));
    }
}
