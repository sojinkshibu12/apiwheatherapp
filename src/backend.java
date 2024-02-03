
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class backend {

    public static void main(String args[]) {
        try {
            JSONObject n;
            String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name=Berlin&count=10&language=en&format=json";
            URL url = new URL(apiUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String line;
                StringBuilder response = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                JSONParser parser = new JSONParser();
                JSONObject jsonobject = (JSONObject) parser.parse(response.toString());
                JSONArray locationdata = (JSONArray) jsonobject.get("results") ;
                JSONObject locationdetails = (JSONObject) locationdata.get(0);
                double lattitude = (double)locationdetails.get("latitude");
                System.out.println("json object:\n" + jsonobject);




                System.out.println(lattitude);
            }catch (Exception e){
                e.printStackTrace();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
