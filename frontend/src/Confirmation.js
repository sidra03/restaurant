import React from "react";
import PropTypes from "prop-types";
import "./Confirmation.css";

function Confirmation({ order, total, discount, message, onReset }) {
  const displaySpiceMessage = message;

  return (
    <div className="confirmation-container">
      <h2>Thank you for your order!</h2>

      <p>{displaySpiceMessage}</p>

      <ul className="order-list">
        {order.map((item) => (
          <li key={item.id}>
            <span>{item.name}</span>
            <span>${item.price.toFixed(2)}</span>
          </li>
        ))}
      </ul>

      {discount > 0 && <p className="discount-text">Discount applied: ${discount.toFixed(2)}</p>}

      <h3 className="total-paid">Total Paid: ${total.toFixed(2)}</h3>

      <p>
        Your delicious meal will arrive soon!{" "}
        <span role="img" aria-label="fork and knife">
          🍽️
        </span>
      </p>

      <button onClick={onReset} className="order-again-btn">
        Order Again
      </button>
    </div>
  );
}

Confirmation.propTypes = {
  order: PropTypes.arrayOf(
    PropTypes.shape({
      id: PropTypes.number.isRequired,
      name: PropTypes.string.isRequired,
      price: PropTypes.number.isRequired,
    })
  ).isRequired,
  total: PropTypes.number.isRequired,
  discount: PropTypes.number.isRequired,
  message: PropTypes.string,
  onReset: PropTypes.func.isRequired,
};

export default Confirmation;
