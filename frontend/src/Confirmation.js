import React from "react";
import "./Confirmation.css";

function Confirmation({ order, total, discount, totalChillies, isHotOrder, onReset }) {
  let spiceMessage = "This order is mild.";
  if (totalChillies >= 3) {
    spiceMessage = "🌶️ This is a HOT order! 🌶️";
  } else if (totalChillies >= 1) {
    spiceMessage = "This order is medium spicy.";
  }

  return (
    <div className="confirmation-container">
      <h2>Thank you for your order!</h2>

      <p>{spiceMessage}</p>

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

export default Confirmation;
