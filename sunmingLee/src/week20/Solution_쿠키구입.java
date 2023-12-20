class Solution_쿠키구입 {
    public int solution(int[] cookie) {
        int answer = 0;

        for (int split = 0; split < cookie.length - 1; split++) {   // 바구니 분기점
            int older = split;   // older ~ split : 형 바구니
            int younger = split + 1;  // 동생에게 줄 과자
            int sumOlder = cookie[older];   // 형에게 줄 과자 총합
            int sumYounger = cookie[younger];   // 동생에게 줄 과자 총합

            while (true) {
                if (sumOlder == sumYounger) {
                    answer = Math.max(answer, sumOlder);
                    older--;
                    younger++;

                    if (older < 0 || younger == cookie.length) {
                        break;
                    }
                    sumOlder += cookie[older];
                    sumYounger += cookie[younger];
                } else if (sumOlder > sumYounger) {
                    younger++;

                    if (younger == cookie.length) {
                        break;
                    }
                    sumYounger += cookie[younger];
                } else {
                    older--;

                    if (older < 0) {
                        break;
                    }
                    sumOlder += cookie[older];
                }
            }
        }

        return answer;
    }
}