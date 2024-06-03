package week43;

import org.w3c.dom.Node;

import java.util.*;

/*
 *
 *  1 1 1
 *  5 1 1
 * 25 5 1
 *
 * */
public class PROG_광물캐기 {
    int t[][] = {{1, 1, 1}, {5, 1, 1}, {25, 5, 1}};
    int answer = Integer.MAX_VALUE;
    public int solution(int[] picks, String[] minerals) {

        dfs(0,picks,minerals,0, "");
        return answer;
    }

    public void dfs(int total, int[] picks, String[] minerals, int cnt , String check) {
        if(cnt == minerals.length){
            answer = Math.min(answer, total);
            return;
        }
        boolean flag = false;
        for (int i = 0; i < picks.length; i++) {
            if (picks[i] != 0) {
                flag = true;
                picks[i]--;
                int sum = 0;
                for (int k = cnt; k < minerals.length && k < cnt+5; k++) {
                    if (minerals[k].equals("diamond")) {
                        sum += t[i][0];
                    } else if (minerals[k].equals("iron")) {
                        sum += t[i][1];
                    } else {
                        sum += t[i][2];
                    }
                }
                dfs(total + sum, picks, minerals, Math.min(cnt + 5, minerals.length) , check + Integer.toString(i));
                picks[i]++;
            }
        }

        if(!flag){
            answer = Math.min(answer, total);
        }

    }
}