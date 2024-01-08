import sys
sys.stdin = open('input.txt')

N, H = map(int, input().split())

down = [0]*(H+1)
up = [0]*(H+1)
ans = N+1
cnt = 0
for i in range(N):
    h = int(input())
    if i%2 == 0:
        down[h] += 1
    else:
        up[h] += 1

for i in range(H-1, 0, -1):
    down[i] += down[i+1]
    up[i] += up[i+1]

for i in range(1, H+1):
    height = down[i]+up[H-i+1]
    if ans > height:
        ans = height
        cnt = 1
    elif ans == height:
        cnt += 1

print(ans, cnt)