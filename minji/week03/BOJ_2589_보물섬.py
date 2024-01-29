from collections import deque
import sys
sys.stdin = open('input.txt')

R, C = map(int, input().split())
mp = [list(input()) for _ in range(R)]
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
def bfs(lr, lc):
    global R, C
    lmax_length = 0
    q = deque()
    q.append((lr, lc))
    v = [[-1]*C for _ in range(R)]
    v[lr][lc] = 0
    while q:
        r, c = q.popleft()
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < R and 0 <= nc < C and mp[nr][nc] == 'L' and v[nr][nc] < 0:
                v[nr][nc] = v[r][c]+1
                q.append((nr, nc))
                if v[nr][nc] > lmax_length:
                    lmax_length = v[nr][nc]
    return lmax_length

def connection():
    global R, C
    connection_lst = []
    while queue:
        r, c = queue.popleft()
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < R and 0 <= nc < C and mp[nr][nc] == 'L' and visited[nr][nc] == 0:
                visited[nr][nc] = 1
                connection_lst.append((nr, nc))
                queue.append((nr, nc))
    return connection_lst

def solution(land):
    max_length = 0
    for lr, lc in land:
        length = bfs(lr, lc)
        if length > max_length:
            max_length = length
    return max_length


visited = [[0]*C for _ in range(R)]
queue = deque()
ans = 0
for r in range(R):
    for c in range(C):
        if mp[r][c] == 'L' and visited[r][c] == 0:
            visited[r][c] = 1
            queue.append((r, c))
            res = solution(connection())
            if ans < res:
                ans = res
print(ans)