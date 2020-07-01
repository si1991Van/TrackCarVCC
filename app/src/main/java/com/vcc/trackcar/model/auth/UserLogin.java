package com.vcc.trackcar.model.auth;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserLogin implements Serializable {

    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("id")
    @Expose
    private Object id;
    @SerializedName("page")
    @Expose
    private Object page;
    @SerializedName("pageSize")
    @Expose
    private Object pageSize;
    @SerializedName("total")
    @Expose
    private Object total;
    @SerializedName("keySearch")
    @Expose
    private Object keySearch;
    @SerializedName("text")
    @Expose
    private Object text;
    @SerializedName("customField")
    @Expose
    private Object customField;
    @SerializedName("errorList")
    @Expose
    private Object errorList;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("filePathError")
    @Expose
    private Object filePathError;
    @SerializedName("totalRecord")
    @Expose
    private Object totalRecord;
    @SerializedName("sysUserId")
    @Expose
    private Integer sysUserId;
    @SerializedName("loginName")
    @Expose
    private String loginName;
    @SerializedName("fullName")
    @Expose
    private String fullName;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("employeeCode")
    @Expose
    private String employeeCode;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phoneNumber")
    @Expose
    private String phoneNumber;
    @SerializedName("status")
    @Expose
    private Object status;
    @SerializedName("birthday")
    @Expose
    private Object birthday;
    @SerializedName("position")
    @Expose
    private Object position;
    @SerializedName("namePhone")
    @Expose
    private String namePhone;
    @SerializedName("sysGroupName")
    @Expose
    private String sysGroupName;
    @SerializedName("sysGroupId")
    @Expose
    private Integer sysGroupId;
    @SerializedName("departmentName")
    @Expose
    private String departmentName;
    @SerializedName("vofficePass")
    @Expose
    private Object vofficePass;
    @SerializedName("catProvinceCode")
    @Expose
    private Object catProvinceCode;
    @SerializedName("constructionCode")
    @Expose
    private Object constructionCode;
    @SerializedName("groupLevel")
    @Expose
    private Object groupLevel;
    @SerializedName("path")
    @Expose
    private Object path;
    @SerializedName("groupNameLevel3")
    @Expose
    private Object groupNameLevel3;
    @SerializedName("type")
    @Expose
    private Object type;
    @SerializedName("departmentId")
    @Expose
    private Integer departmentId;
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("roleCode")
    @Expose
    private String roleCode;
    @SerializedName("name")
    @Expose
    private Object name;
    @SerializedName("lstSysUserId")
    @Expose
    private Object lstSysUserId;
    @SerializedName("fwmodelId")
    @Expose
    private Integer fwmodelId;

    public String getDefaultSortField() {
        return defaultSortField;
    }

    public void setDefaultSortField(String defaultSortField) {
        this.defaultSortField = defaultSortField;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Object getPage() {
        return page;
    }

    public void setPage(Object page) {
        this.page = page;
    }

    public Object getPageSize() {
        return pageSize;
    }

    public void setPageSize(Object pageSize) {
        this.pageSize = pageSize;
    }

    public Object getTotal() {
        return total;
    }

    public void setTotal(Object total) {
        this.total = total;
    }

    public Object getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(Object keySearch) {
        this.keySearch = keySearch;
    }

    public Object getText() {
        return text;
    }

    public void setText(Object text) {
        this.text = text;
    }

    public Object getCustomField() {
        return customField;
    }

    public void setCustomField(Object customField) {
        this.customField = customField;
    }

    public Object getErrorList() {
        return errorList;
    }

    public void setErrorList(Object errorList) {
        this.errorList = errorList;
    }

    public Integer getMessageColumn() {
        return messageColumn;
    }

    public void setMessageColumn(Integer messageColumn) {
        this.messageColumn = messageColumn;
    }

    public Object getFilePathError() {
        return filePathError;
    }

    public void setFilePathError(Object filePathError) {
        this.filePathError = filePathError;
    }

    public Object getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Object totalRecord) {
        this.totalRecord = totalRecord;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
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

    public Object getStatus() {
        return status;
    }

    public void setStatus(Object status) {
        this.status = status;
    }

    public Object getBirthday() {
        return birthday;
    }

    public void setBirthday(Object birthday) {
        this.birthday = birthday;
    }

    public Object getPosition() {
        return position;
    }

    public void setPosition(Object position) {
        this.position = position;
    }

    public String getNamePhone() {
        return namePhone;
    }

    public void setNamePhone(String namePhone) {
        this.namePhone = namePhone;
    }

    public String getSysGroupName() {
        return sysGroupName;
    }

    public void setSysGroupName(String sysGroupName) {
        this.sysGroupName = sysGroupName;
    }

    public Integer getSysGroupId() {
        return sysGroupId;
    }

    public void setSysGroupId(Integer sysGroupId) {
        this.sysGroupId = sysGroupId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Object getVofficePass() {
        return vofficePass;
    }

    public void setVofficePass(Object vofficePass) {
        this.vofficePass = vofficePass;
    }

    public Object getCatProvinceCode() {
        return catProvinceCode;
    }

    public void setCatProvinceCode(Object catProvinceCode) {
        this.catProvinceCode = catProvinceCode;
    }

    public Object getConstructionCode() {
        return constructionCode;
    }

    public void setConstructionCode(Object constructionCode) {
        this.constructionCode = constructionCode;
    }

    public Object getGroupLevel() {
        return groupLevel;
    }

    public void setGroupLevel(Object groupLevel) {
        this.groupLevel = groupLevel;
    }

    public Object getPath() {
        return path;
    }

    public void setPath(Object path) {
        this.path = path;
    }

    public Object getGroupNameLevel3() {
        return groupNameLevel3;
    }

    public void setGroupNameLevel3(Object groupNameLevel3) {
        this.groupNameLevel3 = groupNameLevel3;
    }

    public Object getType() {
        return type;
    }

    public void setType(Object type) {
        this.type = type;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public Object getName() {
        return name;
    }

    public void setName(Object name) {
        this.name = name;
    }

    public Object getLstSysUserId() {
        return lstSysUserId;
    }

    public void setLstSysUserId(Object lstSysUserId) {
        this.lstSysUserId = lstSysUserId;
    }

    public Integer getFwmodelId() {
        return fwmodelId;
    }

    public void setFwmodelId(Integer fwmodelId) {
        this.fwmodelId = fwmodelId;
    }

}
