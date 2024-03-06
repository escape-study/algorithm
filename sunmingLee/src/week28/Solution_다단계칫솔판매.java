package week28;

import java.util.*;

public class Solution_다단계칫솔판매 {
    public static void main(String[] args) {
        String[] enroll = {"john", "mary", "edward", "sam", "emily", "jaimie", "tod", "young"};
        String[] referral = {"-", "-", "mary", "edward", "mary", "mary", "jaimie", "edward"};
        String[] seller = {"young", "john", "tod", "emily", "mary"};
        int[] amount = {12, 4, 2, 5, 10};
        Solution_다단계칫솔판매 sol = new Solution_다단계칫솔판매();
//        System.out.println(Arrays.toString(sol.solution(enroll, referral, seller, amount)));

        int[] res = {360, 958, 108, 0, 450, 18, 180, 1080};
//        System.out.println(Arrays.toString(res));
    }

    Map<String, Integer> mapper;
    int[] answer;

    public int[] solution(String[] enroll, String[] referral, String[] seller, int[] amount) {
        answer = new int[enroll.length];
        mapper = new HashMap<>();  // key : 이름, value : enroll 내 인덱스
        setMapper(enroll);

        for (int i = 0; i < seller.length; i++) {
            shareProfit(seller[i], amount[i] * 100, enroll, referral);
            System.out.println(i + " : " + Arrays.toString(answer));
        }

        return answer;
    }   // end of solution

    /**
     * 이익 배분
     */
    private void shareProfit(String seller, int amount, String[] enroll, String[] referral) {
        int sellerIndex = mapper.get(seller);   // 판매자 인덱스
        int referralIndex = mapper.get(referral[sellerIndex]);  // 추천인 인덱스
        while (true) {
            int share = (int) (amount * 0.1);   // 추천인에게 줄 금액
            if (share < 1) {    // 나눠줄 금액이 1원 미만일 경우 수익은 판매자가 가져감
                answer[sellerIndex] += amount;
                break;
            }

            answer[sellerIndex] += amount - share;
            if (referralIndex == -1) {   // 추천인이 없는 경우
                break;
            }

            // 다음 추천인 설정
            sellerIndex = referralIndex;
            referralIndex = mapper.get(referral[sellerIndex]);
            amount = share;
        }
    }   // end of shareProfit

    /**
     * 이름별 인덱스 설정
     */
    private void setMapper(String[] enroll) {
        for (int i = 0; i < enroll.length; i++) {
            mapper.put(enroll[i], i);
        }
        mapper.put("-", -1);    // 민호(center)
    }   // end of setMapper

}
