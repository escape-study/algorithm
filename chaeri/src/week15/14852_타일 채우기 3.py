import sys
input = sys.stdin.readline

n = int(input())

if n == 1:
    print(2)
    sys.exit()
elif n == 2:
    print(7)
    sys.exit()

dp = list(0 for _ in range(n + 1))
dp2 = list(0 for _ in range(n + 1))
dp[0], dp2[0] = 1, 1
dp[1], dp2[1] = 2, 3
dp[2], dp2[2] = 7, 10

for i in range(3, n + 1):
    dp[i] = (dp2[i - 1] * 2 + dp[i - 2]) % 1_000_000_007
    dp2[i] = (dp[i] + dp2[i - 1]) % 1_000_000_007

print(dp[n])