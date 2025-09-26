import { describe, it, expect, vi } from 'vitest';
import { render, screen, fireEvent } from '@testing-library/react';
import SweetCard from './SweetCard';

// Mock the useAuth hook
vi.mock('../../context/AuthContext', () => ({
  useAuth: () => ({
    isAdmin: () => false
  })
}));

// Mock axios
vi.mock('../../services/api', () => ({
  default: {
    post: vi.fn(() => Promise.resolve({ data: {} }))
  }
}));

describe('SweetCard', () => {
  const mockSweet = {
    id: 1,
    name: 'Chocolate Bar',
    category: 'Chocolate',
    price: 2.50,
    quantityInStock: 10
  };

  const mockFetchSweets = vi.fn();

  it('renders sweet information correctly', () => {
    render(<SweetCard sweet={mockSweet} fetchSweets={mockFetchSweets} />);

    expect(screen.getByText('Chocolate Bar')).toBeInTheDocument();
    expect(screen.getByText('Category: Chocolate')).toBeInTheDocument();
    expect(screen.getByText('Price: $2.50')).toBeInTheDocument();
    expect(screen.getByText('Stock: 10')).toBeInTheDocument();
  });

  it('shows purchase button when in stock', () => {
    render(<SweetCard sweet={mockSweet} fetchSweets={mockFetchSweets} />);

    const purchaseButton = screen.getByText('Purchase');
    expect(purchaseButton).toBeInTheDocument();
    expect(purchaseButton).not.toBeDisabled();
  });

  it('shows out of stock when quantity is zero', () => {
    const outOfStockSweet = { ...mockSweet, quantityInStock: 0 };
    render(<SweetCard sweet={outOfStockSweet} fetchSweets={mockFetchSweets} />);

    expect(screen.getByText('Out of Stock')).toBeInTheDocument();
  });
});
