const fs = require("fs");
const filePath = process.platform === "linux" ? "/dev/stdin" : "./input.txt";
let input = fs.readFileSync(filePath).toString().trim().split("\n");

const n = Number(input.shift());
let arr = input.shift().trim().split(" ").map(Number);

arr.sort((a, b) => a - b); // 정렬

let ans = 1;
for (let i = 0; i < n; i++) {
  if (arr[i] > ans) {
    break;
  }
  ans += arr[i];
}

console.log(ans);
