package dao;


import entity.WeatherData;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

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
}
