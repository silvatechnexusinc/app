# Frontend Guide - SCI Projects Archive

## 📱 Complete Single-Page Application

The frontend is a **complete, fully-functional single-page application** with all HTML, CSS, and JavaScript in ONE file: `index.html`

## 🚀 How to Use

### Step 1: Start the Backend

First, make sure the backend API is running:

**Windows (PowerShell):**
```powershell
cd sci-projects-archive
.\run.ps1
```

**Windows (Command Prompt):**
```cmd
cd sci-projects-archive
run.bat
```

**Linux/Mac:**
```bash
cd sci-projects-archive
./run.sh
```

The backend will start at: `http://localhost:8080`

---

### Step 2: Open the Frontend

Simply **double-click** the `index.html` file, or:

**Option A: Double-click**
- Navigate to `sci-projects-archive` folder
- Double-click `index.html`
- It will open in your default browser

**Option B: Serve with Python (Optional)**
```bash
# In the sci-projects-archive directory
python -m http.server 3000
```
Then open: `http://localhost:3000`

**Option C: Serve with Node.js (Optional)**
```bash
npx http-server -p 3000
```
Then open: `http://localhost:3000`

---

## 🎨 Features Included

### ✅ Authentication
- **Login** - For existing users
- **Sign Up** - For new students and lecturers
- **Role Selection** - Student or Lecturer
- **Auto Login** - Remembers your session

### ✅ Student Features
- **Create Projects** - Add new project submissions
- **Upload Documents** - Multiple document types supported:
  - Product Requirements Document
  - Software Design Document
  - User Manual
  - Technical Documentation
  - Test Plans
  - Source Code
  - Final Reports
  - Presentations
  - Other documents
- **View Projects** - See all your projects
- **Track Status** - Monitor submission status
- **View Marks** - See lecturer feedback and grades
- **Download Documents** - Access uploaded files

### ✅ Lecturer Features
- **View All Projects** - See student submissions
- **Review Projects** - Approve/reject submissions
- **Add Comments** - Provide feedback
- **Assign Marks** - Grade projects (0-100)
- **Download Documents** - Access student files
- **Change Status** - Update project status

### ✅ Professional UI/UX
- **Responsive Design** - Works on all devices
- **Modern Interface** - Clean, professional look
- **Real-time Updates** - Instant feedback
- **Status Badges** - Visual status indicators
- **Modal Dialogs** - Smooth interactions
- **File Upload** - Drag and drop support
- **Loading Indicators** - Visual feedback

---

## 📖 User Guide

### For Students

#### 1. **Sign Up**
1. Click "Sign Up" tab
2. Enter your details:
   - Full Name
   - Username
   - Email
   - Password
   - Role: Select "Student"
   - Student ID (e.g., SCI/2024/001)
3. Click "Sign Up"

#### 2. **Login**
1. Enter your username and password
2. Click "Login"

#### 3. **Create a Project**
1. Click "Create New Project" button
2. Fill in project details:
   - Title (required)
   - Description
   - Course
   - Academic Year (e.g., 2023/2024)
   - Semester (e.g., Semester 1)
   - Supervisor (optional)
3. Click "Create Project"

#### 4. **Upload Documents**
1. Find your project in the list
2. Click "Upload Document"
3. Select document type
4. Add description (optional)
5. Click to browse or drag file
6. Click "Upload Document"

#### 5. **View Project Status**
- Projects show status badges:
  - 🔵 **Submitted** - Initial submission
  - 🟡 **Under Review** - Being evaluated
  - 🟢 **Approved** - Accepted
  - 🔴 **Rejected** - Not accepted
  - 🟠 **Needs Revision** - Requires changes

#### 6. **View Details**
1. Click "View Details" on any project
2. See:
   - Project information
   - All uploaded documents
   - Lecturer comments
   - Marks received
3. Download any document

---

### For Lecturers

#### 1. **Sign Up**
1. Click "Sign Up" tab
2. Enter your details:
   - Full Name
   - Username
   - Email
   - Password
   - Role: Select "Lecturer"
   - Staff ID (e.g., STAFF/001)
