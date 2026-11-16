package com.parking.service.impl;

import com.parking.model.ParkingRecord;
import com.parking.repository.custom.ParkingRecordRepository;
import com.parking.service.ParkingRecordService;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class ParkingRecordServiceImpl implements ParkingRecordService {
    private static final Set<String> STATUSES = Set.of("ACTIVE", "COMPLETED", "CANCELLED");
    private final ParkingRecordRepository recordRepository;
    private final DataSource dataSource;

    public ParkingRecordServiceImpl(ParkingRecordRepository recordRepository, DataSource dataSource) {
        this.recordRepository = recordRepository;
        this.dataSource = dataSource;
    }

    @Override
    public ParkingRecord createRecord(ParkingRecord record) throws SQLException {
        normalizeAndValidate(record, true);
        try (Connection connection = dataSource.getConnection()) {
            record.setRecordId(recordRepository.nextRecordId(connection));
            record.setBookingTime(LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS));
            if (!recordRepository.create(record, connection))
                throw new IllegalStateException("Parking record creation failed");
            return record;
        }
    }

    @Override
    public List<ParkingRecord> getAllRecords() throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            return recordRepository.findAll(connection);
        }
    }

    @Override
    public ParkingRecord getRecordById(String recordId) throws SQLException {
        String id = normalizeId(recordId);
        try (Connection connection = dataSource.getConnection()) {
            return recordRepository.findById(id, connection)
                    .orElseThrow(() -> new NoSuchElementException("Parking record not found: " + id));
        }
    }

    @Override
    public boolean updateRecord(String recordId, ParkingRecord record) throws SQLException {
        String id = normalizeId(recordId);
        normalizeAndValidate(record, false);
        try (Connection connection = dataSource.getConnection()) {
            return recordRepository.update(id, record, connection);
        }
    }

    @Override
    public boolean deleteRecord(String recordId) throws SQLException {
        String id = normalizeId(recordId);
        try (Connection connection = dataSource.getConnection()) {
            return recordRepository.deleteById(id, connection);
        }
    }

    private String normalizeId(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Record id is required");
        return id.trim().toUpperCase(Locale.ROOT);
    }

    private void normalizeAndValidate(ParkingRecord r, boolean creating) {
        if (r == null) throw new IllegalArgumentException("Parking record cannot be null");
        if (r.getCustomerName() == null || r.getCustomerName().isBlank() || r.getCustomerName().length() > 100)
            throw new IllegalArgumentException("Valid customer name is required");
        if (r.getVehicleNumber() == null || r.getVehicleNumber().isBlank())
            throw new IllegalArgumentException("Vehicle number is required");
        String vehicle = r.getVehicleNumber().trim().toUpperCase(Locale.ROOT);
        if (vehicle.length() > 20 || !vehicle.matches("[A-Z0-9-]+"))
            throw new IllegalArgumentException("Invalid vehicle number format");
        if (r.getSlotNumber() == null || r.getSlotNumber().isBlank())
            throw new IllegalArgumentException("Slot number is required");
        String slot = r.getSlotNumber().trim().toUpperCase(Locale.ROOT);
        if (slot.length() > 20 || !slot.matches("[A-Z0-9-]+"))
            throw new IllegalArgumentException("Invalid slot number format");
        String status = (r.getStatus() == null || r.getStatus().isBlank())
                ? "ACTIVE" : r.getStatus().trim().toUpperCase(Locale.ROOT);
        if (!STATUSES.contains(status))
            throw new IllegalArgumentException("Invalid status. Use: " + STATUSES);
        r.setCustomerName(r.getCustomerName().trim());
        r.setVehicleNumber(vehicle);
        r.setSlotNumber(slot);
        r.setStatus(status);
    }
}
