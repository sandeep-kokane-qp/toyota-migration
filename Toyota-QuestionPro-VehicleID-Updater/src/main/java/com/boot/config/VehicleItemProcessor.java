package com.boot.config;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.boot.entity.Vehicle;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class VehicleItemProcessor implements ItemProcessor<Vehicle, Vehicle> {

	@Override
	public Vehicle process(Vehicle item) throws Exception {
		log.info("Updating for VehicleID : " + item.getVehicleID());
		return item;
	}

}
