import sys
sys.stdin = open('input.txt')

N = int(input())
nlst = sorted(list(map(int, input().split())))
left, right = 0, N-1
ans = 2000000000
l, r = nlst[0], nlst[-1]
while left < right:
    res = nlst[left]+nlst[right]
    if abs(res) < ans:
        l, r, ans = nlst[left], nlst[right], abs(res)
    if res < 0:
        left += 1
    elif res > 0:
        right -= 1
    else:
        print(nlst[left], nlst[right])
        break
else:
    print(l, r)