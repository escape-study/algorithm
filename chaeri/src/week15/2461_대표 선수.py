import sys 
import heapq
input = sys.stdin.readline

N, M = map(int, input().split())
arr = []
ind = [1] * 1001 
maxVal = 0
hq = []

for i in range(N):
    input_list = list(map(int, input().split()))
    input_list.sort()
    maxVal = max(maxVal, input_list[0])
    arr.append(input_list)
    heapq.heappush(hq, (arr[i][0], i)) #일단 그룹별 최솟값 heapq에 넣어두고 시작

res = 10**9

while hq:
    front = heapq.heappop(hq) #그룹최솟값, 그룹넘버
    minVal = front[0] 
    index = front[1]

    res = min(res, maxVal - minVal) #그룹별 최솟값중에 젤 큰애 - 현재 최솟값 => 차이가 가장 작은애를 찾음

    if ind[index] == M: #주어진 그룹 탐색 끝났을 때
        break

    heapq.heappush(hq, (arr[index][ind[index]], index)) # 현재 그룹의 최솟값 다음 숫자 넣어줌
    maxVal = max(maxVal, arr[index][ind[index]]) #다음 탐색을 위한 max 값 갱신

    ind[index] += 1

print(res)