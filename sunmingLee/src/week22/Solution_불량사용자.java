import java.util.*;

class Solution_불량사용자 {
    String[] userId, bannedId;
    Set<Set<String>> set = new HashSet<>();

    public int solution(String[] user_id, String[] banned_id) {
        userId = user_id;
        bannedId = banned_id;
        dfs(0, new HashSet<>());

        return set.size();
    }   // end of solution

    void dfs(int depth, HashSet<String> tempSet) {
        if (depth == bannedId.length) {
            set.add(tempSet);
            return;
        }

        for (String id : userId) {
            if (tempSet.contains(id)) {
                continue;
            }

            if (isMatched(id, bannedId[depth])) {
                tempSet.add(id);
                dfs(depth + 1, new HashSet<>(tempSet));
                tempSet.remove(id);
            }

        }
    }   // end of dfs

    boolean isMatched(String user_id, String banned_id) {
        if (user_id.length() != banned_id.length()) {
            return false;
        }

        int len = user_id.length();
        for (int i = 0; i < len; i++) {
            if (banned_id.charAt(i) == '*') {
                continue;
            }

            if (user_id.charAt(i) != banned_id.charAt(i)) {
                return false;
            }
        }

        return true;
    }   // end of check
}