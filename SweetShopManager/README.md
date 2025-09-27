# Sweet Shop Management System 🍬

## 📋 Project Overview

A comprehensive full-stack web application for managing a sweet shop's inventory with modern authentication, role-based access control, and complete CRUD operations. This system allows customers to browse and purchase sweets while providing administrators with powerful inventory management tools.

### 🎯 Key Features

#### Backend (Spring Boot + MySQL + JWT)
- 🔐 **Secure Authentication**: JWT-based user authentication with registration and login
- 👥 **Role-Based Access Control**: Separate USER and ADMIN roles with different permissions
- 🍬 **Sweet Inventory Management**: Complete CRUD operations for sweets
- 📦 **Inventory Control**: Purchase and restock functionality with stock validation
- 🔍 **Advanced Search**: Search sweets by name, category, and price range
- 🌐 **RESTful API**: Well-structured REST endpoints following best practices

#### Frontend (React + Vite)
- ⚡ **Modern SPA**: Single-page application built with React and React Router
- 🎨 **Beautiful UI**: Clean, responsive design with intuitive user experience
- 🔐 **Authentication Interface**: User-friendly login and registration forms
- 📊 **Interactive Dashboard**: Display all sweets with powerful search and filtering
- 👑 **Admin Panel**: Comprehensive admin features for inventory management
- 🛒 **Shopping Experience**: Easy-to-use purchase system with quantity selection
- 📱 **Mobile Responsive**: Optimized for all device sizes

## 🛠️ Technology Stack

### Backend Architecture
- **Java**: 21 (Latest LTS)
- **Framework**: Spring Boot 3.3.4
- **Security**: Spring Security with JWT authentication
- **Database**: Spring Data JPA with MySQL 8.0
- **Build Tool**: Maven 3.6+
- **Authentication**: JSON Web Tokens (JWT)

### Frontend Architecture
- **React**: 18.2.0 (Latest stable)
- **Build Tool**: Vite (Ultra-fast development server)
- **Routing**: React Router DOM 6.22.3
- **HTTP Client**: Axios for API communication
- **Testing**: Vitest for unit testing
- **Styling**: Pure CSS3 with responsive design

## 📋 Prerequisites

Before setting up the project, ensure you have the following installed:

