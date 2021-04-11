package com.app.foodforyou.model;

public class FoodRecipeDetail {
    private int cntntsNo; // 컨텐츠 번호 (Contents number)
    private String dietDtlNm; // 식단 상세명 (Diet detail name)
    private String dietNm; // 식단 명 (Diet name)
    private int fdInfoFirst; // 음식 순서 (Order of food)
    private String fdInfo; // 음식 정보 (Information of Food)
    private String dietNtrsmallInfo; // 식단 영양소 정보 (Information of nutrition)
    private String dietCn; // 식단 내용 (Diet Contents)
    private int fdCntntsNo; // 음식 컨텐츠 번호 (Number of food contents)
    private String fdNm; // 음식 명 (Food name)
    private String matrlInfo; // 재료 정보 (Information of material)
    private String ckngMthInfo; // 조리 방법 정보 (recipe)
    private String ntkQyInfo; // 섭취 량 정보 (Intake amount information)
    private String crbhInfo; // 당질 정보 (glycoprotein information)
    private String clriInfo; // 칼로리 정보 (calorie information)
    private String protInfo; // 단백질 정보 (protein information)
    private String ntrfsInfo; // 지질  (lipid information)
    private String chlsInfo; // 콜레스트롤 정보 (Colestrol Information)
    private String crfbInfo; // 조섬요 정보 //TODO: this is not clear. investigate it
    private String clciInfo; // 칼슘 정보 (calcium information)
    private String ircnInfo; // 철분 정보 (iron information)
    private String naInfo; // 나트륨 정보 (sodium information)
    private String frmlasaltEqvlntqyInfo; // 식염 상당량 정보 //TODO: this is not clear. investigate it
    private String vtmaInfo; // 비타민A 정보 (vitamin A information)
    private String vtmbInfo; // 비타민B 정보 (vitamin B information)
    private String vtmcInfo; // 비타민C 정보 (vitamin C information)
    private String cntntsSj; // 컨텐츠 제목 (Contents Subject)
    private String cntntsChargerEsntlNm; // 담당자 명 (The person in charge)
    private String registDt; // 등록 일시 (Registration Date)
    private int cntntsRdcnt; // 조회수  (View count)
    private int rtnFileSeCode; // 파일 구분 코드 (Separation code of file)
    private int rtnFileSn; // 파일 순번 (Order number of file)
    private String rtnOrginlFileNm; // 원본 파일명 (Original file's name)
    private String rtnStreFileNm; // 저장 파일명 (Stored file name)
    private String rtnFileCours; // 파일 경로 (File path)
    private String rtnImageDc; // 이미지 설명 (Description of image)
    private String rtnThumbFileNm; // 썸네일 파일명 (Thumbnail file's name)
    private String rtnImgSeCode; // 이미지 구분 코드 (Separation code of image)

    public int getCntntsNo() {
        return cntntsNo;
    }

    public void setCntntsNo(int cntntsNo) {
        this.cntntsNo = cntntsNo;
    }

    public String getDietDtlNm() {
        return dietDtlNm;
    }

    public void setDietDtlNm(String dietDtlNm) {
        this.dietDtlNm = dietDtlNm;
    }

    public String getDietNm() {
        return dietNm;
    }

    public void setDietNm(String dietNm) {
        this.dietNm = dietNm;
    }

    public int getFdInfoFirst() {
        return fdInfoFirst;
    }

    public void setFdInfoFirst(int fdInfoFirst) {
        this.fdInfoFirst = fdInfoFirst;
    }

    public String getFdInfo() {
        return fdInfo;
    }

    public void setFdInfo(String fdInfo) {
        this.fdInfo = fdInfo;
    }

    public String getDietNtrsmallInfo() {
        return dietNtrsmallInfo;
    }

    public void setDietNtrsmallInfo(String dietNtrsmallInfo) {
        this.dietNtrsmallInfo = dietNtrsmallInfo;
    }

    public String getDietCn() {
        return dietCn;
    }

    public void setDietCn(String dietCn) {
        this.dietCn = dietCn;
    }

    public int getFdCntntsNo() {
        return fdCntntsNo;
    }

