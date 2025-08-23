package apiClasses;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.spec.ECField;

public class OpenWeatherClient {

    //krijojme nje metode qe percakto apiKey
    private static String apiKey(){
        //kjo eshte nje metode e klases System qe perdoret
        //per te marr environment variables
        // nga sistemi operativ
        String k = System.getenv("OPENWEATHER_API_KEY");
        if (k == null) throw new IllegalStateException("Mungon OPENWEATHER_API_KEY");
        k = k.replace("\"", "").trim(); //heq thonjezat/ose hapesira
        if (k.isBlank()) throw new IllegalStateException("OPENWEATHER_API_KEY eshte bosh");
        return k;
    }

    //krijojme nje metode qe dergon nje kerkese HTTP GET TE nje url
    //dhe kthen nje json si pergjigje per te marr te dhena nga API e OpenWeather
    private static JSONObject getJson(String urlStr) throws Exception{
        String key = apiKey();
        //vendosim disa mesazhe ne rast se kemi error
        System.out.println("[DEBUG] Using OPENWEATHER_API_KEY length=" +key.length());
        System.out.println("[DEBUG] URL:" +urlStr.replace(key, "--MASKED--"));


        //KRIJOJME nje objekt url
        //hapet lidhja http me url
        //metoda qe do te perdoret per thirrje eshte GET
        //SEPSE NE DUAM TE MARRIM TE DHENA
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        //merr kodin e pergjigjes(200 per sukses, 401 per apikey te gabuar)
        int code = conn.getResponseCode();


        //kjo try  hap nje BufferReader dhe lexon rreshtat
        // nga pergjogja qe marrim
        //nese kodi eshte 200-399 kemi pergigje normale-getInputStream
        //nese kodi >-400 ath kemi gabim- getErrorStream
        try(var in = new BufferedReader(new InputStreamReader(
                (code >= 200 && code < 400 ) ? conn.getInputStream() :
              conn.getErrorStream()))) {
            //lexo cdo rresht nga pergj qe do marrim
            //shtimi i nje StringBuilder qe ruan permbajtjen e prgj json si string
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) sb.append(line);

            //nqs api kthen error
            if (code >=400){
                throw new IOException("Http " + code + " from weatherApi:  " + sb );
            }
    return new JSONObject(sb.toString());
        }

    }

    private static String urlEncode(String s){
        try {
            return URLEncoder.encode(s, StandardCharsets.UTF_8);
        } catch (Exception e){
            return s;
        }
    }

    //metoda qe kerkoj sipas kordinatave
    public  static  JSONObject currentByLatLon(double lat, double lon) throws Exception{
        String url = String.format(
                "https://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&appid=%s",
                lat, lon, apiKey()

        );
        return getJson(url);
    }

    //metoda sipas city, country
    public  static JSONObject currentByCityCountry(String city, String country) throws Exception {
        String url = String.format("https://api.openweathermap.org/data/2.5/weather?q=%s,%s&units=metric&appid=%s",
                urlEncode(city), urlEncode(country), apiKey()
        );
        //siguron qe emeri i qytetit dhe shtetit jane te enkoduar sakte ne url
        return getJson(url);

    }
}

