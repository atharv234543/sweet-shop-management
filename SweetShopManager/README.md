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

## 🧪 Testing

### Backend Tests
```bash
cd sweet-shop-api
mvn test
```

### Frontend Tests
```bash
cd sweet-shop-frontend
npm run test
```

## 🔧 Troubleshooting

### Common Issues & Solutions

#### Backend Won't Start
- **Check Java Version**: Ensure Java 21+ is installed (`java -version`)
- **Check Maven**: Ensure Maven is installed (`mvn -version`)
- **Database Connection**: Verify MySQL is running and credentials are correct
- **Port Conflict**: Ensure port 8080 is available

#### Frontend Won't Start
- **Check Node Version**: Ensure Node.js 18+ is installed (`node -version`)
- **Dependencies**: Run `npm install` in the frontend directory
- **Port Conflict**: Ensure port 3000 is available

#### Database Connection Issues
- **MySQL Not Running**: Start MySQL service
- **Wrong Credentials**: Check `application.properties`
- **Database Not Created**: Create database with `CREATE DATABASE sweetshop;`

#### CORS Errors
- Ensure backend is running on port 8080
- Check CORS configuration in SecurityConfig.java

#### Authentication Issues
- Clear browser localStorage and try logging in again
- Check JWT token expiration (24 hours by default)

### Development Tips
- **Hot Reload**: Both frontend and backend support hot reload during development
- **Browser Console**: Check for JavaScript errors in the browser console (F12)
- **Backend Logs**: Check terminal output for Spring Boot logs and errors
- **API Testing**: Use Postman or curl to test API endpoints directly

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

## 🤝 Contributing

We welcome contributions! Please follow these steps:

1. **Fork** the repository
2. **Create** a feature branch: `git checkout -b feature/your-feature-name`
3. **Make** your changes and ensure tests pass
4. **Commit** your changes: `git commit -am 'Add some feature'`
5. **Push** to the branch: `git push origin feature/your-feature-name`
6. **Submit** a pull request

### Development Guidelines
- Follow existing code style and naming conventions
- Add tests for new features
- Update documentation as needed
- Ensure responsive design for mobile devices

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 📞 Support

If you encounter any issues or have questions:

1. Check the troubleshooting section above
2. Review the browser console and backend logs
3. Test API endpoints with Postman
4. Create an issue in the repository

## 🎉 Acknowledgments

- Built with Spring Boot and React
- JWT implementation inspired by industry best practices
- Responsive design patterns from modern web development

## 🚀 Deployment Guide

### Frontend Deployment (Vercel)

#### Step 1: Prepare Frontend for Vercel
1. **Navigate to frontend directory**:
   ```bash
   cd sweet-shop-frontend
   ```

2. **Create environment file**:
   ```bash
   # Create .env.local file
   echo "VITE_API_BASE_URL=http://localhost:8080/api" > .env.local
   ```

3. **Test build locally**:
   ```bash
   npm run build
   ```

#### Step 2: Deploy to Vercel

**Option A: Vercel CLI (Recommended)**
```bash
# Install Vercel CLI
npm i -g vercel

# Login to Vercel
vercel login

# Deploy from frontend directory
cd sweet-shop-frontend
vercel

# Follow the prompts:
# - Link to existing project or create new? → Create new
# - Project name → sweet-shop-frontend
# - Directory → ./
```

