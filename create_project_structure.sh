#!/bin/bash

# Define the main project directory name
PROJECT_ROOT="SweetShopManager"

# --- Backend (Spring Boot/Java) Structure ---
BACKEND_DIR="$PROJECT_ROOT/sweet-shop-api"
JAVA_BASE_PATH="$BACKEND_DIR/src/main/java/com/sweetshop/api"
RESOURCES_PATH="$BACKEND_DIR/src/main/resources"
TEST_JAVA_BASE_PATH="$BACKEND_DIR/src/test/java/com/sweetshop/api"

# Define all backend directories to create
BACKEND_DIRS=(
    "$JAVA_BASE_PATH/config"      # SecurityConfig, JwtTokenUtil, CorsFilter
    "$JAVA_BASE_PATH/auth/controller"
    "$JAVA_BASE_PATH/auth/dto"
    "$JAVA_BASE_PATH/auth/service"
    "$JAVA_BASE_PATH/sweet/controller"
    "$JAVA_BASE_PATH/sweet/model"
    "$JAVA_BASE_PATH/sweet/repository"
    "$JAVA_BASE_PATH/sweet/service"
    "$JAVA_BASE_PATH/user/model"
    "$JAVA_BASE_PATH/user/repository"
    "$JAVA_BASE_PATH/user/service"
    "$RESOURCES_PATH"            # application.properties/yml
    "$BACKEND_DIR/src/main/test" # Other test resources
    "$TEST_JAVA_BASE_PATH/sweet/service" # For TDD-specific unit tests
)

# --- Frontend (React) Structure ---
FRONTEND_DIR="$PROJECT_ROOT/sweet-shop-frontend"
FRONTEND_SRC="$FRONTEND_DIR/src"

# Define all frontend directories to create
FRONTEND_DIRS=(
    "$FRONTEND_SRC/assets/images"
    "$FRONTEND_SRC/components/Auth"         # LoginForm, RegisterForm
    "$FRONTEND_SRC/components/Sweet"        # SweetCard, SweetList, SweetForm
    "$FRONTEND_SRC/components/Layout"       # Header, Footer, Navbar
    "$FRONTEND_SRC/pages"                   # HomePage, LoginPage, AdminDashboard
    "$FRONTEND_SRC/services"                # api.js, auth.service.js
    "$FRONTEND_SRC/context"                 # AuthContext.jsx
    "$FRONTEND_SRC/hooks"                   # useAuth, useSweetData
    "$FRONTEND_DIR/public"
)

echo "🏗️ Starting Project Structure Setup for $PROJECT_ROOT"
echo "--------------------------------------------------------"

# 1. Create Backend Directories
echo "📂 Creating Backend (Spring Boot) directories..."
for dir in "${BACKEND_DIRS[@]}"; do
    mkdir -p "$dir"
done
echo "   -> Backend structure created."

# 2. Create Frontend Directories
echo "📂 Creating Frontend (React) directories..."
for dir in "${FRONTEND_DIRS[@]}"; do
    mkdir -p "$dir"
done
echo "   -> Frontend structure created."

# 3. Create Placeholder Files (Recommended to check for success)
echo "📄 Creating essential placeholder files..."

# Backend Placeholders
touch "$JAVA_BASE_PATH/SweetShopApiApplication.java"
touch "$JAVA_BASE_PATH/config/SecurityConfig.java"
touch "$RESOURCES_PATH/application.properties"
touch "$BACKEND_DIR/pom.xml"

# Frontend Placeholders
touch "$FRONTEND_SRC/App.jsx"
touch "$FRONTEND_SRC/index.js"
touch "$FRONTEND_SRC/services/api.js"
touch "$FRONTEND_SRC/context/AuthContext.jsx"
touch "$FRONTEND_SRC/pages/HomePage.jsx"
touch "$FRONTEND_DIR/package.json"

echo "--------------------------------------------------------"
echo "✅ Project setup complete!"
echo "Navigate to the project root: cd $PROJECT_ROOT"
echo ""
echo "Next Steps:"
echo "1. Initialize Spring Boot in '$BACKEND_DIR'."
echo "2. Initialize React in '$FRONTEND_DIR' (e.g., 'npx create-react-app . --template js' inside the directory or copy your project files)."
echo "3. Start implementing your models and tests!"