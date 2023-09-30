package week08;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 아이디어
 * 빡구현 문제로, 별도의 아이디어가 필요 없다. 시키는 대로 구현하면 된다.
 *
 * 각 계절마다 동작하는 메서드를 만들고, 한번의 반복마다 4가지 메서드를 동작시켜주면 된다.
 * K년까지 반복이 끝나면 맵을 돌면서 살아있는 나무의 수를 구하면 된다.
 * 가장 중요한 것은 빠르게 문제를 찾고 수정할 수 있도록 코드를 모듈처럼 잘 나눠야하고, 나무의 상태등은 반드시 객체를 이용해야 한다.
 *
 * 주의해야 할 점은, 각 칸에 나무가 여러개 심어져 있을 수도 있다.
 *
 * (수정)
 * 또한 봄과 여름을 따로 구했는데, 봄과정을 진행하면서 죽은 나무를 바로 모아뒀다가 나무를 다죽이면 마지막에 모아둔 값을 양분으로 업데이트 하는 식으로
 * 하나의 과정으로 줄이는 것이 좋을 것 같다.
 *
 * (수정2)
 * 나무가 각 땅에 여러개 존재하는데, 이를 리스트를 이용해서 관리하고 있다.
 * 이때 죽은 나무를 삭제하는 것이 아닌, 삭제 체크만해서 유지하고 있는데, 이 방법이 시간초과를 만드는 듯 하다.
 * 계속해서 정렬하고 있는데, 정렬할 필요가 없다.
 * 새로운 나무는 나이 1을 가지고 추가 되는데, 리스트로 관리한다면 왼쪽에 추가하는 식으로 해서 오름차순을 유지할 수 있다.
 * 오름차순이 깨질 가능성이 있는 부분은 양분을 먹고 나이가 증가되는 봄이다.
 * 하지만 잘생각해보면, 오름차순이면 i번째와 i+1번째는 같거나, i+1번째가 더 크다
 * i번째가 양분을 먹고 1증가 했다면, i+1번의 경우, 동일하게 1증가하거나, 죽거나 둘중 하나다.
 * 결국 양분을 먹더라도 i가 i+1의 나이를 역전하는 경우는 발생 할 수 없다.
 *
 */
public class BOJ16235_나무_재테크 {

    //8방향에 번식 - 상하좌우, 좌상,좌하, 우상,우하
    private final static int[] dx = {-1, 1, 0, 0, -1, 1, -1, 1};
    private final static int[] dy = {0, 0, -1, 1, -1, -1, 1, 1};

    private static class Tree{
        int age;

        //이전 노드와 다음 노드값
        Tree prev, next;

        public Tree(int age){
            this.age = age;
            this.prev = null;
            this.next = null;
        }

        public int getAge() {
            return age;
        }

        //5의 배수면 번식가능
        public boolean breedable(){
            if(this.age % 5 == 0) return true;

            return false;
        }

        //해당 양분보다 나이가 많으면 먹음
        public boolean eatNutrient(int x, int y){

            //해당 양분보다 나이가 작거나 같으면 나이를 1살 먹음
            if(this.age <= maps[x][y]){
                maps[x][y] -= this.age;
                this.age++;
                return true;
            }

            return false;
        }

        public void addTree(Tree tree){
            //노드 연결
            tree.next = this;
            this.prev = tree;
        }

        public void deleteTree(){
            this.next = null;
        }

    }

    //땅 크기
    private static int N;
    //나무 개수
    private static int M;
    //몇년
    private static int K;

    //땅을 나타낼 배열.
    private static int[][] maps;

    //겨울에 각 칸마다 추가되는 양분의 양을 저장할 배열
    private static int[][] nutrientMaps;

    //나무 정보를 나타낼 배열. - 내부를 리스트로 하고, 정렬 하도록 한다.
    private static Tree[][] treeInfo;

