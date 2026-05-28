# Chatify - Backend API

Ứng dụng backend REST API cho hệ thống nhắn tin thực thời sử dụng Quarkus, một framework Java siêu nhanh. Dự án này cung cấp các endpoint API RESTful, WebSocket real-time và quản lý cơ sở dữ liệu với PostgreSQL.

*A REST API backend for real-time messaging system using Quarkus, a supersonic Java framework. This project provides RESTful API endpoints, WebSocket real-time communication, and database management with PostgreSQL.*

---

## 🎯 Giới Thiệu (Overview)

Chatify Backend là một ứng dụng Java hiệu suất cao được xây dựng trên nền tảng Quarkus. Nó được thiết kế để xử lý:
- Xác thực người dùng với JWT (JSON Web Token)
- Giao tiếp thời gian thực qua WebSocket
- Các API RESTful cho quản lý người dùng và tin nhắn
- Lưu trữ dữ liệu với Hibernate ORM Panache

*Chatify Backend is a high-performance Java application built on the Quarkus framework. It is designed to handle:*
- *User authentication with JWT (JSON Web Token)*
- *Real-time communication via WebSocket*
- *RESTful APIs for user and message management*
- *Data persistence with Hibernate ORM Panache*

---

## 🚀 Tính Năng Chính (Key Features)

- **Quarkus Framework**: Framework Java hiệu suất cực cao với startup nhanh và dung lượng bộ nhớ tối thiểu
  - *Quarkus Framework: Supersonic Java framework with fast startup and minimal memory footprint*

- **WebSocket Communication**: Hỗ trợ giao tiếp thời gian thực hai chiều
  - *WebSocket Communication: Support for real-time bidirectional communication*

- **REST API**: Dễ dàng xây dựng các endpoint RESTful với Jackson serialization
  - *REST API: Easy building of RESTful endpoints with Jackson serialization*

- **Security**: Bảo mật ứng dụng với SmallRye JWT
  - *Security: Application security with SmallRye JWT*

- **Database**: Hibernate ORM Panache cho quản lý dữ liệu dễ dàng
  - *Database: Hibernate ORM Panache for easy data management*

- **PostgreSQL Support**: Kết nối trực tiếp đến PostgreSQL via JDBC
  - *PostgreSQL Support: Direct connection to PostgreSQL via JDBC*

- **Native Build**: Hỗ trợ biên dịch sang native executable với GraalVM
  - *Native Build: Support for compiling to native executable with GraalVM*

- **Testing**: Bao gồm unit tests và integration tests
  - *Testing: Includes unit tests and integration tests*

---

## 📋 Yêu Cầu (Prerequisites)

Trước khi bắt đầu, hãy đảm bảo bạn đã cài đặt:
- **Java 17** trở lên
- **Maven 3.9.0** trở lên (hoặc sử dụng Maven wrapper)
- **PostgreSQL 12** trở lên (cho cơ sở dữ liệu)
- **GraalVM** (tuỳ chọn, cho native build)

*Before you begin, ensure you have installed:*
- *Java 17 or higher*
- *Maven 3.9.0 or higher (or use Maven wrapper)*
- *PostgreSQL 12 or higher (for database)*
- *GraalVM (optional, for native build)*

---

## 🛠️ Cài Đặt (Installation)

### 1. Sao chép repository (Clone the repository)
```bash
git clone https://github.com/lannguyen151005/chatify.git
cd chatify
```

### 2. Cấu hình cơ sở dữ liệu (Configure database)

Tạo file `src/main/resources/application.properties` hoặc sửa đổi nếu đã tồn tại:

*Create or modify `src/main/resources/application.properties`:*

```properties
# PostgreSQL Configuration
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=postgres
quarkus.datasource.password=your_password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/chatify_db

# Hibernate Configuration
quarkus.hibernate-orm.database.generation=update
quarkus.hibernate-orm.dialect=org.hibernate.dialect.PostgreSQL10Dialect

# JWT Configuration
mp.jwt.verify.publickey.location=publicKey.pem
mp.jwt.verify.issuer=https://chatify.example.com

# Port Configuration
quarkus.http.port=8080
```

### 3. Tạo cơ sở dữ liệu (Create database)

