from collections import deque
import sys
sys.stdin = open('input.txt')

di = ((0, 1), (1, 0), (0, -1), (-1, 0))
N = int(input())
K = int(input())
mp = [['']*N for _ in range(N)]
mp[0][0] = 0
hr, hc = 0, 0
tr, tc = 0, 0
for _ in range(K):
    a, b = map(int, input().split())
    mp[a-1][b-1] = -1

L = int(input())
queue = deque()
for _ in range(L):
    X, C = input().split()
    queue.append((int(X), C))

time, flag = 0, 0
while True:
    nr, nc = hr+di[flag][0], hc+di[flag][1]
    if 0 <= nr < N and 0 <= nc < N and (mp[nr][nc] == -1 or mp[nr][nc] == ''):
        if mp[nr][nc] == '':
            f = mp[tr][tc]
            mp[tr][tc] = ''
            tr += di[f][0]
            tc += di[f][1]
        time += 1
        hr, hc = nr, nc
        if queue and time == queue[0][0]:
            if queue[0][1] == 'L':
                flag = (flag+3)%4
            else:
                flag = (flag+1)%4
            queue.popleft()
        mp[nr][nc] = flag
    else:
        time += 1
        break

print(time)