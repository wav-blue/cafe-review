package kr.sujin.cafereview.lib.constant;

public enum TagType {
    DRINK_TASTE("음료가 맛있어요"), DESSERT_TASTE("디저트가 맛있어요")
    , CONVENIENCE("찾아가기 편해요"), UNIQUE_MENU("신선한 메뉴가 있어요")
    , NICE_PHOTO("사진 찍기 좋아요"), NICE_VIEW("경치가 좋아요") 
    , FRIENDLY("친절하게 응대해요"), GOOD_VIBE("분위기가 좋아요")
    , WITH_KIDS("아이와 함께 했어요"), WITH_PET("애완 동물과 함께 했어요")
    , PET_CAFE("동물이 있어요"), HYGIENIC("위생적이에요");

    private final String label;
    
    TagType(String label){
        this.label = label;
    }

    TagType(){
        this.label = this.name();
    }

    public String label() {
        return label;
    }

}