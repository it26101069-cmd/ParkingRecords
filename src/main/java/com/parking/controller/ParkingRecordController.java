package com.parking.controller;

import com.parking.dto.CreateParkingRecordRequest;
import com.parking.model.ParkingRecord;
import com.parking.service.ParkingRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/api/records")
public class ParkingRecordController {
    private final ParkingRecordService recordService;

    public ParkingRecordController(ParkingRecordService recordService) {
        this.recordService = recordService;
    }

    @PostMapping
    public ResponseEntity<ParkingRecord> createRecord(@RequestBody CreateParkingRecordRequest request) throws SQLException {
        return ResponseEntity.status(HttpStatus.CREATED).body(recordService.createRecord(toRecord(request)));
    }

    @GetMapping
    public ResponseEntity<List<ParkingRecord>> getAllRecords() throws SQLException {
        return ResponseEntity.ok(recordService.getAllRecords());
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<ParkingRecord> getRecordById(@PathVariable String recordId) throws SQLException {
        return ResponseEntity.ok(recordService.getRecordById(recordId));
    }

    @PutMapping("/{recordId}")
    public ResponseEntity<Void> updateRecord(@PathVariable String recordId,
                                             @RequestBody CreateParkingRecordRequest request) throws SQLException {
        return recordService.updateRecord(recordId, toRecord(request))
                ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{recordId}")
    public ResponseEntity<Void> deleteRecord(@PathVariable String recordId) throws SQLException {
        return recordService.deleteRecord(recordId)
                ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private ParkingRecord toRecord(CreateParkingRecordRequest r) {
        ParkingRecord record = new ParkingRecord();
        record.setCustomerName(r.getCustomerName());
        record.setVehicleNumber(r.getVehicleNumber());
        record.setSlotNumber(r.getSlotNumber());
        record.setStatus(r.getStatus());
        return record;
    }
}
