# 🚀 Smart Backend — Production Ready URL Shortener

Smart Backend is a **production-grade backend system** built using **Spring Boot** that demonstrates modern distributed system design patterns such as caching, rate limiting, and database integrity.

This project goes beyond a simple CRUD application and showcases how real-world backend services are designed for **scalability, reliability, and performance** — making it highly valuable for **interviews and system design discussions**.

---

# 🧠 Architecture Overview

```
Client → Controller → Service → Repository → PostgreSQL
                         ↓
                       Redis
```

---

# 🔥 Production Features

✅ Distributed URL Shortener  
✅ Redis Caching (sub-millisecond reads)  
✅ Collision-safe Base62 short code generation  
✅ Database uniqueness constraints  
✅ Clean layered architecture  
✅ Environment-based configuration (.env)  
✅ Structured logging  
✅ Retry mechanism for code generation

---

# 🚦 Rate Limiting (Pluggable / Ready)

Smart Backend is designed to support **Redis-based rate limiting** to protect APIs from abuse and sudden traffic spikes.

### Why it matters:
- Prevents API flooding
- Protects database from overload
- Improves system stability
- Essential for public-facing APIs

### Example Policy:
```
100 requests / minute per IP
```

### Flow:
1. Request hits API
2. Token availability checked in Redis
3. If limit exceeded → **HTTP 429 (Too Many Requests)**

---

# ⚡ Tech Stack

### Backend
- Java 17
- Spring Boot
- Spring Data JPA
- Hibernate

### Database
- PostgreSQL (Neon Serverless)

### Cache
- Redis

### Build Tool
- Maven

---

# 📂 Project Structure

```
com.dev.smart.backend.urlshortener
 ├── controller
 ├── service
 ├── repository
 ├── entity
 └── dto
```

Clean separation of concerns ensures maintainability and scalability.

---

# 🔗 API Endpoints

## Create Short URL

**POST** `/api/v1/shorten`

Request:
```json
{
  "originalUrl": "https://google.com"
}
```

Response:
```
abc123
```

---

## Redirect to Original URL

**GET** `/api/v1/{shortCode}`

Returns HTTP **302 Redirect** to the original URL.

---

# ⚙️ Local Setup

## 1️⃣ Clone Repository

```
git clone https://github.com/<your-username>/smart-backend.git
cd smart-backend
```

---

## 2️⃣ Configure Environment Variables

Create a `.env` file:

```
DB_URL=jdbc:postgresql://<host>/<db>?sslmode=require&currentSchema=smart
DB_USERNAME=your_user
DB_PASSWORD=your_password

REDIS_HOST=localhost
REDIS_PORT=6379
```

---

## 3️⃣ Run Redis (Docker Recommended)

```
docker run -p 6379:6379 redis
```

---

## 4️⃣ Start Application

```
mvn spring-boot:run
```

Server starts on:

```
http://localhost:8080
```

---

# 🧱 Database Schema

Ensure schema exists:

```sql
CREATE SCHEMA IF NOT EXISTS smart;
```

Table is auto-created by Hibernate.

---

# 🎯 What This Project Demonstrates

This project highlights strong backend engineering fundamentals:

✅ Designing scalable services  
✅ Using cache effectively  
✅ Preventing database collisions  
✅ Writing production-style code  
✅ Applying distributed system patterns  
✅ Structuring enterprise-grade Spring Boot apps

---

# 💡 Interview Talking Points

You can confidently discuss:

- How Redis reduces database load
- Cache-aside pattern
- Short code collision handling
- Database indexing strategy
- Rate limiting approaches
- Horizontal scalability
- API protection techniques

👉 This positions you closer to an **SDE-2 / Senior Backend Engineer mindset**.

---

# 🔮 Future Enhancements

- Token Bucket Rate Limiter (Redis Lua)
- Analytics dashboard (click tracking)
- Custom aliases
- Link expiration
- Kafka-based event streaming
- Notification system
- Kubernetes deployment
- API Gateway integration

---

# 👨‍💻 Author

**Vignesh Kanna P**  
Java Backend Engineer | Distributed Systems Enthusiast

LinkedIn: https://linkedin.com/in/vignesh-kanna-p/

---

⭐ If you found this project useful, consider giving it a star!
