package com.parking.repository.custom;

import com.parking.model.ParkingRecord;
import com.parking.repository.CrudRepository;

import java.sql.Connection;
import java.sql.SQLException;

public interface ParkingRecordRepository extends CrudRepository<ParkingRecord, String> {
    // Generates the next id: REC-1001, REC-1002 ...
    String nextRecordId(Connection connection) throws SQLException;
}
