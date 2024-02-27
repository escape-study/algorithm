from collections import deque
import sys
sys.stdin = open('input.txt')

sudoku = [list(map(int, sys.stdin.readline().rstrip().split())) for _ in range(9)]
zero = deque()
flag = False

for r in range(9):
    for c in range(9):
        if sudoku[r][c] == 0:
            zero.append((r, c))


def func(idx):
    global flag
    if len(zero) < idx or flag == True:
        return
    if len(zero) == idx and flag == False:
        for r in range(9):
            print(' '.join(map(str, sudoku[r])))
        flag = True
        return

    rr, cc = zero[idx]

    nlst = set(range(1, 10))
    for i in range(9):
        nlst.discard(sudoku[i][cc])
        nlst.discard(sudoku[rr][i])

    for r in range(3):
        for c in range(3):
            nlst.discard(sudoku[(rr//3)*3+r][(cc//3)*3+c])

    for n in nlst:
        sudoku[rr][cc] = n
        func(idx+1)
        sudoku[rr][cc] = 0

func(0)