3. Click "Sign Up"

#### 2. **Login**
1. Enter your username and password
2. Click "Login"

#### 3. **View Student Projects**
- All submitted projects appear automatically
- See student names and project details
- View submission dates

#### 4. **Review a Project**
1. Click "View Details" to see documents
2. Download and review documents
3. Click "Review Project"
4. Select status:
   - Under Review
   - Approved
   - Rejected
   - Needs Revision
5. Enter marks (0-100)
6. Add comments
7. Click "Submit Review"

#### 5. **Download Documents**
1. Click "View Details" on any project
2. Click "Download" next to any document
3. File downloads automatically

---

## 🎯 Quick Actions

### Student Quick Actions
```
Login → Create Project → Upload Documents → View Status
```

### Lecturer Quick Actions
```
Login → View Projects → Review → Approve/Reject
```

---

## 🔧 Customization

### Change API URL

If your backend is on a different port or server, edit `index.html`:

Find this line (near the top of the `<script>` section):
```javascript
const API_BASE_URL = 'http://localhost:8080/api';
```

Change to your backend URL:
```javascript
const API_BASE_URL = 'http://your-server:port/api';
```

### Change Colors

Edit the CSS `:root` variables in `index.html`:
```css
:root {
    --primary-color: #2563eb;  /* Change main color */
    --secondary-color: #10b981; /* Change secondary color */
    /* ... more colors */
}
```

---

## 🐛 Troubleshooting

### Problem: Can't login
**Solution:**
- Make sure backend is running (`http://localhost:8080`)
- Check username and password
- Try signing up first if new user

### Problem: "Network error" or "Failed to fetch"
**Solution:**
- Backend not running - start it first
- Check if port 8080 is correct
- Look for CORS errors in browser console (F12)

### Problem: File upload fails
**Solution:**
- File might be too large (max 50MB)
- Make sure backend is running
- Check file type is supported

### Problem: Documents won't download
**Solution:**
- Backend must be running
- Check browser's download settings
- Look for popup blocker

### Problem: Page looks broken
**Solution:**
- Refresh the page (F5)
- Clear browser cache (Ctrl+Shift+Delete)
- Try a different browser

---

## 💡 Tips

1. **Keep Backend Running** - Frontend needs backend API
2. **Use Chrome/Firefox** - Best browser support
3. **Check Console** - Press F12 for error details
4. **Test with Sample Data** - Create test projects first
5. **Bookmark the Page** - Easy access

---

## 📱 Browser Support

✅ **Supported:**
- Google Chrome (Recommended)
- Mozilla Firefox
- Microsoft Edge
- Safari
- Opera

⚠️ **Limited Support:**
- Internet Explorer (not recommended)

---

## 🔐 Security Notes

- Passwords are never stored in frontend
- JWT tokens stored in localStorage
- Auto-logout on token expiration
- All API calls authenticated

---

## 📊 Data Flow

```
Frontend (index.html)
    ↓
    API Calls
    ↓
Backend (localhost:8080)
    ↓
    Database (H2/MySQL)
```

---

## 🎓 Educational Context

This application demonstrates:
- ✅ Single-page application (SPA)
- ✅ REST API integration
- ✅ JWT authentication
- ✅ File upload/download
- ✅ Responsive design
- ✅ Modern JavaScript (ES6+)
- ✅ CSS variables and flexbox
- ✅ Modal dialogs
- ✅ Form validation
- ✅ Error handling

---

## 📚 Next Steps

1. ✅ Start the backend
2. ✅ Open index.html
3. ✅ Sign up as student
4. ✅ Create a project
5. ✅ Upload documents
6. ✅ Sign up as lecturer (new browser tab/incognito)
7. ✅ Review and approve

---

## 🆘 Need Help?

1. Check backend is running
2. Look at browser console (F12)
3. Check API_DOCUMENTATION.md
4. Verify all fields are filled correctly

---

**Ready to use! 🚀**

Just open `index.html` in your browser while the backend is running!
