package week31;

import java.util.*;

class Solution_파일명정렬 {
    class File implements Comparable<File> {
        String head;
        int number;
        int index;

        public File(String head, int number, int index) {
            this.head = head;
            this.number = number;
            this.index = index;
        }

        public int compareTo(File o) {
            if (this.head.compareTo(o.head) == 0) {
                return this.number - o.number;
            }

            return this.head.compareTo(o.head);
        }
    }

    public String[] solution(String[] files) {
        String[] answer = new String[files.length];
        List<File> list = new ArrayList<>();
        int index = 0;
        for (String name : files) {
            int i, j;
            for (i = 0; i < name.length(); i++) {
                if (name.charAt(i) >= '0' && name.charAt(i) <= '9') {
                    break;
                }
            }
            String head = name.substring(0, i).toLowerCase();

            for (j = i; j < name.length(); j++) {
                if (name.charAt(j) < '0' || name.charAt(j) > '9') {
                    break;
                }
            }
            int number = Integer.parseInt(name.substring(i, j));
            list.add(new File(head, number, index++));
        }

        Collections.sort(list);
        index = 0;
        for (File file : list) {
            answer[index++] = files[file.index];
        }

        return answer;
    }
}