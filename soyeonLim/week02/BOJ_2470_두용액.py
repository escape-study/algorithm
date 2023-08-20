import sys

n = int(sys.stdin.readline().strip())
l = list(map(int, sys.stdin.readline().strip().split(' ')))

l.sort() #-99, -2, -1, 4, 98

start = 0
end = n-1

answer = abs(l[start]+l[end])
final = [l[start],l[end]]

while start<end:
    left = l[start]
    right = l[end]

    sum = left+right

    if abs(sum)<answer:
        answer = abs(sum)
        final = [left,right]
        if answer ==0:
            break
    if sum<0:
        start+=1
    else:
        end-=1

print(final[0],final[1])