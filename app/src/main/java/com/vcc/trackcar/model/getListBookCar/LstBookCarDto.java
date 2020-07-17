package com.vcc.trackcar.model.getListBookCar;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class LstBookCarDto implements Serializable {

    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("bookCarId")
    @Expose
    private Integer bookCarId;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
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
    @SerializedName("code")
    @Expose
    private String code;
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
    @SerializedName("managerStaffId")
    @Expose
    private Integer managerStaffId;
    @SerializedName("managerStaffName")
    @Expose
    private String managerStaffName;
    @SerializedName("managerStaffEmail")
    @Expose
    private String managerStaffEmail;
    @SerializedName("numPersonTogether")
    @Expose
    private Integer numPersonTogether;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("statusAdministrative")
    @Expose
    private String statusAdministrative;
    @SerializedName("createdDateView")
    @Expose
    private String createdDateView;
    @SerializedName("typeBookCar")
    @Expose
    private String typeBookCar;
    @SerializedName("reasonManage")
    @Expose
    private String reasonManage;
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
    @SerializedName("driverCode")
    @Expose
    private String driverCode;
    @SerializedName("captainCarName")
    @Expose
    private String captainCarName;
    @SerializedName("captainCarEmail")
    @Expose
    private String captainCarEmail;
    @SerializedName("statusCaptainCar")
    @Expose
    private String statusCaptainCar = "";
    @SerializedName("statusDriverBoard")
    @Expose
    private String statusDriverBoard = "";
    @SerializedName("reasonCaptainCar")
    @Expose
    private String reasonCaptainCar;
    @SerializedName("managerCarName")
    @Expose
    private String managerCarName;
    @SerializedName("managerCarEmail")
    @Expose
    private String managerCarEmail;
    @SerializedName("statusManagerCar")
    @Expose
    private String statusManagerCar = "";
    @SerializedName("reasonManagerCar")
    @Expose
    private String reasonManagerCar;
    @SerializedName("fwmodelId")
    @Expose
    private Integer fwmodelId;

    @SerializedName("reasonDriverBoard")
    @Expose
    private String reasonDriverBoard;
    @SerializedName("reasonAdministrative")
    @Expose
    private String reasonAdministrative;

    @SerializedName("captainCarId")
    @Expose
    private Integer captainCarId;
    @SerializedName("managerCarId")
    @Expose
    private Integer managerCarId;
    @SerializedName("driverBoardId")
    @Expose
    private Integer driverBoardId;
    @SerializedName("driverBoardName")
    @Expose
    private String driverBoardName;
    @SerializedName("driverBoardEmail")
    @Expose
    private String driverBoardEmail;
    @SerializedName("administrativeId")
    @Expose
    private Integer administrativeId;
    @SerializedName("administrativeName")
    @Expose
    private String administrativeName;
    @SerializedName("administrativeEmail")
    @Expose
    private String administrativeEmail;
    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("phoneNumberDriver")
    @Expose
    private String phoneNumberDriver;
    @SerializedName("statusManage")
    @Expose
    private String statusManage;
    @SerializedName("statusDriver")
    @Expose
    private String statusDriver;

    @SerializedName("toAddressExtend")
    @Expose
    private String toAddressExtend;
    @SerializedName("endTimeExtend")
    @Expose
    private String endTimeExtend;
    @SerializedName("internalProvince")
    @Expose
    private long internalProvince; // 1 la noi tinh; 2 la ngoai tinh

    @SerializedName("goodsWeight")
    @Expose
    private double goodsWeight;

    @SerializedName("viceManagerId")
    @Expose
    private long viceManagerId;
    @SerializedName("viceManagerName")
    @Expose
    private String viceManagerName;
    @SerializedName("viceManagerEmail")
    @Expose
    private String viceManagerEmail;
    @SerializedName("statusViceManager")
    @Expose
    private String statusViceManager;
    @SerializedName("reasonViceManager")
    @Expose
    private String reasonViceManager;

    @SerializedName("pairingCar")
    @Expose
    private long pairingCar;



    public String getStatusDriver() {
        return statusDriver;
    }

    public void setStatusDriver(String statusDriver) {
        this.statusDriver = statusDriver;
    }

    public String getStatusManage() {
        return statusManage;
    }

    public void setStatusManage(String statusManage) {
        this.statusManage = statusManage;
    }

    public String getPhoneNumberDriver() {
        return phoneNumberDriver;
    }

    public void setPhoneNumberDriver(String phoneNumberDriver) {
        this.phoneNumberDriver = phoneNumberDriver;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDriverCode() {
        return driverCode;
    }

    public void setDriverCode(String driverCode) {
        this.driverCode = driverCode;
    }

    public Integer getCaptainCarId() {
        return captainCarId;
    }

    public void setCaptainCarId(Integer captainCarId) {
        this.captainCarId = captainCarId;
    }

    public Integer getManagerCarId() {
        return managerCarId;
    }

    public void setManagerCarId(Integer managerCarId) {
        this.managerCarId = managerCarId;
    }

    public Integer getDriverBoardId() {
        return driverBoardId;
    }

    public void setDriverBoardId(Integer driverBoardId) {
        this.driverBoardId = driverBoardId;
    }

    public String getDriverBoardName() {
        return driverBoardName;
    }

    public void setDriverBoardName(String driverBoardName) {
        this.driverBoardName = driverBoardName;
    }

    public String getDriverBoardEmail() {
        return driverBoardEmail;
    }

    public void setDriverBoardEmail(String driverBoardEmail) {
        this.driverBoardEmail = driverBoardEmail;
    }

    public Integer getAdministrativeId() {
        return administrativeId;
    }

    public void setAdministrativeId(Integer administrativeId) {
        this.administrativeId = administrativeId;
    }

    public String getAdministrativeName() {
        return administrativeName;
    }

    public void setAdministrativeName(String administrativeName) {
        this.administrativeName = administrativeName;
    }

    public String getAdministrativeEmail() {
        return administrativeEmail;
    }

    public void setAdministrativeEmail(String administrativeEmail) {
        this.administrativeEmail = administrativeEmail;
    }

    public String getReasonAdministrative() {
        return reasonAdministrative;
    }

    public void setReasonAdministrative(String reasonAdministrative) {
        this.reasonAdministrative = reasonAdministrative;
    }

    public String getReasonDriverBoard() {
        return reasonDriverBoard;
    }

    public void setReasonDriverBoard(String reasonDriverBoard) {
        this.reasonDriverBoard = reasonDriverBoard;
    }

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public Integer getMessageColumn() {
        return messageColumn;
    }

    public void setMessageColumn(Integer messageColumn) {
        this.messageColumn = messageColumn;
    }

    public Integer getBookCarId() {
        return bookCarId;
    }

    public void setBookCarId(Integer bookCarId) {
        this.bookCarId = bookCarId;
    }

    public Integer getSysUserId() {
        return sysUserId;
    }

    public void setSysUserId(Integer sysUserId) {
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getNumPersonTogether() {
        return numPersonTogether;
    }

    public void setNumPersonTogether(Integer numPersonTogether) {
        this.numPersonTogether = numPersonTogether;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusAdministrative() {
        return statusAdministrative;
    }

    public void setStatusAdministrative(String statusAdministrative) {
        this.statusAdministrative = statusAdministrative;
    }

    public String getCreatedDateView() {
        return createdDateView;
    }

    public void setCreatedDateView(String createdDateView) {
        this.createdDateView = createdDateView;
    }

    public String getTypeBookCar() {
        return typeBookCar;
    }

    public void setTypeBookCar(String typeBookCar) {
        this.typeBookCar = typeBookCar;
    }

    public String getReasonManage() {
        return reasonManage;
    }

    public void setReasonManage(String reasonManage) {
        this.reasonManage = reasonManage;
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

    public String getCaptainCarName() {
        return captainCarName;
    }

    public void setCaptainCarName(String captainCarName) {
        this.captainCarName = captainCarName;
    }

    public String getCaptainCarEmail() {
        return captainCarEmail;
    }

    public void setCaptainCarEmail(String captainCarEmail) {
        this.captainCarEmail = captainCarEmail;
    }

    public String getStatusCaptainCar() {
        return statusCaptainCar;
    }

    public void setStatusCaptainCar(String statusCaptainCar) {
        this.statusCaptainCar = statusCaptainCar;
    }

    public String getReasonCaptainCar() {
        return reasonCaptainCar;
    }

    public void setReasonCaptainCar(String reasonCaptainCar) {
        this.reasonCaptainCar = reasonCaptainCar;
    }

    public String getManagerCarName() {
        return managerCarName;
    }

    public void setManagerCarName(String managerCarName) {
        this.managerCarName = managerCarName;
    }

    public String getManagerCarEmail() {
        return managerCarEmail;
    }

    public void setManagerCarEmail(String managerCarEmail) {
        this.managerCarEmail = managerCarEmail;
    }

    public String getStatusManagerCar() {
        return statusManagerCar;
    }

    public void setStatusManagerCar(String statusManagerCar) {
        this.statusManagerCar = statusManagerCar;
    }

    public String getReasonManagerCar() {
        return reasonManagerCar;
    }

    public void setReasonManagerCar(String reasonManagerCar) {
        this.reasonManagerCar = reasonManagerCar;
    }

    public Integer getFwmodelId() {
        return fwmodelId;
    }

    public void setFwmodelId(Integer fwmodelId) {
        this.fwmodelId = fwmodelId;
    }

    public String getStatusDriverBoard() {
        return statusDriverBoard;
    }

    public void setStatusDriverBoard(String statusDriverBoard) {
        this.statusDriverBoard = statusDriverBoard;
    }

    public String getToAddressExtend() {
        return toAddressExtend;
    }

    public void setToAddressExtend(String toAddressExtend) {
        this.toAddressExtend = toAddressExtend;
    }

    public String getEndTimeExtend() {
        return endTimeExtend;
    }

    public void setEndTimeExtend(String endTimeExtend) {
        this.endTimeExtend = endTimeExtend;
    }

    public long getInternalProvince() {
        return internalProvince;
    }

    public void setInternalProvince(long internalProvince) {
        this.internalProvince = internalProvince;
    }

    public long getViceManagerId() {
        return viceManagerId;
    }

    public void setViceManagerId(long viceManagerId) {
        this.viceManagerId = viceManagerId;
    }

    public String getViceManagerName() {
        return viceManagerName;
    }

    public void setViceManagerName(String viceManagerName) {
        this.viceManagerName = viceManagerName;
    }

    public String getViceManagerEmail() {
        return viceManagerEmail;
    }

    public void setViceManagerEmail(String viceManagerEmail) {
        this.viceManagerEmail = viceManagerEmail;
    }

    public String getStatusViceManager() {
        return statusViceManager;
    }

    public void setStatusViceManager(String statusViceManager) {
        this.statusViceManager = statusViceManager;
    }

    public String getReasonViceManager() {
        return reasonViceManager;
    }

    public void setReasonViceManager(String reasonViceManager) {
        this.reasonViceManager = reasonViceManager;
    }

    public double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(double goodsWeight) {
        this.goodsWeight = goodsWeight;
    }

    public long getPairingCar() {
        return pairingCar;
    }

    public void setPairingCar(long pairingCar) {
        this.pairingCar = pairingCar;
    }
}
