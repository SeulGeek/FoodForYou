package com.example.foodforyou.model;

import java.util.Date;

// Response API
public class RecommendDietListResponse {
    int cntntsNo;
    String dietNm;
    String fdNm;
    String cntntsSj;
    String cntntsChargerEsntlNm;
    Date registDt;
    int cntntsRdcnt;
    int rtnFileSeCode;
    int rtnFileSn;
    int rtnStreFileNu;
    String rtnImageDc;
    String rtnThumbFileNm;
    int rtnImgSeCode;

    public int getCntntsNo() {
        return cntntsNo;
    }

    public void setCntntsNo(int cntntsNo) {
        this.cntntsNo = cntntsNo;
    }

    public String getDietNm() {
        return dietNm;
    }

    public void setDietNm(String dietNm) {
        this.dietNm = dietNm;
    }

    public String getFdNm() {
        return fdNm;
    }

    public void setFdNm(String fdNm) {
        this.fdNm = fdNm;
    }

    public String getCntntsSj() {
        return cntntsSj;
    }

    public void setCntntsSj(String cntntsSj) {
        this.cntntsSj = cntntsSj;
    }

    public String getCntntsChargerEsntlNm() {
        return cntntsChargerEsntlNm;
    }

    public void setCntntsChargerEsntlNm(String cntntsChargerEsntlNm) {
        this.cntntsChargerEsntlNm = cntntsChargerEsntlNm;
    }

    public Date getRegistDt() {
        return registDt;
    }

    public void setRegistDt(Date registDt) {
        this.registDt = registDt;
    }

    public int getCntntsRdcnt() {
        return cntntsRdcnt;
    }

    public void setCntntsRdcnt(int cntntsRdcnt) {
        this.cntntsRdcnt = cntntsRdcnt;
    }

    public int getRtnFileSeCode() {
        return rtnFileSeCode;
    }

    public void setRtnFileSeCode(int rtnFileSeCode) {
        this.rtnFileSeCode = rtnFileSeCode;
    }

    public int getRtnFileSn() {
        return rtnFileSn;
    }

    public void setRtnFileSn(int rtnFileSn) {
        this.rtnFileSn = rtnFileSn;
    }

    public int getRtnStreFileNu() {
        return rtnStreFileNu;
    }

    public void setRtnStreFileNu(int rtnStreFileNu) {
        this.rtnStreFileNu = rtnStreFileNu;
    }

    public String getRtnImageDc() {
        return rtnImageDc;
    }

    public void setRtnImageDc(String rtnImageDc) {
        this.rtnImageDc = rtnImageDc;
    }

    public String getRtnThumbFileNm() {
        return rtnThumbFileNm;
    }

    public void setRtnThumbFileNm(String rtnThumbFileNm) {
        this.rtnThumbFileNm = rtnThumbFileNm;
    }

    public int getRtnImgSeCode() {
        return rtnImgSeCode;
    }

    public void setRtnImgSeCode(int rtnImgSeCode) {
        this.rtnImgSeCode = rtnImgSeCode;
    }
}
