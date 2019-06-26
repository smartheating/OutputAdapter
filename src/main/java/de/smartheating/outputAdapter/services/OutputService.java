package de.smartheating.outputAdapter.services;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

import de.smartheating.SmartHeatingCommons.persistedData.Device;
import de.smartheating.SmartHeatingCommons.persistedData.Event;
import de.smartheating.SmartHeatingCommons.restcommunication.DeviceClient;
import de.smartheating.SmartHeatingCommons.restcommunication.RepositoryClient;
import de.smartheating.SmartHeatingCommons.services.DiscoveryService;
import de.smartheating.SmartHeatingCommons.services.ValueChecker;
import javassist.NotFoundException;

@Service
public class OutputService {

	Logger logger = LoggerFactory.getLogger(OutputService.class);

	@Autowired
	RepositoryClient repoClient;
	@Autowired
	DeviceClient deviceClient;
	@Autowired
	DiscoveryService discoveryService;
	@Autowired
	ValueChecker valueChecker;

	public void updateDeviceValue(Long deviceId, String value) throws NotFoundException, IllegalArgumentException, RestClientException {
		logger.info("Trying to obtain device with ID: '" + deviceId + "' from repository");
		Device device = repoClient.getDevice(discoveryService.getServiceUrl("repository"), deviceId);
		logger.info("Validating new value for the device");
		valueChecker.checkValueFormat(value, device.getValueType());
		logger.info("Creating Event-Object");
		Event event = createEvent(device, value);
		logger.info("Trying to send Event to Device");
		deviceClient.updateDeviceValue(discoveryService.getDeviceUrl(device.getIp(), device.getPort()), event);

	}

	private Event createEvent(Device device, String value) {
		Event event = new Event();
		event.setValue(value);
		event.setValueType(device.getValueType());
		event.setDeviceId(device.getId());

		Date date = new Date();
		event.setTimestamp(date);
		return event;
	}

}
