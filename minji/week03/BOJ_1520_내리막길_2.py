import sys
sys.setrecursionlimit(10**9)
sys.stdin = open('input.txt')

M, N = map(int, input().split())
mp = [list(map(int, input().split())) for _ in range(M)]
dp = [[-1]*N for _ in range(M)]
di = ((-1, 0), (1, 0), (0, -1), (0, 1))

def dfs(r, c):
    global N, M
    if r == M-1 and c == N-1:
        return 1

    if dp[r][c] == -1:
        dp[r][c] = 0
        for dr, dc in di:
            nr, nc = r+dr, c+dc
            if 0 <= nr < M and 0 <= nc < N and mp[r][c] > mp[nr][nc]:
                dp[r][c] += dfs(nr, nc)

    return dp[r][c]

print(dfs(0, 0))