import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;

public class WeatherApp {

    
    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your actual API key
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name: ");
        String city = scanner.nextLine();
        scanner.close();

        fetchWeatherData(city);
    }

    private static void fetchWeatherData(String city) {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(API_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric"))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                displayWeatherData(response.body());
            } else {
                System.err.println("Error: " + response.statusCode() + " - " + response.body());
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Error fetching weather data: " + e.getMessage());
        }
    }

    private static void displayWeatherData(String jsonResponse) {
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(jsonResponse, JsonObject.class);

       
        String city = jsonObject.get("name").getAsString();
        JsonObject main = jsonObject.getAsJsonObject("main");
        double temperature = main.get("temp").getAsDouble();
        double feelsLike = main.get("feels_like").getAsDouble();
        int humidity = main.get("humidity").getAsInt();
        JsonObject weather = jsonObject.getAsJsonArray("weather").get(0).getAsJsonObject();
        String description = weather.get("description").getAsString();

        // Display formatted weather data
        System.out.println("\nWeather Data for " + city + ":");
        System.out.println("------------------------------------");
        System.out.printf("Temperature: %.1f°C\n", temperature);
        System.out.printf("Feels Like: %.1f°C\n", feelsLike);
        System.out.println("Humidity: " + humidity + "%");
        System.out.println("Description: " + description);
        System.out.println("------------------------------------");
    }
}
