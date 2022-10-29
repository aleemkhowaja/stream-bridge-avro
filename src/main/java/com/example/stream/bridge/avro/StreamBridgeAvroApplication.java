package com.example.stream.bridge.avro;

import java.util.Random;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;

import com.example.Sensor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.util.MimeType.valueOf;

@SpringBootApplication
@RestController
public class StreamBridgeAvroApplication {

	private Random random = new Random();

	@Autowired
	StreamBridge streamBridge;

	@GetMapping("/kafka/sendMessage")
	public void sendMessage() {
		Sensor sensor = new Sensor();
		sensor.setId(UUID.randomUUID().toString() + "-v1");
		sensor.setAcceleration(random.nextFloat() * 10);
		sensor.setVelocity(random.nextFloat() * 100);
		sensor.setTemperature(random.nextFloat() * 50);
		System.out.println("sending Sensor: ");
		streamBridge.send("supplier-out-0", sensor, valueOf("avro/bytes"));
	}

//	@Bean
//	public Supplier<Sensor> supplier() {
//
//		return () -> {
//			Sensor sensor = new Sensor();
//			sensor.setId(UUID.randomUUID().toString() + "-v1");
//			sensor.setAcceleration(random.nextFloat() * 10);
//			sensor.setVelocity(random.nextFloat() * 100);
//			sensor.setTemperature(random.nextFloat() * 50);
//			return sensor;
//		};
//	}

//	@Bean
//	public Consumer<Sensor> receiveAndForward() {
//		    Sensor sensor = new Sensor();
//			sensor.setId(UUID.randomUUID().toString() + "-v1");
//			sensor.setAcceleration(random.nextFloat() * 10);
//			sensor.setVelocity(random.nextFloat() * 100);
//			sensor.setTemperature(random.nextFloat() * 50);
//
//		System.out.println("sending Sensor: ");
//		return s -> streamBridge.send("sensor-out-0", sensor);
//	}


//	@Bean
//	Consumer<Sensor> receive() {
//		return s -> System.out.println("Received Sensor: " + s);
//	}

	public static void main(String[] args) {
		SpringApplication.run(StreamBridgeAvroApplication.class, args);
	}

}
