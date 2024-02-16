package week27;

import java.util.Arrays;

public class Solution_양궁대회 {
    boolean canWin;
    int max, N;
    int[] infos, answer;

    public int[] solution(int n, int[] info) {
        N = n;
        infos = info;

        canWin = false;
        shoot(0, 0, new int[11]);

        if (!canWin) {
            return new int[]{-1};
        }

        return answer;
    }   // end of solution

    private void shoot(int count, int start, int[] choice) {
        if (count == N) {
            calcScore(choice);
            return;
        }

        for (int i = start; i <= 10; i++) {
            choice[i]++;
            shoot(count + 1, i, choice);
            choice[i]--;
        }
    }   // end of shoot

    private void calcScore(int[] choice) {
        int ryan = 0;
        int apeach = 0;
        for (int i = 0; i < choice.length; i++) {
            if (infos[i] == 0 && choice[i] == 0) {
                continue;
            }

            if (infos[i] < choice[i]) {
                ryan += 10 - i;
            } else {
                apeach += 10 - i;
            }
        }

        if (apeach < ryan) {
            canWin = true;

            int gap = ryan - apeach;
            if (max < gap) {   // 최고 점수 갱신
                max = gap;
                answer = Arrays.copyOf(choice, choice.length);
            } else if (max == gap) {
                for (int i = choice.length - 1; i >= 0; i--) {
                    if (answer[i] > choice[i]) {
                        break;
                    }

                    if (answer[i] < choice[i]) {    // 적은 점수를 더 많이 맞췄을 경우
                        if (choice[0] == 1 && choice[2] == 2) {
                            System.out.println(i);
                        }
                        answer = Arrays.copyOf(choice, choice.length);
                        break;
                    }
                }
            }
        }
    }   // end of calcScore


    public static void main(String[] args) {
        int n = 3;
        int[] info = {0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1};
//        int n = 9;
//        int[] info = {0, 0, 1, 2, 0, 1, 1, 1, 1, 1, 1};
        Solution_양궁대회 a = new Solution_양궁대회();
        int[] ans = a.solution(n, info);
        for (int i = 0; i < ans.length; i++) {
            System.out.print(ans[i] + " ");
        }
        System.out.println();
    }

}
