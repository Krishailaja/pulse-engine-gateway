# 🚀 PulseEngine API Gateway

A high-performance, multi-tenant API Gateway built with **Spring Boot 3**, **Spring Security**, and **Redis**. Designed to handle centralized JWT authentication, tenant-level isolation, concurrent request management with bounded queueing, and strategy-based notification routing for microservice architectures.

---

## ✨ System Features

- **🔐 JWT Authentication:** Custom stateless authentication pipeline leveraging Spring Security filters and JJWT.
- **🏢 Multi-Tenant Isolation:** Dynamic tenant validation and isolation ensuring strict security boundaries across tenant data.
- **🚦 Concurrency Control & Bounded Queues:** Traffic management using bounded queues to handle burst traffic, prevent resource exhaustion, and manage concurrent requests gracefully.
- **🛠️ Extensible Strategy Pattern:** Modular notification engine that dynamically resolves and dispatches payloads (`Transactional`, `Campaign`, `OTP`).
- **⚡ High Performance Caching (In Progress):** Sub-millisecond token blacklisting and dynamic tenant validation via Redis.

---

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.x, Spring Security
- **Language:** Java 17+
- **Security:** JSON Web Tokens (JJWT)
- **Database:** MySQL, Spring Data JPA / Hibernate
- **Caching & Messaging:** Redis
- **Build Tool:** Maven

---

## 📂 Project Architecture & Package Structure

```text
com.pulseengine.gateway
├── 📁 config             # Security & App configuration (SecurityConfig, NotificationConfig)
├── 📁 controller         # REST API Endpoints (AuthController, NotificationController)
├── 📁 dto                # Request/Response payloads (CampaignRequest, TransactionalRequest, etc.)
├── 📁 enums              # Domain Enums (NotificationStatus, NotificationType, Role)
├── 📁 model              # JPA Domain Entities (User, Notification, UserChannel, UserPreference)
├── 📁 repository         # Data Access Layer (Spring Data JPA Repositories)
├── 📁 security           # Security implementations & JWT Filters
│   ├── 📁 jwt            # JwtUtils & Custom JwtAuthenticationFilter
│   └── 📁 services       # UserDetailsServiceImpl & UserDetailsImpl
└── 📁 service            # Business Logic Layer
    └── 📁 strategy       # Notification Strategies (Strategy Pattern implementation)
