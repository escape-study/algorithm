class Solution_표현가능한이진트리 {
    public static int[] solution(long[] numbers) {
        int[] answer = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            if (numbers[i] == 1) {
                answer[i] = 1;
                continue;
            }

            String binary = Long.toBinaryString(numbers[i]);
            binary = addDummy(binary);  // 더미 노드 추가하여 포화 이진트리로 만들기
            answer[i] = search(0, binary.length() - 1, binary);
        }

        return answer;
    }

    private static String addDummy(String binary) {
        long depth = 1;
        long len = binary.length();
        while (true) {
            if (len < Math.pow(2, depth)) { // 포화 이진 트리의 노드 개수
                break;
            }

            depth++;
        }

        depth = (long) Math.pow(2, depth);
        String res = "";
        for (int i = 1; i < depth - len; i++) {
            res += "0";
        }
        res += binary;

        return res;
    }

    //    private static boolean search(String binary) {
//        boolean possible = true;
//
//        int len = binary.length();
//        int mid = len / 2;
//        String left = binary.substring(0, mid);
//        String right = binary.substring(mid + 1, len);
//
//        if (binary.charAt(mid) == '0' && (left.charAt(left.length() / 2) == '1' || right.charAt(right.length() / 2) == '1')) {
////                System.out.println(binary.charAt(left) + "" + binary.charAt(mid) + "" + binary.charAt(right));
//            return false;
//        }
//
//        if (left.length() > 2) {
//            possible = search(left);
//            if (possible) {
//                possible = search(right);
//            }
//        }
//
//        return possible;
//    }
    private static int search(int left, int right, String binary) {
        int mid = (left + right) / 2;   // 루트
        int leftMid = (left + mid - 1) / 2; // 왼쪽 이진트리의 루트
        int rightMid = (mid + 1 + right) / 2;   // 오른쪽 이진트리의 루트

        // 루트가 없는데 자식노드가 있는 경우
        if (binary.charAt(mid) == '0' && (binary.charAt(leftMid) == '1' || binary.charAt(rightMid) == '1')) {
            return 0;
        }

        if (right - left > 2) {
            int resLeft = search(left, mid - 1, binary);
            if (resLeft == 0) {
                return 0;
            }

            int resRight = search(mid + 1, right, binary);
            return resRight;
        }

        return 1;
    }
}