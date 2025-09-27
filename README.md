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
<img width="1920" height="869" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_26_56" src="https://github.com/user-attachments/assets/dfc1f042-d583-4606-abf3-5b33ce63918d" />


Registration:
<img width="1920" height="859" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_27_06" src="https://github.com/user-attachments/assets/b89525ad-28c4-4edd-a801-02f4c96cad5a" />


Admin Layout:
<img width="1920" height="869" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_27_48" src="https://github.com/user-attachments/assets/47bcfd10-2644-4205-8711-5c77204059a1" />


Search funtionality:
<img width="1920" height="849" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_11" src="https://github.com/user-attachments/assets/e8756f0f-4f0f-4bd7-9412-0040b4073d48" />


Max price limit functionality:
<img width="1920" height="859" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_35" src="https://github.com/user-attachments/assets/a807bdd8-c2e7-4889-96ce-2e6390134a2f" />


#Purchase:
(Before)
<img width="1920" height="852" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_47" src="https://github.com/user-attachments/assets/0a8f1dd5-41ec-4898-87a4-83ea63c4bc47" />

(After)
<img width="1920" height="863" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_28_54" src="https://github.com/user-attachments/assets/d94dfed5-d557-4d95-97ba-e715e633f595" />


#Restock:
(Before)
<img width="1920" height="866" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_29_16" src="https://github.com/user-attachments/assets/c359398e-5c77-43d3-bbaf-bd248cae2112" />

(After)
<img width="1920" height="859" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_29_24" src="https://github.com/user-attachments/assets/6007a48c-8b49-4bbb-ac07-c642c57c5854" />


#Adding a sweet (only admin)
<img width="1920" height="876" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_02" src="https://github.com/user-attachments/assets/fb52025b-7873-4036-b16d-53ea248e1a49" />
<img width="1920" height="836" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_12" src="https://github.com/user-attachments/assets/a3265ae4-fca3-4872-ad6f-1d64b43bf0ae" />


#User layout:
<img width="1920" height="845" alt="Sweet Shop Management System - Google Chrome 27-09-2025 14_30_33" src="https://github.com/user-attachments/assets/052bce1b-30f9-4cd6-bcee-c0eff33be648" />

## Tests:
For service test : 15 of 15 test cases passed:
<img width="1920" height="436" alt="Sweet Management System – SweetServiceTest java  sweet-shop-api  Administrator 27-09-2025 15_08_50" src="https://github.com/user-attachments/assets/0aca1fd7-c5d5-4126-989b-401221456c30" />

For model test : 2 of 2 test cases passed:
<img width="1920" height="245" alt="Sweet Management System – SweetServiceTest java  sweet-shop-api  Administrator 27-09-2025 15_09_37" src="https://github.com/user-attachments/assets/e02b84c5-84bc-494b-b25d-421cf62316ec" />

For repository test : 18 of 18 test cases passed:
<img width="1920" height="471" alt="Sweet Management System – SweetServiceTest java  sweet-shop-api  Administrator 27-09-2025 15_14_48" src="https://github.com/user-attachments/assets/874aa20c-baf3-4924-9014-4102a53f7646" />

## My AI Usage

### 1. **Gemini (Google AI)**
**Purpose**: Project structure design and enhancement
- **Initial Design**: Designed the overall project architecture and structure
- **Enhancement**: Added JWT authentication module and security configuration
- **Modules Added**: Authentication system, security configurations, user management structure

### 2. **ChatGPT (OpenAI)**
**Purpose**: Frontend development
- **Complete Frontend Generation**: Generated the entire frontend codebase from scratch
- **Technologies Used**: React, Vite, React Router, Axios, CSS3
- **Components Created**: All UI components, pages, forms, layouts, and styling

### Backend Development (AI-Leveraged Implementation)

I had comprehensive knowledge of backend architecture, business logic, and system requirements. Rather than writing code from scratch, I strategically leveraged AI as a development accelerator while maintaining full control over the design and implementation approach.

## Tests
Leveraged AI to generate unit test cases for backend . Like for model ,repo, service and controller layer . 
