# E-commerce REST API with Spring Boot & CI/CD Integration

Developed a robust e-commerce REST API using Spring Boot, offering comprehensive features for an online store:

☞ User authentication and authorization

☞ Product management

☞ Image upload and download capabilities

☞ Shopping cart operations

☞ Order processing

☞ Implemented Continuous Integration and Continuous Deployment (CI/CD) using a local GitLab host on an Ubuntu server.

![Gitlab CI/CD](https://i.imgur.com/bOMnlN5.png)

First of all, you need to comment out two lines: `createDefaultUserIfNotExists()` and `createDefaultAdminIfNotExists()` to avoid conflicts when no roles exist in your database. After that, you can uncomment and rerun the project to obtain some sample user and admin accounts.

![sample comment code for adding data to project](https://i.imgur.com/fZkyvCb.png)

After that, you need to follow tutorial in `example.evn` file correct your information in `local.evn` file

![sample environment setup](https://i.imgur.com/eLH7wfM.png)

##### That all, have fun !

## 🚀 Features

- **Authentication & Authorization**
  - JWT-based authentication
  - Role-based access control
  - Secure password handling with BCrypt

- **Product Management**
  - CRUD operations for products
  - Category management
  - Image upload and management
  - Product search and filtering

- **Shopping Experience**
  - Shopping cart functionality
  - Order management
  - Cart item manipulation

- **User Management**
  - User registration and authentication
  - Profile management
  - Role-based permissions

## 🛠️ Tech Stack

- **Framework:** Spring Boot 3.4.1
- **Security:** Spring Security with JWT
- **Database:** MySQL
- **Build Tool:** Maven
- **Java Version:** 17
- **Additional Libraries:**
  - Lombok
  - ModelMapper
  - JJWT
  - Spring HATEOAS

## 📋 Prerequisites

- JDK 21 or later
- Maven 3.6+
- MySQL Server
- Your favorite IDE (IntelliJ IDEA recommended)

## 🔧 Configuration

1. Clone the repository:
```bash
git clone [repository-url]
```

2. Create a `local.env` file in the root directory with the following properties:
```properties
DB_URL=your_database_url
DB_USERNAME=your_database_username
DB_PASSWORD=your_database_password
JWT_SECRET=your_jwt_secret_key
JWT_EXPIRATION=token_expiration_time_in_minutes
```

3. Configure your MySQL database and update the connection details in `local.env`

## 🚀 Running the Application

1. Build the project:
```bash
mvn clean install
```

2. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at `http://localhost:8080`

## 🔒 API Security

The API uses JWT (JSON Web Token) for authentication. Protected endpoints require a valid JWT token in the Authorization header:
```
Authorization: Bearer your_jwt_token
```

## 📝 API Endpoints

### Authentication
- `POST /api/v1/auth/login` - User login
- `POST /api/v1/auth/register` - User registration

### Products
- `GET /api/v1/products` - List all products
- `GET /api/v1/products/{id}` - Get product details
- `POST /api/v1/products/add` - Add new product (ADMIN)
- `PUT /api/v1/products/{id}` - Update product (ADMIN)
- `DELETE /api/v1/products/{id}` - Delete product (ADMIN)

### Categories
- `GET /api/v1/categories` - List all categories
- `POST /api/v1/categories` - Create category (ADMIN)
- `PUT /api/v1/categories/{id}` - Update category (ADMIN) 
- `DELETE /api/v1/categories/{id}` - Delete category (ADMIN)

### Cart & Orders
- `GET /api/v1/carts` - View cart (Authenticate)
- `POST /api/v1/cartItems` - Add item to cart  (Authenticate)
- `PUT /api/v1/cartItems/{id}` - Update cart item  (Authenticate)
- `DELETE /api/v1/cartItems/{id}` - Remove item from cart (Authenticate)
- `POST /api/v1/orders` - Create order  (Authenticate)
- `GET /api/v1/orders` - List orders  (Authenticate)

### Users
- `GET /api/v1/users/profile` - Get user profile  (Authenticate)
- `PUT /api/v1/users/profile` - Update user profile  (Authenticate)

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the LICENSE file for details.

## 👥 Authors

- **TrungBeSo** - *Initial work*

## 🙏 Acknowledgments

- Spring Boot team for the amazing framework
- The open-source community for their invaluable contributions