### Required Software
- **Java**: JDK 21 or higher ([Download](https://adoptium.net/))
- **Node.js**: Version 18+ with npm ([Download](https://nodejs.org/))
- **MySQL**: Version 8.0+ ([Download](https://dev.mysql.com/downloads/mysql/))
- **Git**: For cloning the repository ([Download](https://git-scm.com/))

### Optional (Recommended)
- **VS Code**: With Spring Boot and React extensions ([Download](https://code.visualstudio.com/))
- **Postman**: For API testing ([Download](https://www.postman.com/))

## 🚀 Quick Start Guide

### Step 1: Clone the Repository
```bash
git clone <repository-url>
cd SweetShopManager
```

### Step 2: Database Setup

#### Option A: Using MySQL Workbench/Command Line
1. **Install MySQL** and start the MySQL server
2. **Create Database**:
   ```sql
   CREATE DATABASE sweetshop;
   ```
3. **Update Credentials** in `sweet-shop-api/src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_mysql_username
   spring.datasource.password=your_mysql_password
   ```

#### Option B: Using Docker (Alternative)
```bash
# Run MySQL in Docker
docker run --name mysql-sweetshop -e MYSQL_ROOT_PASSWORD=password -e MYSQL_DATABASE=sweetshop -p 3306:3306 -d mysql:8.0

# Update application.properties with:
spring.datasource.username=root
spring.datasource.password=password
```

### Step 3: Backend Setup and Launch

#### Method A: Using VS Code (Recommended)
1. **Open VS Code** in the project root
2. **Navigate** to `sweet-shop-api` folder
3. **Open** `SweetShopApiApplication.java`
4. **Click** the ▶️ play button in the top-right corner
5. **Backend starts** on `http://localhost:8080`


### Step 4: Frontend Setup and Launch

#### Method A: Using VS Code
1. **Open** a new terminal in VS Code
2. **Navigate** to `sweet-shop-frontend` folder
3. **Run**: `npm install` (first time only)
4. **Run**: `npm run dev`
5. **Frontend starts** on `http://localhost:3000`

## 🎮 How to Use the Application

### First-Time Setup
1. **Open Browser**: Navigate to `http://localhost:3000`
2. **Register**: Click "Register" and create an admin account:
   - Username: `admin`
   - Password: `password123`
   - Role: `ADMIN`
3. **Login**: Use your credentials to log in

### User Roles & Permissions

#### 👤 Regular Users (USER Role)
- ✅ Browse all available sweets
- ✅ Search and filter sweets
- ✅ Purchase sweets (specify quantity)
- ✅ View personal account information

#### 👑 Administrators (ADMIN Role)
- ✅ All regular user permissions
- ✅ Add new sweets to inventory
- ✅ Edit existing sweet details
- ✅ Delete sweets from inventory
- ✅ Restock inventory quantities

### Common Workflows

#### 🛒 Purchasing Sweets (Users)
1. **Browse**: View all available sweets on the dashboard
2. **Search**: Use filters to find specific sweets by name, category, or price
3. **Select Quantity**: Choose how many items to purchase (1 to available stock)
4. **Purchase**: Click "Purchase X" button
5. **Confirmation**: Receive success message with updated inventory

#### 📦 Managing Inventory (Admins)
1. **Add Sweets**: Click "Add New Sweet" to create new inventory items
2. **Edit Sweets**: Click "Edit" on any sweet card to modify details
3. **Restock**: Use quantity input and "Restock" button to increase inventory
4. **Remove**: Click "Delete" to remove sweets (with confirmation prompt)

## 📡 API Documentation

### Authentication Endpoints
```http
POST /api/auth/register
Content-Type: application/json

{
  "username": "string",
  "password": "string",
  "role": "USER" | "ADMIN"
}
```

```http
POST /api/auth/login
Content-Type: application/json

{
  "username": "string",
  "password": "string"
}
```

### Protected Sweet Endpoints

#### Get All Sweets
```http
GET /api/sweets
Authorization: Bearer <jwt-token>
```

#### Search Sweets
```http
GET /api/sweets/search?name=chocolate&category=candy&minPrice=1.00&maxPrice=10.00
Authorization: Bearer <jwt-token>
```

#### Purchase Sweet
```http
POST /api/sweets/{id}/purchase?quantity=2
Authorization: Bearer <jwt-token>
```

#### Admin-Only Endpoints
```http
POST /api/sweets          # Add new sweet
PUT /api/sweets/{id}      # Update sweet
DELETE /api/sweets/{id}   # Delete sweet
POST /api/sweets/{id}/restock?quantity=10  # Restock sweet
```


## 📁 Project Structure

```
SweetShopManager/
├── sweet-shop-api/                    # Spring Boot Backend
│   ├── pom.xml                        # Maven configuration
│   ├── src/main/java/com/sweetshop/api/
│   │   ├── SweetShopApiApplication.java    # Main application class
│   │   ├── auth/                      # Authentication module
│   │   │   ├── controller/            # Auth endpoints
│   │   │   ├── dto/                   # Data transfer objects
│   │   │   └── service/               # JWT services
│   │   ├── config/                    # Security configuration
│   │   ├── sweet/                     # Sweet management module
│   │   │   ├── controller/            # Sweet REST endpoints
│   │   │   ├── model/                 # Sweet entity
│   │   │   ├── repository/            # Data access layer
│   │   │   └── service/               # Business logic
│   │   └── user/                      # User management
│   │       ├── model/                 # User entity
│   │       ├── repository/            # User data access
│   │       └── service/               # User services
│   └── src/main/resources/
│       └── application.properties     # Configuration
│
└── sweet-shop-frontend/               # React Frontend
    ├── package.json                   # NPM configuration
    ├── vite.config.js                # Vite configuration
    ├── index.html                    # HTML template
    ├── src/
    │   ├── main.jsx                  # Application entry point
    │   ├── App.jsx                   # Main React component
    │   ├── App.css                   # Global styles
    │   ├── components/               # Reusable components
    │   │   ├── Layout/               # Layout components (Header, etc.)
    │   │   ├── Auth/                 # Authentication forms
    │   │   └── Sweet/                # Sweet-related components
    │   ├── context/                  # React Context (AuthContext)
    │   ├── pages/                    # Page components
    │   └── services/                 # API services
    └── public/                       # Static assets
```

## 🔒 Security Features

- **JWT Authentication**: Secure token-based authentication
- **Password Encryption**: BCrypt hashing for password security
- **Role-Based Access**: USER and ADMIN roles with different permissions
- **CORS Protection**: Configured cross-origin resource sharing
- **Request Validation**: Input validation on both frontend and backend
- **SQL Injection Prevention**: Parameterized queries with JPA

## Screenshots :
Login:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_26_56" src="https://github.com/user-attachments/assets/352534e3-c992-420e-b0ad-c12ae4032ee7" />

Registration:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_27_06" src="https://github.com/user-attachments/assets/2cf9825f-5e90-4bea-ac30-946d664d2515" />

Admin Layout:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_27_48" src="https://github.com/user-attachments/assets/43bd4481-9700-466a-b22f-843739c7ca95" />

Search funtionality:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_11" src="https://github.com/user-attachments/assets/79c5a099-9b32-42b3-817d-970bd7ecf8b8" />

Max price limit functionality:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_35" src="https://github.com/user-attachments/assets/61c0ea7e-22a8-4ac2-ab09-7fa88619c2a6" />

#Purchase:
(Before)
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_47" src="https://github.com/user-attachments/assets/1f93117c-cf91-4a96-b913-1a0ff8c101b6" />
(After)
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_54" src="https://github.com/user-attachments/assets/ac362270-a536-4092-8fa4-b6dddc296660" />

#Restock:
(Before)
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_29_16" src="https://github.com/user-attachments/assets/b2fad371-e0bf-4e39-ae3e-b3a94ecdd0d8" />
(After)
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_29_24" src="https://github.com/user-attachments/assets/2d80d912-ceec-4c03-80d7-fd70fecc798b" />

#Adding a sweet (only admin)
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_02" src="https://github.com/user-attachments/assets/43d18c2b-4cd6-4185-a126-bc5b8fb7175a" />
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_12" src="https://github.com/user-attachments/assets/a9454cae-0031-4f12-bbf1-3ba9ef791ea7" />

#User layout:
<img width="1920" height="1020" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_33" src="https://github.com/user-attachments/assets/d4e145ec-9f41-4d40-8858-856c5c537ae9" />


## My AI Usage

1. I designed the project structure and used Gemini to enhance it and add some more modules like "JWT Module and Config" . 
2. I used Chatgpt to generate the full frontend code from scratch . Also , I knew my backend logic and required functionalities in the backend API. So , I instructed that logic to AI and got the relevant results. Though it lacked in some places , so I added the logic manually there . Most of work from my side was done in backend and full AI generated code in frontend .

