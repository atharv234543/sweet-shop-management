import React, { useState } from 'react';
import { useAuth } from '../../context/AuthContext';
import api from '../../services/api';

const SweetCard = ({ sweet, fetchSweets, onEdit }) => {
  const { id, name, category, price, quantityInStock } = sweet;
  const { isAdmin } = useAuth();
  const [purchaseQuantity, setPurchaseQuantity] = useState(1);
  const [restockQuantity, setRestockQuantity] = useState(1);
  const [loading, setLoading] = useState(false);
  const isOutOfStock = quantityInStock <= 0;

  const handlePurchase = async () => {
    if (isOutOfStock || purchaseQuantity <= 0 || purchaseQuantity > quantityInStock) return;
    setLoading(true);

    try {
      await api.post(`/sweets/${id}/purchase`, null, {
        params: { quantity: purchaseQuantity }
      });
      alert(`Purchased ${purchaseQuantity} ${name}(s)! Enjoy!`);
      fetchSweets();
    } catch (error) {
      console.error('Purchase failed:', error);
      if (error.response && error.response.status === 401) {
        alert('Purchase failed: Please log in to complete your order.');
      } else if (error.response && error.response.status === 400) {
        alert('Purchase failed: Insufficient stock or invalid quantity.');
      } else {
        alert('Purchase failed. Inventory error or server issue.');
      }
    } finally {
      setLoading(false);
    }
  };

  const handleRestock = async () => {
    if (restockQuantity <= 0) return;
    setLoading(true);

    try {
      await api.post(`/sweets/${id}/restock`, null, {
        params: { quantity: restockQuantity }
      });
      alert(`Restocked ${restockQuantity} ${name}(s)!`);
      fetchSweets();
    } catch (error) {
      console.error('Restock failed:', error);
      alert('Restock failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  const handleDelete = async () => {
    if (!window.confirm(`Are you sure you want to delete ${name}?`)) return;
    setLoading(true);

    try {
      await api.delete(`/sweets/${id}`);
      alert(`${name} deleted successfully!`);
      fetchSweets();
    } catch (error) {
      console.error('Delete failed:', error);
      alert('Delete failed. Please try again.');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="sweet-card">
      <div className="sweet-info">
        <h3>{name}</h3>
        <p className="category">Category: {category}</p>
        <p className="price">Price: ${price.toFixed(2)}</p>
        <p className={`stock ${isOutOfStock ? 'stock-low' : 'stock-high'}`}>
          Stock: {quantityInStock}
        </p>
      </div>

      <div className="sweet-actions">
        {!isOutOfStock && (
          <div className="purchase-section">
            <div className="quantity-input">
              <label>Qty:</label>
              <input
                type="number"
                min="1"
                max={quantityInStock}
                value={purchaseQuantity}
                onChange={(e) => setPurchaseQuantity(Math.max(1, Math.min(quantityInStock, parseInt(e.target.value) || 1)))}
                className="quantity-field"
              />
            </div>
            <button
              onClick={handlePurchase}
              disabled={loading || purchaseQuantity <= 0 || purchaseQuantity > quantityInStock}
              className={isOutOfStock ? 'btn-disabled' : 'btn-primary'}
            >
              {loading ? 'Processing...' : `Purchase ${purchaseQuantity}`}
            </button>
          </div>
        )}
        {isOutOfStock && (
          <button disabled className="btn-disabled">
            Out of Stock
          </button>
        )}

        {isAdmin() && (
          <div className="admin-actions">
            <div className="restock-section">
              <input
                type="number"
                min="1"
                value={restockQuantity}
                onChange={(e) => setRestockQuantity(parseInt(e.target.value) || 1)}
                className="restock-input"
              />
              <button
                onClick={handleRestock}
                disabled={loading}
                className="btn-restock"
              >
                Restock
              </button>
            </div>

            <div className="admin-buttons">
              <button
                onClick={() => onEdit(sweet)}
                disabled={loading}
                className="btn-edit"
              >
                Edit
              </button>
              <button
                onClick={handleDelete}
                disabled={loading}
                className="btn-delete"
              >
                Delete
              </button>
            </div>
          </div>
        )}
      </div>
    </div>
  );
};

export default SweetCard;