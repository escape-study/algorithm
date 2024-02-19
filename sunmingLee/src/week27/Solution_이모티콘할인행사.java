package week27;

public class Solution_이모티콘할인행사 {
    final int[] discount = {10, 20, 30, 40};    // 할인율
    int maxSubscriber, maxProfit;
    int[] emoticon;
    int[][] user;

    public int[] solution(int[][] users, int[] emoticons) {
        user = users;
        emoticon = emoticons;

        setDiscount(0, new int[emoticons.length]);

        return new int[]{maxSubscriber, maxProfit};
    }   // end of solution

    /**
     * 중복조합으로 이모티콘별 할인율 책정하기
     */
    private void setDiscount(int count, int[] choice) {
        if (count == emoticon.length) {
            calcResult(choice);
            return;
        }

        for (int i = 0; i < discount.length; i++) {
            choice[count] = i;
            setDiscount(count + 1, choice);
        }
    }   // end of setDiscount

    /**
     * 고객별로 할인행사 결과를 계산하여 임티플 가입자수와 판매액 구하기
     */
    private void calcResult(int[] choice) {
        int subscriber = 0;
        int profit = 0;

        loop:
        for (int i = 0; i < user.length; i++) {
            int sum = 0;
            for (int j = 0; j < choice.length; j++) {
                int discRate = discount[choice[j]];
                if (discRate >= user[i][0]) {
                    sum += emoticon[j] * (100 - discRate) / 100;
                }

                if (sum >= user[i][1]) {
                    subscriber++;
                    continue loop;
                }
            }

            profit += sum;
        }

        if (maxSubscriber < subscriber) {
            maxSubscriber = subscriber;
            maxProfit = profit;
        } else if (maxSubscriber == subscriber) {
            if (maxProfit < profit) {
                maxProfit = profit;
            }
        }
    }   // end of calcResult
}
