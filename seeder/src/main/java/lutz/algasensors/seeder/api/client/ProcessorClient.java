package lutz.algasensors.seeder.api.client;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

import io.hypersistence.tsid.TSID;

@HttpExchange("/api/sensors/{sensorId}/temperatures")
public interface ProcessorClient {
	@PostExchange(value = "/", contentType = MediaType.TEXT_PLAIN_VALUE)
	void sendTemperature(@PathVariable TSID sensorId, @RequestBody String temperature);
}

