from itertools import permutations
import re

def solution(user_id, banned_id):
    answer = set()
    n = len(banned_id) 
    perm = list(permutations(user_id, n)) #밴 당한 수만큼 순열

    for p in perm:
        cnt = 0
        for i in range(n):
            temp = banned_id[i].replace('*', '.') #fr.d. 스타일로 변경
            if not re.match(temp, p[i]) or len(banned_id[i]) != len(p[i]): #정규식 None, 아이디 길이 다를 때
                break
            else: 
                cnt += 1
        # 해당 순열 원소의 부정사용자가 banned id 길이와 일치할 때
        if cnt == n: 
            answer.add(frozenset(p)) #set 처리해야되니까 집합 자체를 추가

    return len(answer)