```bash
psql -U postgres
CREATE DATABASE chatify_db;
\q
```

---

## 💻 Phát Triển (Development)

### Khởi động ứng dụng ở chế độ phát triển (Run in dev mode)

```bash
./mvnw quarkus:dev
```

Ứng dụng sẽ chạy tại `http://localhost:8080`

*The application will run at `http://localhost:8080`*

**Dev UI** có sẵn tại: `http://localhost:8080/q/dev/`

*Dev UI is available at: `http://localhost:8080/q/dev/`*

### Tính năng Live Coding

Trong chế độ phát triển, bạn có thể sửa đổi code và thay đổi sẽ được tự động tải lại mà không cần khởi động lại ứng dụng.

*In development mode, you can modify code and changes will be automatically reloaded without restarting the application.*

### Chạy các test (Run tests)

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify
```

---

## 📦 Cấu Trúc Dự Án (Project Structure)

```
chatify/
├── src/
│   ├── main/
│   │   ├── java/alan/nguyen/
│   │   │   ├── GreetingResource.java       # REST Endpoint example
│   │   │   ├── MyEntity.java               # JPA Entity example
│   │   │   └── StartWebSocket.java         # WebSocket endpoint
│   │   └── resources/
│   │       └── application.properties      # Configuration
│   └── test/
│       └── java/alan/nguyen/
│           ├── GreetingResourceTest.java   # Unit tests
│           └── GreetingResourceIT.java     # Integration tests
├── target/                                  # Build output
├── pom.xml                                 # Maven configuration
└── README.md                               # This file
```

---

## 🎨 Stack Công Nghệ (Technology Stack)

| Công Nghệ | Phiên Bản | Mục Đích |
|-----------|---------|---------|
| Quarkus | 3.34.3 | Java framework - Core framework |
| Java | 17+ | Programming language |
| Jakarta REST | Latest | RESTful web services |
| Jakarta WebSocket | Latest | Real-time communication |
| Hibernate ORM Panache | 3.34.3 | Database ORM |
| SmallRye JWT | Latest | JWT authentication |
| PostgreSQL JDBC | Latest | Database driver |
| Jackson | Latest | JSON serialization |
| JUnit 5 | Latest | Unit testing |
| REST Assured | Latest | API testing |

---

## 🔧 Các Tệp Cấu Hình (Configuration Files)

### pom.xml
Maven configuration file chứa tất cả dependencies và build plugins.

*Maven configuration file containing all dependencies and build plugins.*

### application.properties
Cấu hình Quarkus, datasource, JWT, và các thuộc tính ứng dụng khác.

*Quarkus configuration, datasource, JWT, and other application properties.*

---

## 📝 Các API Endpoint Chính (Main API Endpoints)

### REST Endpoints

```
GET  /hello                    # Greeting endpoint
```

### WebSocket Endpoints

```
ws://localhost:8080/start-websocket/{name}    # WebSocket connection
```

---

## 🏗️ Xây Dựng (Build)

### Xây dựng ứng dụng thường (Build standard JAR)

```bash
./mvnw package
```

Kết quả: `target/quarkus-app/quarkus-run.jar`

*Result: `target/quarkus-app/quarkus-run.jar`*

Chạy ứng dụng:

*Run the application:*

```bash
java -jar target/quarkus-app/quarkus-run.jar
```

### Xây dựng Über-JAR (Build Über-JAR)

Tạo một file JAR đơn lẻ chứa tất cả dependencies:

*Create a single JAR file containing all dependencies:*

```bash
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

Chạy ứng dụng:

*Run the application:*

```bash
java -jar target/*-runner.jar
```

### Xây dựng Native Executable (Build Native Executable)

Biên dịch thành native binary (yêu cầu GraalVM):

*Compile to native binary (requires GraalVM):*

```bash
./mvnw package -Dnative
```

Hoặc trong container:

*Or in container:*

