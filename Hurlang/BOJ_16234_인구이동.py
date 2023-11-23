'''
문제 해결 방법: bfs

while open:
open=0 (초기화)
1. 인접한 칸이 L이상 R이하이면 국경을 열고(open=1), 같은 그룹으로 지정
2. 그룹 평균 인구 계산
3. 인구 분배

'''

from collections import deque
dx = [0, 0, 1, -1]
dy = [1, -1, 0, 0]

def bfs(x, y):
    global open
    q = deque()
    q.append((x, y))
    visited[x][y] = g_num
    group_count[g_num] = [1, arr[x][y]]
    while q:
        x, y = q.popleft()
        for i in range(4):
            nx = x + dx[i]
            ny = y + dy[i]
            if 0 <= nx < N and 0 <= ny < N and L <= abs(arr[nx][ny] - arr[x][y]) <= R and not visited[nx][ny]:
                open = 1
                visited[nx][ny] = g_num
                group_count[g_num] = [group_count.get(g_num)[0] + 1, group_count.get(g_num)[1] + arr[nx][ny]]
                q.append((nx, ny))


N, L, R = map(int, input().split(' '))
arr = []
for i in range(N):
    arr.append(list(map(int, input().split(' '))))


open = 1
day = 0
while open:
    open = 0
    g_num = 0
    group_count = dict()
    visited = [[0] * N for _ in range(N)]

    # 그룹 지정
    for row in range(N):
        for col in range(N):
            if not visited[row][col]:
                g_num += 1
                bfs(row, col)

    if open:
        # 그룹별 평균계산
        for key, value in group_count.items():
            group_count[key] = value[1] // value[0]

        # 인구 분배
        for row in range(N):
            for col in range(N):
                if visited[row][col]:
                    arr[row][col] = group_count.get(visited[row][col])

        day += 1

print(day)