package week30;

class Solution_방금그곡 {
    public String solution(String m, String[] musicinfos) {
        String answer = "";
        int maxTime = 0;
        m = changeSharp(m); // 반올림음 -> 소문자 치환
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < musicinfos.length; i++) {
            sb.setLength(0);
            String[] musicinfo = musicinfos[i].split(",");
            int time = getTime(musicinfo[0], musicinfo[1]);
            int len = musicinfo[3].length();    // 음악의 길이
            int index = 0;
            // 재생길이만큼 음 구성
            while (sb.length() != time) {
                if (index + 1 < len && musicinfo[3].charAt(index + 1) == '#') { // 반올림음 -> 소문자 치환
                    sb.append(musicinfo[3].substring(index, index + 1).toLowerCase());
                    index += 2;
                } else {
                    sb.append(musicinfo[3].charAt(index));
                    index++;
                }

                if (index == len) {
                    index = 0;
                }
            }

            if (sb.toString().contains(m)) {
                if (maxTime < time) {   // 조건이 일치하는 음악 중 재생 시간이 가장 긴것
                    maxTime = time;
                    answer = musicinfo[2];
                }
            }
        }
        return answer.isEmpty() ? "(None)" : answer;
    }   // end of solution

    /**
     * 반올림음 치환
     */
    private String changeSharp(String m) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < m.length(); i++) {
            if (i != m.length() - 1 && m.charAt(i + 1) == '#') {
                sb.append(m.substring(i, i + 1).toLowerCase());
                i++;
            } else {
                sb.append(m.charAt(i));
            }
        }

        return sb.toString();
    }   // end of changeSharp

    /**
     * 음악 재생시간 반환
     */
    private int getTime(String start, String end) {
        String[] starts = start.split(":");
        int startMin = Integer.parseInt(starts[0]) * 60 + Integer.parseInt(starts[1]);
        String[] ends = end.split(":");
        int endMin = Integer.parseInt(ends[0]) * 60 + Integer.parseInt(ends[1]);
        return endMin - startMin;
    }   // end of getTime
}