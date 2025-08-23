package service;

import apiClasses.OpenWeatherClient;
import dao.LocationDAO;
import dao.WeatherDAO;
import entity.Location;
import entity.WeatherData;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class WeatherService {

    private LocationDAO locationDAO;
    private WeatherDAO weatherDAO;

    public WeatherService(){
        locationDAO = new LocationDAO();
        weatherDAO = new WeatherDAO();
    }


    //LLOGARITJA E TEMP MES PER NJE QYTEt
    public double calculateAverageTemperature(String city, String country){

        try {
            //kerko ne databaze nese ekziston Location apo jo
            Location loc = locationDAO.getLocationByCityAndCountry(city, country);
            JSONObject json;

            if (loc != null){
                json = OpenWeatherClient.currentByLatLon(loc.getLatitude(), loc.getLongitude());
            } else {
                json = OpenWeatherClient.currentByCityCountry(city, country);
            }

            double temp = json.getJSONObject("main").getDouble("temp");
            double pressure = json.getJSONObject("main").getDouble("pressure");
            double humidity = json.getJSONObject("main").getDouble("humidity");

            double windSpeed = json.has("wind") && json.getJSONObject("wind").has("speed")
                    ?json.getJSONObject("wind").getDouble("speed") : 0.0;

            double windDeg = json.has("wind") && json.getJSONObject("wind").has("deg")
                    ?json.getJSONObject("wind").getDouble("deg") : 0.0;

            //nese ne databze nuk e ke location te ruajtur
            if (loc == null){
                double lat  = json.getJSONObject("coord").getDouble("lat");
                double lon  = json.getJSONObject("coord").getDouble("lon");

                loc = new Location(city, "Unknown", country, lat, lon);
                locationDAO.saveLocation(loc);

            }
            WeatherData wd = new WeatherData( LocalDate.now(), temp, pressure, humidity, windSpeed, windDeg, loc);
            weatherDAO.saveWeatherData(wd);
            return temp;

        } catch (Exception e){
            e.printStackTrace();
            return Double.NaN;
        }


      /*  //merr location nga city dhe country
       Location location = locationDAO.getLocationByCityAndCountry(city, country);

       //merr te dhenat e motit per qytetin dhe shtetin
        List<WeatherData> weatherDataList = weatherDAO.getWeatherDataByLocation(city, country);

        //llogaritjen e mesatares se temp
        double totalTemp = 0;
        for (WeatherData data : weatherDataList){
            totalTemp += data.getTemperature();
        }
        return totalTemp /weatherDataList.size();*/
    }

    // metoda per leximin e file-it csv
    public void importWeatherDataFromCSV(String csvPath){
        //try - catch ne kete metode perdoret per trajtimin e gabimeve dhe
        //eshte shume i rendesishem kur punohet me fileaa

        //hap file-in dhe e pergatit per ta lexuar rresht pas rreshti
        //perdorim buffer mbi fileReader per nje performance me te mire te file-it
        try(BufferedReader br = new BufferedReader(new FileReader(csvPath))){
            String line;
            br.readLine(); // rreshtin e pare duke qene se eshte header nk e lexon


            //lexohet cdo rresht dhe ndahet ne fusga me split(",")
            while ((line = br.readLine()) != null) {
                String[] fields = line.split(",");

                //cdo fushe konvertohet ne tipin perkates
                String city = fields[0];
                String country = fields[1];
                LocalDate date = LocalDate.parse(fields[2]);
                double temperature = Double.parseDouble(fields[3]);
                double pressure = Double.parseDouble(fields[4]);
                double humidity = Double.parseDouble(fields[5]);
                double windSpeed = Double.parseDouble(fields[6]);
                double windDirection = Double.parseDouble(fields[7]);

                //kontrollo nese ekziston vendodhja
                Location location = locationDAO.getLocationByCityAndCountry(city, country);
                if (location == null){
                    //nese qyteti dhe shteti nk ekzistojne te krijohet nje location i ri me te dhena te thjeshta
                    //me kordinata 0.0 dhe region = unknown
                    location = new Location(city, "Unknown", country,  0,0);
                    locationDAO.saveLocation(location);
                }

                WeatherData weatherData = new WeatherData( date, temperature, pressure,humidity, windSpeed, windDirection, location);
                weatherDAO.saveWeatherData(weatherData);

            }
            System.out.println("Te dhenat u importuan me sukses");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
