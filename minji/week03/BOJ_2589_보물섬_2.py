from collections import deque
import sys
sys.stdin = open('input.txt')

R, C = map(int, input().split())
mp = [list(input()) for _ in range(R)]
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
def bfs(lr, lc):
    global R, C
    lmax_length = 0
    v = [[-1]*C for _ in range(R)]
    v[lr][lc] = 0
    while queue:
        r, c = queue.popleft()
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < R and 0 <= nc < C and mp[nr][nc] == 'L' and v[nr][nc] < 0:
                v[nr][nc] = v[r][c]+1
                queue.append((nr, nc))
                lmax_length = max(v[nr][nc], lmax_length)

    return lmax_length


queue = deque()
ans = 0
for r in range(R):
    for c in range(C):
        if mp[r][c] == 'L':
            visited = [[0] * C for _ in range(R)]
            visited[r][c] = 1
            queue.append((r, c))
            res = bfs(r, c)
            ans = max(ans, res)

print(ans)