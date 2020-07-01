package com.vcc.trackcar.model.addBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookCarDto {
    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("sysUserId")
    @Expose
    private String sysUserId;
    @SerializedName("loginName")
    @Expose
    private String loginName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("sysGroupId")
    @Expose
    private Integer sysGroupId;
    @SerializedName("sysGroupName")
    @Expose
    private String sysGroupName;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("carTypeId")
    @Expose
    private Integer carTypeId;
    @SerializedName("carTypeName")
    @Expose
    private String carTypeName;
    @SerializedName("typeBookCar")
    @Expose
    private String typeBookCar;
    @SerializedName("startTime")
    @Expose
    private String startTime;
    @SerializedName("endTime")
    @Expose
    private String endTime;
    @SerializedName("fromAddress")
    @Expose
    private String fromAddress;
    @SerializedName("toAddress")
    @Expose
    private String toAddress;
    @SerializedName("numPersonTogether")
    @Expose
    private Integer numPersonTogether;
    @SerializedName("managerStaffId")
    @Expose
    private Integer managerStaffId;
    @SerializedName("managerStaffName")
    @Expose
    private String managerStaffName;
    @SerializedName("managerStaffEmail")
    @Expose
    private String managerStaffEmail;
    @SerializedName("carId")
    @Expose
    private Integer carId;
    @SerializedName("licenseCar")
    @Expose
    private String licenseCar;
    @SerializedName("driverId")
    @Expose
    private Integer driverId;
    @SerializedName("driverName")
    @Expose
    private String driverName;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("createdDateView")
    @Expose
    private String createdDateView;
    @SerializedName("code")
    @Expose
    private String code;

    @SerializedName("driverCode")
    @Expose
    private String driverCode;
    @SerializedName("phoneNumberDriver")
    @Expose
    private String phoneNumberDriver;

    @SerializedName("internalProvince")
    @Expose
    private long internalProvince; // 1 la noi tinh; 2 la ngoai tinh


    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public String getPhoneNumberDriver() {
        return phoneNumberDriver;
    }

    public void setPhoneNumberDriver(String phoneNumberDriver) {
        this.phoneNumberDriver = phoneNumberDriver;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCreatedDateView() {
        return createdDateView;
    }

    public void setCreatedDateView(String createdDateView) {
        this.createdDateView = createdDateView;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getBookCarId() {
        return bookCarId;
    }

    public void setBookCarId(Integer bookCarId) {
        this.bookCarId = bookCarId;
    }

    public String getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(String sysUserId) {
        this.sysUserId = sysUserId;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(Integer sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Integer getCarTypeId() {
        return carTypeId;
    }

    public void setCarTypeId(Integer carTypeId) {
        this.carTypeId = carTypeId;
    }

    public String getCarTypeName() {
        return carTypeName;
    }

    public void setCarTypeName(String carTypeName) {
        this.carTypeName = carTypeName;
    }

    public String getTypeBookCar() {
        return typeBookCar;
    }

    public void setTypeBookCar(String typeBookCar) {
        this.typeBookCar = typeBookCar;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public Integer getNumPersonTogether() {
        return numPersonTogether;
    }

    public void setNumPersonTogether(Integer numPersonTogether) {
        this.numPersonTogether = numPersonTogether;
    }

    public Integer getManagerStaffId() {
        return managerStaffId;
    }

    public void setManagerStaffId(Integer managerStaffId) {
        this.managerStaffId = managerStaffId;
    }

    public String getManagerStaffName() {
        return managerStaffName;
    }

    public void setManagerStaffName(String managerStaffName) {
        this.managerStaffName = managerStaffName;
    }

    public String getManagerStaffEmail() {
        return managerStaffEmail;
    }

    public void setManagerStaffEmail(String managerStaffEmail) {
        this.managerStaffEmail = managerStaffEmail;
    }

    public Integer getCarId() {
        return carId;
    }

    public void setCarId(Integer carId) {
        this.carId = carId;
    }

    public String getLicenseCar() {
        return licenseCar;
    }

    public void setLicenseCar(String licenseCar) {
        this.licenseCar = licenseCar;
    }

    public Integer getDriverId() {
        return driverId;
    }

    public void setDriverId(Integer driverId) {
        this.driverId = driverId;
    }

    public String getDriverName() {
        return driverName;
    }

    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    public long getInternalProvince() {
        return internalProvince;
    }

    public void setInternalProvince(long internalProvince) {
        this.internalProvince = internalProvince;
    }
}
