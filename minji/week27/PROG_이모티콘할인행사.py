from itertools import product


def solution(users, emoticons):
    e_num = len(emoticons)
    dscnt = list(product([10, 20, 30, 40], repeat=e_num))
    answer = [0, 0]
    for dc in dscnt:
        plus, result = 0, 0
        for ratio, price in users:
            total = 0
            for i in range(e_num):
                if dc[i] >= ratio:
                    total += (emoticons[i] * (100 - dc[i])) // 100
                if total >= price:
                    break
            if total >= price:
                plus += 1
            else:
                result += total

        if plus > answer[0] or (plus == answer[0] and result > answer[1]):
            answer[0], answer[1] = plus, result

    return answer