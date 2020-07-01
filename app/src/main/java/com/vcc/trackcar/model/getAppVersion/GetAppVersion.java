package com.vcc.trackcar.model.getAppVersion;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetAppVersion {
    @SerializedName("defaultSortField")
    @Expose
    private String defaultSortField;
    @SerializedName("messageColumn")
    @Expose
    private Integer messageColumn;
    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("link")
    @Expose
    private String link;

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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
