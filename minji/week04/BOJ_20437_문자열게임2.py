import sys
sys.stdin = open('input.txt')

T = int(input())
for _ in range(T):
    S = input()
    K = int(input())
    alp = {}
    minlen, maxlen = len(S)+1, 0
    for i, s in enumerate(S):
        if s in alp:
            alp[s].append(i)
        else:
            alp[s] = [i]
    for key in alp.keys():
        if len(alp[key]) >= K:
            for idx in range(len(alp[key])-K+1):
                res = alp[key][idx+K-1]-alp[key][idx]+1
                minlen = min(minlen, res)
                maxlen = max(maxlen, res)
    if minlen == len(S)+1 and maxlen == 0:
        print(-1)
    else:
        print(minlen, maxlen)