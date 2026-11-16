package com.parking.dto;

public class CreateParkingRecordRequest {
    private String customerName;
    private String vehicleNumber;
    private String slotNumber;
    private String status;

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getVehicleNumber() { return vehicleNumber; }
    public void setVehicleNumber(String vehicleNumber) { this.vehicleNumber = vehicleNumber; }
    public String getSlotNumber() { return slotNumber; }
    public void setSlotNumber(String slotNumber) { this.slotNumber = slotNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
