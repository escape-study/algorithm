from collections import deque
import sys
sys.stdin = open("input.txt")

mt = [list(input()) for _ in range(12)]

di = ((-1, 0), (1, 0), (0, -1), (0, 1))
def func(color):
    idx = 0
    while True:
        r, c = queue[idx][0], queue[idx][1]
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < 12 and 0 <= nc < 6 and mt[nr][nc] == color and visited[nr][nc] == 0:
                visited[nr][nc] = 1
                queue.append((nr, nc))
        idx += 1
        if idx >= len(queue):
            break
    if idx >= 4:
        for r, c in queue:
            mt[r][c] = '.'
        return True
    return False


ans = 0
while True:
    res = 0
    for r in range(11, -1, -1):
        for c in range(6):
            if mt[r][c] != '.':
                visited = [[0] * 6 for _ in range(12)]
                visited[r][c] = 1
                queue = deque()
                queue.append((r, c))
                if func(mt[r][c]):
                    res = 1
    if res == 1:
        for c in range(6):
            cnt = 0
            for r in range(11, -1, -1):
                if mt[r][c] == '.':
                    cnt += 1
                elif mt[r][c] != '.' and cnt > 0:
                    mt[r][c], mt[r+cnt][c] = mt[r+cnt][c], mt[r][c]
        ans += 1
    else:
        break

print(ans)