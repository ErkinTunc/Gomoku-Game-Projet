@echo off
echo Generating Javadoc with private methods...

javadoc -d doc -private ^
  src\main\java\model\*.java ^
  src\main\java\util\*.java ^
  src\main\java\ai\*.java ^
  src\main\java\save\*.java ^
  src\main\java\app\*.java

if %errorlevel% neq 0 (
    echo Failed to generate Javadoc.
    pause
    exit /b
)

echo Javadoc generated in the 'doc' folder.
start doc\index.html

pause
