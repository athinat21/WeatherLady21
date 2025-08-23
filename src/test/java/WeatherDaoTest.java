import dao.LocationDAO;
import dao.WeatherDAO;
import entity.Location;
import entity.WeatherData;
import org.hibernate.Session;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import util.HibernateUtil;

import java.util.List;

public class WeatherDaoTest {

    private WeatherDAO weatherDAO;
    private LocationDAO locationDAO;


    @BeforeEach
    void setUp(){
        weatherDAO = new WeatherDAO();
        locationDAO = new LocationDAO();
    }

    @Test
    void testSaveWeatherData(){

        Session session = HibernateUtil.getSession();
        session.beginTransaction();

        //krijmi i nje location
        Location loc = new Location("Tirana", "Europe" , "AL", 41.1,34.6);
        locationDAO.saveLocation(loc);

        WeatherData wd = new WeatherData();
        wd.setTemperature(27.5);
        wd.setHumidity(60);
        wd.setPressure(1015.0);
        wd.setWindSpeed(3.2);
        wd.setLocation(loc);

        weatherDAO.saveWeatherData(wd);
        session.getTransaction().commit();

        List<WeatherData> retrieved = weatherDAO.getWeatherDataByLocation("Tirana", "AL");

        Assertions.assertFalse(retrieved.isEmpty(), "Te dhenat nuk duhet te jene bosh");
        WeatherData first = retrieved.get(0);
        Assertions.assertEquals(27.5, first.getTemperature(), 0.01);
        Assertions.assertEquals("Tirana", first.getLocation().getCity());
    }

    @AfterEach
    void tearDown(){
        Session session = HibernateUtil.getSession();
        session.beginTransaction();
        session.createQuery("DELETE FROM WeatherData").executeUpdate();
        session.createQuery("DELETE FROM Location").executeUpdate();
        session.getTransaction().commit();
    }
}
