$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $PSScriptRoot
Set-Location $projectRoot

$java = Join-Path $projectRoot 'runtime\bin\java.exe'
$jar = Join-Path $projectRoot 'dist\fate-algorithm-war.jar'

if (-not (Test-Path $java)) {
    Write-Host "ERROR: No se encontró Java portable: $java" -ForegroundColor Red
    exit 1
}

if (-not (Test-Path $jar)) {
    Write-Host "ERROR: No se encontró el juego: $jar" -ForegroundColor Red
    exit 1
}

Write-Host 'Iniciando FATE Algorithm War...' -ForegroundColor Cyan
Write-Host 'Abrí http://localhost:8080 en tu navegador.' -ForegroundColor Green

& $java -jar $jar

exit $LASTEXITCODE
