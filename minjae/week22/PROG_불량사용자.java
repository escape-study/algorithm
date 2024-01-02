package week22;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PipedReader;
import java.util.*;

public class PROG_불량사용자 {
    static Set<Integer> set = new HashSet<>();
//    static public int solution(String[] user_id, String[] banned_id) {
//        int answer = 0;
//        boolean checked[][] = new boolean[user_id.length][banned_id.length];
//
//        for (int i = 0; i < user_id.length; i++) {
//            for (int j = 0; j < banned_id.length; j++) {
//                if (user_id[i].length() != banned_id[j].length()) continue;
//                checked[i][j] = true;
//                for (int k = 0; k < user_id[i].length(); k++) {
//                    if(banned_id[j].charAt(k) == '*') continue;
//                    if(user_id[i].charAt(k) != banned_id[j].charAt(k)){
//                        checked[i][j] = false;
//                        break;
//                    }
//                }
//            }
//        }
//
//        cul(0 , 0 , checked);
//        answer = set.size();
//        return answer;
//    }
//    public static void cul(int cnt ,int isUsed , boolean checked[][]){
//        if(cnt == checked[0].length){
//            set.add(isUsed);
//            return;
//        }
//        for (int i = 0; i < checked.length; i++) {
//            if((isUsed & (1 << i)) == 0 && checked[i][cnt]){
//                cul(cnt + 1 , (isUsed | (1 << i)) , checked);
//            }
//        }
//
//    }

    static public int solution(String[] user_id, String[] banned_id) {
        int answer = 0;
        cul(0 , 0, user_id , banned_id);
        answer = set.size();
        return answer;
    }
    public static void cul(int cnt ,int isUsed , String[] user_id, String[] banned_id){  // 정규식을 이용한 풀이 -> 시간이 훨씬 오래걸림
        if(cnt == banned_id.length){
            set.add(isUsed);
            return;
        }

        String reg = banned_id[cnt].replace("*", "[\\w\\d]");
        for (int i = 0; i < user_id.length; i++) {
            if((isUsed & (1 << i)) == 0 && user_id[i].matches(reg)){
                cul(cnt + 1 , (isUsed | (1 << i)) , user_id , banned_id);
            }
        }

    }

    public static void main(String[] args) throws IOException {
        System.out.println(solution(new String[]{"frodo", "fradi", "crodo", "abc123", "frodoc"}, new String[]{"fr*d*", "abc1**"}));
    }
}