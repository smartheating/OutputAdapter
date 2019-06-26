package de.smartheating.outputAdapter.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import de.smartheating.SmartHeatingCommons.persistedData.Event;

@Component
public class MessageConsumer {
	
	Logger logger = LoggerFactory.getLogger(MessageConsumer.class);

	public void consumeEvent(Event event) {
		logger.info("Consumed event with id: " + event.getId());
	}
}
