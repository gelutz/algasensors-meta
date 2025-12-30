package lutz.algasensors.seeder.domain.service;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Service;

import io.hypersistence.tsid.TSID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class TemperatureGeneratorService {
	private static final double BASE_TEMPERATURE = 25.0;
	private static final double INITIAL_VARIANCE = 10.0;
	private static final double MIN_STEP = 1.0;
	private static final double MAX_STEP = 5.0;

	private final Map<TSID, Double> sensorTemperatures = new ConcurrentHashMap<>();
	private final Random random = new Random();

	public double initializeTemperature(TSID sensorId) {
		double initialTemp = BASE_TEMPERATURE + (random.nextDouble() * 2 - 1) * INITIAL_VARIANCE;
		sensorTemperatures.put(sensorId, initialTemp);
		log.debug("Initialized temperature for sensor {}: {}", sensorId, initialTemp);
		return initialTemp;
	}

	public double getNextTemperature(TSID sensorId) {
		Double currentTemp = sensorTemperatures.get(sensorId);
		if (currentTemp == null) {
			return initializeTemperature(sensorId);
		}

		double step = MIN_STEP + random.nextDouble() * (MAX_STEP - MIN_STEP);
		boolean increase = random.nextBoolean();
		double newTemp = increase ? currentTemp + step : currentTemp - step;

		sensorTemperatures.put(sensorId, newTemp);
		log.debug("Temperature for sensor {} changed from {} to {} ({}{})",
				sensorId, currentTemp, newTemp, increase ? "+" : "-", step);
		return newTemp;
	}

	public boolean hasSensor(TSID sensorId) {
		return sensorTemperatures.containsKey(sensorId);
	}

	public void removeSensor(TSID sensorId) {
		sensorTemperatures.remove(sensorId);
		log.debug("Removed temperature tracking for sensor {}", sensorId);
	}
}

