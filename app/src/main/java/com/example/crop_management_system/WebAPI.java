package com.example.crop_management_system;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;

public class WebAPI {
    /*------------------------Weather Temperature-------------------------------------------*/
    public static String getWeatherTemperature(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return url;
    }

    public static String setWeatherTempLimit(String restURL, Float weatherTempValue1, Float weatherTempValue2)
            throws Exception{
        String POST_PARAMS = String.format("minValue=%f&maxValue=%f",
                weatherTempValue1, weatherTempValue2);

        URL obj = new URL(restURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK)
            return null;

        BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }
    /*-------------------------------------------------------------------------------------------*/

    /*---------------------Soil Temperature------------------------------------------------------*/
    public static String getSoilTemperature(String url) throws Exception {
        URL obj = new URL(url); // Create a url object, call URL constructor and add url to the object
        // Object is a copy of the class.
        // Instance is a variable that holds the memory address of the object.
        // call openConnection() method on URL object that returns instance of HttpConnection class
        //When you invoke.openConnection(), you send a request
        // to the server to establish a connection. There is a handshake that occurs where the server
        // sends back an acknowledgement to you that the connection is established.
        // It is then at that point in time that you're prepared to send or receive data.
        // Thus, you do not need to call getOutputStream to establish a connection open a stream,
        // unless your purpose for making the request is to send data.
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // Call setRequestProperty() method on HttpURLConnection instance to set request header values,
        // such as “User-Agent” and “Accept-Language” etc.
        con.setRequestMethod("GET");

        //We can call getResponseCode() to get the response HTTP code.
        // This way we know if the request was processed successfully or
        // there was any HTTP error message thrown.
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // For GET response is read by using Reader and Input stream
            // to read response and process it accordingly
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return url;
    }

    public static String setSoilTempLimit(String restURL, Float soilTempValue1, Float soilTempValue2)
            throws Exception{
        String POST_PARAMS = String.format("minValue=%f&maxValue=%f",
                soilTempValue1, soilTempValue2);

        URL obj = new URL(restURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        // For POST, before we read response we need to get the OutputStream from HttpURLConnection
        // instance and write POST parameters into it.
        //getOutputStream basically opens a  stream (not connection), with the intention of writing data
        // to the server. In the above code example "message" could be a comment that we're sending
        // to the server that represents a comment left on a post. When you see getOutputStream,
        // you're opening the connection stream for writing, but you don't actually write any data
        // until you call writer.write("message=" + message);.
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK)
            return null;
        //getInputStream does the opposite. Like getOutputStream,
        // it also opens a connection stream, but the intent is to read data from the server,
        // not write to it. If the connection or stream-opening fails, you'll see a SocketTimeoutException.
        // InputStreamReader uses the input stream
        BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }
    /*------------------------------------------------------------------------------------*/

    /*-------------------------Soil Humidity-----------------------------------------------------*/
    public static String getSoilHumidity(String url) throws Exception {
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        con.setRequestMethod("GET");

        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // For GET response is read by using Reader and Input stream
            // to read response and process it accordingly
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return url;
    }

    public static String setSoilHumLimit(String restURL, int soilHumValue1, int soilHumValue2)
            throws Exception{
        String POST_PARAMS = String.format("minValue=%d&maxValue=%d",
                soilHumValue1, soilHumValue2);

        URL obj = new URL(restURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");

        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK)
            return null;

        BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }
    /*-------------------------------------------------------------------------------------*/
    /*---------------------Soil Ph--------------------------------------------------------*/
    public static String getSoilPh(String url) throws Exception { //Vietoj public static String getSoilTemperature(String url) throws Exception {
        URL obj = new URL(url); // Create a url object, call URL constructor and add url to the object
        // Object is a copy of the class.
        // Instance is a variable that holds the memory address of the object.
        // call openConnection() method on URL object that returns instance of HttpConnection class
        //When you invoke.openConnection(), you send a request
        // to the server to establish a connection. There is a handshake that occurs where the server
        // sends back an acknowledgement to you that the connection is established.
        // It is then at that point in time that you're prepared to send or receive data.
        // Thus, you do not need to call getOutputStream to establish a connection open a stream,
        // unless your purpose for making the request is to send data.
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        // Call setRequestProperty() method on HttpURLConnection instance to set request header values,
        // such as “User-Agent” and “Accept-Language” etc.
        con.setRequestMethod("GET");

        //We can call getResponseCode() to get the response HTTP code.
        // This way we know if the request was processed successfully or
        // there was any HTTP error message thrown.
        int responseCode = con.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // For GET response is read by using Reader and Input stream
            // to read response and process it accordingly
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        }
        return url;
    }

    public static String setSoilPhLimit(String restURL, Float soilPhValue1, Float soilPhValue2) //public static String setSoilTempLimit(String restURL, Float soilTempValue1, Float soilTempValue2)
            throws Exception{
        String POST_PARAMS = String.format("minValue=%f&maxValue=%f",
                soilPhValue1, soilPhValue2); //Vietoj soilTempValue1, soilTempValue2);

        URL obj = new URL(restURL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("POST");
        // For POST, before we read response we need to get the OutputStream from HttpURLConnection
        // instance and write POST parameters into it.
        //getOutputStream basically opens a  stream (not connection), with the intention of writing data
        // to the server. In the above code example "message" could be a comment that we're sending
        // to the server that represents a comment left on a post. When you see getOutputStream,
        // you're opening the connection stream for writing, but you don't actually write any data
        // until you call writer.write("message=" + message);.
        con.setDoOutput(true);
        OutputStream os = con.getOutputStream();
        os.write(POST_PARAMS.getBytes());
        os.flush();
        os.close();

        int responseCode = con.getResponseCode();
        System.out.println("Response Code :: " + responseCode);
        if (responseCode != HttpURLConnection.HTTP_OK)
            return null;
        //getInputStream does the opposite. Like getOutputStream,
        // it also opens a connection stream, but the intent is to read data from the server,
        // not write to it. If the connection or stream-opening fails, you'll see a SocketTimeoutException.
        // InputStreamReader uses the input stream
        BufferedReader in = new BufferedReader(new InputStreamReader( con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return  response.toString();
    }
    /*------------------------------------------------------------------------------------*/
}
