package lutz.algasensors.seeder.api.client;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestClientFactory {
	private final RestClient.Builder builder;

	@Value("${app.services.manager.url}")
	private String managerUrl;
	@Value("${app.services.manager.port}")
	private String managerPort;

	@Value("${app.services.processor.url}")
	private String processorUrl;
	@Value("${app.services.processor.port}")
	private String processorPort;

	public RestClient managerClient() {
		log.debug("Creating manager client with base URL: {}:{}", managerUrl, managerPort);
		return builder.clone()
				.baseUrl(managerUrl + ":" + managerPort)
				.requestFactory(generateClientHttpRequestFactory())
				.build();
	}

	public RestClient processorClient() {
		log.debug("Creating processor client with base URL: {}:{}", processorUrl, processorPort);
		return builder.clone()
				.baseUrl(processorUrl + ":" + processorPort)
				.requestFactory(generateClientHttpRequestFactory())
				.build();
	}

	private ClientHttpRequestFactory generateClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setReadTimeout(Duration.ofSeconds(5));
		factory.setConnectTimeout(Duration.ofSeconds(3));
		return factory;
	}
}

