import React, { createContext, useState, useContext, useEffect } from 'react';
import api from '../services/api';

const AuthContext = createContext();

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

export const AuthProvider = ({ children }) => {
  const [user, setUser] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const token = localStorage.getItem('jwtToken');
    const role = localStorage.getItem('userRole');
    const username = localStorage.getItem('username');

    console.log('AuthContext init - token:', !!token, 'role:', role, 'username:', username);

    if (token && role && username) {
      setUser({ username, role, token });
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;
      console.log('User authenticated:', { username, role });
    } else {
      console.log('User not authenticated');
    }
    setLoading(false);
  }, []);

  const login = async (username, password) => {
    try {
      const response = await api.post('/auth/login', { username, password });
      const { token, role } = response.data;

      localStorage.setItem('jwtToken', token);
      localStorage.setItem('userRole', role);
      localStorage.setItem('username', username);

      setUser({ username, role, token });
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      return { success: true };
    } catch (error) {
      return {
        success: false,
        error: error.response?.data?.message || 'Login failed'
      };
    }
  };

  const register = async (username, password, role = 'USER') => {
    try {
      console.log('Sending registration request:', { username, password, role });
      const response = await api.post('/auth/register', { username, password, role });
      console.log('Registration response:', response.data);

      const { token, role: userRole } = response.data;

      localStorage.setItem('jwtToken', token);
      localStorage.setItem('userRole', userRole);
      localStorage.setItem('username', username);

      setUser({ username, role: userRole, token });
      api.defaults.headers.common['Authorization'] = `Bearer ${token}`;

      return { success: true };
    } catch (error) {
      console.error('Registration error:', error);
      console.error('Error response:', error.response);
      return {
        success: false,
        error: error.response?.data || error.message || 'Registration failed'
      };
    }
  };

  const logout = () => {
    localStorage.removeItem('jwtToken');
    localStorage.removeItem('userRole');
    localStorage.removeItem('username');
    setUser(null);
    delete api.defaults.headers.common['Authorization'];
  };

  const isAdmin = () => user?.role === 'ADMIN';
  const isAuthenticated = () => !!user;

  const value = {
    user,
    login,
    register,
    logout,
    isAdmin,
    isAuthenticated,
    loading
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};
