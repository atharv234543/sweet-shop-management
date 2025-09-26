import React, { useState } from 'react';

const SearchBar = ({ onSearch }) => {
  const [filters, setFilters] = useState({
    name: '',
    category: '',
    minPrice: '',
    maxPrice: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFilters(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const searchParams = {
      name: filters.name || null,
      category: filters.category || null,
      minPrice: filters.minPrice ? parseFloat(filters.minPrice) : null,
      maxPrice: filters.maxPrice ? parseFloat(filters.maxPrice) : null
    };
    onSearch(searchParams);
  };

  const handleReset = () => {
    setFilters({
      name: '',
      category: '',
      minPrice: '',
      maxPrice: ''
    });
    onSearch({});
  };

  return (
    <div className="search-bar">
      <form onSubmit={handleSubmit} className="search-form">
        <div className="search-fields">
          <div className="search-field">
            <input
              type="text"
              name="name"
              placeholder="Search by name"
              value={filters.name}
              onChange={handleChange}
            />
          </div>

          <div className="search-field">
            <input
              type="text"
              name="category"
              placeholder="Filter by category"
              value={filters.category}
              onChange={handleChange}
            />
          </div>

          <div className="search-field price-field">
            <input
              type="number"
              name="minPrice"
              placeholder="Min price"
              value={filters.minPrice}
              onChange={handleChange}
              min="0"
              step="0.01"
            />
            <span className="price-separator">-</span>
            <input
              type="number"
              name="maxPrice"
              placeholder="Max price"
              value={filters.maxPrice}
              onChange={handleChange}
              min="0"
              step="0.01"
            />
          </div>

          <div className="search-actions">
            <button type="submit" className="btn-search">
              Search
            </button>
            <button type="button" onClick={handleReset} className="btn-reset">
              Reset
            </button>
          </div>
        </div>
      </form>
    </div>
  );
};

export default SearchBar;
