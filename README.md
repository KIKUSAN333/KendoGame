# 剣道 CLI ゲーム

JDK 17 以降（java と javac）が必要です。

## Windows で起動

PowerShell またはコマンドプロンプト（cmd）で、このディレクトリから実行します。
端末は横81桁以上・縦25行以上に広げてください。

```powershell
powershell .\scripts\play.ps1
```

「スクリプトの実行が無効」と表示される場合は、次を使ってください。

```powershell
powershell -NoProfile -ExecutionPolicy Bypass -File .\scripts\play.ps1
```

この指定は今回起動する PowerShell にだけ適用され、PC 全体の実行ポリシーは変更しません。

自動でコンパイルして起動します。Enter は不要です。Esc または Ctrl+C で終了します。
IDE の出力パネルや PowerShell ISE ではなく、入力できる端末で実行してください。

## 操作

| 場面 | キー | 操作 |
| --- | --- | --- |
| メニュー | W / S | 上 / 下 |
| メニュー | F | 決定 |
| レベル選択・ランキング | Q | 戻る |
| 対戦 | A / D | 左 / 右 |
| 対戦 | S | 防御 |
| 対戦 | J / K / L | 面 / 突き / 小手 |
| 2P・練習相手 | 1 / 3、2、7 / 8 / 9 | 移動、防御、攻撃 |
| 練習 | Q | メニューに戻る |
| 名前入力 | Space / Backspace / ! | 確定 / 1文字削除 / 全消去 |

英字操作は半角小文字で入力してください。