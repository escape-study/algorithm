import sys
sys.stdin = open('input.txt')

N = int(input())
dp = [0]*(N+1)
for i in range(1, N+1):
    t, p = map(int, input().split())
    dp[i] = max(dp[i-1], dp[i])
    if t+i-1 <= N:
        dp[t+i-1] = max(p+dp[i-1], dp[t+i-1])

print(dp[-1])