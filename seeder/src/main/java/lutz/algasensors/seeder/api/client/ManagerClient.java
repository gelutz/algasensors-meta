package lutz.algasensors.seeder.api.client;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;

import lutz.algasensors.seeder.api.model.PageResponse;
import lutz.algasensors.seeder.api.model.SensorOutput;

@HttpExchange("/api/sensors")
public interface ManagerClient {
	@GetExchange
	PageResponse<SensorOutput> getSensors(@RequestParam int size);
}

