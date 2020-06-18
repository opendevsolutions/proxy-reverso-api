package ar.com.medife.services;

import org.apache.camel.Exchange;
import org.apache.camel.component.http.HttpMethods;
import org.apache.camel.component.jackson.JacksonDataFormat;

public class SSORouter extends BaseRouteBuilder {
	
    @Override
    public void addRoutes() throws Exception {
    	
		from("direct:callSSO")
			.setHeader("pathBkp", simple("${header.CamelHttpPath}"))
			.setHeader("bodyBkp", simple("${bodyAs(String)}"))
			.removeHeader(Exchange.HTTP_PATH)
			.setHeader(Exchange.HTTP_METHOD, constant(HttpMethods.POST))
			.setHeader(Exchange.CONTENT_TYPE, simple("application/x-www-form-urlencoded"))
			.setBody().simple("client_id={{sso.login.clientId}}&client_secret={{sso.login.clientSecret}}&grant_type=client_credentials")
			.to("https4://{{sso.login.url}}?bridgeEndpoint=true")
			.unmarshal(new JacksonDataFormat(Object.class))
			.setHeader("Authorization", simple("Bearer ${body[access_token]}"))
			.setHeader(Exchange.HTTP_PATH, simple("${header.pathBkp}"))
			.setBody(simple("${header.bodyBkp}"));
	}

}