package week22;

import java.util.*;

/**
 * 아이디어
 * 조합을 이용한 완전탐색
 * 제재 아이디는 중복을 허용하지 않고, 순서는 상관없기 때문에 조합이다.
 * 제재 아이디를 하나 ㅅ선택해서, user_id 리스트를 돌면서 가능한 문자가 있으면 다음 제재 아이디로 넘어간다.
 * 모든 제재 아이디가 매칭이 되면 경우의 수를 1 증가 시키고 다시 되돌아간다.
 * 조합이긴 한데 순열처럼 풀어야 한다.
 * 다음 banned_id가 이전 banned id보다 앞쪽에 있어서 무조건 새로운 banned id이면 인덱스 0부터 확인해야 한다.
 */
public class Prog_불량사용자 {


    //user_id 방문 처리
    private static boolean[] visited;

    //결과 저장
    private static List<Set<String>> resultSet;

    //마스킹 처리된 아이디에 userid가 포함되는지 확인하는 메서드
    private static boolean idCheck(String userId, String maskingId){

        if(userId.length() != maskingId.length()) return false;

        int index = 0;

        //두 문자열 인덱스를 넘어가지 않을 떄 까지 반복.
        while(index < userId.length()){

            char userIdChr = userId.charAt(index);
            char maskingIdChr = maskingId.charAt(index);

            //maskingIdChr이 * 가 아닌데 서로 다르다면 종료.
            if(maskingIdChr != '*' && userIdChr != maskingIdChr) return false;

            index++;
        }

        return true;
    }

    private static Set<String> copySet(Set<String> originSet){
        Set<String> returnSet = new HashSet<>();

        for(String origin : originSet){
            returnSet.add(origin);
        }

        return returnSet;
    }

    //결과 비교 - 이전에 없는 조합인지.
    private static boolean checkSet(Set<String> originSet){

        for(Set<String> temp : resultSet){

            //다른연산에도 구해야 하기 때문.
            Set<String> tempCopy = copySet(temp);
            //두수의 차집합으로 같은지 판단.
            tempCopy.removeAll(originSet);
            if(tempCopy.size() == 0) return false;
        }

        return true;
    }


    //재귀 호출 - banned_id만큼 골랐으면 종료.
    private static void recusive(int bannedIdIndex, String[] user_id, String[] banned_id, Set<String> bannedIdSet){

        if(bannedIdIndex >= banned_id.length){
            //저장한 Set이 이전에 넣은적 없다면,
            if(checkSet(bannedIdSet)){
                resultSet.add(bannedIdSet);
            }
            return;
        }


        //visitedIndex부터 탐색하면 됨.
        for(int i = 0; i < user_id.length; i++){

            //해당 아이디가 마스킹에 포함되지 않거나 이미 체크한 문자라면 패스
            if(visited[i] || !idCheck(user_id[i], banned_id[bannedIdIndex])) continue;

            visited[i] = true;//해당 문자 방문 처리.
            Set<String> tempSet = copySet(bannedIdSet);
            tempSet.add(user_id[i]);//값추가.
            recusive(bannedIdIndex + 1, user_id, banned_id, tempSet);
            visited[i] = false;//방문처리 해제

        }
    }



    public int solution(String[] user_id, String[] banned_id) {
        visited = new boolean[user_id.length];
        resultSet = new ArrayList<>();

        recusive(0,user_id, banned_id, new HashSet<>());

        System.out.println(resultSet.toString());

        return resultSet.size();
    }
    public static void main(String[] args) {
        Prog_불량사용자 s = new Prog_불량사용자();

        String[] user_id1 = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] banned_id1 = {"fr*d*", "abc1**"};
        System.out.println(s.solution(user_id1,banned_id1));
        System.out.println("+++++++++++++++++++++++++++++");

        String[] user_id2 = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] banned_id2 = {"*rodo", "*rodo", "******"};
        System.out.println(s.solution(user_id2,banned_id2));
        System.out.println("+++++++++++++++++++++++++++++");

        String[] user_id3 = {"frodo", "fradi", "crodo", "abc123", "frodoc"};
        String[] banned_id3 = {"fr*d*", "*rodo", "******", "******"};
        System.out.println(s.solution(user_id3, banned_id3));
        System.out.println("+++++++++++++++++++++++++++++");


        String reg = "fr*d*".replace("*", "[\\w\\d]");
        System.out.println("frodo".matches(reg));

        System.out.println(0>>2);
    }
}
