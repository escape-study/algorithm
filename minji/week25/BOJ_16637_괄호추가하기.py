import sys
sys.stdin = open('input.txt')

N = int(input())
s = input()
ans = -9**11

def cal(ys, a, b):
    if ys == '+':
        return a+b
    elif ys == '-':
        return a-b
    else:
        return a*b


def func(res, S):
    global ans
    if len(S) == 0:                             # 1. 연산 끝냈을 경우
        ans = max(res, ans)                     # 최대값 구하기
    elif len(S) == 2:                           # 2. 이후로 괄호를 더 만들 수 없을 경우
        func(cal(S[0], res, int(S[1])), '')     # 괄호 안만들고 계산
    else:                                       # 3. 이후로 괄호를 더 만들 수 있을 경우
        func(cal(S[0], res, int(S[1])), S[2:])  # 3-1. 지금까지 결과값과 다음 숫자 연산할 경우
        res2 = cal(S[2], int(S[1]), int(S[3]))
        func(cal(S[0], res, res2), S[4:])       # 3-2. 다음 숫자+다다음 숫자 괄호 만들 경우

if N == 1:
    print(s)
else:
    func(int(s[0]), s[1:])
    print(ans)