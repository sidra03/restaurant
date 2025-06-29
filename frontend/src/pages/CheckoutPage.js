import React, { useState, useEffect } from "react";

const CheckoutPage = () => {
  const [menu, setMenu] = useState([]);
  const [order, setOrder] = useState([]);
  const [customerName, setCustomerName] = useState("");
  const [orderStatus, setOrderStatus] = useState("");
  const [error, setError] = useState("");
  const [orderResponse, setOrderResponse] = useState(null);

  // Fetch menu on component mount
  useEffect(() => {
    fetch("http://localhost:8081/menu")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to load menu");
        return res.json();
      })
      .then((data) => setMenu(data))
      .catch((err) => setError(err.message));
  }, []);

  const addToOrder = (item) => {
    if (!order.find((i) => i.id === item.id)) {
      setOrder([...order, item]);
      setOrderResponse(null);
      setOrderStatus("");
      setError("");
    }
  };

  const removeFromOrder = (id) => {
    setOrder(order.filter((item) => item.id !== id));
    setOrderResponse(null);
    setOrderStatus("");
    setError("");
  };

  const totalChillies = order.reduce((sum, item) => sum + item.chilliLevel, 0);
  const rawTotal = order.reduce((sum, item) => sum + item.price, 0);
  const anyHot = totalChillies > 0;

  const handleConfirmOrder = () => {
    if (order.length === 0) {
      setError("Your order is empty. Please add items.");
      return;
    }
    if (!customerName.trim()) {
      setError("Please enter your name.");
      return;
    }

    setError("");
    setOrderStatus("Placing order...");

    const itemIds = order.map((item) => item.id);

    fetch("http://localhost:8081/order", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ customerName, itemIds }),
    })
      .then((res) => {
        if (!res.ok) throw new Error("Failed to place order");
        return res.json();
      })
      .then((data) => {
        setOrderStatus("Order placed successfully!");
        setOrderResponse(data);
      })
      .catch((err) => {
        setOrderStatus("");
        setError(err.message);
      });
  };

  return (
    <div style={{ padding: 20, fontFamily: "Arial" }}>
      <h2>Menu</h2>
      {menu.length === 0 ? (
        <p>Loading menu...</p>
      ) : (
        <ul>
          {menu.map((item) => (
            <li key={item.id}>
              {item.name} - ${item.price.toFixed(2)} (Chilli Level: {item.chilliLevel}){" "}
              <button
                onClick={() => addToOrder(item)}
                disabled={order.find((i) => i.id === item.id)}
              >
                {order.find((i) => i.id === item.id) ? "Added" : "Add"}
              </button>
            </li>
          ))}
        </ul>
      )}

      <h2>Review Your Order</h2>
      {order.length === 0 ? (
        <p>Your order is empty.</p>
      ) : (
        <ul>
          {order.map((item) => (
            <li key={item.id}>
              {item.name} - ${item.price.toFixed(2)}{" "}
              <button onClick={() => removeFromOrder(item.id)}>Remove</button>
            </li>
          ))}
        </ul>
      )}

      <p><strong>Total Chillies:</strong> {totalChillies}</p>
      <p><strong>Order Status:</strong> {anyHot ? "Hot dishes included" : "No hot dishes"}</p>
      <p><strong>Raw Total:</strong> ${rawTotal.toFixed(2)}</p>

      {orderResponse && orderResponse.discount > 0 && (
        <p style={{ color: "green" }}>
          Discount Applied: ${orderResponse.discount.toFixed(2)} <br />
          {orderResponse.message}
        </p>
      )}

      <div style={{ marginTop: 20 }}>
        <label>
          Your Name:{" "}
          <input
            type="text"
            value={customerName}
            onChange={(e) => setCustomerName(e.target.value)}
            placeholder="Enter your name"
          />
        </label>
      </div>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <button
        onClick={handleConfirmOrder}
        disabled={order.length === 0 || !customerName.trim()}
        style={{ marginTop: 20, padding: "10px 20px" }}
      >
        Confirm Order
      </button>

      {orderStatus && <p>{orderStatus}</p>}
    </div>
  );
};

export default CheckoutPage;
