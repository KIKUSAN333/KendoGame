
$ErrorActionPreference = 'Stop'
if ([Console]::IsInputRedirected) { throw 'Run in an interactive Windows Terminal or PowerShell console.' }
# 起動前の設定を保存
Push-Location $PSScriptRoot
$game = $null
$oldControlC = [Console]::TreatControlCAsInput
$oldOutputEncoding = [Console]::OutputEncoding
$esc = [char]27
try {
    # Java ソースをコンパイル
    $java = (Get-Command java -ErrorAction Stop).Source
    $javac = (Get-Command javac -ErrorAction Stop).Source
    New-Item -ItemType Directory -Force -Path bin | Out-Null
    $sources = @(Get-ChildItem -LiteralPath src -Filter '*.java' | ForEach-Object FullName)
    & $javac -encoding UTF-8 -d bin $sources
    if ($LASTEXITCODE -ne 0) { throw 'Java compilation failed.' }

    # Java を起動し、標準入力へ接続
    $info = New-Object System.Diagnostics.ProcessStartInfo
    $info.FileName = $java
    $info.Arguments = '-Dkendo.ansi=true -Dfile.encoding=UTF-8 -Dsun.stdout.encoding=UTF-8 -cp "bin;src" Model'
    $info.WorkingDirectory = $PSScriptRoot
    $info.UseShellExecute = $false
    $info.RedirectStandardInput = $true
    [Console]::TreatControlCAsInput = $true
    [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding($false)
    [Console]::Write("${esc}[?1049h${esc}[2J${esc}[H${esc}[?25l")
    $game = [System.Diagnostics.Process]::Start($info)
    $inputWriter = New-Object System.IO.StreamWriter($game.StandardInput.BaseStream, (New-Object System.Text.UTF8Encoding($false)))

    # キーを読み取り、改行付きで Java へ送信
    while (-not $game.HasExited) {
        if (-not [Console]::KeyAvailable) {
            Start-Sleep -Milliseconds 10
            continue
        }
        $key = [Console]::ReadKey($true)
        if ($key.Key -eq [ConsoleKey]::Escape -or
            ($key.Key -eq [ConsoleKey]::C -and
             ($key.Modifiers -band [ConsoleModifiers]::Control))) { break }
        if ($key.Key -eq [ConsoleKey]::Backspace) {
            $inputWriter.WriteLine([string][char]127)
        } elseif (-not [char]::IsControl($key.KeyChar)) {
            $inputWriter.WriteLine([string]$key.KeyChar)
        }
        $inputWriter.Flush()
    }
} finally {
    # ゲームを終了し、端末の設定を復元
    if ($null -ne $game) {
        if (-not $game.HasExited) {
            $game.StandardInput.Close()
            if (-not $game.WaitForExit(2000)) { $game.Kill() }
        }
        $game.Dispose()
    }
    [Console]::TreatControlCAsInput = $oldControlC
    [Console]::Write("${esc}[?25h${esc}[?1049l")
    [Console]::OutputEncoding = $oldOutputEncoding
    Pop-Location
}