    //가능한 칸인지 확인
    private static boolean check(int nextX, int nextY){
        return nextX >= 0 && nextX < N &&
                nextY >= 0 && nextY < N;
    }

    //봄에 나무를 처리할 메서드
    private static void springAndSummer(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){

                //나무 리스트를 뽑음
                Tree tree = treeInfo[i][j];


                //해당 노드가 null이 아니고, 먹을 수 있다면 먹음.
                while(tree != null && tree.eatNutrient(i,j)){
                    tree = tree.next;
                }


                Tree deleteTree = tree;

                int totalNewNutrient = 0; //해당칸에 추가할 총 양분

                //먹고 나이먹는 나무뒤로는 전부다 죽여야 됨.
                while(tree != null){
                    totalNewNutrient += tree.getAge() / 2;
                    tree = tree.next;
                }


                maps[i][j] += totalNewNutrient;

                //트리 삭제
                if(deleteTree != null){
                    if(deleteTree.prev != null) deleteTree.prev.deleteTree(); //앞의 노드가 있으면 삭제.
                    else treeInfo[i][j] = null; //앞에 노드가 없으면 해당 배열에 헤드노드 삭제
                }


            }
        }

    }

    //나무의 나이가 5의 배수면 번식
    private static void breeding(int x, int y){

        Tree tree = treeInfo[x][y];

        while(tree != null){

            if(tree.breedable()){
                //번식가능하면 8방향에 번식.
                for(int i = 0; i < 8; i++){
                    int nextX = x + dx[i];
                    int nextY = y + dy[i];

                    if(check(nextX, nextY)){

                        Tree tempTree = new Tree(1);
                        //null이 아니면 기존 노드 앞에 추가.
                        if(treeInfo[nextX][nextY] != null){
                            treeInfo[nextX][nextY].addTree(tempTree);
                        }
                        treeInfo[nextX][nextY] = tempTree;
                    }
                }
            }

            tree = tree.next;
        }
    }

    //가을에 나무를 처리할 메서드 - 번식
    private static void autumn(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++) {

                breeding(i, j);

            }
        }
    }
    //겨울에 나무를 처리할 메서드 - 양분추가
    private static void winter(){

        for(int i = 0; i < N; i++){
            for(int j = 0; j < N; j++){
                maps[i][j] += nutrientMaps[i][j];
            }
        }

    }

    //살아있는 나무를 구할 메서드.
    private static int findAliveTreeCount(){

        int count = 0;

        for(int i = 0; i < N ; i++){
            for(int j = 0; j < N; j++){

                Tree tree = treeInfo[i][j];

                //노드가 있으면 반복해서 돌림.
                while(tree != null){
                    count++;
                    tree = tree.next;
                }
            }
        }

        return count;

    }

    public static void main(String[] args) throws Exception{


        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());

        N = Integer.parseInt(st.nextToken());
        M = Integer.parseInt(st.nextToken());
        K = Integer.parseInt(st.nextToken());

        maps = new int[N][N];
        nutrientMaps = new int[N][N];
        treeInfo = new Tree[N][N];

        //땅과 나무 정보 배열 입력.
        for(int i = 0; i < N; i++){
            st = new StringTokenizer(br.readLine());

            for(int j = 0; j < N; j++){
                maps[i][j] = 5;
                nutrientMaps[i][j] = Integer.parseInt(st.nextToken());
            }
        }

        //초기 나무 입력
        for(int i = 0; i < M; i++){
            st = new StringTokenizer(br.readLine());

            int x = Integer.parseInt(st.nextToken());
            int y = Integer.parseInt(st.nextToken());
            int z = Integer.parseInt(st.nextToken());

            treeInfo[x - 1][y - 1] = new Tree(z);
        }

        //K년 동안 4계절 반복
        while(K-- > 0){

            //봄, 여름
            springAndSummer();
            //가을
            autumn();
            //겨울
            winter();
        }

        //최종적으로 살아있는 나무의 수를 반환.
        System.out.println(findAliveTreeCount());

    }

}
