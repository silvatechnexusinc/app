# Windows Setup Guide - SCI Projects Archive

## ⚠️ Prerequisites Installation

You need to install **Java** and **Maven** before running the application.

### Step 1: Install Java 17 or Higher

#### Option A: Download from Oracle
1. Go to: https://www.oracle.com/java/technologies/downloads/
2. Download **Java 17** or higher (Windows x64 Installer)
3. Run the installer
4. Follow the installation wizard (use default settings)

#### Option B: Install via Chocolatey (if you have it)
```powershell
choco install openjdk17
```

#### Verify Java Installation
Open **PowerShell** or **Command Prompt** and run:
```powershell
java -version
```

You should see something like:
```
java version "17.0.x" or higher
```

---

### Step 2: Install Apache Maven

#### Manual Installation

1. **Download Maven:**
   - Go to: https://maven.apache.org/download.cgi
   - Download `apache-maven-3.9.x-bin.zip` (Binary zip archive)

2. **Extract Maven:**
   - Extract to: `C:\Program Files\Apache\maven`
   - Or any location you prefer

3. **Add Maven to PATH:**
   - Right-click **This PC** → **Properties**
   - Click **Advanced system settings**
   - Click **Environment Variables**
   - Under **System Variables**, find **Path** and click **Edit**
   - Click **New** and add: `C:\Program Files\Apache\maven\bin`
   - Click **OK** on all dialogs

4. **Verify Maven Installation:**
   - **Close and reopen** PowerShell/Command Prompt
   - Run:
   ```powershell
   mvn -version
   ```
   
   You should see:
   ```
   Apache Maven 3.9.x
   ```

---

## 🚀 Running the Application

### Option 1: Using PowerShell (Recommended)

1. **Navigate to project folder:**
```powershell
cd C:\Users\hp\Downloads\files (1)\sci-projects-archive\sci-projects-archive
```

2. **Run the PowerShell script:**
```powershell
.\run.ps1
```

If you get an execution policy error, run this first:
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

---

### Option 2: Using Command Prompt (CMD)

1. **Open Command Prompt** (not PowerShell)
   - Press `Win + R`
   - Type: `cmd`
   - Press Enter

2. **Navigate to project:**
```cmd
cd C:\Users\hp\Downloads\files (1)\sci-projects-archive\sci-projects-archive
```

3. **Run the batch file:**
```cmd
run.bat
```

---

### Option 3: Manual Maven Commands

Open PowerShell or CMD and run:

```powershell
# Navigate to project
cd C:\Users\hp\Downloads\files (1)\sci-projects-archive\sci-projects-archive

# Build the project
mvn clean install

# Run the application
mvn spring-boot:run
```

---

## 📱 Accessing the Application

Once running, access:
- **API Base URL:** http://localhost:8080/api
- **H2 Database Console:** http://localhost:8080/h2-console

### H2 Console Login:
- **JDBC URL:** `jdbc:h2:file:./data/scidb`
- **Username:** `sa`
- **Password:** `password`

---

## 🧪 Testing the Application

### Using PowerShell:

#### 1. Register a Student
```powershell
$body = @{
    username = "student1"
    email = "student1@sci.edu"
    password = "password123"
    fullName = "John Student"
    role = "STUDENT"
    studentId = "SCI2024001"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/auth/signup" -Method Post -Body $body -ContentType "application/json"
```

#### 2. Login
```powershell
$loginBody = @{
    username = "student1"
    password = "password123"
} | ConvertTo-Json

$response = Invoke-RestMethod -Uri "http://localhost:8080/api/auth/signin" -Method Post -Body $loginBody -ContentType "application/json"
$token = $response.token
Write-Host "Token: $token"
```

#### 3. Create a Project
```powershell
$headers = @{
    "Authorization" = "Bearer $token"
    "Content-Type" = "application/json"
}

$projectBody = @{
    title = "My First Project"
    description = "Test project"
    course = "Software Engineering"
    academicYear = "2023/2024"
    semester = "Semester 1"
} | ConvertTo-Json

Invoke-RestMethod -Uri "http://localhost:8080/api/projects" -Method Post -Body $projectBody -Headers $headers
```

---

## 🛠 Using Postman (Recommended for Testing)

1. **Download Postman:** https://www.postman.com/downloads/
2. **Import the API endpoints** (see API_DOCUMENTATION.md)
3. **Test all endpoints** easily with a GUI

---

## ❗ Troubleshooting

### Problem: "mvn is not recognized"
**Solution:** Maven is not installed or not in PATH
- Follow Step 2 above to install Maven
- Make sure to restart PowerShell/CMD after adding to PATH

### Problem: "Java version 24 found, but application requires 17+"
**Solution:** You have Java 24, which is fine! The application will work.

### Problem: PowerShell script won't run
**Solution:** Execution policy restriction
```powershell
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
```

### Problem: Port 8080 already in use
**Solution:** Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Problem: Build fails with errors
**Solutions:**
- Make sure you're in the correct directory
- Check that `pom.xml` exists in current folder
- Ensure you have internet connection (Maven downloads dependencies)
- Try: `mvn clean install -U` (force update)

---

## 📂 Project Structure Verification

Make sure you're in the correct folder. You should see:
```
sci-projects-archive/
├── pom.xml              ← Maven configuration
├── run.ps1              ← PowerShell script
├── run.bat              ← Batch script
├── run.sh               ← Linux/Mac script
├── src/                 ← Source code
│   ├── main/
│   └── test/
├── README.md
└── other docs...
```

Verify with:
```powershell
dir
```

---

## 🎯 Quick Commands Reference

### Build only:
```powershell
mvn clean install
```

### Run without building:
```powershell
mvn spring-boot:run
```

### Clean build directory:
```powershell
mvn clean
```

### Skip tests:
```powershell
mvn clean install -DskipTests
```

### Package as JAR:
```powershell
mvn clean package
```

Then run:
```powershell
java -jar target/projects-archive-1.0.0.jar
```

---

## 📚 Next Steps

1. ✅ Install Java and Maven
2. ✅ Run the application
3. ✅ Test with Postman or PowerShell
4. 📖 Read API_DOCUMENTATION.md for all endpoints
5. 📖 Read QUICKSTART.md for usage examples

---

## 💡 Tips

- **Use Postman** for easier API testing
- Keep **PowerShell/CMD open** while app is running
- Check **H2 Console** to see database data
- **Logs** show what's happening in the application
- Press **Ctrl+C** to stop the application

---

## 🆘 Still Having Issues?

1. Make sure Java is installed: `java -version`
2. Make sure Maven is installed: `mvn -version`
3. Make sure you're in the project directory (where pom.xml is)
4. Try running manually: `mvn clean install` then `mvn spring-boot:run`
5. Check if port 8080 is available

---

**Good luck! 🚀**
