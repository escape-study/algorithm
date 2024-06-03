from collections import deque

def solution(land):
    flag = 'a'
    cnt = {'a': 0}
    W, H = len(land[0]), len(land)
    di = ((-1, 0), (1, 0), (0, -1), (0, 1))
    queue = deque()
    answer = 0
    visited = [[0] * W for _ in range(H)]

    def bfs(flag):
        while queue:
            r, c = queue.popleft()
            for i in range(4):
                nr, nc = r + di[i][0], c + di[i][1]
                if 0 <= nr < H and 0 <= nc < W and land[nr][nc] == 1 and visited[nr][nc] == 0:
                    visited[nr][nc] = flag
                    queue.append((nr, nc))
                    cnt[flag] += 1

    for c in range(W):
        res = set()
        tmp = 0
        for r in range(H):
            if land[r][c] == 1:
                if visited[r][c] == 0:
                    cnt[flag] = 1
                    queue.append((r, c))
                    visited[r][c] = flag
                    bfs(flag)
                    flag = chr(ord(flag)+1)
                    cnt[flag] = 0
                res.add(visited[r][c])

        for alp in res:
            tmp += cnt[alp]
        if tmp > answer:
            answer = tmp

    return answer