```bash
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

Chạy native executable:

*Run native executable:*

```bash
./target/chatify-1.0.0-SNAPSHOT-runner
```

---

## 📊 Thành Phần Ngôn Ngữ (Language Composition)

- **Java**: 100%

---

## 🚀 Triển Khai (Deployment)

### Triển khai trên Docker (Deploy with Docker)

Tạo `Dockerfile`:

*Create `Dockerfile`:*

```dockerfile
FROM openjdk:17-jdk-slim
COPY target/quarkus-app/lib/ /deployments/lib/
COPY target/quarkus-app/*.jar /deployments/
EXPOSE 8080
CMD ["java", "-jar", "/deployments/quarkus-run.jar"]
```

Xây dựng và chạy:

*Build and run:*

```bash
docker build -t chatify:latest .
docker run -p 8080:8080 -e QUARKUS_DATASOURCE_JDBC_URL=jdbc:postgresql://postgres:5432/chatify_db chatify:latest
```

### Triển khai trên Kubernetes (Deploy on Kubernetes)

```bash
kubectl apply -f k8s/deployment.yaml
kubectl apply -f k8s/service.yaml
```

### Triển khai trên Cloud Platform (Deploy on Cloud Platform)

- **Heroku**: Sử dụng buildpack Heroku cho Quarkus
- **AWS**: Deploy trên EC2, ECS, hoặc Lambda
- **Google Cloud**: Deploy trên Cloud Run hoặc App Engine
- **Azure**: Deploy trên App Service

---

## 📚 Tài Nguyên Học Tập (Learning Resources)

- [Quarkus Official Documentation](https://quarkus.io/guides/)
  - *Tài liệu chính thức Quarkus với hàng trăm hướng dẫn*

- [Jakarta REST Guide](https://quarkus.io/guides/rest)
  - *Hướng dẫn xây dựng REST API*

- [WebSocket Guide](https://quarkus.io/guides/websockets)
  - *Hướng dẫn cấu hình WebSocket*

- [Hibernate ORM Panache](https://quarkus.io/guides/hibernate-orm-panache)
  - *Hướng dẫn sử dụng Hibernate Panache*

- [SmallRye JWT](https://quarkus.io/guides/security-jwt)
  - *Hướng dẫn cấu hình JWT authentication*

- [PostgreSQL JDBC Guide](https://quarkus.io/guides/datasource)
  - *Hướng dẫn kết nối PostgreSQL*

---

## 🧪 Kiểm Thử (Testing)

### Unit Tests

```bash
./mvnw test
```

### Integration Tests

```bash
./mvnw verify
```

### Test Coverage

```bash
./mvnw jacoco:report
```

---

## 🤝 Đóng Góp (Contributing)

Chúng tôi hoan nghênh các đóng góp! Vui lòng tự do:

*Contributions are welcome! Please feel free to:*

1. **Fork repository**
   - Tạo một bản sao của repository

2. **Tạo nhánh tính năng** (Create a feature branch)
   ```bash
   git checkout -b feature/amazing-feature
   ```

3. **Commit các thay đổi của bạn** (Commit your changes)
   ```bash
   git commit -m 'Add amazing feature'
   ```

4. **Đẩy đến nhánh** (Push to the branch)
   ```bash
   git push origin feature/amazing-feature
   ```

5. **Mở một Pull Request** (Open a Pull Request)
   - Mô tả chi tiết về những thay đổi của bạn

---

## 📄 Giấy Phép (License)

Dự án này không có giấy phép được chỉ định. Vui lòng liên hệ với chủ sở hữu repository để biết thông tin giấy phép.

*This project does not have a specified license. Please check with the repository owner for licensing information.*

---

## 👤 Tác Giả (Author)

- **GitHub**: [@lannguyen151005](https://github.com/lannguyen151005)

---

## 📞 Hỗ Trợ (Support)

Để báo cáo vấn đề, câu hỏi hoặc gợi ý, vui lòng mở một [issue](https://github.com/lannguyen151005/chatify/issues) trên GitHub.

*For issues, questions, or suggestions, please open an [issue](https://github.com/lannguyen151005/chatify/issues) on GitHub.*

---

## 🔗 Liên Kết Liên Quan (Related Links)

- [Frontend Repository](https://github.com/lannguyen151005/chatify_frontend) - Next.js Frontend
- [Quarkus Official Website](https://quarkus.io)
- [Jakarta EE Documentation](https://jakarta.ee)

---

**Cập Nhật Lần Cuối (Last Updated)**: May 28, 2026

**Status**: Active Development 🚀

Made with ❤️ by the Chatify team
