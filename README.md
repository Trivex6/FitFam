# 🏃 FitFam – Family Fitness Application

* **Department:** Computer Science and Business Systems (CSBS) 
* **Subject:** Object Oriented Programming (Java) 
* **College:** Institute of Engineering and Management (IEM), Salt Lake 
* **Academic Year:** 2025–2026 

---

## 📌 Project Overview
FitFam is a family-centered fitness tracking system designed to promote health awareness and strengthen family bonding through shared goals. The application allows multiple family members to create profiles, track daily activities like steps and calories, and participate in group challenges.

## 👥 Team Members (Loop Lords) 
* **Snehasish Nag** (04)
* **Manan Mitra** (13) 
* **Puskar Das** (17) 
* **Prodosh Bhattacharya** (18) 

## 🚀 Key Features 
* **User Management:** Support for multiple users within a single family.
* **Activity Tracking:** Daily monitoring of steps and calories burned. 
* **Challenge System:** Ability to set and participate in collective fitness goals.
* **Leaderboard:** Real-time ranking of users to encourage healthy competition.
* **User-Friendly GUI:** A simple and intuitive graphical interface.

## 💻 Technical Architecture (OOP Concepts) 
This project demonstrates the practical application of core Object-Oriented Programming principles:
* **Encapsulation:** Protects user data by keeping variables private and providing controlled access through methods.
* **Inheritance:** Reduces code duplication and enhances system flexibility for future expansion.
* **Polymorphism:** Allows different classes to implement methods dynamically, improving user experience.
* **Abstraction:** Hides unnecessary technical details to show only essential features to the user.

##🛠️ Technology Stack 
* **Language:** Java (JDK 17+) 
* **IDE:** Visual Studio Code 
* **Platform:** Platform Independent 

## ⚙️ How to Run
1. Open a terminal/command prompt in the project root folder.
2. **clean old build**
   ```bash
   rd /s /q bin && mkdir bin
3. **Compile the source code:**
   ```bash
   javac -d bin -sourcepath src src\com\fitfam\Main.java
4. **Launch the application:**
   ```bash
   java -cp bin com.fitfam.Main
        
## 📂 Project Structure
```text
FitFam/
├── src/
│   └── com/fitfam/
│       ├── Main.java              # Entry point for the application [cite: 62]
│       ├── model/                 # Data Models (Encapsulation)
│       │   ├── FamilyMember.java  # Stores individual fitness data [cite: 62]
│       │   └── Challenge.java     # Manages group challenge data [cite: 62]
│       ├── service/               # Backend Service (Business Logic)
│       │   └── FitnessService.java # Handles data processing and rankings [cite: 65]
│       └── gui/                   # Frontend (UI Components)
│           └── FitFamUI.java      # Main application window [cite: 74]
├── FitFam-ProjectDoc.docx         # Official Project Documentation [cite: 2]
└── README.md                      # Project Overview

