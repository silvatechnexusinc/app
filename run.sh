#!/bin/bash

# SCI Projects Archive - Build and Run Script

echo "======================================"
echo "SCI Projects Archive"
echo "School of Computing and Informatics"
echo "======================================"
echo ""

# Check Java installation
if ! command -v java &> /dev/null; then
    echo "ERROR: Java is not installed!"
    echo "Please install Java 17 or higher"
    exit 1
fi

# Check Maven installation
if ! command -v mvn &> /dev/null; then
    echo "ERROR: Maven is not installed!"
    echo "Please install Maven 3.6 or higher"
    exit 1
fi

echo "✓ Java version:"
java -version 2>&1 | head -n 1

echo "✓ Maven version:"
mvn -version | head -n 1

echo ""
echo "======================================"
echo "Building application..."
echo "======================================"

# Clean and build
mvn clean install

if [ $? -eq 0 ]; then
    echo ""
    echo "======================================"
    echo "Build successful!"
    echo "======================================"
    echo ""
    echo "Starting application..."
    echo ""
    echo "Access points:"
    echo "  - API Base URL: http://localhost:8080/api"
    echo "  - H2 Console: http://localhost:8080/h2-console"
    echo ""
    echo "Press Ctrl+C to stop the application"
    echo ""
    
    # Run the application
    mvn spring-boot:run
else
    echo ""
    echo "======================================"
    echo "Build failed!"
    echo "======================================"
    exit 1
fi
