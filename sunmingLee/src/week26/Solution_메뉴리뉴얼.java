package week26;

import java.util.*;

class Solution_메뉴리뉴얼 {

    class Course {
        String menu;
        int count;

        public Course(String menu, int count) {
            this.menu = menu;
            this.count = count;
        }
    }

    public static void main(String[] args) {
        String[] orders = {"XYZ", "XWY", "WXA"};
        int[] course = {2, 3, 4};
        Solution_메뉴리뉴얼 a = new Solution_메뉴리뉴얼();

        System.out.println(Arrays.toString(a.solution(orders, course)));
    }

    Map<String, Integer> map;

    public String[] solution(String[] orders, int[] course) {
        map = new HashMap<>();
        for (int count : course) {
            for (int i = 0; i < orders.length; i++) {
                if (orders[i].length() < count) {
                    continue;
                }

                char[] menus = orders[i].toCharArray();
                Arrays.sort(menus);
                getMenu(0, 0, count, "", menus);
            }
        }


        List<Course>[] list = new List[11];
        for (int i = 1; i <= 10; i++) {
            list[i] = new ArrayList<>();
        }

        for (String menu : map.keySet()) {
            int len = menu.length();
            list[len].add(new Course(menu, map.get(menu)));
        }

        List<String> answer = new ArrayList<>();
        for (int count : course) {
            if (list[count].isEmpty()) {
                continue;
            }

            Collections.sort(list[count], (a, b) -> b.count - a.count);

            int max = list[count].get(0).count;
            if (max < 2) {
                continue;
            }

            for (Course temp : list[count]) {
                if (temp.count < max) {
                    break;
                }

                answer.add(temp.menu);
            }
        }

        Collections.sort(answer);
        return answer.toArray(new String[answer.size()]);
    }   // end of solution

    private void getMenu(int start, int depth, int goal, String menus, char[] order) {
        if (depth == goal) {
            map.put(menus, map.getOrDefault(menus, 0) + 1);
            return;
        }

        for (int i = start; i < order.length; i++) {
            getMenu(i + 1, depth + 1, goal, menus + order[i], order);
        }
    }   // end of getMenu
}