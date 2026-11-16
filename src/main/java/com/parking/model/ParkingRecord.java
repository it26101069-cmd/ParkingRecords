package com.parking.model;

import java.time.LocalDateTime;

public class ParkingRecord {
    private String recordId;
    private String customerName;
    private String vehicleNumber;
    private String slotNumber;
    private LocalDateTime bookingTime;
    private String status;

    public ParkingRecord() {}

    public ParkingRecord(String recordId, String customerName, String vehicleNumber,
                         String slotNumber, LocalDateTime bookingTime, String status) {
        this.recordId = recordId;
        this.customerName = customerName;
        this.vehicleNumber = vehicleNumber;
        this.slotNumber = slotNumber;
        this.bookingTime = bookingTime;
        this.status = status;
    }

    public String getRecordId() { return recordId; }
    public void setRecordId(String recordId) { this.recordId = recordId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public LocalDateTime getBookingTime() { return bookingTime; }
    public void setBookingTime(LocalDateTime bookingTime) { this.bookingTime = bookingTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
