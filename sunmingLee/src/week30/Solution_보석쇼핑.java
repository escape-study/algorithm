package week30;

import java.util.*;

public class Solution_보석쇼핑 {
    public int[] solution(String[] gems) {
        int[] answer = new int[2];
        Set<String> gemType = new HashSet<>();  // 보석 종류
        for (String gem : gems) {
            gemType.add(gem);
        }

        int gemCount = gemType.size();    // 보석 종류의 개수
        int left = 0;
        int right = gemCount - 1;
        Map<String, Integer> curGems = new HashMap<>(); // key : 현재 갖고있는 보석, value : 보석의 개수
        for (int i = left; i <= right; i++) {
            setCurgems(gems[i], 1, curGems);
        }

        int minLength = gems.length + 1;    // 조건을 만족하는 구간의 최소길이
        while (left <= right && right < gems.length) {
            int tempLength = right - left + 1;
            if (tempLength < gemCount || curGems.size() != gemCount) {   // 현재 보고있는 구간의 길이가 보석 종류의 개수보다 작거나 현재 구간에 모든 보석이 있지 않음
                right++;
                if (right == gems.length) {
                    break;
                }

                setCurgems(gems[right], 1, curGems);
            } else if (curGems.size() == gemCount) {   // 보석을 적어도 하나씩 포함함
                if (tempLength < minLength) {
                    // 최단 구간 갱신
                    answer[0] = left + 1;
                    answer[1] = right + 1;
                    minLength = tempLength;

                }

                // 왼쪽 길이 줄이기
                if (curGems.get(gems[left]) == 1) {   // 왼쪽포인터의 보석이 구간내 하나뿐임
                    curGems.remove(gems[left]);
                } else {
                    setCurgems(gems[left], -1, curGems);
                }

                left++;
            }
        }
        return answer;
    }   // end of solution

    /**
     * 현재 갖고있는 보석의 개수 변경
     */
    private void setCurgems(String gem, int num, Map<String, Integer> curGems) {
        curGems.put(gem, curGems.getOrDefault(gem, 0) + num);
    }   // end of addGem
}
