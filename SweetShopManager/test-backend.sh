#!/bin/bash

# Sweet Shop Backend Test Runner
# This script runs all backend tests and provides a summary

echo "🍬 Sweet Shop Backend Test Suite"
echo "=================================="

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Function to print colored output
print_status() {
    if [ $1 -eq 0 ]; then
        echo -e "${GREEN}✓ $2${NC}"
    else
        echo -e "${RED}✗ $2${NC}"
    fi
}

# Navigate to backend directory
cd sweet-shop-api

echo "📋 Running Backend Tests..."
echo "---------------------------"

# Run all tests
echo "🧪 Running unit tests and integration tests..."
mvn test -q

TEST_RESULT=$?

echo ""
echo "📊 Test Results Summary:"
echo "-----------------------"

if [ $TEST_RESULT -eq 0 ]; then
    print_status 0 "All tests passed successfully!"
    echo ""
    echo "🎉 Backend is ready for deployment!"
    echo "   ✓ Unit tests passed"
    echo "   ✓ Integration tests passed"
    echo "   ✓ Repository tests passed"
    echo "   ✓ Controller tests passed"
else
    print_status 1 "Some tests failed!"
    echo ""
    echo "❌ Please fix the failing tests before deployment."
    echo "   Run 'mvn test' for detailed error information"
fi

echo ""
echo "📁 Test Coverage Areas:"
echo "  • SweetService - Business logic validation"
echo "  • SweetController - REST API endpoints"
echo "  • SweetRepository - Data access layer"
echo "  • AuthController - Authentication endpoints"
echo "  • Integration tests - Full application context"

echo ""
echo "🔧 Useful Commands:"
echo "  • Run all tests: mvn test"
echo "  • Run specific test: mvn test -Dtest=ClassName"
echo "  • Generate coverage: mvn test jacoco:report"

exit $TEST_RESULT
