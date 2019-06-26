package de.smartheating.outputAdapter.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import de.smartheating.outputAdapter.services.OutputService;
import io.swagger.annotations.ApiOperation;
import javassist.NotFoundException;

@RestController
public class OutputController {
	
	Logger logger = LoggerFactory.getLogger(OutputController.class);
	
	@Autowired
	OutputService outputService;
	
	@PostMapping(value = "/updateDeviceValue/{deviceId}", produces = "application/json")
	@ApiOperation(value = "Update the current value of a registered device")
	public ResponseEntity<?> updateDeviceValue(@PathVariable Long deviceId, @RequestParam(required = true) String value) {
		try {
			logger.info("Received request to update device with ID: '" + deviceId + "' with the new value: '" + value + "'");
			outputService.updateDeviceValue(deviceId, value);
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (RestClientException e) {
			logger.error("Connection with Device failed");
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (NotFoundException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
