import React from "react";
import "./Confirmation.css";

function Confirmation({ order, total, discount }) {
  return (
    <div className="confirmation-container">
      <h2>Thank you for your order!</h2>

      <ul className="confirmation-list">
        {order.map((item) => (
          <li key={item.id} className="confirmation-item">
            <span>{item.name}</span>
            <span>${item.price.toFixed(2)}</span>
          </li>
        ))}
      </ul>

      {discount > 0 && (
        <p className="discount-text">
          Discount applied: <strong>${discount.toFixed(2)}</strong>
        </p>
      )}

      <h3 className="total-text">Total Paid: ${total.toFixed(2)}</h3>

      <p className="thank-you-message">
        Your delicious meal will arrive soon! <span role="img" aria-label="fork and knife">🍽️</span>
      </p>
    </div>
  );
}

export default Confirmation;
