package ar.com.medife.services;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http4.HttpComponent;
import org.springframework.stereotype.Component;

import ar.com.medife.config.HttpClientConfigurerTrustAllCACerts;

/* insert in this class:
 * General configuration to the rest services
 */
@Component
public abstract class BaseRouteBuilder extends RouteBuilder {

	@Override
	public void configure() throws Exception {
		/* insert in this method:
		 * General excelptions
		 */		
		HttpComponent httpComponent = getContext().getComponent("https4", HttpComponent.class);
    	httpComponent.setHttpClientConfigurer(new HttpClientConfigurerTrustAllCACerts());
		
		addRoutes();
	}
	
		
	protected abstract void addRoutes() throws Exception;
}
