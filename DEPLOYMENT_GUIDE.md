# Deployment Guide - SCI Projects Archive

This guide provides step-by-step instructions for deploying the SCI Projects Archive application in different environments.

## Table of Contents
1. [Local Development Setup](#local-development-setup)
2. [Production Deployment](#production-deployment)
3. [Docker Deployment](#docker-deployment)
4. [Cloud Deployment](#cloud-deployment)

---

## Local Development Setup

### Prerequisites
- Java 17 or higher
- Maven 3.6+
- Git

### Steps

1. **Clone the Repository**
```bash
git clone <repository-url>
cd sci-projects-archive
```

2. **Configure Application**

The default configuration uses H2 in-memory database. No additional setup required.

File: `src/main/resources/application.properties`

3. **Build the Application**
```bash
mvn clean install
```

4. **Run the Application**
```bash
mvn spring-boot:run
```

5. **Access the Application**
- API Base URL: http://localhost:8080/api
- H2 Console: http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:file:./data/scidb`
  - Username: `sa`
  - Password: `password`

6. **Test the API**
```bash
# Register a user
curl -X POST http://localhost:8080/api/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "username": "testuser",
    "email": "test@sci.edu",
    "password": "password123",
    "fullName": "Test User",
    "role": "STUDENT",
    "studentId": "TEST001"
  }'
```

---

## Production Deployment

### Prerequisites
- Java 17 Runtime Environment
- MySQL 8.0+
- Server with at least 2GB RAM
- Domain name (optional)

### 1. Database Setup

**Install MySQL**
```bash
# Ubuntu/Debian
sudo apt update
sudo apt install mysql-server

# Start MySQL
sudo systemctl start mysql
sudo systemctl enable mysql
```

**Create Database**
```sql
# Login to MySQL
mysql -u root -p

# Create database
CREATE DATABASE sci_projects CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

# Create user
CREATE USER 'sciapp'@'localhost' IDENTIFIED BY 'your_secure_password';

# Grant privileges
GRANT ALL PRIVILEGES ON sci_projects.* TO 'sciapp'@'localhost';
FLUSH PRIVILEGES;

EXIT;
```

### 2. Application Configuration

**Create production configuration file:**

`src/main/resources/application-prod.properties`
```properties
# Server Configuration
server.port=8080

# MySQL Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/sci_projects?useSSL=false&serverTimezone=UTC
spring.datasource.username=sciapp
spring.datasource.password=your_secure_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# File Upload Configuration
spring.servlet.multipart.max-file-size=50MB
spring.servlet.multipart.max-request-size=50MB
file.upload-dir=/var/sci-projects/uploads

# JWT Configuration
jwt.secret=CHANGE_THIS_TO_A_SECURE_RANDOM_STRING_AT_LEAST_256_BITS
jwt.expiration=86400000

# Logging
logging.level.com.sci.archive=INFO
logging.file.name=/var/log/sci-projects/application.log
```

### 3. Build Application

```bash
# Build JAR file
mvn clean package -DskipTests

# JAR file will be in target/projects-archive-1.0.0.jar
```

### 4. Prepare Server

```bash
# Create application directory
sudo mkdir -p /opt/sci-projects
sudo mkdir -p /var/sci-projects/uploads
sudo mkdir -p /var/log/sci-projects

# Copy JAR file
sudo cp target/projects-archive-1.0.0.jar /opt/sci-projects/

# Set permissions
sudo chmod 755 /opt/sci-projects/projects-archive-1.0.0.jar
sudo chown -R $USER:$USER /var/sci-projects
sudo chown -R $USER:$USER /var/log/sci-projects
```

### 5. Create Systemd Service

**Create service file:** `/etc/systemd/system/sci-projects.service`

```ini
[Unit]
Description=SCI Projects Archive
After=syslog.target network.target mysql.service

[Service]
User=your_user
ExecStart=/usr/bin/java -jar /opt/sci-projects/projects-archive-1.0.0.jar --spring.profiles.active=prod
SuccessExitStatus=143
Environment="JAVA_OPTS=-Xms512m -Xmx1024m"

[Install]
WantedBy=multi-user.target
```

**Enable and start service:**
```bash
sudo systemctl daemon-reload
sudo systemctl enable sci-projects
sudo systemctl start sci-projects

# Check status
sudo systemctl status sci-projects

# View logs
sudo journalctl -u sci-projects -f
```

### 6. Nginx Reverse Proxy (Optional but Recommended)

**Install Nginx**
```bash
sudo apt install nginx
```

**Configure Nginx:** `/etc/nginx/sites-available/sci-projects`

```nginx
server {
    listen 80;
    server_name your-domain.com;

    client_max_body_size 50M;

    location / {
        proxy_pass http://localhost:8080;
        proxy_set_header Host $host;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
    }
}
```

**Enable site:**
```bash
sudo ln -s /etc/nginx/sites-available/sci-projects /etc/nginx/sites-enabled/
sudo nginx -t
sudo systemctl restart nginx
```

### 7. SSL Certificate with Let's Encrypt (Optional)

```bash
sudo apt install certbot python3-certbot-nginx
sudo certbot --nginx -d your-domain.com
sudo systemctl reload nginx
```

---

## Docker Deployment

### Create Dockerfile

`Dockerfile`
```dockerfile
FROM openjdk:17-jdk-slim

WORKDIR /app

COPY target/projects-archive-1.0.0.jar app.jar

RUN mkdir -p /app/uploads

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
```

### Create Docker Compose

`docker-compose.yml`
```yaml
version: '3.8'

services:
  mysql:
    image: mysql:8.0
    container_name: sci-mysql
    environment:
      MYSQL_ROOT_PASSWORD: rootpassword
      MYSQL_DATABASE: sci_projects
      MYSQL_USER: sciapp
      MYSQL_PASSWORD: scipass123
    ports:
      - "3306:3306"
    volumes:
      - mysql-data:/var/lib/mysql
    networks:
      - sci-network

  app:
    build: .
    container_name: sci-app
    depends_on:
      - mysql
    environment:
      SPRING_DATASOURCE_URL: jdbc:mysql://mysql:3306/sci_projects
      SPRING_DATASOURCE_USERNAME: sciapp
      SPRING_DATASOURCE_PASSWORD: scipass123
      JWT_SECRET: your_jwt_secret_key_here
    ports:
      - "8080:8080"
    volumes:
      - ./uploads:/app/uploads
    networks:
      - sci-network

volumes:
  mysql-data:

networks:
  sci-network:
    driver: bridge
```

### Deploy with Docker

```bash
# Build and start
docker-compose up -d

# View logs
docker-compose logs -f app

# Stop
docker-compose down

# Rebuild
docker-compose up -d --build
```

---

## Cloud Deployment

### AWS Deployment

#### Using Elastic Beanstalk

1. **Install AWS CLI and EB CLI**
```bash
pip install awscli awsebcli
```

2. **Initialize EB Application**
```bash
eb init -p java-17 sci-projects --region us-east-1
```

3. **Create Environment**
```bash
eb create sci-projects-env
```

4. **Deploy**
```bash
mvn clean package
eb deploy
```

#### Using EC2

1. Launch EC2 instance (t2.medium recommended)
2. Follow Production Deployment steps
3. Configure Security Groups:
   - Port 22 (SSH)
   - Port 80 (HTTP)
   - Port 443 (HTTPS)
   - Port 8080 (Application - optional)

### Heroku Deployment

1. **Create Procfile**
```
web: java -jar target/projects-archive-1.0.0.jar --server.port=$PORT
```

2. **Deploy**
```bash
heroku create sci-projects-archive
heroku addons:create jawsdb:kitefin
git push heroku main
```

### Azure Deployment

Use Azure Spring Apps or Azure App Service for Java applications.

---

## Environment Variables

For production, use environment variables instead of hardcoding sensitive data:

```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sci_projects
export SPRING_DATASOURCE_USERNAME=sciapp
export SPRING_DATASOURCE_PASSWORD=your_password
export JWT_SECRET=your_jwt_secret
export FILE_UPLOAD_DIR=/var/sci-projects/uploads
```

Or create `.env` file:
```env
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3306/sci_projects
SPRING_DATASOURCE_USERNAME=sciapp
SPRING_DATASOURCE_PASSWORD=your_password
JWT_SECRET=your_jwt_secret
FILE_UPLOAD_DIR=/var/sci-projects/uploads
```

---

## Monitoring and Maintenance

### Application Logs
```bash
# View application logs
tail -f /var/log/sci-projects/application.log

# View systemd logs
journalctl -u sci-projects -f
```

### Database Backup
```bash
# Backup
mysqldump -u sciapp -p sci_projects > backup_$(date +%Y%m%d).sql

# Restore
mysql -u sciapp -p sci_projects < backup_20240115.sql
```

### File Uploads Backup
```bash
# Backup uploads
tar -czf uploads_backup_$(date +%Y%m%d).tar.gz /var/sci-projects/uploads

# Restore uploads
tar -xzf uploads_backup_20240115.tar.gz -C /var/sci-projects/
```

### Health Check
```bash
# Check if application is running
curl http://localhost:8080/api/users/lecturers

# Check database connection
mysql -u sciapp -p -e "USE sci_projects; SHOW TABLES;"
```

---

## Troubleshooting

### Common Issues

1. **Application won't start**
   - Check Java version: `java -version`
   - Check logs: `journalctl -u sci-projects -n 100`
   - Verify database connection

2. **Database connection failed**
   - Check MySQL is running: `sudo systemctl status mysql`
   - Verify credentials
   - Check firewall rules

3. **File upload fails**
   - Check upload directory permissions
   - Verify disk space: `df -h`
   - Check file size limits in configuration

4. **Out of memory errors**
   - Increase heap size in systemd service:
   ```
   Environment="JAVA_OPTS=-Xms1024m -Xmx2048m"
   ```

---

## Security Recommendations

1. **Change default passwords**
2. **Use strong JWT secret** (at least 256 bits)
3. **Enable HTTPS** with SSL certificate
4. **Configure firewall** to restrict ports
5. **Regular security updates**
6. **Database backups** automated daily
7. **Rate limiting** implementation
8. **Input validation** on all endpoints

---

## Performance Optimization

1. **Database Indexes**
```sql
CREATE INDEX idx_project_student ON projects(student_id);
CREATE INDEX idx_project_supervisor ON projects(supervisor_id);
CREATE INDEX idx_project_status ON projects(status);
CREATE INDEX idx_document_project ON documents(project_id);
```

2. **JVM Tuning**
```
-Xms1024m -Xmx2048m -XX:+UseG1GC
```

3. **Connection Pooling**
```properties
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=5
```

---

**Last Updated:** January 2024
