package chansik.src.week08;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_1062_가르침 {
    static int ans;
    static int innerAns;
    public static void main(String[] args) throws IOException {
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(bf.readLine());
        HashSet<Character> storeSet = new HashSet<>();
        List<HashSet<Character>> list = new ArrayList<>();
        int n = Integer.parseInt(st.nextToken());
        int k = Integer.parseInt(st.nextToken());
        for (int i = 0; i < n; i++) {
            String input = bf.readLine();
            HashSet<Character> set = new HashSet<>();
            for (char c : input.toCharArray()) {
                if (c == 'a' || c == 'n' || c == 't' || c == 'i' || c == 'c') continue;
                set.add(c);
            }
            if (set.isEmpty()) ans++;
            else {
                list.add(set);
                storeSet.addAll(set);
            }
        }
        innerAns = 0;
        if (k - 5 < 0) ans = 0;
        else {
            k -= 5;

            char[] store = new char[storeSet.size()];
            int index = 0;
            for (char c : storeSet) store[index++] = c;
            perm(store, 0, new HashSet<>(), 0, Math.min(k, storeSet.size()), list);
        }
        System.out.println(ans + innerAns);
    }

    public static void perm(char[] store, int index, HashSet<Character> inBox, int count, int k, List<HashSet<Character>> list) {
        if (count == k) {
            int passCnt = 0;
            for (HashSet<Character> set : list) {
                boolean check = true;
                for (char word : set) {
                    if (!inBox.contains(word)) {
                        check = false;
                        break;
                    }
                }
                passCnt = check ? passCnt + 1 : passCnt;
            }
            innerAns = Math.max(innerAns, passCnt);
            return;
        }

        for(int i=index;i<store.length;i++) {
            inBox.add(store[i]);
            perm(store, i+1, inBox, count + 1, k, list);
            inBox.remove(store[i]);
        }
    }
}