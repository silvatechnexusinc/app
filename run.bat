@echo off
REM SCI Projects Archive - Build and Run Script for Windows

echo ======================================
echo SCI Projects Archive
echo School of Computing and Informatics
echo ======================================
echo.

REM Check Java installation
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Java is not installed!
    echo Please install Java 17 or higher
    pause
    exit /b 1
)

REM Check Maven installation
mvn -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: Maven is not installed!
    echo Please install Maven 3.6 or higher
    pause
    exit /b 1
)

echo Java version:
java -version 2>&1 | findstr /C:"version"

echo.
echo Maven version:
mvn -version | findstr /C:"Maven"

echo.
echo ======================================
echo Building application...
echo ======================================

REM Clean and build
call mvn clean install

if %errorlevel% equ 0 (
    echo.
    echo ======================================
    echo Build successful!
    echo ======================================
    echo.
    echo Starting application...
    echo.
    echo Access points:
    echo   - API Base URL: http://localhost:8080/api
    echo   - H2 Console: http://localhost:8080/h2-console
    echo.
    echo Press Ctrl+C to stop the application
    echo.
    
    REM Run the application
    call mvn spring-boot:run
) else (
    echo.
    echo ======================================
    echo Build failed!
    echo ======================================
    pause
    exit /b 1
)
