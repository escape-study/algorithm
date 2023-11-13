import sys
input = sys.stdin.readline

n = int(input())
arr = list(map(int, input().split()))
arr.sort()
ans = 1
for i in arr:
    if ans < i:
        break
    ans += i

print(ans)