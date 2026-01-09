# SCI Projects Archive - PowerShell Build and Run Script

Write-Host "======================================" -ForegroundColor Cyan
Write-Host "SCI Projects Archive" -ForegroundColor Green
Write-Host "School of Computing and Informatics" -ForegroundColor Green
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Check Java installation
try {
    $javaVersion = java -version 2>&1 | Select-String "version"
    Write-Host "✓ Java installed:" -ForegroundColor Green
    Write-Host "  $javaVersion" -ForegroundColor Gray
} catch {
    Write-Host "✗ ERROR: Java is not installed!" -ForegroundColor Red
    Write-Host "  Please install Java 17 or higher from:" -ForegroundColor Yellow
    Write-Host "  https://www.oracle.com/java/technologies/downloads/" -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""

# Check Maven installation
try {
    $mavenVersion = mvn -version 2>&1 | Select-String "Apache Maven"
    Write-Host "✓ Maven installed:" -ForegroundColor Green
    Write-Host "  $mavenVersion" -ForegroundColor Gray
} catch {
    Write-Host "✗ ERROR: Maven is not installed!" -ForegroundColor Red
    Write-Host ""
    Write-Host "Maven is required to build this application." -ForegroundColor Yellow
    Write-Host "Please install Maven from: https://maven.apache.org/download.cgi" -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Quick Installation Steps:" -ForegroundColor Cyan
    Write-Host "1. Download Maven binary zip archive" -ForegroundColor White
    Write-Host "2. Extract to C:\Program Files\Apache\maven" -ForegroundColor White
    Write-Host "3. Add C:\Program Files\Apache\maven\bin to PATH" -ForegroundColor White
    Write-Host "4. Restart PowerShell and try again" -ForegroundColor White
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}

Write-Host ""
Write-Host "======================================" -ForegroundColor Cyan
Write-Host "Building application..." -ForegroundColor Yellow
Write-Host "======================================" -ForegroundColor Cyan
Write-Host ""

# Clean and build
mvn clean install

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "======================================" -ForegroundColor Cyan
    Write-Host "Build successful!" -ForegroundColor Green
    Write-Host "======================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Starting application..." -ForegroundColor Yellow
    Write-Host ""
    Write-Host "Access points:" -ForegroundColor Cyan
    Write-Host "  - API Base URL: " -NoNewline -ForegroundColor White
    Write-Host "http://localhost:8080/api" -ForegroundColor Green
    Write-Host "  - H2 Console: " -NoNewline -ForegroundColor White
    Write-Host "http://localhost:8080/h2-console" -ForegroundColor Green
    Write-Host ""
    Write-Host "Press Ctrl+C to stop the application" -ForegroundColor Yellow
    Write-Host ""
    
    # Run the application
    mvn spring-boot:run
} else {
    Write-Host ""
    Write-Host "======================================" -ForegroundColor Cyan
    Write-Host "Build failed!" -ForegroundColor Red
    Write-Host "======================================" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Please check the error messages above." -ForegroundColor Yellow
    Write-Host ""
    Read-Host "Press Enter to exit"
    exit 1
}
