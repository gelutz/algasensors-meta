package lutz.algasensors.seeder.infrastructure.scheduler;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lutz.algasensors.seeder.api.client.ManagerClient;
import lutz.algasensors.seeder.api.client.ProcessorClient;
import lutz.algasensors.seeder.api.model.PageResponse;
import lutz.algasensors.seeder.api.model.SensorOutput;
import lutz.algasensors.seeder.domain.service.TemperatureGeneratorService;

@Slf4j
@Component
@RequiredArgsConstructor
public class TemperatureSeederScheduler {
	private static final int MAX_SENSORS_PAGE_SIZE = 1000;

	private final ManagerClient managerClient;
	private final ProcessorClient processorClient;
	private final TemperatureGeneratorService temperatureGeneratorService;

	private final List<TSID> sensorIds = new CopyOnWriteArrayList<>();

	@Scheduled(fixedDelay = 180000, initialDelay = 0)
	public void refreshSensors() {
		log.info("Refreshing sensor list from manager service...");
		try {
			PageResponse<SensorOutput> response = managerClient.getSensors(MAX_SENSORS_PAGE_SIZE);
			List<TSID> newSensorIds = response.content().stream()
					.map(item -> item.id())
					.toList();

			sensorIds.clear();
			sensorIds.addAll(newSensorIds);

			newSensorIds.forEach(sensorId -> {
				if (!temperatureGeneratorService.hasSensor(sensorId)) {
					temperatureGeneratorService.initializeTemperature(sensorId);
				}
			});

			log.info("Sensor list refreshed. Found {} sensors.", sensorIds.size());
		} catch (Exception e) {
			log.error("Failed to refresh sensor list: {}", e.getMessage(), e);
		}
	}

	@Scheduled(fixedDelay = 3000, initialDelay = 5000)
	public void sendTemperatures() {
		if (sensorIds.isEmpty()) {
			log.debug("No sensors available. Skipping temperature sending.");
			return;
		}

		log.info("Sending temperatures for {} sensors...", sensorIds.size());

		for (TSID sensorId : sensorIds) {
			try {
				double temperature = temperatureGeneratorService.getNextTemperature(sensorId);
				String temperatureStr = String.format("%.2f", temperature);
				processorClient.sendTemperature(sensorId, temperatureStr);
				log.debug("Sent temperature {} for sensor {}", temperatureStr, sensorId);
			} catch (Exception e) {
				log.error("Failed to send temperature for sensor {}: {}", sensorId, e.getMessage());
			}
		}

		log.info("Temperature sending cycle completed.");
	}
}
