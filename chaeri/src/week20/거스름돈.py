def solution(n, money):
    dp = [0] * (n+1)
    for i in money:
        dp[i] += 1
        for j in range(i+1, n+1):
            dp[j] += dp[j-i]
    return dp[n]