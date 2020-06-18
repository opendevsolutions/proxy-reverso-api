package ar.com.medife.processors;

import java.io.UnsupportedEncodingException;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.commons.codec.binary.Base64;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class TokenDecoderProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {

        String token = (String) exchange.getIn().getHeader("Authorization");
        if(token != null && token != "" && !token.contains("Basic")){
	        JsonObject decode = decodeToken(token);
	        System.out.println("token= " + decode.toString());
        }
        exchange.getIn().setHeader("Authorization", token);
        exchange.getIn().removeHeader("Authorization");

    }

    private JsonObject decodeToken(String token) throws UnsupportedEncodingException {
        String base64EncodedBody = token.split("\\.")[1];
        Base64 base64Url = new Base64(true);
        String body = new String(base64Url.decode(base64EncodedBody), "UTF-8");
        Gson gson = new Gson();
        JsonElement element = gson.fromJson(body, JsonElement.class);
        JsonElement userdata = element.getAsJsonObject().get("userdata");

        return userdata.getAsJsonObject();
    }
}
