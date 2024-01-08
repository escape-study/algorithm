import sys
input = sys.stdin.readline

pelin = list(input())
pelin = pelin[:-1]

answer = 0
same = 0

if len(pelin) % 2 == 0 :
    for i in range(len(pelin) // 2):
        if pelin[i] == pelin[-(i+1)] :
            same += 1
        else : 
            break 
    if same == (len(pelin)//2):
        answer = -1
    elif same == 0:
        answer = len(pelin)
    else :
        answer = len(pelin) - 1

else :
    for i in range(len(pelin)//2) :
        if pelin[i] == pelin[-(i+1)] :
            same += 1
        else : 
            break 
    if same == (len(pelin) // 2) and pelin[0] == pelin[(len(pelin)-1) // 2]:
        answer = -1
    elif same == 0:
        answer = len(pelin)
    else :
        answer = len(pelin) - 1


print(answer)
