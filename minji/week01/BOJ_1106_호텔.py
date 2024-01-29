import sys
sys.stdin = open('input.txt')

C, N = map(int, input().split())
dp = [0] + [987654321]*(C+100)

for _ in range(N):
    cost, customer = map(int, input().split())
    for i in range(customer, C+101):
        dp[i] = min(dp[i], dp[i-customer]+cost)

print(dp)
print(min(dp[C:]))