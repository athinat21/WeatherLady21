package util;

import entity.Location;
import entity.WeatherData;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    //eshte nje objekt qe krijon dhe menaxhion sesionet ne Hibernate
    //Session -> nje lidhje me bazen e te dhenave dhe perdoret per te
    //realizuar operacione si save, update delete ose search
    private static SessionFactory sessionFactory;

    // ky blloku statik ekzekutohet vtm nje here
    // kur inicializohet Hibetnate
    static {
        try{
            //thjrrjen e metodes configure() per te lexuar file-in hibernate.cfg.xml
            //i cili permban te gjitha konfigurimet e hibernate
            sessionFactory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Location.class)
                    .addAnnotatedClass(WeatherData.class)
                    //kto shtojne klasat qe kane anotation @Entity ose  @Table
                    .buildSessionFactory();
        } catch (Throwable ex){
            throw new ExceptionInInitializerError(ex);
        }

    }

    //hapja e nje sessioni te ri
    public static Session getSession() {
        return sessionFactory.openSession();
    }

    //mbyllja
    public static void shutdown(){
        sessionFactory.close();
    }
}
