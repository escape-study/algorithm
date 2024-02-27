from collections import deque
import sys
sys.stdin = open('input.txt')

N = int(input())
mt = [list(map(int, input().split())) for _ in range(N)]
shark = 2
sr, sc = 0, 0
fish = False
ans, cnt = 0, 0
queue = deque()
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
def bfs():
    global ans
    visited = [[0]*N for _ in range(N)]
    res = []
    queue.append((sr, sc))
    visited[sr][sc] = 1

    while queue:
        r, c = queue.popleft()
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < N and 0 <= nc < N and visited[nr][nc] == 0 and mt[nr][nc] <= shark:
                if 0 < mt[nr][nc] < shark:
                    res.append((nr, nc, visited[r][c]))
                visited[nr][nc] = visited[r][c]+1
                queue.append((nr, nc))

    res.sort(key=lambda x: (x[2], x[0], x[1]))

    ans += res[0][2]

    return res[0][:2]



for r in range(N):
    for c in range(N):
        if mt[r][c] == 1:
            fish = True
        elif mt[r][c] == 9:
            sr, sc = r, c
            mt[r][c] = 0


if fish == False:
    print(0)
else:
    while True:
        try:
            fr, fc = bfs()
            mt[fr][fc] = 0
            sr, sc = fr, fc
            cnt += 1
            if shark == cnt:
                shark += 1
                cnt = 0
        except:
            print(ans)
            break