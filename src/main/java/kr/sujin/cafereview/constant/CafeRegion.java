package kr.sujin.cafereview.constant;

public enum CafeRegion {
    NOT_SELECTED("선택"), SEOUL("서울"), BUSAN("부산"), DAEGU("대구"), INCHEON("인천"), DAEJEON("대전"), GWANGJU("광주"), 
    ULSAN("울산"), SEJONG("세종"), GYEONGGI("경기"), CHUNGCHEONG("충청"), JEONLA("전라"),
    GYEONGSANG("경상"),GANGWON("강원"),JEONBUG("전북"), JEJU("제주"), ETC("기타");

    private final String label;
    
    CafeRegion(String label){
        this.label = label;
    }

    CafeRegion(){
        this.label = this.name();
    }

    public String label() {
        return label;
    }

}