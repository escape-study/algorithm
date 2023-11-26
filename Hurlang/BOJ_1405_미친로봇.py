'''
문제해결방법 : 백트래킹(재귀)

방문체크 : 1. 딕셔너리 활용 (.get(key, [default]) 메서드는 key값이 없을 경우 default로 설정한 값을 반환)
         2. 방향벡터 활용

'''

dr = [0, 0, 1, -1]
dc = [1, -1, 0, 0]

def dfs(n, k, p):
    global p_sum, r, c
    if n == k:
        p_sum += p
        return
    else:
        # 한 방향씩 체크
        for i in range(4):
            # 중복X: 좌표변경, 중복체크, dfs(n, k+1, p*p_list[i])
            # 중복O: pass (더 이상 재귀 진행X)
            if not visit.get((r+dr[i], c+dc[i]), 0):
                visit[r + dr[i], c + dc[i]] = 1
                r, c = r+dr[i], c+dc[i]
                dfs(n, k+1, p*p_list[i])
                ######################### 윗 줄에서 재귀 보내버리고, 같은 조건에서 다른방향도 체크하기위해 좌표와 방문체크를 돌려놓기
                visit[r, c] = 0
                r, c = r-dr[i], c-dc[i]

n, E, W, S, N = map(int, input().split(' '))
p_list = list(map(lambda x: x/100, [E, W, S, N])) # [25, 25, 25 ,25] -> [0.25, 0.25, 0.25, 0.25]

# 방문체크
visit = dict()

p_sum = 0       # 단순 경로로 갈 확률 누적합
r, c = 0, 0     # 출발지 좌표
visit[0, 0] = 1 # 출발지 방문
dfs(n, 0, 1)    # dfs(n, 현재 횟수, 확률)
print(p_sum)