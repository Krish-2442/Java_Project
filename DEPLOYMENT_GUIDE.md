# 🚀 Ultimate Free Deployment Guide: Spring Boot + MySQL + Frontend

This guide will walk you through deploying your entire full-stack application **completely for free**, step-by-step. We will use **GitHub**, **Aiven/Railway** (for MySQL), **Render** (for the Spring Boot backend), and **Vercel** (for the frontend).

---

## 🏗️ Phase 1: Git & GitHub Setup

First, we need to push your local project to a GitHub repository.

### 1. Initialize Git (If not already done)
Open your terminal in the root folder of your project (where your backend and frontend reside, or do this separately for both if they are in different folders).

```bash
# Initialize a new git repository
git init

# Check the status of your files
git status
```

### 2. Create a `.gitignore` file
You must prevent large or sensitive folders from being pushed. Create a file named `.gitignore` in your root folder and add this:

```text
# Node
node_modules/
dist/
build/

# Spring Boot
target/
*.jar
.mvn/wrapper/maven-wrapper.jar

# IDEs and Env
.idea/
.vscode/
.env
application-local.properties
```

### 3. Push to GitHub
1. Go to [GitHub](https://github.com/) and create a **New Repository**. Leave it empty (do not add a README, .gitignore, or license from the UI).
2. Copy the repository URL (e.g., `https://github.com/your-username/your-repo.git`).
3. Run the following commands in your terminal:

```bash
# Add all files to staging
git add .

# Commit your files
git commit -m "Initial commit ✨"

# Link your local folder to GitHub
git remote add origin https://github.com/your-username/your-repo.git

# Set the main branch
git branch -M main

# Push your code to GitHub
git push -u origin main
```

---

## 🗄️ Phase 2: MySQL Database Setup (Free via Aiven)

Railway recently removed their permanent free tier without a credit card, but **Aiven** offers a highly reliable **completely free** MySQL database. 

### 1. Create the Database
1. Go to [Aiven](https://aiven.io/) and sign up with GitHub.
2. Click **Create Service** -> Choose **MySQL**.
3. Select the **Free Plan** (Hobbyist).
4. Select a region closest to you and click **Create Service**.

### 2. Get connection details
Once running, you will see a "Connection URI" or parameters. Keep these handy:
- **Host:** `xxx.aivencloud.com`
- **Port:** `12345`
- **User:** `avnadmin`
- **Password:** `your_password`
- **Database:** `defaultdb`

### 3. Import your `schema.sql`
Since you have a `schema.sql` file, you can import it to your new database. You can do this via the command line or any DB tool you use (like MySQL Workbench).

```bash
# From your terminal, run this command (replace with your DB host, port, user, and database name)
mysql -h xxx.aivencloud.com -P 12345 -u avnadmin -p defaultdb < schema.sql

# It will prompt you for the password. Paste it and press Enter.
```
*(If you do not have `mysql` installed on your machine, you can run the SQL script via a GUI like DBeaver or MySQL Workbench by pasting the text directly).*

---

## ⚙️ Phase 3: Prepare Backend (Spring Boot)

Before deploying the backend, we need to make it capable of connecting to your production DB and allowing requests from your frontend (CORS).

### 1. Update `application.properties`
Open your `src/main/resources/application.properties` (or `application.yml`). We will use Environment Variables here so you don't expose your password on GitHub.

```properties
# Server Port
server.port=${PORT:8080}

# Database Connection (Uses Environment Variables)
spring.datasource.url=jdbc:mysql://${DB_HOST}:${DB_PORT}/${DB_NAME}?useSSL=true&requireSSL=false
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate settings (Optional, ensures schema isn't accidentally overwritten)
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
```

### 2. Enable CORS (Crucial for connecting Frontend to Backend)
Your Spring Boot app will block your frontend otherwise. Create a new configuration class `CorsConfig.java`:

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                // Allow all endpoints, and specifically allow the Vercel app later
                registry.addMapping("/**")
                        .allowedOriginPatterns("*") // Better dynamic origin allowance
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }
}
```

### 3. Push the backend changes
```bash
git add .
git commit -m "Configure env vars and CORS for production"
git push
```

---

## 🚀 Phase 4: Deploy Backend (Render)

Render provides a fantastic free tier for Web Services (Sleeps after 15 mins of inactivity but it's 100% free).

### 1. Create Render Web Service
1. Go to [Render](https://render.com/) and sign up with GitHub.
2. Click **New** -> **Web Service**.
3. Select **Build and deploy from a Git repository**.
4. Connect to your GitHub repository.

### 2. Configure the Service
- **Name:** `my-springboot-backend`
- **Region:** Choose one close to you (and your DB).
- **Branch:** `main`
- **Root Directory:** If your Spring Boot code is inside a subfolder (like `backend/`), put that here. If it's in the root, leave blank.
- **Environment:** `Java` (or `Docker` if you wrote a Dockerfile)
- **Build Command:** `./mvnw clean package -DskipTests` *(Make sure you have `.mvn` folder and `mvnw` file in your repo)*
- **Start Command:** `java -jar target/*.jar`

### 3. Add Environment Variables (IMPORTANT)
Scroll down to **Environment Variables** and add the following keys with the values from your Aiven database:
- `DB_HOST`: `xxx.aivencloud.com`
- `DB_PORT`: `12345`
- `DB_USER`: `avnadmin`
- `DB_PASSWORD`: `your_password`
- `DB_NAME`: `defaultdb`

Click **Create Web Service**. 
*Wait 3-5 minutes for the build to finish. Once done, Render gives you a URL like: `https://my-springboot-backend.onrender.com`. Save this!*

---

## 🌐 Phase 5: Deploy Frontend (Vercel)

Vercel is the easiest and fastest completely free frontend hosting provider.

### 1. Update Frontend API Base URL
In your frontend code (React/Vue/HTML), you are probably calling `http://localhost:8080/api...`.
You need to change this to point to an environment variable so that locally it uses localhost, but on production, it uses Render.

If you are using **Vite (React/Vue)**:
Create a `.env` file locally: `VITE_API_BASE_URL=http://localhost:8080`

In your API calls: 
```javascript
const API_URL = import.meta.env.VITE_API_BASE_URL;

fetch(`${API_URL}/endpoint`)
```

### 2. Push Frontend to GitHub
```bash
git add .
git commit -m "Update frontend API URL for production"
git push
```

### 3. Deploy to Vercel
1. Go to [Vercel](https://vercel.com/) and login with GitHub.
2. Click **Add New** -> **Project**.
3. Import your GitHub repository. (If your frontend is in a subfolder, select it in "Root Directory").
4. Under **Framework Preset**, Vercel will usually auto-detect your stack (e.g., Vite, Create React App, HTML).
5. Open **Environment Variables** and add:
   - **Name:** `VITE_API_BASE_URL` (or whatever prefix your framework uses, like `REACT_APP_API_BASE_URL`)
   - **Value:** `https://my-springboot-backend.onrender.com` *(Do NOT add a trailing slash `/`)*
6. Click **Deploy**.

*Within 2 minutes, Vercel will give you a live URL like `https://my-frontend.vercel.app`.*

---

## 🛠️ Common Errors & Fixes

1. **Backend Render Build Failed: "mvnw command not found"**
   - **Fix:** Your repository is missing the maven wrapper. Run `mvn wrapper:wrapper` locally, then commit and push the `.mvn` folder and `mvnw` / `mvnw.cmd` files to GitHub. Also ensure the file has execute permissions: `git update-index --chmod=+x mvnw`, then commit.

2. **Backend Starts but Fails: "Cannot load driver class: com.mysql.cj.jdbc.Driver"**
   - **Fix:** Make sure you have the MySQL dependency in your `pom.xml`:
     ```xml
     <dependency>
         <groupId>com.mysql</groupId>
         <artifactId>mysql-connector-j</artifactId>
         <scope>runtime</scope>
     </dependency>
     ```

3. **Frontend Network Error / CORS Policy Blocked**
   - **Fix:** Ensure you added the `CorsConfig.java` to your Spring Boot project. Also, verify that the backend is currently awake (Render free tier sleeps after 15 minutes, so the first request might take 50 seconds to wake it up).

4. **Frontend API returns 404**
   - **Fix:** Double check Vercel environment variables. Make sure your `VITE_API_BASE_URL` exactly matches Render's URL. e.g. `https://your-app.onrender.com`. No extra `/` at the end!

## 🎉 Summary Checklist
- [x] Code pushed to GitHub
- [x] Schema imported into Aiven/Railway MySQL
- [x] Backend connects to DB using `.properties` env vars
- [x] React/Vue frontend hits Render backend via env vars
- [x] App deployed fully free!
