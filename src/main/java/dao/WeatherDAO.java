package dao;


import entity.WeatherData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.HibernateUtil;

import java.util.List;

//NJE klase dao (data acccess object) sherben per te percaktuar
// cfare te dhenash duam te dergojme dhe cfare te dhenash presim
public class WeatherDAO {

    //metoda per te ruajtur nje objekt te tipit Weatherdata ne databaze
    public void saveWeatherData(WeatherData weatherData){
        Session session = HibernateUtil.getSession();

        //nje transaction eshte nje grup operacionesh qe duhen te
        // perfundojne te gjitha me sukses ose te mos behen fare. Nese ndodh
        //nje problem gjate ekzekutimit ath duhet te anullohet transaksioni
        //siguron konsistence dhe integritetin e te dhenave
        Transaction transaction = session.beginTransaction();
        session.persist(weatherData); //ruan objektin weatherData ne databaze
                                        // krijon nje rresht te ri ne tabele

        transaction.commit();//eshte hap i rendesishem sepse siguron qe ndryshimet e bera jane ruajtur ne databaze
    }

    //metode qe merr te dhenat e motit per nje qytet dhe vend
    public List<WeatherData> getWeatherDataByLocation(String city, String country){
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();

        //query per te marr te dhenat e motit per qytetin dhe vendin
        //qe kemi vendos. Kjo metode ekzekuton nje HQL(Hibernate Query Lnaguage)
        String hql = "FROM WeatherData wd WHERE wd.location.city = :city AND wd.location.country";
        //krijo nje query qe eshte e bazuar ne hql dhe ekzekuton ne entitetin WeatherData
        Query<WeatherData> query = session.createQuery(hql, WeatherData.class);
        query.setParameter("city", city);
        query.setParameter("country",country);

        List<WeatherData> weatherDataList = query.list(); //kthimi ne list te dhenave te motit
        transaction.commit();

        return weatherDataList;
    }
}
