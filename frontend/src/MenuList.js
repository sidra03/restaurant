import React from "react";
import "./MenuList.css";

export default function MenuList({ menuItems, quantities, addToOrder, removeFromOrder }) {
  return (
    <div className="menu-list">
      {menuItems.map(({ id, name, description, price, chilliLevel }) => (
        <div key={id} className="menu-item">
          <div className="menu-item-info">
            <h3>{name}</h3>
            <p className="menu-description">{description}</p>
            <p className="menu-price">${price.toFixed(2)}</p>
            <p className="menu-chilli">🌶️ Level: {chilliLevel}</p>
          </div>
          <div className="menu-item-controls">
            <button onClick={() => removeFromOrder(name)} disabled={!quantities[name]}>
              −
            </button>
            <span>{quantities[name] || 0}</span>
            <button onClick={() => addToOrder(name)}>+</button>
          </div>
        </div>
      ))}
    </div>
  );
}
