package com.boot.config;

import org.springframework.batch.item.database.ItemSqlParameterSourceProvider;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import com.boot.entity.Vehicle;

@Component
public class VehicleItemParameterSourceProvider implements ItemSqlParameterSourceProvider<Vehicle> {

	@Override
	public SqlParameterSource createSqlParameterSource(Vehicle item) {
		SqlParameterSource source = new MapSqlParameterSource().addValue("vehicleID", item.getVehicleID())
				.addValue("vin", item.getVin());
		return source;
	}

}
