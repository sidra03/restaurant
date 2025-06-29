import React, { useEffect, useState } from "react";
import MenuList from "./MenuList";
import OrderSummary from "./OrderSummary";
import Confirmation from "./Confirmation";
import "./App.css";

function App() {
  const [menuItems, setMenuItems] = useState([]);
  const [quantities, setQuantities] = useState({});
  const [isSubmitting, setIsSubmitting] = useState(false);
  const [orderConfirmed, setOrderConfirmed] = useState(false);
  const [confirmedOrder, setConfirmedOrder] = useState(null);

  useEffect(() => {
    fetch("http://localhost:8081/menu")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch menu");
        return res.json();
      })
      .then(setMenuItems)
      .catch((err) => alert(err.message));
  }, []);

  const addToOrder = (name) => {
    setQuantities((prev) => ({ ...prev, [name]: (prev[name] || 0) + 1 }));
  };

  const removeFromOrder = (name) => {
    setQuantities((prev) => {
      const newQty = (prev[name] || 0) - 1;
      if (newQty <= 0) {
        const copy = { ...prev };
        delete copy[name];
        return copy;
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

    const itemIds = Object.entries(quantities)
      .map(([name]) => {
        const item = menuItems.find((i) => i.name === name);
        return item ? item.id : null;
      })
      .filter((id) => id !== null);

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
      .catch((e) => alert("Order submission error: " + e.message));
  };

  if (orderConfirmed) {
    return <Confirmation order={confirmedOrder.items} total={confirmedOrder.total}
     discount={confirmedOrder.discount || 0}
/>;
  }

  return (
    <div className="app-container">
      <h1 className="app-title">Restaurant Menu</h1>
      <MenuList
        menuItems={menuItems}
        quantities={quantities}
        addToOrder={addToOrder}
        removeFromOrder={removeFromOrder}
      />
      <OrderSummary
        menuItems={menuItems}
        quantities={quantities}
        submitOrder={submitOrder}
        isSubmitting={isSubmitting}
      />
    </div>
  );
}

export default App;
