package Root.Parsing;

import Root.Model.Currency;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import java.util.HashMap;
import java.util.Map;
public class Pars {


    public static BigDecimal  change(Currency usersCurrency, Currency recipientCurrency, BigDecimal sumn ) throws IOException {
        String users=usersCurrency.toString();
        String recipients=recipientCurrency.toString();
        String URL="https://v6.exchangerate-api.com/v6/cf24fb622f6685ab83d850ec/pair/"+users+"/"+recipients;

        URL url = new URL(URL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

// Convert to JSON
        JsonParser jp = new JsonParser();
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent()));
        JsonObject jsonobj = root.getAsJsonObject();

        JsonElement req_result = jsonobj.get("conversion_rate");
        System.out.println(req_result.toString());
        BigDecimal conversionRate = new BigDecimal(req_result.toString());

    BigDecimal result=conversionRate.multiply(sumn);
        return  result;
    }

    }
