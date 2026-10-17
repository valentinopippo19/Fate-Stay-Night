
@echo off
cd /d "%~dp0"

if not exist "%~dp0launcher\run.ps1" (
    echo ERROR: No se encuentra launcher\run.ps1
    pause
    exit /b 1
)

powershell -NoProfile -ExecutionPolicy Bypass -File "%~dp0launcher\run.ps1"

if errorlevel 1 (
    echo.
    echo El programa termino con un error.
    pause
)
