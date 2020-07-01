package com.vcc.trackcar.ui.home.custom_infowindow_maps;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class InfoWindowData {
    @SerializedName("maLenh")
    @Expose
    private String maLenh;
    @SerializedName("nguoiLai")
    @Expose
    private String nguoiLai;
    @SerializedName("nguoiTao")
    @Expose
    private String nguoiTao;
    @SerializedName("thoiGianDi")
    @Expose
    private String thoiGianDi;
    @SerializedName("thoiGianDuKienConLai")
    @Expose
    private String thoiGianDuKienConLai;
    @SerializedName("content")
    @Expose
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMaLenh() {
        return maLenh;
    }

    public void setMaLenh(String maLenh) {
        this.maLenh = maLenh;
    }

    public String getNguoiLai() {
        return nguoiLai;
    }

    public void setNguoiLai(String nguoiLai) {
        this.nguoiLai = nguoiLai;
    }

    public String getNguoiTao() {
        return nguoiTao;
    }

    public void setNguoiTao(String nguoiTao) {
        this.nguoiTao = nguoiTao;
    }

    public String getThoiGianDi() {
        return thoiGianDi;
    }

    public void setThoiGianDi(String thoiGianDi) {
        this.thoiGianDi = thoiGianDi;
    }

    public String getThoiGianDuKienConLai() {
        return thoiGianDuKienConLai;
    }

    public void setThoiGianDuKienConLai(String thoiGianDuKienConLai) {
        this.thoiGianDuKienConLai = thoiGianDuKienConLai;
    }
}