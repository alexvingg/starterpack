package br.com.starterpack.core.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {

    private String msg;

    private Map<String, Object> data;

    /**
     * Response ok
     * @return {@link Response}
     */
    public static Response ok() {
        return new Response();
    }

    /**
     * Resposta ok com mensagem.
     *
     * @param message .
     * @return Response
     */
    public static Response ok(final String message) {
        final Response r = Response.ok();
        r.setMsg(message);
        return r;
    }

    /**
     * Resposta de erro com mensagem.
     *
     * @param message .
     * @return Response
     */
    public static Response error(final String message) {
        final Response r = new Response();
        r.setMsg(message);
        return r;
    }

    /**
     * Resposta do tipo internal server error
     * @return {@link Response}
     */
    public static Response internalServerError() {
        return Response.error("Internal Server Error");
    }

    /**
     * Adiciona um parametro para retorno JSON.
     *
     * @param key .
     * @param value .
     * @return Response
     */
    public Response addData(final String key, final Object value) {

        if (this.data == null) {
            this.data = new HashMap<>();
        }

        if (this.data.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Key [%s] exists.", key));
        }

        this.data.put(key, value);

        return this;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response [msg=" + msg + ", data=" + data + "]";
    }

}
