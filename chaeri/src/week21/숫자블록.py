#자기 제외 가장 큰 약수 찾으면 됨
def solution(begin, end):
    answer = []
    
    for i in range(begin, end + 1):
        min_num = 1
        max_num = 1
        for j in range(2, int(i**0.5) + 1):
            if i % j == 0:
                if i // j <= 10000000:
                    min_num = j
                    answer.append(i // j)
                    break
                else:
                    max_num = j
        if i == 1:
            answer.append(0)
        elif min_num == 1:
            answer.append(max_num)
    return answer