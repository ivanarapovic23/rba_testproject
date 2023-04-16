package postman;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONArray;
import org.json.JSONObject;

public class Wikipedia {

    public static void main(String[] args) {
        try {
            String search_term = "Raiffeisen";
            String url = "https://en.wikipedia.org/w/api.php?action=query&list=search&srsearch=" + search_term + "&format=json&srlimit=10";
            URL object = new URL(url);

            HttpURLConnection connection = (HttpURLConnection) object.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();

            JSONObject json = new JSONObject(response.toString());

            try (FileWriter jsonfile = new FileWriter("results.json")) {
                jsonfile.write(json.toString());
            }
            catch (Exception e){
                e.printStackTrace();
            }

            JSONObject query = json.getJSONObject("query");
            JSONArray pages = query.getJSONArray("search");
            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                System.out.println(page.getString("title"));
            }

            long responseTimeStart = System.currentTimeMillis();
            int responseCode = connection.getResponseCode();
            long responseTimeEnd = System.currentTimeMillis();
            long responseTime = responseTimeEnd - responseTimeStart;

            if (responseTime < 5000) {
            }
            else {
                System.out.println("Response time je veći od 5 sekundi.");
            }

            if (responseCode == 200) {
            }
            else {
                System.out.println("Neočekivani response code: " + responseCode);
            }

            if (json.has("pages")) {
                System.out.println("Response sadrži objekt 'pages'");
            } else {
                System.out.println("Response ne sadrži objekt 'pages'");
            }

            boolean keyContainsTitle = false;
            for (int i = 0; i < pages.length(); i++) {
                JSONObject page = pages.getJSONObject(i);
                if (page.getString("title").equals("Raiffeisen Bank International")) {
                    keyContainsTitle = true;
                    break;
                }
            }
            if (keyContainsTitle == true) {
                System.out.println("Neka od stranica sadrži key 'title' te vrijednost 'Raiffeisen Bank International'");
            }
            else {
                System.out.println("Nema stranice koja sadrži key 'title' te vrijednost 'Raiffeisen Bank International'");
                }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

