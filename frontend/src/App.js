import React, { useState, useEffect } from "react";
import Confirmation from "./Confirmation";
import "./App.css";

function App() {
  const [menuItems, setMenuItems] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [orderConfirmed, setOrderConfirmed] = useState(false);
  const [confirmedOrder, setConfirmedOrder] = useState(null);
  const [isSubmitting, setIsSubmitting] = useState(false);

useEffect(() => {
  fetch("http://localhost:8081/menu")
    .then((res) => res.json())
    .then((data) => {
      let allItems = [];

      if (Array.isArray(data)) {
        // If backend returns a flat array of items
        allItems = data.map(item => ({
          ...item,
          category: item.category ? item.category.toLowerCase() : "unknown",
          chilliLevel: item.chilliLevel ?? item.chiliLevel ?? 0,
        }));
      } else if (typeof data === "object" && data !== null) {
        // If backend returns an object with categories as keys
        allItems = Object.entries(data).flatMap(([category, items]) =>
          (items || []).map(item => ({
            ...item,
            category: category.toLowerCase(),
            chilliLevel: item.chilliLevel ?? item.chiliLevel ?? 0,
          }))
        );
      } else {
        throw new Error("Unexpected menu data format");
      }

      setMenuItems(allItems);
    })
    .catch((error) => {
      alert("Failed to load menu: " + error.message);
    });
}, []);


  const incrementQuantity = (name) => {
    setQuantities((prev) => ({
      ...prev,
      [name]: (prev[name] || 0) + 1,
    }));
  };

  const decrementQuantity = (name) => {
    setQuantities((prev) => {
      const newQty = (prev[name] || 0) - 1;
      if (newQty <= 0) {
        const { [name]: _, ...rest } = prev; // remove if zero
        return rest;
      }
      return { ...prev, [name]: newQty };
    });
  };

  const submitOrder = () => {
    if (Object.keys(quantities).length === 0) {
      alert("Please add some items to your order.");
      return;
    }

    setIsSubmitting(true);

    // Send array of item IDs only as backend expects { customerName, itemIds: [...] }
    const itemIds = Object.keys(quantities)
      .map((name) => {
        const item = menuItems.find((i) => i.name === name);
        return item?.id;
      })
      .filter(Boolean);

    fetch("http://localhost:8081/order", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ customerName: "Tester", itemIds }),
    })
      .then(async (res) => {
        setIsSubmitting(false);
        if (!res.ok) {
          const errText = await res.text();
          throw new Error(errText || "Order failed");
        }
        return res.json();
      })
      .then((data) => {
        setConfirmedOrder(data);
        setOrderConfirmed(true);
        setQuantities({});
      })
      .catch((e) => {
        alert("Order submission error: " + e.message);
        setIsSubmitting(false);
      });
  };

  const resetOrder = () => {
    setOrderConfirmed(false);
    setConfirmedOrder(null);
  };

  const categories = [...new Set(menuItems.map((item) => item.category))];

  if (orderConfirmed) {
    return (
      <Confirmation
        order={confirmedOrder?.orderedItems}
        total={confirmedOrder?.total}
        discount={confirmedOrder?.discount}
        totalChillies={confirmedOrder?.totalChillies}
        onReset={resetOrder}
      />
    );
  }

  return (
    <div className="app-container">
      <h1>Restaurant Menu</h1>

      {categories.map((category) => (
        <div key={category} className="category-section">
          <h2>{category}</h2>

          <ul className="menu-list">
            {menuItems
              .filter((item) => item.category === category)
              .map((item) => (
                <li key={item.id} className="menu-item">
                  <div className="menu-item-info">
                    <strong>{item.name}</strong>{" "}
                    {item.chilliLevel > 0 && (
                      <span
                        className="spice-icons"
                        title={`Spice Level: ${item.chilliLevel}`}
                      >
                        {"🌶️".repeat(item.chilliLevel)}
                      </span>
                    )}
                    <div className="menu-item-description">{item.description}</div>
                  </div>

                  <div className="menu-item-controls">
                    <span style={{ marginRight: "15px" }}>
                      ${item.price.toFixed(2)}
                    </span>

                    <button
                      onClick={() => decrementQuantity(item.name)}
                      disabled={!quantities[item.name]}
                    >
                      -
                    </button>

                    <span>{quantities[item.name] || 0}</span>

                    <button onClick={() => incrementQuantity(item.name)}>+</button>
                  </div>
                </li>
              ))}
          </ul>
        </div>
      ))}

      <button
        onClick={submitOrder}
        disabled={isSubmitting || Object.keys(quantities).length === 0}
        className="place-order-btn"
      >
        {isSubmitting ? "Placing order..." : "Place Order"}
      </button>
    </div>
  );
}

export default App;