    public void setFdCntntsNo(int fdCntntsNo) {
        this.fdCntntsNo = fdCntntsNo;
    }

    public String getFdNm() {
        return fdNm;
    }

    public void setFdNm(String fdNm) {
        this.fdNm = fdNm;
    }

    public String getMatrlInfo() {
        return matrlInfo;
    }

    public void setMatrlInfo(String matrlInfo) {
        this.matrlInfo = matrlInfo;
    }

    public String getCkngMthInfo() {
        return ckngMthInfo;
    }

    public void setCkngMthInfo(String ckngMthInfo) {
        this.ckngMthInfo = ckngMthInfo;
    }

    public String getNtkQyInfo() {
        return ntkQyInfo;
    }

    public void setNtkQyInfo(String ntkQyInfo) {
        this.ntkQyInfo = ntkQyInfo;
    }

    public String getCrbhInfo() {
        return crbhInfo;
    }

    public void setCrbhInfo(String crbhInfo) {
        this.crbhInfo = crbhInfo;
    }

    public String getClriInfo() {
        return clriInfo;
    }

    public void setClriInfo(String clriInfo) {
        this.clriInfo = clriInfo;
    }

    public String getProtInfo() {
        return protInfo;
    }

    public void setProtInfo(String protInfo) {
        this.protInfo = protInfo;
    }

    public String getNtrfsInfo() {
        return ntrfsInfo;
    }

    public void setNtrfsInfo(String ntrfsInfo) {
        this.ntrfsInfo = ntrfsInfo;
    }

    public String getChlsInfo() {
        return chlsInfo;
    }

    public void setChlsInfo(String chlsInfo) {
        this.chlsInfo = chlsInfo;
    }

    public String getCrfbInfo() {
        return crfbInfo;
    }

    public void setCrfbInfo(String crfbInfo) {
        this.crfbInfo = crfbInfo;
    }

    public String getClciInfo() {
        return clciInfo;
    }

    public void setClciInfo(String clciInfo) {
        this.clciInfo = clciInfo;
    }

    public String getIrcnInfo() {
        return ircnInfo;
    }

    public void setIrcnInfo(String ircnInfo) {
        this.ircnInfo = ircnInfo;
    }

    public String getNaInfo() {
        return naInfo;
    }

    public void setNaInfo(String naInfo) {
        this.naInfo = naInfo;
    }

    public String getFrmlasaltEqvlntqyInfo() {
        return frmlasaltEqvlntqyInfo;
    }

    public void setFrmlasaltEqvlntqyInfo(String frmlasaltEqvlntqyInfo) {
        this.frmlasaltEqvlntqyInfo = frmlasaltEqvlntqyInfo;
    }

    public String getVtmaInfo() {
        return vtmaInfo;
    }

    public void setVtmaInfo(String vtmaInfo) {
        this.vtmaInfo = vtmaInfo;
    }

    public String getVtmbInfo() {
        return vtmbInfo;
    }

    public void setVtmbInfo(String vtmbInfo) {
        this.vtmbInfo = vtmbInfo;
    }

    public String getVtmcInfo() {
        return vtmcInfo;
    }

    public void setVtmcInfo(String vtmcInfo) {
        this.vtmcInfo = vtmcInfo;
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

    public String getRegistDt() {
        return registDt;
    }

    public void setRegistDt(String registDt) {
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

    public String getRtnOrginlFileNm() {
        return rtnOrginlFileNm;
    }

    public void setRtnOrginlFileNm(String rtnOrginlFileNm) {
        this.rtnOrginlFileNm = rtnOrginlFileNm;
    }

    public String getRtnStreFileNm() {
        return rtnStreFileNm;
    }

    public void setRtnStreFileNm(String rtnStreFileNm) {
        this.rtnStreFileNm = rtnStreFileNm;
    }

    public String getRtnFileCours() {
        return rtnFileCours;
    }

    public void setRtnFileCours(String rtnFileCours) {
        this.rtnFileCours = rtnFileCours;
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

    public String getRtnImgSeCode() {
        return rtnImgSeCode;
    }

    public void setRtnImgSeCode(String rtnImgSeCode) {
        this.rtnImgSeCode = rtnImgSeCode;
    }
}
