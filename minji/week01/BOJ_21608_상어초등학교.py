import sys
sys.stdin = open("input.txt")

N = int(input())
mt = [[0]*N for _ in range(N)]
di = ((-1, 0), (1, 0), (0, -1), (0, 1))
flst = [0]*(N**2+1)
ans = 0

for _ in range(N**2):
    student, *friends = map(int, input().split())
    flst[student] = tuple(friends)
    res = []
    for r in range(N):
        for c in range(N):
            if mt[r][c] == 0:
                friend, blank = 0, 0
                for dr, dc in di:
                    if 0 <= r+dr < N and 0 <= c+dc < N:
                        if mt[r+dr][c+dc] == 0:
                            blank += 1
                        elif mt[r+dr][c+dc] in friends:
                            friend += 1
                res.append((r, c, friend, blank))
    res.sort(key= lambda x: (-x[2], -x[3], x[0], x[1]))
    mt[res[0][0]][res[0][1]] = student

for r in range(N):
    for c in range(N):
        tmp = 0
        for dr, dc in di:
            if 0 <= r+dr < N and 0 <= c+dc < N:
                if mt[r+dr][c+dc] in flst[mt[r][c]]:
                    tmp += 1
        if tmp != 0:
            ans += 10**(tmp-1)

print(ans)