import React, { useState, useEffect } from 'react';
import { useAuth } from '../context/AuthContext';
import api from '../services/api';
import SweetCard from '../components/Sweet/SweetCard';
import SearchBar from '../components/Sweet/SearchBar';
import SweetForm from '../components/Sweet/SweetForm';

const HomePage = () => {
  const [sweets, setSweets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [showForm, setShowForm] = useState(false);
  const [editingSweet, setEditingSweet] = useState(null);
  const { isAuthenticated, isAdmin, user } = useAuth();

  const fetchSweets = async (searchParams = {}) => {
    console.log('Fetching sweets with params:', searchParams);
    console.log('Type of searchParams:', typeof searchParams);

    // Ensure searchParams is always an object
    if (!searchParams || typeof searchParams !== 'object') {
      searchParams = {};
    }

    setLoading(true);
    try {
      let url = '/sweets';
      const params = new URLSearchParams();
      console.log('URLSearchParams created:', params);

      if (searchParams.name || searchParams.category ||
          (searchParams.minPrice !== null && searchParams.minPrice !== undefined) ||
          (searchParams.maxPrice !== null && searchParams.maxPrice !== undefined)) {
        url = '/sweets/search';
        if (searchParams.name && searchParams.name.trim()) params.append('name', searchParams.name);
        if (searchParams.category && searchParams.category.trim()) params.append('category', searchParams.category);
        if (searchParams.minPrice !== null && searchParams.minPrice !== undefined && searchParams.minPrice !== '') params.append('minPrice', searchParams.minPrice);
        if (searchParams.maxPrice !== null && searchParams.maxPrice !== undefined && searchParams.maxPrice !== '') params.append('maxPrice', searchParams.maxPrice);
      }

      console.log('Making API call to:', `${url}?${params}`);
      const response = await api.get(`${url}?${params}`);
      console.log('Sweets response:', response.data);
      setSweets(response.data);
    } catch (error) {
      console.error('Error fetching sweets:', error);
      console.error('Error response:', error.response);
      if (error.response?.status === 401) {
        console.log('401 error - user not authenticated');
        setSweets([]);
      } else if (error.response?.status === 403) {
        console.log('403 error - forbidden access');
        setSweets([]);
      }
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    console.log('Authentication status:', isAuthenticated());
    console.log('User:', user);
    if (isAuthenticated()) {
      console.log('User is authenticated, fetching sweets...');
      fetchSweets();
    } else {
      console.log('User not authenticated, skipping fetch');
      setLoading(false);
    }
  }, [isAuthenticated()]);

  const handleSearch = (searchParams) => {
    fetchSweets(searchParams);
  };

  const handleAddSweet = () => {
    setEditingSweet(null);
    setShowForm(true);
  };

  const handleEditSweet = (sweet) => {
    setEditingSweet(sweet);
    setShowForm(true);
  };

  const handleFormSuccess = () => {
    fetchSweets();
  };

  const handleCloseForm = () => {
    setShowForm(false);
    setEditingSweet(null);
  };

  if (!isAuthenticated()) {
    return (
      <div className="homepage">
        <div className="welcome-message">
          <h1>Welcome to the Sweet Shop! 🍬</h1>
          <p>Please log in to browse our delicious selection of sweets.</p>
        </div>
      </div>
    );
  }

  if (loading) return <div className="loading">Loading the Sweet Shop catalog...</div>;

  return (
    <div className="homepage">
      <div className="page-header">
        <h1>Welcome to the Sweet Shop! 🍬</h1>
        {isAdmin() && (
          <button onClick={handleAddSweet} className="btn-add-sweet">
            Add New Sweet
          </button>
        )}
      </div>

      <SearchBar onSearch={handleSearch} />

      <div className="sweet-list-container">
        {sweets.length > 0 ? (
          sweets.map(sweet => (
            <SweetCard
              key={sweet.id}
              sweet={sweet}
              fetchSweets={fetchSweets}
              onEdit={handleEditSweet}
            />
          ))
        ) : (
          <div className="empty-state">
            <p>No sweets currently available.</p>
            {isAdmin() && (
              <button onClick={handleAddSweet} className="btn-add-first">
                Add Your First Sweet
              </button>
            )}
          </div>
        )}
      </div>

      {showForm && (
        <SweetForm
          sweet={editingSweet}
          onClose={handleCloseForm}
          onSuccess={handleFormSuccess}
        />
      )}
    </div>
  );
};

export default HomePage;