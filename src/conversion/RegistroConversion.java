package conversion;

import com.google.gson.annotations.SerializedName;

/**
 * Clase que representa la respuesta de la API de ExchangeRate-API.
 * Proporciona métodos para acceder a los datos del intercambio.
 */
public class RespuestaCambioDivisas {

    /** El código de la divisa base en el intercambio. Índices de la API de ExchangeRate-API:
     * "base_code"."target_code" "conversion_rate" "conversion_result"*/

    /** El código de la divisa origen en el intercambio. */
    @SerializedName("base_code")
    private String divisaOrigen;

    /** El código de la divisa destino en el intercambio. */
    @SerializedName("target_code")
    private String divisaDestino;

    /** La tasa de intercambio entre la divisa base y la divisa destino. */
    @SerializedName("conversion_rate")
    private double tasaCambio;

    /** El resultado del intercambio. */
    @SerializedName("conversion_result")
    private double resultado;

    /** Obtiene el código de la divisa base. */
    public String getDivisaOrigen() {
        return divisaOrigen;
    }

    /** Establece el código de la divisa base. */
    public void setDivisaOrigen(String divisaOrigen) {
        this.divisaOrigen = divisaOrigen;
    }

    /** Obtiene el código de la divisa destino. */
    public String getDivisaDestino() {
        return divisaDestino;
    }

    /** Establece el código de la divisa destino. */
    public void setDivisaDestino(String divisaDestino) {
        this.divisaDestino = divisaDestino;
    }

    /** Obtiene la tasa de intercambio. */
    public double getTasaCambio() {
        return tasaCambio;
    }

    /** Establece la tasa de intercambio. */
    public void setTasaCambio(double tasaCambio) {
        this.tasaCambio = tasaCambio;
    }

    /** Obtiene el resultado del intercambio. */
    public double getResultado() {
        return resultado;
    }

    /** Establece el resultado del intercambio. */
    public void setResultado(double resultado) {
        this.resultado = resultado;
    }

//    /** Devuelve una representación de cadena de la respuesta de intercambio. */
//    @Override
//    public String toString() {
//        return "RespuestaCambioDivisas{" +
//                "divisaOrigen='" + divisaOrigen + '\'' +
//                "divisaDestino='" + divisaDestino + '\'' +
//                "tasaCambio=" + tasaCambio +
//                "resultado=" + resultado +
//                '}';
//    }
}
