import sys
sys.stdin = open("input.txt")

A = input()
B = input()
w, h = len(A), len(B)
mt = [[0]*(w+1) for _ in range(h+1)]

for r in range(1, h+1):
    for c in range(1, w+1):
        if A[c-1] == B[r-1]:
            mt[r][c] = mt[r-1][c-1]+1
        else:
            mt[r][c] = max(mt[r-1][c], mt[r][c-1])

print(mt[-1][-1])