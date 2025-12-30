package lutz.algasensors.seeder.api.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class RestClientConfig {

	@Bean
	public ManagerClient managerClient(RestClientFactory factory) {
		RestClient restClient = factory.managerClient();
		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
		return proxyFactory.createClient(ManagerClient.class);
	}

	@Bean
	public ProcessorClient processorClient(RestClientFactory factory) {
		RestClient restClient = factory.processorClient();
		RestClientAdapter restClientAdapter = RestClientAdapter.create(restClient);
		HttpServiceProxyFactory proxyFactory = HttpServiceProxyFactory.builderFor(restClientAdapter).build();
		return proxyFactory.createClient(ProcessorClient.class);
	}
}

