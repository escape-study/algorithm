package week30;

import java.util.*;

public class Solution_여행경로 {
    public static void main(String[] args) {
//        String[][] tickets = {{"ICN", "JFK"}, {"HND", "IAD"}, {"JFK", "HND"}};
        String[][] tickets = {{"ICN", "JFK"}, {"JFK", "ICN"}, {"ICN", "HND"}};
//        String[][] tickets = {{"ICN", "SFO"}, {"ICN", "ATL"}, {"SFO", "ATL"}, {"ATL", "ICN"}, {"ATL", "SFO"}};
        Solution_여행경로 sol = new Solution_여행경로();
        System.out.println(Arrays.toString(sol.solution(tickets)));
    }

    class Airport {
        String name;
        boolean isUsed;

        public Airport(String name) {
            this.name = name;
        }
    }

    String[] answer;
    Map<String, Integer> map;
    List<Airport>[] airports;

    public String[] solution(String[][] tickets) {
        answer = new String[tickets.length + 1];
        map = new HashMap<>();
        int portNum = 0;
        for (int i = 0; i < tickets.length; i++) {
            if (!map.containsKey(tickets[i][0])) {
                map.put(tickets[i][0], portNum);
                portNum++;
            }

            if (!map.containsKey(tickets[i][1])) {
                map.put(tickets[i][1], portNum);
                portNum++;
            }
        }

        airports = new List[portNum];
        for (int i = 0; i < portNum; i++) {
            airports[i] = new ArrayList<>();
        }

        for (int i = 0; i < tickets.length; i++) {
            String depart = tickets[i][0];
            int index = map.get(depart);
            airports[index].add(new Airport(tickets[i][1]));
        }

        for (int i = 0; i < airports.length; i++) {
            Collections.sort(airports[i], (a, b) -> a.name.compareTo(b.name));
        }

        String[] route = new String[answer.length];
        route[0] = "ICN";
        searchRoute(1, map.get("ICN"), route);

        return answer;
    }   // end of solution

    private void searchRoute(int count, int index, String[] route) {
        if (count == route.length) {
            if (answer[0] == null) {
                answer = Arrays.copyOf(route, route.length);
            } else {
                for (int i = 0; i < route.length; i++) {
                    if (answer[i].compareTo(route[i]) < 0) {
                        break;
                    }

                    if (answer[i].compareTo(route[i]) > 0) {
                        answer = Arrays.copyOf(route, route.length);
                        break;
                    }
                }
            }

            return;
        }

        for (Airport arrive : airports[index]) {
            if (arrive.isUsed) {
                continue;
            }
            route[count] = arrive.name;
            arrive.isUsed = true;
            searchRoute(count + 1, map.get(arrive.name), route);
            arrive.isUsed = false;
        }

    }   // end of searchRoute
}
