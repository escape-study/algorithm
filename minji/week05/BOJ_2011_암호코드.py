import sys
sys.stdin = open('input.txt')

N = input()
dp = [[0]*len(N) for _ in range(2)]
lst = [0]*len(N)

for i in range(1, len(N)):
    n, p = int(N[i]), int(N[i-1])
    if n != 0:
        dp[0][i] = 1
    if p == 1:
        dp[1][i] = 1
    elif p == 2:
        if n <= 6:
            dp[1][i] = 1

lst[0] = int(N[0]) and 1
if len(N) > 1:
    lst[1] = dp[0][1]*lst[0] + dp[1][1]
    for i in range(2, len(N)):
        lst[i] = lst[i-1]*dp[0][i] + lst[i-2]*dp[1][i]

print(lst[-1]%1000000)