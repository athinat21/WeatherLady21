# 🌤️ WeatherLady Java Application

**WeatherLady** is a Java-based console application that retrieves real-time weather data from the OpenWeather API, stores it in a MySQL database using Hibernate ORM, and allows users to view historical weather records by city and country.

---

## 📌 Features

- Fetches **current weather** data using:
    - City & Country
    - Latitude & Longitude (if known)
- Stores weather information in a **MySQL database** with Hibernate
- Retrieves **historical weather records**
- Allows **editing of locations**
- Fully testable without mocking or H2

---

## 🛠 Technologies Used

- Java 17+
- Hibernate ORM
- MySQL 8+
- JSON (via org.json)
- JUnit 5

---

## 📁 Project Structure

src/
│
├── entity/
│ ├── Location.java
│ └── WeatherData.java
│
├── dao/
│ ├── LocationDAO.java
│ └── WeatherDao.java
│
├── service/
│ └── WeatherService.java
│
├── api/
│ └── OpenWeatherClient.java
│
├── util/
│ └── HibernateUtil.java
│
├── main/
│ └── WeatherLadyApp.java
│
└── test/
└── WeatherDaoTest.java


---

## 🧩 Database Schema

### Tables:
- **location**
    - `id` (PK)
    - `city`, `country`, `region`
    - `latitude`, `longitude`

- **weatherdata**
    - `id` (PK)
    - `date`
    - `temperature`, `pressure`, `humidity`
    - `wind_speed`, `wind_deg`
    - `location_id` (FK)

### Sample MySQL Setup

```sql
CREATE DATABASE weatherlady21;

-- Location Table
CREATE TABLE location (
  id INT PRIMARY KEY AUTO_INCREMENT,
  city VARCHAR(100),
  country VARCHAR(100),
  region VARCHAR(100),
  latitude DOUBLE,
  longitude DOUBLE
);

-- WeatherData Table
CREATE TABLE weatherdata (
  id INT PRIMARY KEY AUTO_INCREMENT,
  date DATE,
  temperature DOUBLE,
  pressure DOUBLE,
  humidity DOUBLE,
  wind_speed DOUBLE,
  wind_deg DOUBLE,
  location_id INT,
  CONSTRAINT fk_location FOREIGN KEY (location_id) REFERENCES location(id)
); 
``` 

##  🔧 Configuration
 ``` Hibernate hibernate.cfg.xml
<hibernate-configuration>
 <session-factory>
   <property name="hibernate.connection.driver_class">com.mysql.cj.jdbc.Driver</property>
   <property name="hibernate.connection.url">jdbc:mysql://localhost:3306/weatherlady21</property>
   <property name="hibernate.connection.username">yourUsername</property>
   <property name="hibernate.connection.password">yourPassword</property>
   <property name="hibernate.dialect">org.hibernate.dialect.MySQL8Dialect</property>
   <property name="show_sql">true</property>
   <property name="hbm2ddl.auto">update</property>
 </session-factory>
</hibernate-configuration> 
``` 

##  API Key

You must define the OPENWEATHER_API_KEY in your environment:
``` 
export OPENWEATHER_API_KEY=your_api_key_here
``` 


or in Java:
``` 
String apiKey = System.getenv("OPENWEATHER_API_KEY");
``` 

