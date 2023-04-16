package postman;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class RestPoziv {

    public static void main(String[] args) {
        try {
            String url = "https://www.rba.hr/alati/tecajni-kalkulator?p_p_id=tecajKalkulator_WAR_calculatorsportlet&p_p_lifecycle=2&p_p_state=normal&p_p_mode=view&p_p_resource_id=calculateExchangeRate&p_p_cacheability=cacheLevelPage&p_p_col_id=column-4&p_p_col_count=2";

            JSONObject postData = new JSONObject();
            postData.put("valuta1", "EUR");
            postData.put("valuta2", "GBP");
            postData.put("suma1", "100.00");

            URL obj = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);
            connection.getOutputStream().write(postData.toString().getBytes());

            int responseCode = connection.getResponseCode();
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONObject jsonObject = new JSONObject(response.toString());
            System.out.println(response.toString());

            String valuta1 = jsonObject.getString("valuta1");
            String valuta2 = jsonObject.getString("valuta2");
            String suma1 = jsonObject.getString("suma1");
            String kurs = jsonObject.getString("kurs");

            System.out.println("Valuta iz: " + valuta1);
            System.out.println("Iznos: " + suma1);
            System.out.println("Valuta u: " + valuta2);
            System.out.println("Tečaj: " + kurs);

        } catch (Exception e) {
            System.out.println("Došlo je do greške: ");
            e.printStackTrace();
        }
    }
}
