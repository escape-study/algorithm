from collections import deque
import sys
sys.stdin = open('input.txt')

N = int(input())
cnt = 0
def nQueen():
    global N, cnt
    if len(stack) == N:
        cnt += 1
        return

    cnow = len(stack)
    for r in range(N):
        if visited[r] == 0:
            for c in range(cnow):
                if abs(c-cnow) != abs(r - stack[c]):
                    continue
                else:
                    break
            else:
                stack.append(r)
                visited[r] = 1
                nQueen()
                stack.pop()
                visited[r] = 0

stack = deque()
visited = [0]*N
nQueen()
print(cnt)