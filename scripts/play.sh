#!/usr/bin/env bash
set -euo pipefail

# プロジェクトへ移動してコンパイル
cd -- "$(dirname -- "${BASH_SOURCE[0]}")/.."
[[ -t 0 && -t 1 ]] || { echo 'Run in an interactive terminal.' >&2; exit 1; }
mkdir -p build
javac -encoding UTF-8 -d build src/*.java

# 終了時に端末の設定を復元
terminal_state=$(stty -g)
cleanup() {
    stty "$terminal_state"
    printf '\033[?25h\033[?1049l'
}
trap cleanup EXIT
trap 'exit 130' INT
trap 'exit 143' TERM
printf '\033[?1049h\033[2J\033[H\033[?25l'

# キー入力を Java へ送信
bash scripts/read_key.sh | java -Dkendo.ansi=true -Dfile.encoding=UTF-8 -cp 'build:resources' Model
