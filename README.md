# Getting Started

This project implements a RESTful API in **Spring Boot 3.5.0 (Java 21)** with features like:

- ✅ Redis caching with fallback
- ✅ Resilience via retries
- ✅ Global error handling
- ✅ Rate limiting (3 RPM)

---

### 🚀 How to Run this Application

This application includes a `docker` folder that contains everything you need to run the service locally using Docker and Docker Compose.

#### ✅ Requirements

- [Docker](https://www.docker.com/products/docker-desktop)
- [Java 21](https://adoptium.net/) (only needed if you want to build or run the project outside Docker)
- [Postman](https://www.postman.com/downloads/) (for testing endpoints, optional)

---

#### 📦 Dependencies

- **OpenJDK 21**
- **Spring Boot 3.5.0**
- **Docker**
- **PostgreSQL** (handled by docker)
- **Redis** (handled by docker)
- **json-server** (mock service handled by docker)
- **Bucket4j** (rate limiting in local instance, but It could be changed it using bucket4j-redis)
---

#### 🛠️ Running the App

1. Clone the repository:

```bash
git clone https://github.com/your-username/tenpo-challenge.git
cd tenpo-challenge
```

2. Build and run the containers:

```bash
docker compose up --build
```

This will start:
- PostgreSQL (database)
- Redis (cache)
- Percentage service (mock)
- The main application API (`http://localhost:8080`)

---

#### 📬 Testing the API

You can use the included Postman collection to explore and test the endpoints.

> ✅ The Postman collection is located at:
>
> ```
> tenpo-challenge-collection.postman_collection.json
> ```

To use it:

1. Open [Postman](https://www.postman.com/)
2. Click **"Import"**
3. Choose the file `tenpo-challenge-collection.postman_collection.json`
4. Run the requests defined for:
    - `/calculate`
    - `/logs`

---
