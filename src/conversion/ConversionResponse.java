package conversion;

import adapters.LocalDateTimeAdapter;
import com.google.gson.*;
import com.google.gson.annotations.SerializedName;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;

/**
 * Clase que representa una transacción de intercambio de divisas.
 */
public class TransaccionCambioDivisas {

    /** El código de la divisa de origen. */
    @SerializedName("Divisa_Origen")
    private String divisaOrigen;

    /** El código de la divisa de destino. */
    @SerializedName("Divisa_Destino")
    private String divisaDestino;

    /** El monto o cantidad de divisa a intercambiar. */
    @SerializedName("Monto_a_Intercambiar")
    private double cantidad;

    /** El resultado del intercambio. */
    @SerializedName("Resultado")
    private double resultado;

    /** La tasa de cambio. */
    @SerializedName("Tasa_Cambio")
    private double tasaCambio;

    /** Constructor vacío. */
    public TransaccionCambioDivisas() {}

    /**
     * Constructor con parámetros.
     * @param divisaOrigen El código de la divisa de origen.
     * @param divisaDestino El código de la divisa de destino.
     * @param cantidad La cantidad a intercambiar.
     */
    public TransaccionCambioDivisas(String divisaOrigen, String divisaDestino, double cantidad) {
        this.divisaOrigen = divisaOrigen;
        this.divisaDestino = divisaDestino;
        this.cantidad = cantidad;
    }

    // Getters y setters

    public String getDivisaOrigen() {
        return divisaOrigen;
    }

    public void setDivisaOrigen(String divisaOrigen) {
        this.divisaOrigen = divisaOrigen;
    }

    public String getDivisaDestino() {
        return divisaDestino;
    }

    public void setDivisaDestino(String divisaDestino) {
        this.divisaDestino = divisaDestino;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getResultado() {
        return resultado;
    }

    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

    public double getTasaCambio() {
        return tasaCambio;
    }

    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    /**
     * Realiza el intercambio de divisas.
     * @param codDivisaOrigen El código de la divisa de origen.
     * @param codDivisaDestino El código de la divisa de destino.
     * @param cantidad La cantidad a intercambiar.
     * @return El registro del intercambio.
     */
    public RegistroIntercambioDivisas intercambiar(String codDivisaOrigen, String codDivisaDestino, int cantidad) {
        // Construir la URI para la solicitud a la API
        URI direccion_API = URI.create("https://v6.exchangerate-api.com/v6/d50362c2646d99e082d99a42/pair/"
                + codDivisaOrigen + "/" + codDivisaDestino + "/" + cantidad);

        // Crear cliente y solicitud HTTP
        HttpClient cliente = HttpClient.newHttpClient();
        HttpRequest solicitud = HttpRequest.newBuilder()
                .uri(direccion_API)
                .build();
        HttpResponse<String> respuesta = null;

        try {
            // Realizar la solicitud HTTP
            respuesta = cliente.send(solicitud, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }

        // Leer el JSON de la respuesta
        try (JsonReader lector = new JsonReader(new StringReader(respuesta.body()))) {
            lector.setLenient(true);

            // Configurar Gson para deserializar la respuesta
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                    .create();

            // Deserializar la respuesta en un objeto RespuestaCambioDivisas
            RespuestaCambioDivisas respuestaCambio = gson.fromJson(lector, RespuestaCambioDivisas.class);

            // Calcular el resultado y la tasa de cambio
            this.tasaCambio = respuestaCambio.getTasaCambio();
            this.resultado = cantidad * tasaCambio;

            // Crear un objeto TransaccionCambioDivisas con los datos de la respuesta
            TransaccionCambioDivisas transaccion = new TransaccionCambioDivisas(respuestaCambio.getDivisaOrigen(), respuestaCambio.getDivisaDestino(), cantidad);
            transaccion.setResultado(resultado);
            transaccion.setTasaCambio(respuestaCambio.getTasaCambio());

            // Crear un registro del intercambio
            RegistroIntercambioDivisas registroIntercambio = new RegistroIntercambioDivisas(transaccion);

            // Leer el historial de intercambios desde el archivo JSON
            RegistroIntercambioDivisas[] historial;
            try (Reader lectorArchivo = new FileReader("registros_intercambio_divisas.json")) {
                historial = gson.fromJson(lectorArchivo, RegistroIntercambioDivisas[].class);
            } catch (FileNotFoundException e) {
                historial = new RegistroIntercambioDivisas[0];
            }

            // Agregar el nuevo registro al historial
            RegistroIntercambioDivisas[] nuevoHistorial = new RegistroIntercambioDivisas[historial.length + 1];
            System.arraycopy(historial, 0, nuevoHistorial, 0, historial.length);
            nuevoHistorial[historial.length] = registroIntercambio;

            // Escribir el historial actualizado en el archivo JSON
            try (Writer escritorArchivo = new FileWriter("registros_intercambio_divisas.json")) {
                gson.toJson(nuevoHistorial, escritorArchivo);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return registroIntercambio; // Devolver el registro del intercambio
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}