import React from "react";
import "./OrderSummary.css";

export default function OrderSummary({ menuItems, quantities, submitOrder, isSubmitting }) {
  const items = Object.entries(quantities).map(([name, qty]) => {
    const item = menuItems.find((i) => i.name === name);
    return { ...item, qty };
  });

  const total = items.reduce((sum, item) => sum + item.price * item.qty, 0);

  return (
    <div className="order-summary">
      <h2>Your Order</h2>
      {items.length === 0 && <p>No items added yet.</p>}
      <ul>
        {items.map(({ id, name, qty, price }) => (
          <li key={id}>
            {name} × {qty} = ${(price * qty).toFixed(2)}
          </li>
        ))}
      </ul>
      <h3>Total: ${total.toFixed(2)}</h3>
      <button onClick={submitOrder} disabled={isSubmitting || items.length === 0}>
        {isSubmitting ? "Submitting..." : "Place Order"}
      </button>
    </div>
  );
}
