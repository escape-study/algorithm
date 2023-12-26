package week18;

        import java.io.BufferedReader;
        import java.io.IOException;
        import java.io.InputStreamReader;
        import java.util.*;

public class BOJ_2931_가스관 {
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    static StringTokenizer st;

    static char Map[][] , result , block[] = {'-','|','1','2','3','4','+'};
    static boolean visited[][];
    static int delta[][] = {{0,1},{0,-1},{1,0},{-1,0}} , R , C , cnt , resultX , resultY;
    static Map<Character,Map<Integer, Integer>> check;

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        R  = Integer.parseInt(st.nextToken());
        C = Integer.parseInt(st.nextToken());

        Map = new char[R+1][C+1];
        visited = new boolean[R+1][C+1];
        cnt = 0;
        int[][] startEnd = new int[2][2];

        for (int i = 1; i <= R; i++) {
            String input = br.readLine();
            for (int j = 1; j <= C; j++) {
                Map[i][j] = input.charAt(j-1);
                if (Map[i][j] == 'M'){
                    startEnd[0][0] = i;
                    startEnd[0][1] = j;
                }else if (Map[i][j] == 'Z'){
                    startEnd[1][0] = i;
                    startEnd[1][1] = j;
                }else if (Map[i][j] != '.'){
                    cnt++;
                }
            }
        }

        check = new HashMap<>();
        check.put('1' , new HashMap<>());
        check.put('2' , new HashMap<>());
        check.put('3' , new HashMap<>());
        check.put('4' , new HashMap<>());
        check.put('|' , new HashMap<>());
        check.put('-' , new HashMap<>());
        check.put('+' , new HashMap<>());
        check.put('Z' , new HashMap<>());

        check.get('|').put(2 ,2);
        check.get('|').put(3 ,3);

        check.get('-').put(0 ,0);
        check.get('-').put(1 ,1);

        check.get('+').put(0 ,0);
        check.get('+').put(1 ,1);
        check.get('+').put(2 ,2);
        check.get('+').put(3 ,3);

        check.get('1').put(1 ,2);
        check.get('1').put(3 ,0);

        check.get('2').put(2 ,0);
        check.get('2').put(1 ,3);

        check.get('3').put(0 ,3);
        check.get('3').put(2 ,1);

        check.get('4').put(0 ,2);
        check.get('4').put(3 ,1);

        for (int i = 0; i < 4; i++) {
            check.get('Z').put(i ,1);
        }

        dfs(startEnd[0][0] , startEnd[0][1] , 0);



    }
    public static void dfs(int x, int y,  int d){
        int nextX;
        int nextY;
        if(Map[x][y] == 'M'){
            for (int i = 0; i < 4; i++) {

                nextX = x + delta[i][0];
                nextY = y + delta[i][1];
                if(nextX <= 0 || nextX > R || nextY <= 0 || nextY > C || Map[nextX][nextY] == '.' || Map[nextX][nextY] == 'Z') continue;
                if (check.get(Map[nextX][nextY]).containsKey(i)) {
                    dfs(nextX , nextY , i);
                }
            }
        }else if (Map[x][y] == 'Z'){
            if(cnt == 0){
                System.out.println(resultX + " " + resultY + " " + result);
                System.exit(0);
            }
        } else if (Map[x][y] == '.') { // 빠진 블록
            for (int i = 0; i < 4; i++) {
                if(delta[i][0] == -delta[d][0] &&delta[i][1] == -delta[d][1]) continue;
                nextX = x + delta[i][0];
                nextY = y + delta[i][1];
                if(nextX <= 0 || nextX > R || nextY <= 0 || nextY > C || Map[nextX][nextY] == '.' || Map[nextX][nextY] == 'M') continue;

                if(Map[nextX][nextY] == 'Z' || check.get(Map[nextX][nextY]).containsKey(i)){
                    for (int j = 0; j < block.length; j++) {
                        if(check.get(block[j]).containsKey(d) && check.get(block[j]).get(d) == i){
                            Map[x][y] = block[j];
                            visited[x][y] = true;
                            resultX = x;
                            resultY = y;
                            result = block[j];

                            dfs(nextX , nextY , i);

                            Map[x][y] = '.';
                            visited[x][y] = false;
                        }
                    }
                }
            }
        }else{
            if(!check.get(Map[x][y]).containsKey(d)){
                return;
            }
            boolean flag = false;
            if(!visited[x][y]){
                visited[x][y] = true;
                cnt--;
                flag = true;
            }
//            System.out.println("--------");
//            System.out.println(x + " " + y);
//            System.out.println(Map[x][y] + " " + d);
//            System.out.println("--------");
            int nextDir = check.get(Map[x][y]).get(d);
            nextX = x + delta[nextDir][0];
            nextY = y + delta[nextDir][1];
            dfs(nextX , nextY , nextDir);

            if(flag){
                cnt++;
                visited[x][y] = false;
            }

        }

    }
}