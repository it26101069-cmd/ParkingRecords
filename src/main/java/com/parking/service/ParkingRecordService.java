package com.parking.service;

import com.parking.model.ParkingRecord;
import java.sql.SQLException;
import java.util.List;

public interface ParkingRecordService {
    ParkingRecord createRecord(ParkingRecord record) throws SQLException;
    List<ParkingRecord> getAllRecords() throws SQLException;
    ParkingRecord getRecordById(String recordId) throws SQLException;
    boolean updateRecord(String recordId, ParkingRecord record) throws SQLException;
    boolean deleteRecord(String recordId) throws SQLException;
}
