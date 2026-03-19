# Local Running Plan (Step-by-Step)

Follow this plan to run your Fleet Management System locally on your Mac.

---

## Step 1: Start the Database 🐬
Your Spring Boot application connects to a local MySQL instance using your credentials (root / Krish#244).

1. Open your terminal or MySQL client.
2. Ensure your MySQL server service is running. 
   *(If you installed via Homebrew, you can start it with: `brew services start mysql`)*
3. Make sure the database `fleet_management_db` exists.
   *(If it doesn't, create it and import the `database/schema.sql` file using your preferred tool like DBeaver or MySQL Workbench).*

---

## Step 2: Run the Backend API ☕
Now that the database is ready, start the Spring Boot API.

1. Open your **Terminal App**.
2. Navigate into the `backend` folder:
   ```bash
   cd "/Users/krishgadara/Desktop/java_assignment_AntiGravity copy/backend"
   ```
3. Run the application using your global Maven installation:
   ```bash
   mvn spring-boot:run
   ```
4. Wait for the logs to display: `Started FleetManagementApplication`.
5. Your backend is now running at: `http://localhost:8080/api`.

*(Keep this terminal window open!)*

---

## Step 3: Run the Frontend UI 🌐
The frontend uses Vite to bundle the static files and inject environment variables.

1. Open a **new Terminal window** (leave the backend running in the first one).
2. Navigate into the `frontend` folder:
   ```bash
   cd "/Users/krishgadara/Desktop/java_assignment_AntiGravity copy/frontend"
   ```
3. Install Vite and dependencies (first time only):
   ```bash
   npm install
   ```
4. Start the Vite development server:
   ```bash
   npm run dev
   ```
5. The terminal will output a local address (usually `http://localhost:5173`).

---

## Step 4: Verify Full Functionality 🚀
1. Open your web browser and go to the frontend URL (e.g., `http://localhost:5173`).
2. Look at the top right of the navigation bar. You should see a green dot with **"API Connected"**.
3. Navigate between the "Vehicles", "Drivers", and "Trips" tabs.
4. Try adding a new Vehicle or Driver to confirm the frontend can write to your local MySQL database.

---
**Troubleshooting Tips:**
- **Port in use (8080):** If Spring Boot fails because port 8080 is already used, find and kill the process or change the port in `backend/src/main/resources/application.properties`.
- **Database Connection Error:** Verify your MySQL server is running and the password `Krish#244` is correct.
- **npm command not found:** Ensure Node.js is installed on your Mac (`brew install node`).
