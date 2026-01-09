# 🚀 COMPLETE SETUP GUIDE - SCI Projects Archive
## Using Android Studio - Zero Room for Errors

---

## 📋 TABLE OF CONTENTS
1. [What You're Building](#what-youre-building)
2. [Prerequisites - Android Studio](#prerequisites)
3. [Step 1: Install Android Studio](#step-1-install-android-studio)
4. [Step 2: Extract Project Files](#step-2-extract-project-files)
5. [Step 3: Open Project in Android Studio](#step-3-open-project-in-android-studio)
6. [Step 4: Configure Project](#step-4-configure-project)
7. [Step 5: Run Backend](#step-5-run-backend)
8. [Step 6: Open Frontend](#step-6-open-frontend)
9. [Step 7: Test Complete System](#step-7-test-complete-system)
10. [Troubleshooting](#troubleshooting)

---

## 🎯 WHAT YOU'RE BUILDING

**SCI Projects Archive System**
- ✅ Modern dark theme with floating particles
- ✅ Student registration with automatic course detection
- ✅ Project submission and document upload
- ✅ Lecturer review system with marks
- ✅ Real-time validation (only 3rd & 5th year students)

**Technology Stack:**
- Backend: Java Spring Boot (Port 8080)
- Frontend: Single HTML file with animations
- Database: H2 (embedded - no separate install needed)

---

## ✅ PREREQUISITES

### Required Software:
1. **Android Studio** (Latest Version - Hedgehog or newer)
   - Includes Java JDK automatically
   - Includes Maven (via embedded terminal)

### System Requirements:
- Windows 10/11, macOS 10.14+, or Linux
- 8GB RAM minimum (16GB recommended)
- 4GB free disk space

---

## 📥 STEP 1: INSTALL ANDROID STUDIO

### For Windows:

1. **Download Android Studio:**
   - Go to: https://developer.android.com/studio
   - Click **"Download Android Studio"**
   - Save the installer file

2. **Run Installer:**
   ```
   ✓ Double-click: android-studio-xxxx-windows.exe
   ✓ Click "Next" through all screens
   ✓ Choose installation location (default is fine)
   ✓ Wait for installation (5-10 minutes)
   ```

3. **First Launch Setup:**
   ```
   ✓ Launch Android Studio
   ✓ Choose "Standard" installation
   ✓ Select your preferred theme (Light or Dark)
   ✓ Click "Finish" and wait for downloads
   ```

4. **Verify Installation:**
   ```
   ✓ Android Studio should open to welcome screen
   ✓ You should see "New Project", "Open", etc.
   ```

### For macOS:

1. **Download Android Studio:**
   - Go to: https://developer.android.com/studio
   - Click **"Download Android Studio"**
   - Download the .dmg file

2. **Install:**
   ```
   ✓ Open the .dmg file
   ✓ Drag Android Studio to Applications folder
   ✓ Open Applications → Android Studio
   ✓ Right-click and choose "Open" (first time only)
   ```

3. **First Launch Setup:**
   ```
   ✓ Choose "Standard" installation
   ✓ Wait for component downloads
   ✓ Click "Finish"
   ```

### For Linux:

1. **Download and Extract:**
   ```bash
   # Download from https://developer.android.com/studio
   cd ~/Downloads
   tar -xzf android-studio-*-linux.tar.gz
   sudo mv android-studio /opt/
   ```

2. **Launch:**
   ```bash
   cd /opt/android-studio/bin
   ./studio.sh
   ```

3. **Follow setup wizard** (Standard installation)

---

## 📂 STEP 2: EXTRACT PROJECT FILES

### 2.1 Locate Your Downloaded File

Find: `sci-projects-archive.zip`

**Typical locations:**
- Windows: `C:\Users\YourName\Downloads\`
- macOS: `/Users/YourName/Downloads/`
- Linux: `/home/yourname/Downloads/`

### 2.2 Extract the ZIP File

#### Windows:
```
1. Right-click: sci-projects-archive.zip
2. Choose: "Extract All..."
3. Extract to: C:\Projects\sci-projects-archive
4. Click: "Extract"
```

#### macOS:
```
1. Double-click: sci-projects-archive.zip
2. Folder auto-extracts to same location
3. MOVE to: /Users/YourName/Projects/sci-projects-archive
```

#### Linux:
```bash
cd ~/Downloads
unzip sci-projects-archive.zip
mv sci-projects-archive ~/Projects/
cd ~/Projects/sci-projects-archive
```

### 2.3 Verify Extracted Files

**Your folder should contain:**
```
sci-projects-archive/
├── src/                          ✓ Backend code
├── pom.xml                       ✓ Maven configuration
├── index.html                    ✓ Frontend (MODERN UI)
├── README.md                     ✓ Documentation
├── run.ps1                       ✓ Windows PowerShell script
├── run.bat                       ✓ Windows batch script
├── run.sh                        ✓ Linux/Mac script
└── SETUP_GUIDE.md               ✓ This guide
```

**⚠️ IMPORTANT:** If you see another `sci-projects-archive` folder inside, use the inner one!

---

## 🎮 STEP 3: OPEN PROJECT IN ANDROID STUDIO

### 3.1 Launch Android Studio

1. **Open Android Studio**
2. On welcome screen, click: **"Open"** (NOT "New Project")

### 3.2 Select Project Folder

**Windows:**
```
Navigate to: C:\Projects\sci-projects-archive
Click: "OK"
```

**macOS:**
```
Navigate to: /Users/YourName/Projects/sci-projects-archive
Click: "Open"
```

**Linux:**
```
Navigate to: /home/yourname/Projects/sci-projects-archive
Click: "OK"
```

### 3.3 Wait for Project to Load

**What happens:**
```
✓ Android Studio indexes files (1-3 minutes)
✓ Maven downloads dependencies (2-5 minutes)
✓ Bottom right shows progress bar
✓ Wait until it says "Indexing complete"
```

**⚠️ DO NOT INTERRUPT THIS PROCESS!**

### 3.4 Check Project Structure

On the left panel, you should see:
```
📁 sci-projects-archive
  📁 src
    📁 main
      📁 java
        📁 com.sci.archive     ← Backend code
      📁 resources             ← Configuration
  📄 pom.xml                   ← Maven file
  📄 index.html                ← Frontend
```

---

## ⚙️ STEP 4: CONFIGURE PROJECT

### 4.1 Check Java Version

1. **Open Terminal in Android Studio:**
   - Bottom of screen → Click **"Terminal"** tab
   - Or: View → Tool Windows → Terminal

2. **Check Java version:**
   ```bash
   java -version
   ```

3. **Expected output:**
   ```
   openjdk version "17.0.x" or higher
   ```

4. **If Java version is wrong:**
   - File → Project Structure
   - Under "Project SDK" → Choose Java 17 or higher
   - Click "OK"

### 4.2 Configure Maven (If Needed)

**Check Maven:**
```bash
mvn -version
```

**Expected output:**
```
Apache Maven 3.6.x or higher
Java version: 17.0.x
```

**If Maven not found:**

**Windows:**
1. Download from: https://maven.apache.org/download.cgi
2. Extract to: `C:\Program Files\Apache\maven`
3. Add to PATH:
   - Search Windows: "Environment Variables"
   - Under "System Variables" → Find "Path"
   - Click "Edit" → "New"
   - Add: `C:\Program Files\Apache\maven\bin`
   - Click "OK" on all windows
4. **RESTART Android Studio**

**macOS (using Homebrew):**
```bash
brew install maven
```

**Linux (Ubuntu/Debian):**
```bash
sudo apt update
sudo apt install maven
```

---

## 🚀 STEP 5: RUN BACKEND

### Method 1: Using Android Studio Terminal (RECOMMENDED)

1. **Open Terminal in Android Studio** (bottom of screen)

2. **Clean and Build:**
   ```bash
   mvn clean install
   ```
   
   **Wait for:** `BUILD SUCCESS` (2-5 minutes)

3. **Run the Application:**
   ```bash
   mvn spring-boot:run
   ```

4. **Wait for Success Message:**
   ```
   ✓ Started ProjectsArchiveApplication in X.XXX seconds
   ✓ Tomcat started on port(s): 8080
   ```

5. **✅ Backend is NOW RUNNING!**
   - **DO NOT CLOSE THIS TERMINAL**
   - Keep it running in background

### Method 2: Using Run Scripts

#### Windows PowerShell:
```powershell
cd C:\Projects\sci-projects-archive
.\run.ps1
```

#### Windows Command Prompt:
```cmd
cd C:\Projects\sci-projects-archive
run.bat
```

#### macOS/Linux:
```bash
cd ~/Projects/sci-projects-archive
chmod +x run.sh
./run.sh
```

### 5.1 Verify Backend is Running

**Open browser and go to:**
```
http://localhost:8080
```

**You should see:**
```json
{"timestamp":"...","status":404,"error":"Not Found",...}
```

**This is CORRECT!** (Backend API is running, just no page at root)

---

## 🌐 STEP 6: OPEN FRONTEND

### 6.1 Locate Frontend File

**Navigate to:**
```
C:\Projects\sci-projects-archive\index.html          (Windows)
/Users/YourName/Projects/sci-projects-archive/index.html   (macOS)
~/Projects/sci-projects-archive/index.html           (Linux)
```

### 6.2 Open in Browser

#### Option A: Double-Click
```
1. Find: index.html in File Explorer/Finder
2. Double-click to open
3. Opens in default browser
```

#### Option B: Right-Click
```
1. Right-click: index.html
2. Choose: "Open with" → Chrome/Firefox/Edge
```

#### Option C: Drag & Drop
```
1. Open Chrome/Firefox/Edge
2. Drag index.html into browser window
```

### 6.3 Verify Frontend Loaded

**You should see:**
- 🎨 **Dark theme** with blue/purple gradients
- ✨ **Floating particles** animation in background
- 🎓 Logo: "SCI Projects Archive"
- 📋 Login and Sign Up tabs

**⚠️ If you see a blank page:**
- Press F12 → Check Console for errors
- Make sure backend is running (Step 5)

---

## 🧪 STEP 7: TEST COMPLETE SYSTEM

### 7.1 Test Student Registration

1. **Click "Sign Up" tab**

2. **Fill in form:**
   ```
   Full Name:    John Doe
   Username:     john.doe
   Email:        john.doe@student.sci.edu
   Password:     password123
   Role:         Student
   ```

3. **Enter Registration Number:**
   ```
   Reg No: SCI/001/2023
   ```

4. **Watch Auto-Detection:**
   ```
   ✓ Computer Science (CS)
   ✓ 3rd Year (Admitted 2023)
   ✓ Current year: 2026
   ```

5. **Click "SIGN UP"**

6. **Expected result:**
   ```
   ✓ Success message: "Account created successfully! Please login."
   ✓ Auto-switches to Login tab
   ```

### 7.2 Test First Year Block

1. **Try registration with:**
   ```
   Reg No: SCI/001/2026
   ```

2. **Expected result:**
   ```
   ❌ First year students (2026 admission) are NOT allowed to register!
   ```

### 7.3 Test Login

1. **Click "Login" tab**

2. **Enter credentials:**
   ```
   Username: john.doe
   Password: password123
   ```

3. **Click "LOGIN"**

4. **Expected result:**
   ```
   ✓ Redirects to dashboard
   ✓ See "My Projects Dashboard"
   ✓ Button: "➕ Create New Project"
   ```

### 7.4 Test Create Project

1. **Click "➕ Create New Project"**

2. **Fill form:**
   ```
   Project Title:     E-Commerce Platform
   Description:       Online shopping system with payment integration
   Course:           Software Engineering
   Academic Year:    2025/2026
   Semester:         Semester 1
   ```

3. **Click "CREATE PROJECT"**

4. **Expected result:**
   ```
   ✓ Success message
   ✓ Project appears in list
   ✓ Status badge shows "SUBMITTED"
   ```

### 7.5 Test Document Upload

1. **Click "UPLOAD DOCUMENT" on your project**

2. **Select:**
   ```
   Document Type: Final Report
   Description: Complete project documentation
   File: [Choose any PDF/DOCX file]
   ```

3. **Click "UPLOAD DOCUMENT"**

4. **Expected result:**
   ```
   ✓ Success message
   ✓ Document appears in project details
   ```

### 7.6 Test Lecturer Features

1. **Logout** (top right)

2. **Sign up as lecturer:**
   ```
   Full Name:    Dr. Jane Smith
   Username:     jane.smith
   Email:        jane.smith@sci.edu
   Password:     lecturer123
   Role:         Lecturer
   Staff ID:     STAFF/001
   ```

3. **Login**

4. **Expected result:**
   ```
   ✓ See "Student Projects" dashboard
   ✓ See student project with year badge
   ✓ Buttons: "View Details" and "Review Project"
   ```

5. **Click "REVIEW PROJECT"**

6. **Submit review:**
   ```
   Status:    Approved
   Marks:     85
   Comments:  Excellent work. Well documented.
   ```

7. **Expected result:**
   ```
   ✓ Project updated
   ✓ Marks appear on project card
   ```

---

## ✅ VERIFICATION CHECKLIST

After completing all steps, verify:

**Backend:**
- [ ] Terminal shows "Started ProjectsArchiveApplication"
- [ ] http://localhost:8080 shows JSON response
- [ ] No error messages in terminal

**Frontend:**
- [ ] Dark theme with floating particles visible
- [ ] Login/Signup forms work
- [ ] Registration number auto-detects course
- [ ] First year students (2026) are blocked

**Features:**
- [ ] Student can create projects
- [ ] Student can upload documents
- [ ] Lecturer can view projects
- [ ] Lecturer can review and mark projects
- [ ] Year badges show correctly (3rd/5th)

---

## 🐛 TROUBLESHOOTING

### Problem: "mvn: command not found"

**Solution:**
```
1. Install Maven (see Step 4.2)
2. Restart Android Studio Terminal
3. Try: mvn -version
```

### Problem: "Port 8080 is already in use"

**Solution:**

**Find what's using port 8080:**

**Windows:**
```cmd
netstat -ano | findstr :8080
taskkill /PID [PID_NUMBER] /F
```

**macOS/Linux:**
```bash
lsof -ti:8080 | xargs kill -9
```

**Or change port:**
```
1. Edit: src/main/resources/application.properties
2. Change: server.port=8080
3. To: server.port=8081
4. In index.html, change: 
   const API_BASE_URL = 'http://localhost:8081/api';
5. Restart backend
```

### Problem: Frontend not connecting to backend

**Check these:**
```
1. Backend running? Check terminal
2. Correct URL? Should be http://localhost:8080
3. CORS error? Backend should allow all origins
4. Browser console (F12) → Check error messages
```

**Solution:**
```
1. Stop backend (Ctrl+C in terminal)
2. Restart: mvn spring-boot:run
3. Refresh browser (F5)
```

### Problem: "BUILD FAILURE" when running mvn

**Solution:**
```
1. Check Java version: java -version (must be 17+)
2. Delete old builds:
   Windows: rmdir /s target
   Mac/Linux: rm -rf target
3. Try again: mvn clean install
4. Check pom.xml file exists
```

### Problem: Floating particles not showing

**Solution:**
```
1. Try different browser (Chrome recommended)
2. Clear browser cache (Ctrl+Shift+Delete)
3. Hard refresh (Ctrl+F5)
4. Check browser console (F12) for errors
```

### Problem: Can't register (validation errors)

**Check registration number format:**
```
✓ Correct:   SCI/001/2023
✗ Wrong:     SCI-001-2023
✗ Wrong:     sci/001/2023
✗ Wrong:     SCI/1/2023
```

**Year rules:**
```
✓ 2023 = 3rd year (ALLOWED)
✓ 2021 = 5th year (ALLOWED)
✗ 2026 = 1st year (BLOCKED)
✗ 2024 = 2nd year (BLOCKED)
```

### Problem: Database errors

**Solution:**
```
1. Stop backend (Ctrl+C)
2. Delete database files:
   Windows: del /s data\*
   Mac/Linux: rm -rf data/
3. Restart backend: mvn spring-boot:run
4. Database will recreate automatically
```

---

## 🎯 COMMON ERRORS & SOLUTIONS

### Error 1: "java.lang.ClassNotFoundException"

**Cause:** Dependencies not downloaded

**Solution:**
```bash
mvn clean install -U
```

### Error 2: "Address already in use"

**Cause:** Port 8080 occupied

**Solution:** Kill process or change port (see Troubleshooting)

### Error 3: "Failed to execute goal"

**Cause:** Incorrect Java version

**Solution:**
```
1. Android Studio → File → Project Structure
2. Set Project SDK to Java 17+
3. Rebuild: mvn clean install
```

### Error 4: "Cannot find symbol" compilation error

**Cause:** Lombok not processing

**Solution:**
```
1. File → Settings → Plugins
2. Search: "Lombok"
3. Install if not present
4. Restart Android Studio
5. Rebuild project
```

### Error 5: Frontend blank page

**Cause:** JavaScript error or wrong path

**Solution:**
```
1. Open browser console (F12)
2. Check for errors
3. Verify backend running: http://localhost:8080
4. Check index.html API_BASE_URL matches backend port
```

---

## 📞 NEED MORE HELP?

### Check These Files:
1. `README.md` - General overview
2. `API_DOCUMENTATION.md` - API endpoints
3. `FRONTEND_GUIDE.md` - Frontend features
4. `DEPLOYMENT_GUIDE.md` - Production deployment

### Debug Mode:

**Enable detailed logging:**
```
1. Edit: src/main/resources/application.properties
2. Add: logging.level.com.sci.archive=DEBUG
3. Restart backend
4. Check terminal for detailed logs
```

---

## 🎉 SUCCESS!

### If everything works:

**You should be able to:**
✅ Register students (only 3rd and 5th year)
✅ Block first year students (2026 admission)
✅ Create projects with modern UI
✅ Upload documents (PDF, DOCX, etc.)
✅ Review projects as lecturer
✅ See floating particles animation
✅ Use dark theme interface

### Next Steps:

1. **Explore Features:**
   - Create multiple projects
   - Upload different document types
   - Test lecturer review system
   - Try different registration numbers

2. **Customize:**
   - Change colors in index.html (CSS variables)
   - Add more course codes
   - Modify year restrictions

3. **Deploy:**
   - See DEPLOYMENT_GUIDE.md
   - Deploy to cloud (AWS, Heroku, Azure)
   - Use MySQL for production

---

## 📝 QUICK REFERENCE

### Start Backend:
```bash
cd sci-projects-archive
mvn spring-boot:run
```

### Open Frontend:
```
Double-click: index.html
Or browse to: file:///path/to/index.html
```

### Stop Backend:
```
Press Ctrl+C in terminal
```

### Restart Everything:
```bash
# Stop backend (Ctrl+C)
# Then:
mvn clean install
mvn spring-boot:run
# Refresh browser (F5)
```

### Test URLs:
```
Backend API:  http://localhost:8080
Frontend:     file:///path/to/index.html
Database UI:  http://localhost:8080/h2-console
```

---

## 🔐 DEFAULT CREDENTIALS (For Testing)

**Test Student:**
```
Username: john.doe
Password: password123
Reg No:   SCI/001/2023
```

**Test Lecturer:**
```
Username: jane.smith
Password: lecturer123
Staff ID: STAFF/001
```

---

**🎓 School of Computing and Informatics**  
**Version:** 1.0.0  
**Last Updated:** January 2026

**Ready to Build! 🚀**
