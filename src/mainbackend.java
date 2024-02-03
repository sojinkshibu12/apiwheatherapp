import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class mainbackend{

    public JSONObject operation (String name){
        String countryname = name;
        countryname = formatstring(countryname);
        String apiUrl = "https://geocoding-api.open-meteo.com/v1/search?name="+countryname+"&count=10&language=en&format=json";
        JSONObject s = getdata(apiUrl);

        JSONArray locationdetails = (JSONArray)  s.get("results");
        JSONObject location = (JSONObject) locationdetails.get(0);
        double lattitude = (double)location.get("latitude");
        double longitude = (double)location.get("longitude");


        String tempdataurl = "https://api.open-meteo.com/v1/forecast?latitude="+lattitude+"&longitude="+longitude+"&hourly=temperature_2m,weather_code,wind_speed_10m";
        JSONObject whetherdetails = getdata(tempdataurl);
        JSONObject hourly = (JSONObject)whetherdetails.get("hourly");

        JSONArray time =  (JSONArray) hourly.get("time") ;
        int index = getindex(time);


        JSONArray temperaturearray = (JSONArray) hourly.get("temperature_2m");
        JSONArray  wheathercodearray = (JSONArray) hourly.get("weather_code");
        JSONArray  windspeedarray = (JSONArray) hourly.get("wind_speed_10m");
        String temprature = temperaturearray.get(index).toString();
        long wheathercode = (long) wheathercodearray.get(index);
        String condition = checkwhethercode(wheathercode);
        String windspeed = windspeedarray.get(index).toString();


        System.out.println(temprature);
        System.out.println(condition);
        System.out.println(windspeed);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("temperature", temprature);
        jsonObject.put("condition", condition);
        jsonObject.put("windspeed", windspeed);


        return jsonObject;
    }

    private static int getindex(JSONArray TIME){
        String date = getdate();
        for(int i = 0;i<TIME.size();i++){
            if(date.equalsIgnoreCase(TIME.get(i).toString())){

                return  i;
            }

        }
        return 0;

    }


    private String checkwhethercode(long code){

        if(code>= 0L && code <= 3L)
            return "Clear";
        else if(code >= 45L && code <= 48L)
            return "foggy";
        else if(code >= 51L && code <= 55L)
            return "Drizzle";
        else if(code >= 56L && code <= 57L)
            return "Freezing Drizzle";
        else if(code >= 61L && code <= 65L)
            return "Rain";
        else if( code >= 66L && code <= 67L)
            return "Freezing Rain";
        else if(code >= 71L && code <= 75L)
            return "Snow Fall";
        else if(code == 71L)
            return "snow grains";
        else if(code>= 80L && code <= 82L)
            return "Rain Showers";
        else if(code>= 85L && code <= 86L)
            return "snow showers";
        else if(code>= 95L && code <= 99L)
            return "Thenderstorm" ;
        else
            return null;
    }
    private static String getdate(){
        Instant instant = Instant.now();
        LocalDateTime gmtDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("GMT"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        String formattedDateTime = gmtDateTime.format(formatter);
        return  formattedDateTime;
    }
    private static String formatstring(String s){
        s = s.replaceAll(" " , "+");
        return s;
    }
    //function that retrive the data from the api...
    private static JSONObject getdata(String apiurl){
        JSONObject jsonobject = new JSONObject();
        try{
            URL url = new URL(apiurl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            int responseCode = connection.getResponseCode();
            if(responseCode != 200){
                System.out.println("something went wrong");
            }
            else{
                try  {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String line;
                    StringBuilder response = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    JSONParser parser = new JSONParser();
                    jsonobject = (JSONObject) parser.parse(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }catch(Exception e){
            e.printStackTrace();
        }

        return jsonobject;
    }



}