package com.parking.repository.custom.impl;

import com.parking.model.ParkingRecord;
import com.parking.repository.custom.ParkingRecordRepository;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ParkingRecordRepositoryImpl implements ParkingRecordRepository {

    @Override
    public String nextRecordId(Connection connection) throws SQLException {
        String sql = "SELECT COALESCE(MAX(CAST(SUBSTRING(record_id, 5) AS UNSIGNED)), 1000) + 1 FROM parking_records";
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return "REC-" + rs.getLong(1);
        }
    }

    @Override
    public boolean create(ParkingRecord r, Connection connection) throws SQLException {
        String sql = "INSERT INTO parking_records (record_id, customer_name, vehicle_number, slot_number, booking_time, status) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getRecordId());
            ps.setString(2, r.getCustomerName());
            ps.setString(3, r.getVehicleNumber());
            ps.setString(4, r.getSlotNumber());
            ps.setTimestamp(5, Timestamp.valueOf(r.getBookingTime()));
            ps.setString(6, r.getStatus());
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public List<ParkingRecord> findAll(Connection connection) throws SQLException {
        List<ParkingRecord> list = new ArrayList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM parking_records ORDER BY booking_time DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) list.add(mapRecord(rs));
        }
        return list;
    }

    @Override
    public Optional<ParkingRecord> findById(String recordId, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM parking_records WHERE record_id = ?")) {
            ps.setString(1, recordId);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? Optional.of(mapRecord(rs)) : Optional.empty();
            }
        }
    }

    @Override
    public boolean update(String recordId, ParkingRecord r, Connection connection) throws SQLException {
        String sql = "UPDATE parking_records SET customer_name = ?, vehicle_number = ?, slot_number = ?, status = ? WHERE record_id = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, r.getCustomerName());
            ps.setString(2, r.getVehicleNumber());
            ps.setString(3, r.getSlotNumber());
            ps.setString(4, r.getStatus());
            ps.setString(5, recordId);
            return ps.executeUpdate() > 0;
        }
    }

    @Override
    public boolean deleteById(String recordId, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement("DELETE FROM parking_records WHERE record_id = ?")) {
            ps.setString(1, recordId);
            return ps.executeUpdate() > 0;
        }
    }

    private ParkingRecord mapRecord(ResultSet rs) throws SQLException {
        return new ParkingRecord(rs.getString("record_id"), rs.getString("customer_name"),
                rs.getString("vehicle_number"), rs.getString("slot_number"),
                rs.getTimestamp("booking_time").toLocalDateTime(), rs.getString("status"));
    }
}
