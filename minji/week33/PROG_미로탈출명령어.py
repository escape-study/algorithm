from collections import deque

dlru = {0: 'd', 1: 'l', 2: 'r', 3: 'u'}
di = ((1, 0), (0, -1), (0, 1), (-1, 0))


def solution(n, m, x, y, r, c, k):
    length = abs(x - r) + abs(y - c)
    if length > k or (length - k) % 2 != 0:
        return 'impossible'

    answer = ''
    queue = deque()
    queue.append((x, y, 0, ''))
    while queue:
        x, y, cnt, strng = queue.popleft()
        if x == r and y == c and cnt == k:
            return strng
        for i in range(4):
            nx, ny = x + di[i][0], y + di[i][1]
            if 0 < nx <= n and 0 < ny <= m and abs(nx - r) + abs(ny - c) + cnt + 1 <= k:
                queue.append((nx, ny, cnt + 1, strng + dlru[i]))
                break
    return answer