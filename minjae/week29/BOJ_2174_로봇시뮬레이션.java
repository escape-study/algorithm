package week29;

import org.w3c.dom.Node;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class BOJ_2174_로봇시뮬레이션{
    static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    static StringTokenizer st;
    static class Node{
        int x;
        int y;
        String dir;
        Node(int x, int y , String dir){
            this.x = x;
            this.y = y;
            this.dir = dir;
        }
        void dirL(){
            switch (dir) {
                case "N":
                    dir = "W";
                    break;
                case "W":
                    dir = "S";
                    break;
                case "S":
                    dir = "E";
                    break;
                case "E":
                    dir = "N";
                    break;
            }
        }
        void dirR(){
            switch (dir) {
                case "N":
                    dir = "E";
                    break;
                case "W":
                    dir = "N";
                    break;
                case "S":
                    dir = "W";
                    break;
                case "E":
                    dir = "S";
                    break;
            }
        }
    }

    public static void main(String[] args) throws IOException {
        st = new StringTokenizer(br.readLine());
        int map_y = Integer.parseInt(st.nextToken());
        int map_x =  Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());
        int N = Integer.parseInt(st.nextToken());
        int M =  Integer.parseInt(st.nextToken());

        Node[] nodes = new Node[N+1];
        for (int i = 1; i <= N; i++) {
            st = new StringTokenizer(br.readLine());
            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            nodes[i] = new Node(y, x , st.nextToken());
        }

        for (int i = 0; i < M; i++) {
            st = new StringTokenizer(br.readLine());
            int robot = Integer.parseInt(st.nextToken());
            String order = st.nextToken();
            int req = Integer.parseInt(st.nextToken());

            if(order.equals("R")){
                for (int j = 0; j < req; j++) {
                    nodes[robot].dirR();
                }

            } else if (order.equals("L")) {
                for (int j = 0; j < req; j++) {
                    nodes[robot].dirL();
                }
            }else{
                for (int j = 0; j < req; j++) {
                    switch (nodes[robot].dir) {
                        case "N":
                            nodes[robot].x++;
                            if(nodes[robot].x > map_x){
                                System.out.printf("Robot %d crashes into the wall",robot);
                                return;
                            }
                            break;
                        case "W":
                            nodes[robot].y--;
                            if(nodes[robot].y < 1){
                                System.out.printf("Robot %d crashes into the wall",robot);
                                return;
                            }
                            break;
                        case "E":
                            nodes[robot].y++;
                            if(nodes[robot].y > map_y){
                                System.out.printf("Robot %d crashes into the wall",robot);
                                return;
                            }
                            break;
                        case "S":
                            nodes[robot].x--;
                            if(nodes[robot].x < 1){
                                System.out.printf("Robot %d crashes into the wall",robot);
                                return;
                            }
                            break;
                    }
                    int flag = check(nodes , robot);

                    if(flag != -1){
                        System.out.printf("Robot %d crashes into robot %d",robot,flag);
                        return;
                    }
                }
            }
        }
        System.out.println("OK");
    }

    private static int check(Node[] nodes, int robot) {
        int num = -1;
        for (int i = 1; i < nodes.length; i++) {
            if(i == robot) continue;

            if(nodes[i].x == nodes[robot].x  && nodes[i].y == nodes[robot].y ){
                num = i;
                return num;
            }
        }
        return num;
    }

}