**Option B: GitHub Integration**
1. Push your code to GitHub
2. Go to [vercel.com](https://vercel.com)
3. Click "Import Project"
4. Connect your GitHub repository
5. Vercel will automatically detect it's a Vite project

#### Step 3: Configure Environment Variables in Vercel
1. Go to your Vercel dashboard
2. Select your project
3. Go to Settings → Environment Variables
4. Add:
   ```
   VITE_API_BASE_URL=https://your-backend-url.com/api
   ```

### Backend Deployment Options

Since Vercel doesn't support Java applications, deploy your backend separately:

#### Option 1: Railway (Recommended for beginners)
```bash
# Install Railway CLI
npm install -g @railway/cli

# Login and deploy
railway login
railway init
railway up
```

#### Option 2: Heroku
```bash
# Install Heroku CLI
# Create a Procfile in sweet-shop-api/:
echo "web: java -jar target/*.jar" > Procfile

# Deploy
heroku create
git push heroku main
```

#### Option 3: DigitalOcean App Platform
1. Go to DigitalOcean App Platform
2. Create new app from GitHub
3. Configure Java build settings
4. Set environment variables for database

#### Option 4: AWS/Railway/Render
- **Railway**: `railway up` (easiest)
- **Render**: Connect GitHub repo, select Java
- **AWS**: Use Elastic Beanstalk or EC2

### Database Deployment

#### Option 1: PlanetScale (MySQL-compatible)
```bash
# Create database
# Update application.properties with PlanetScale URL
```

#### Option 2: Railway Database
```bash
# Railway provides free PostgreSQL, but you can use MySQL
railway add mysql
```

#### Option 3: AWS RDS
- Create MySQL instance
- Update security groups
- Use connection string in application.properties

### Production Configuration

#### Backend Environment Variables:
```properties
# Database
spring.datasource.url=jdbc:mysql://your-db-host:3306/sweetshop
spring.datasource.username=your_db_user
spring.datasource.password=your_db_password

# JWT
app.jwt.secret=your-production-jwt-secret-here
app.jwt.expiration-ms=86400000

# CORS for production frontend
spring.web.cors.allowed-origins=https://your-frontend-domain.vercel.app
```

#### Frontend Environment Variables:
```bash
VITE_API_BASE_URL=https://your-backend-domain.com/api
```

### Testing Production Deployment

1. **Update CORS**: Add your Vercel domain to backend CORS settings
2. **Test Registration**: Create an admin account
3. **Test Purchases**: Add sweets and test buying
4. **Test Admin Features**: Verify CRUD operations work

### Troubleshooting Deployment

#### Frontend Issues:
- **Build fails**: Check Node.js version compatibility
- **API calls fail**: Verify environment variables are set
- **CORS errors**: Update backend CORS configuration

#### Backend Issues:
- **Database connection fails**: Check connection string and credentials
- **Port conflicts**: Use default port or configure custom port
- **Memory issues**: Increase memory limits in deployment platform

#### Common Fixes:
```bash
# Clear build cache
rm -rf node_modules/.vite
npm run build

# Check environment variables
vercel env ls
```

---

**Happy coding! 🍬✨**
- `GET /api/sweets/{id}` - Get sweet by ID
- `POST /api/sweets` - Add new sweet (Admin only)
- `PUT /api/sweets/{id}` - Update sweet (Admin only)
- `DELETE /api/sweets/{id}` - Delete sweet (Admin only)

### Inventory (Protected)
- `POST /api/sweets/{id}/purchase` - Purchase sweet
- `POST /api/sweets/{id}/restock` - Restock sweet (Admin only)

## Testing

### Backend Tests
```bash
cd sweet-shop-api
mvn test
```

### Frontend Tests
```bash
cd sweet-shop-frontend
npm run test
```

## Default Users

After starting the application, you can register new users or use these default accounts:

- **Admin User**: Register with role "ADMIN"
- **Regular User**: Register with role "USER" (default)

## Usage

1. **Registration**: Create an account with username, password, and role
2. **Login**: Authenticate to access the sweet shop
3. **Browse**: View all available sweets
4. **Search**: Filter sweets by name, category, or price range
5. **Purchase**: Buy sweets (decreases inventory)
6. **Admin Actions** (Admin users only):
   - Add new sweets
   - Edit existing sweets
   - Delete sweets
   - Restock inventory

## Project Structure

```
SweetShopManager/
├── sweet-shop-api/           # Spring Boot backend
│   ├── src/main/java/com/sweetshop/api/
│   │   ├── auth/             # Authentication components
│   │   ├── config/           # Security configuration
│   │   ├── sweet/            # Sweet management
│   │   └── user/             # User management
│   └── src/main/resources/   # Application properties
└── sweet-shop-frontend/      # React frontend
    ├── src/
    │   ├── components/       # React components
    │   ├── context/          # React context (Auth)
    │   ├── pages/            # Page components
    │   └── services/         # API services
    └── public/               # Static assets
```

## Security Features

- JWT token-based authentication
- Password encryption with BCrypt
- Role-based access control (USER/ADMIN)
- Protected API endpoints
- CORS configuration for frontend integration

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new features
5. Submit a pull request

## License

This project is licensed under the MIT License.
