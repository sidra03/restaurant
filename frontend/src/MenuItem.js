import React from "react";

function MenuItem({ item, quantity, addToOrder, removeFromOrder }) {
  // Fallback for spice level: use item.spiceLevel or item.chilliLevel
  const spiceLevel = item.spiceLevel ?? item.chilliLevel ?? 0;

  return (
    <div className="card mb-3 shadow-sm">
      <div className="card-body">
        <h5 className="card-title">
          {item.name}{" "}
          {spiceLevel > 0 && (
            <span className="badge bg-danger">{spiceLevel} 🌶️</span>
          )}
        </h5>
        <p className="card-text">{item.description}</p>
        <p className="card-text fw-bold">${item.price.toFixed(2)}</p>
        <div className="d-flex align-items-center">
          <button
            className="btn btn-danger btn-sm me-2"
            onClick={() => removeFromOrder(item.name)}
            disabled={!quantity}
          >
            -
          </button>
          <span className="me-2">{quantity || 0}</span>
          <button
            className="btn btn-success btn-sm"
            onClick={() => addToOrder(item.name)}
          >
            +
          </button>
        </div>
      </div>
    </div>
  );
}

export default MenuItem;
