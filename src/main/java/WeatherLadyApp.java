import dao.LocationDAO;
import entity.Location;
import service.WeatherService;

import java.util.Scanner;

public class WeatherLadyApp {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        WeatherService weatherService = new WeatherService();

        while (true) {
            System.out.println("\n--- MENU ---");
            System.out.println("1. Shto qytet");
            System.out.println("2. Shiko mesataren e temperaturës për qytet");
            System.out.println("3. Dalje");
            System.out.println("4. Importo të dhëna moti nga CSV");

            System.out.print("Zgjedhja: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // për të hequr newline karakterin

            switch (choice) {
                case 1:
                    System.out.print("Emri i qytetit: ");
                    String city = scanner.nextLine();
                    System.out.print("Vendi: ");
                    String country = scanner.nextLine();
                    System.out.print("Rajoni: ");
                    String region = scanner.nextLine();

                    // Koordinatat për shembull janë të Tiranës, mund t’i zëvendësosh më vonë
                    Location location = new Location(city, region, country, 41.3275, 19.8189);
                    new LocationDAO().saveLocation(location);
                    System.out.println("Qyteti u shtua me sukses!");
                    break;

                case 2:
                    System.out.print("Emri i qytetit: ");
                    city = scanner.nextLine();
                    System.out.print("Vendi: ");
                    country = scanner.nextLine();
                    double avgTemp = weatherService.calculateAverageTemperature(city, country);

                    if (Double.isNaN(avgTemp)) {
                        System.out.println("Nuk u gjetën të dhëna për këtë qytet.");
                    } else {
                        System.out.printf("Mesatarja e temperaturës për %s, %s: %.2fC%n", city, country, avgTemp);
                    }
                    break;

                case 3:
                    System.out.println("Programi u mbyll.");
                    return;


                case 4:
                   /* // Shto path-in absolut këtu — shiko që të përdorësh dy \\ në Windows
                    String csvPath = "C:\\Users\\SwissComputers\\Desktop\\weather.csv";
                    weatherService.importWeatherDataFromCSV(csvPath);
                    break;*/

                    System.out.println("Shkruaj path-in e plote te CSV file: ");
                    String csvPath = scanner.nextLine();
                    weatherService.importWeatherDataFromCSV(csvPath);
                    break;

                default:
                    System.out.println("Zgjedhje e pavlefshme. Provo përsëri.");
            }
        }
    }
}