import React, { useEffect, useState } from "react";
import Menu from "../components/Menu";
import Order from "../components/Order";
import { useNavigate } from "react-router-dom";

function HomePage() {
  const [menu, setMenu] = useState([]);
  const [order, setOrder] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
const navigate = useNavigate();

  useEffect(() => {
    fetch("http://localhost:8081/menu")
      .then((res) => {
        if (!res.ok) throw new Error("Failed to fetch menu");
        return res.json();
      })
      .then((data) => {
        setMenu(data);
        setLoading(false);
      })
      .catch((err) => {
        setError(err.message);
        setLoading(false);
      });
  }, []);

  const addToOrder = (item) => {
    setOrder((prev) => [...prev, item]);
  };

  const removeFromOrder = (index) => {
    const updated = [...order];
    updated.splice(index, 1);
    setOrder(updated);
  };

  const handleCheckout = () => {
    if (order.length === 0) {
      alert("Please add something to your order!");
      return;
    }
    navigate("/checkout", { state: { order } });
  };

  if (loading) return <p>Loading menu...</p>;
  if (error) return <p>Error: {error}</p>;

  return (
    <div>
      <h1>🍽️ Welcome to Our Restaurant</h1>
      <Menu items={menu} addToOrder={addToOrder} />
      <Order order={order} removeFromOrder={removeFromOrder} />
      <button onClick={handleCheckout}>Checkout</button>
    </div>
  );
}

export default HomePage;
