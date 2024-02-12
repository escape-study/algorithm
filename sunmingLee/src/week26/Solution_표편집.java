package week26;

import java.util.*;

public class Solution_표편집 {
    int cursor, moves;
    char[] table;
    Stack<Integer> deleted;

    public String solution(int n, int k, String[] cmd) {
        cursor = k; // 현재 선택된 행 번호
        table = new char[n]; // 표 (false면 해당행이 삭제된것임)
        Arrays.fill(table, 'O');
        deleted = new Stack<>();    // 삭제된 행 저장용 스택

        moves = 0;
        for (int i = 0; i < cmd.length; i++) {
            char c = cmd[i].charAt(0);

            switch (c) {
                case 'U':
                    moves -= Integer.parseInt(cmd[i].substring(2));
                    break;
                case 'D':
                    moves += Integer.parseInt(cmd[i].substring(2));
                    break;
                case 'C':
                    moveCursor();
                    deleteRow();
                    break;
                case 'Z':
                    moveCursor();
                    revertRow();
                    break;
                default:
                    break;
            }
        }

        return new String(table) + "";
    }   // end of solution

    private void editTable(String cmd) {
        char c = cmd.charAt(0);

        switch (c) {
            case 'U':
                moves -= Integer.parseInt(cmd.substring(2));
                // moveCursor(Integer.parseInt(cmd.substring(2)), -1);
                break;
            case 'D':
                moves += Integer.parseInt(cmd.substring(2));
                // moveCursor(Integer.parseInt(cmd.substring(2)), 1);
                break;
            case 'C':
                moveCursor();
                deleteRow();
                break;
            case 'Z':
                moveCursor();
                revertRow();
                break;
            default:
                break;
        }
    }   // end of editTable

    /**
     * moves칸만큼 커서 이동
     */
    private void moveCursor() {
        if (moves > 0) {
            while (moves != 0) {
                cursor++;

                if (table[cursor] == 'X') {
                    continue;
                }
                moves--;
            }
        } else {
            while (moves != 0) {
                cursor--;

                if (table[cursor] == 'X') {
                    continue;
                }
                moves++;
            }
        }

    }   // end of moveCursor

    private void deleteRow() {
        deleted.push(cursor);
        table[cursor] = 'X';
        int temp = cursor;
        while (cursor < table.length && table[cursor] == 'X') {
            cursor++;
        }

        if (cursor < table.length) {
            return;
        }

        // 원래 커서 위치가 현재 표의 마지막 행이었음
        cursor = temp;
        while (table[cursor] == 'X') {
            cursor--;
        }
    }   // end of deleteRow

    private void revertRow() {
        int rowNum = deleted.pop();
        table[rowNum] = 'O';
    }   // end of revertRow
}
