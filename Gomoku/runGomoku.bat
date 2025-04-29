@echo off
echo Compiling...

javac -d target\classes ^
src\main\java\model\*.java ^
src\main\java\util\*.java ^
src\main\java\ai\*.java ^
src\main\java\save\*.java ^
src\main\java\app\*.java 

if %errorlevel% neq 0 (
    echo Compilation failed.
    pause
    exit /b
)

echo.
echo Running Gomoku...
echo.

java -cp target\classes app.Gomoku

pause
