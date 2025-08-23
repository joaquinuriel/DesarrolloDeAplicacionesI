package edu.uade.ritmofit.conecciones;

import android.util.Log;
import edu.uade.ritmofit.Sedes.Model.SedeDto;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class ApiService {
    private static final String BASE_URL = "http://10.0.2.2:9090/"; // Cambiado a 10.0.2.2 para emulador
    private static final String TAG = "ApiService"; // Etiqueta para los logs

    public void fetchSedes(final OnSedeFetchListener listener) {
        new Thread(() -> {
            HttpURLConnection connection = null;
            try {
                Log.d(TAG, "Iniciando conexión a: " + BASE_URL + "sedes");
                URL url = new URL(BASE_URL + "sedes");
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                Log.d(TAG, "Conexión establecida, obteniendo código de respuesta...");
                int responseCode = connection.getResponseCode();
                Log.i(TAG, "Código de respuesta: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    reader.close();

                    Log.i(TAG, "Respuesta JSON recibida: " + response.toString());
                    List<SedeDto> sedes = parseSedesJson(response.toString());
                    listener.onSedeFetched(sedes);
                } else {
                    Log.w(TAG, "Error en la respuesta: " + responseCode);
                    listener.onSedeFetchError(new Exception("Error: " + responseCode));
                }
            } catch (IOException e) {
                Log.e(TAG, "Error de IO: " + e.getMessage());
                listener.onSedeFetchError(e);
            } catch (JSONException e) {
                Log.e(TAG, "Error al parsear JSON: " + e.getMessage());
                listener.onSedeFetchError(e);
            } finally {
                if (connection != null) {
                    connection.disconnect();
                    Log.d(TAG, "Conexión cerrada");
                }
            }
        }).start();
    }

    private List<SedeDto> parseSedesJson(String jsonString) throws JSONException {
        Log.d(TAG, "Parseando JSON: " + jsonString);
        List<SedeDto> sedes = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(jsonString);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            SedeDto sede = new SedeDto(
                    jsonObject.getString("id_sede"),
                    jsonObject.getString("nombre"),
                    jsonObject.getString("ubicacion")
            );
            sedes.add(sede);
            Log.d(TAG, "Sede parseada: " + sede.toString());
        }
        return sedes;
    }

    // Interfaz para manejar callbacks
    public interface OnSedeFetchListener {
        void onSedeFetched(List<SedeDto> sedes);
        void onSedeFetchError(Throwable t);
    }
}