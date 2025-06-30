import React from "react";
import MenuItem from "./MenuItem";
import "./MenuList.css";

function MenuList({ menuItems, quantities, addToOrder, removeFromOrder }) {
  // Normalize categories to lowercase for grouping
  const groupedItems = menuItems.reduce((acc, item) => {
    const category = item.category.toLowerCase();
    if (!acc[category]) acc[category] = [];
    acc[category].push(item);
    return acc;
  }, {});

  const categoryTitles = {
    snacks: "Snacks",
    mains: "Mains",
    dessert: "Desserts",
  };

  return (
    <div className="menu-list">
      {Object.keys(groupedItems).map((categoryKey) => (
        <div key={categoryKey} className="category-section">
          <h2>{categoryTitles[categoryKey] || categoryKey}</h2>
          {groupedItems[categoryKey].map((item) => (
            <MenuItem
              key={item.id}
              item={item}
              quantity={quantities[item.name]}
              addToOrder={addToOrder}
              removeFromOrder={removeFromOrder}
            />
          ))}
        </div>
      ))}
    </div>
  );
}

export default MenuList;
