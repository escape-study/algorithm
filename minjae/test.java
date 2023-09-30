import java.util.Arrays;

public class test {
    public static void dijkstra(int[][] graph, int start) {
        int V = graph.length;
        int[] distance = new int[V];
        boolean[] visited = new boolean[V];

        Arrays.fill(distance, Integer.MAX_VALUE);
        distance[start] = 0;

        for (int i = 0; i < V - 1; i++) {
            int minDistance = findMinDistance(distance, visited);
            visited[minDistance] = true;

            for (int j = 0; j < V; j++) {
                if (!visited[j] && graph[minDistance][j] != 0 && distance[minDistance] != Integer.MAX_VALUE
                        && distance[minDistance] + graph[minDistance][j] < distance[j]) {
                    distance[j] = distance[minDistance] + graph[minDistance][j];
                }
            }
        }

        printShortestDistances(distance);
    }

    public static int findMinDistance(int[] distance, boolean[] visited) {
        int V = distance.length;
        int minDistance = Integer.MAX_VALUE;
        int minIndex = -1;

        for (int i = 0; i < V; i++) {
            if (!visited[i] && distance[i] <= minDistance) {
                minDistance = distance[i];
                minIndex = i;
            }
        }

        return minIndex;
    }

    public static void printShortestDistances(int[] distance) {
        int V = distance.length;
        System.out.println("Shortest distances from the starting node:");
        for (int i = 0; i < V; i++) {
            System.out.println("To node " + i + ": " + distance[i]);
        }
    }

    public static void main(String[] args) {
        int[][] graph = {
                {0, 4, 0, 0, 0, 0, 0, 8, 0},
                {4, 0, 8, 0, 0, 0, 0, 11, 0},
                {0, 8, 0, 7, 0, 4, 0, 0, 2},
                {0, 0, 7, 0, 9, 14, 0, 0, 0},
                {0, 0, 0, 9, 0, 10, 0, 0, 0},
                {0, 0, 4, 14, 10, 0, 2, 0, 0},
                {0, 0, 0, 0, 0, 2, 0, 1, 6},
                {8, 11, 0, 0, 0, 0, 1, 0, 7},
                {0, 0, 2, 0, 0, 0, 6, 7, 0}
        };

        int startNode = 0;
        dijkstra(graph, startNode);
    }
}