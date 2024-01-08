const input = require("fs").readFileSync("/dev/stdin").toString().trim();
const n = Number(input);

if (n == 1) {
  console.log(2);
  exit();
} else if (n == 2) {
  console.log(7);
  exit();
}

let dp = new Array(n + 1).fill(0n);
let dp2 = new Array(n + 1).fill(0n);

dp[0] = 1;
dp2[0] = 1;
dp[1] = 2;
dp2[1] = 3;
dp[2] = 7;
dp2[2] = 10;

for (i = 3; i <= n; i++) {
  dp[i] = (dp2[i - 1] * 2 + dp[i - 2]) % 1000000007;
  dp2[i] = (dp[i] + dp2[i - 1]) % 1000000007;
}
console.log(dp[n]);
