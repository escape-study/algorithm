from collections import deque
import sys
sys.stdin = open("input.txt")

W, H = map(int, input().split())
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
mp = [list(input()) for _ in range(H)]

def bfs():
    R, C = 0, 0
    while queue:
        r, c, flag = queue.popleft()
        for i in range(4):
            nr, nc = r+di[i][0], c+di[i][1]
            if i == flag:
                res = mp[r][c]
            else:
                res = mp[r][c]+1
            while 0 <= nr < H and 0 <= nc < W:
                if mp[nr][nc] == 'C':
                    mp[nr][nc] = res
                    R, C = nr, nc
                elif mp[nr][nc] == '.' or (mp[nr][nc] != '*' and res <= mp[nr][nc]):
                    mp[nr][nc] = res
                    if (nr, nc, i) not in queue:
                        queue.append((nr, nc, i))
                    nr += di[i][0]
                    nc += di[i][1]
                else:
                    break
    return mp[R][C]

queue = deque()

def solution():
    for r in range(H):
        for c in range(W):
            if mp[r][c] == 'C':
                mp[r][c] = '*'
                for i in range(4):
                    nr, nc = r+di[i][0], c+di[i][1]
                    if 0 <= nr < H and 0 <= nc < W:
                        if mp[nr][nc] == '.':
                            queue.append((nr, nc, i))
                            mp[nr][nc] = 0
                        elif mp[nr][nc] == 'C':
                            return 0
                return bfs()

print(solution())
for m in mp:
    print(' '.join(map(str, m)))