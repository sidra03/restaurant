# Restaurant Menu Application

This is a simple Spring Boot application for managing a restaurant’s menu items. The app loads initial menu data from a JSON file into the database when the application starts.

---

## Features

- Loads initial menu items from `menu.json` into the database on first run.
- Uses Spring Data JPA for database access.
- Menu items are stored in a relational database.
- Easy to extend with APIs for CRUD operations on menu items.

---

## Technologies Used

- Java 17+
- Spring Boot
- Spring Data JPA
- Hibernate
- H2 (default in-memory DB, can be changed)
- Jackson (for JSON parsing)
- Lombok

---

## Getting Started

### Prerequisites

- Java JDK 17 or newer installed
- Maven or Gradle build tool
- IDE such as IntelliJ IDEA or Eclipse (optional)

---

### Setup and Run

1. **Clone the repository:**

   ```bash
   git clone https://github.com/sidra03/restaurant.git
   cd restaurant
---

## How It Works

- On application startup, the `MenuDataLoader` component runs automatically.
- It checks if the menu table in the database is empty.
- If empty, it loads menu items from `menu.json` and saves them into the database.
- After the initial load, the database becomes the source of truth for menu data.
- Any future changes to menu items should be made directly in the database (or through APIs if implemented).

---

## Extending the Application

Here are some ideas to enhance the application:

- Add RESTful API endpoints to create, read, update, and delete (CRUD) menu items.
- Implement authentication and authorization to restrict admin functions.
- Support multiple menus or restaurants with user roles.
- Add features like search, filtering by category, and pagination.
- Integrate with payment gateways or order management systems.

---

## Troubleshooting

- **Menu data not loading?**
    - Verify that `menu.json` exists in `src/main/resources` and contains valid JSON.
    - Check the logs for any JSON parsing errors.

- **Database errors?**
    - Confirm your datasource configuration in `application.properties`.
    - Ensure the database server is running and accessible.
    - For H2 in-memory DB, data will reset on each app restart — consider switching to a persistent DB.

- **Lombok annotations not working?**
    - Make sure your IDE has the Lombok plugin installed.
    - Enable annotation processing in your IDE settings.

- **Application fails to start?**
    - Look at the console logs for stack traces.
    - Check that all dependencies are correctly resolved.

---