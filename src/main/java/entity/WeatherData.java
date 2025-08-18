package entity;


import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class WeatherData {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //id do gjenerohet automatikisht
    private Long id; //primary key

    private LocalDate date; //data kur jane marr te dhenat
    private double temperature;
    private double pressure;
    private double humidity;
    private double windSpeed;
    private double windDirection;

    @ManyToOne
    @JoinColumn(name = "location_id") //kollona qe lidhet me location
    private Location location; //lidhja me klasen location

    public WeatherData(){

    }

    public WeatherData(LocalDate date, double temperature, double pressure, double humidity, double windSpeed, double windDirection, Location location) {
        this.date = date;
        this.temperature = temperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDirection = windDirection;
        this.location = location;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getPressure() {
        return pressure;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }

    public double getWindDirection() {
        return windDirection;
    }

    public void setWindDirection(double windDirection) {
        this.windDirection = windDirection;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
