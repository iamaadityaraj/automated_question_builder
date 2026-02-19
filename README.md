# 🚀 Automated Question Builder Application

An AI-powered Android application that automatically generates question banks for assessments and examinations.  
The app allows users to generate customized questions based on subject, difficulty level, question type, and number of questions.

---

## 📌 Overview

The **Automated Question Builder Application** is designed to simplify the process of creating assessment questions for training and academic purposes.

It leverages Artificial Intelligence to generate:
- MCQs
- True/False questions
- Question & Answer format

The application integrates with OpenAI API for intelligent question generation and Firebase for storing generated responses.

---

## ✨ Key Features

- 🧠 AI-Powered Question Generation
- 🎯 Custom Question Type Selection (MCQ, True/False, Q/A)
- 📊 Difficulty Level Selection (Easy, Medium, Hard)
- 🔢 Custom Number of Questions
- ☁ Firebase Realtime Database Integration
- 💬 Chat-style Response UI
- 🔐 Secure API Key Handling
- 📱 Clean Android UI using RecyclerView & Adapters

---

## 🛠 Tech Stack

**Frontend (Android):**
- Java
- XML Layouts
- RecyclerView
- Android Studio

**Backend & API:**
- OpenAI Chat Completion API
- OkHttp Client

**Database:**
- Firebase Realtime Database

---

## 🔐 Secure API Integration

The application integrates with the OpenAI API securely.

API keys are:
- NOT hardcoded in source code
- Stored securely using `local.properties`
- Accessed via `BuildConfig` during runtime
- Ignored from Git using `.gitignore`

This ensures:
- No exposure of sensitive keys
- Safe repository sharing
- Compliance with security best practices

---

## 🔄 How It Works

1. User selects:
   - Question Type
   - Difficulty Level
   - Subject
   - Number of Questions
2. User enters additional instructions.
3. App sends request to OpenAI API.
4. AI generates structured questions.
5. Response is displayed in chat UI.
6. Generated questions are stored in Firebase.

---

## 📂 Project Structure

app/
├── views/
│ └── QuestionGenerationActivity.java
├── Adapter/
│ └── MessageAdapter.java
├── Model/
│ └── Message.java


---

## 🚀 Future Improvements

- PDF Export of Question Banks
- Admin Panel for Curriculum Upload
- Question Bank Categorization
- Offline Caching
- Authentication & User Profiles

---

## 📸 Screenshots

(Add screenshots of your app UI here)

---

## 🎓 Author

**Aaditya Raj**  
B.E. Computer Science & Engineering  
Passionate about Android Development, AI Integration & Problem Solving  

GitHub: https://github.com/iamaadityaraj  
LinkedIn: https://www.linkedin.com/in/aadityaraj2004/

---

## 📜 License

This project is for educational and demonstration purposes.
