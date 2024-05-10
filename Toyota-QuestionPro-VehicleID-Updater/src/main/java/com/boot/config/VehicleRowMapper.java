package com.boot.config;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.boot.entity.Vehicle;

@Component
public class VehicleRowMapper implements RowMapper<Vehicle> {

	@Override
	public Vehicle mapRow(ResultSet rs, int rowNum) throws SQLException {
		Vehicle vehicle = new Vehicle();
		vehicle.setVehicleID(rs.getInt("vehicleId"));
		vehicle.setSrcVehicleSalesDataID(rs.getInt("srcVehicleSalesDataID"));
		return vehicle;
	}
}
