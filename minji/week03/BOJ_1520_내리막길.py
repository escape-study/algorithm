# 시간초과
from collections import deque
import sys
sys.stdin = open('input.txt')

M, N = map(int, input().split())
mp = [list(map(int, input().split())) for _ in range(M)]
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
dp = [[0]*N for _ in range(M)]
dp[0][0] = 1
queue = deque()
queue.append((0, 0))

while queue:
    r, c = queue.popleft()
    for dr, dc in di:
        nr, nc = r+dr, c+dc
        if 0 <= nr < M and 0 <= nc < N:
            if mp[nr][nc] < mp[r][c]:
                dp[nr][nc] += 1
                queue.append((nr, nc))

print(dp[-1][-1])