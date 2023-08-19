import sys
a = sys.stdin.readline().rstrip()
b = sys.stdin.readline().rstrip()
array = [[0]*(len(a)+1) for _ in range(len(b)+1)]
for i in range(1,len(a)+1):
    for j in range(1,len(b)+1):
        if a[i-1]==b[j-1]:
            array[j][i]=array[j-1][i-1]+1
        else:
            array[j][i]=max(array[j-1][i],array[j][i-1])

print(array[len(b)][len(a)])