import React from "react";

function Order({ order, removeFromOrder }) {
  return (
    <div>
      <h2>Current Order</h2>
      {order.length === 0 ? (
        <p>No items added yet.</p>
      ) : (
        <ul>
          {order.map((item, index) => (
            <li key={index}>
              {item.name} - ${item.price}{" "}
              <button onClick={() => removeFromOrder(index)}>Remove</button>
            </li>
          ))}
        </ul>
      )}
    </div>
  );
}

export default Order;
