package com.app.foodforyou.model;

import java.util.Date;

// Response API
public class DietListResponse {
    private int cntntsNo; // 컨텐츠 번호 (Contents Number)
    private String dietNm; // 식단 명 (Diet Name)
    private String fdNm; // 음식 명 (Food Name)
    private String cntntsSj; // 컨텐츠 제목 (Contents Subject)
    private String cntntsChargerEsntlNm; // 담당자 명 (The person in charge)
    private Date registDt; // 등록 일시 (Registration Date)
    private int cntntsRdcnt; // 조회수 (View count)
    private int rtnFileSeCode; // 파일 구분 코드 (Separation code of file)
    private int rtnFileSn; // 파일 순번 (Order number of file)
    private int rtnStreFileNm; // 원본 파일명 (Original file's name)
    private String rtnImageDc; // 이미지  설명 (Description of image)
    private String rtnThumbFileNm; // 썸네일 파일명 (Thumbnail file's name)
    private int rtnImgSeCode; // 이미지 구분 코드 (Separation code of image)

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

    public int getRtnStreFileNm() {
        return rtnStreFileNm;
    }

    public void setRtnStreFileNm(int rtnStreFileNm) {
        this.rtnStreFileNm = rtnStreFileNm;
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
