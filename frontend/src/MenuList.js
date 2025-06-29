import React from "react";
import "./MenuList.css";

function MenuList({ menuItems, quantities, addToOrder, removeFromOrder }) {
  return (
    <div className="menu-list">
      {menuItems.map((item) => (
        <div key={item.id} className="menu-item">
          <div className="item-info">
            <h3>{item.name}</h3>
            <p>{item.description}</p>
            <p className="price">${item.price.toFixed(2)}</p>
          </div>
          <div className="quantity-control">
            <button
              className="qty-btn"
              onClick={() => removeFromOrder(item.name)}
              aria-label={`Remove one ${item.name}`}
              disabled={!quantities[item.name]}
            >
              −
            </button>
            <span className="qty-number">{quantities[item.name] || 0}</span>
            <button
              className="qty-btn"
              onClick={() => addToOrder(item.name)}
              aria-label={`Add one ${item.name}`}
            >
              +
            </button>
          </div>
        </div>
      ))}
    </div>
  );
}

export default MenuList;
