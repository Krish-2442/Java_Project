# Fleet Management System

A full-stack application featuring a Spring Boot backend, Vanilla HTML/JS frontend, and a MySQL database.

## Project Structure

This project has been restructured for optimal cloud deployment:
- `/frontend`: The UI. Ready to be deployed on **Vercel**.
- `/backend`: The REST API. Ready to be deployed on **Render** (via Docker).
- `/database`: The MySQL Schema. Ready to be used with **Railway MySQL**.

---

## 🚀 Deployment Instructions

### 1. Database Setup (Railway)
1. Go to [Railway.app](https://railway.app/) and create a new project with a **MySQL plugin**.
2. Connect to the database using any SQL Client (e.g., DBeaver, MySQL Workbench) using the provided connect credentials.
3. Execute the `database/schema.sql` script to create all required tables, triggers, and stored procedures.
4. Note your database connection parameters: `MYSQL_URL` (format: jdbc:mysql://host:port/dbname), `MYSQL_USER`, and `MYSQL_PASSWORD`.

### 2. Backend Deployment (Render)
1. Go to [Render.com](https://render.com/) and create a new **Web Service**.
2. Connect this repository.
3. Render will automatically detect the `Dockerfile` in the `/backend` folder. Make sure the Root Directory is set to `backend`.
4. Add the following **Environment Variables**:
   - `DB_URL`: The JDBC URL from Railway (e.g., `jdbc:mysql://containers...:3306/railway?useSSL=false`)
   - `DB_USER`: The MySQL username.
   - `DB_PASS`: The MySQL password.
   - `PORT`: `8080` (Render will map this automatically).
5. Deploy the application and copy your assigned Render URL (e.g., `https://fleet-backend.onrender.com`).

### 3. Frontend Deployment (Vercel)
1. Go to [Vercel.com](https://vercel.com/) and create a new **Project**.
2. Connect this repository.
3. Vercel will auto-detect Vite. Ensure the **Root Directory** is set to `frontend`.
4. Add the following **Environment Variable**:
   - `VITE_API_URL`: Your Render backend URL appending `/api` (e.g., `https://fleet-backend.onrender.com/api`).
5. Deploy the application. Vercel will build the frontend and serve it globally.

---

## 🛠 Local Development

1. Start your local MySQL server and create a database named `fleet_management_db`.
2. To run the backend, navigate to `/backend` and run:
   ```bash
   mvn spring-boot:run
   ```
3. To run the frontend, navigate to `/frontend` and run:
   ```bash
   npm install
   npm run dev
   ```

## License
MIT
