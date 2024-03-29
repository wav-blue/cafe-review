package kr.sujin.cafereview.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.sujin.cafereview.dto.ReviewFormDto;
import kr.sujin.cafereview.entity.Member;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.service.ReviewService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 새로 리뷰 등록
    @GetMapping(value = "/upload")
    public String createNewReviewForm(Model model){
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        return "cafe/reviewCreateForm";
    }

    @PostMapping(value = "/upload")
    public String createNewReview(@Valid ReviewFormDto reviewFormDto, BindingResult
                           bindingResult, Model model, @RequestParam("reviewImgFile")List<MultipartFile>
                           reviewImgFileList){
        if(bindingResult.hasErrors()){
            return "cafe/reviewCreateForm";
        }

        // if(reviewImgFileList.get(0).isEmpty() && reviewFormDto.getId() == null){
        if(reviewImgFileList.get(0).isEmpty()){
                model.addAttribute("errorMessage", "첫번째 리뷰 이미지는 필수 입력 값입니다.");
            return "cafe/reviewCreateForm";
        }

        try{
            reviewService.saveReview(reviewFormDto, reviewImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "cafe/reviewCreateForm";
        }

        return "redirect:/";
    }

    // @GetMapping(value = "/upload/{reviewId}")
    // public String reviewDtl(@PathVariable("reviewId") Long reviewId, Model model){

    //     try{
    //         ReviewFormDto reviewFormDto = reviewService.getReviewDtl(reviewId);
    //         model.addAttribute("reviewFormDto", reviewFormDto);
    //     } catch (EntityNotFoundException e){
    //         model.addAttribute("errorMessage", "존재하지 않는 리뷰입니다.");
    //         model.addAttribute("reviewFormDto", new ReviewFormDto());
    //         return "item/reviewForm";
    //     }
    //     return "item/reviewForm";
    // }

    // //수정을 위한 URL
    // @PostMapping(value = "/upload/{drinkId}")
    // public String drinkUpdate(@Valid ReviewFormDto reviewFormDto,
    //                           BindingResult bindingResult, @RequestParam("drinkImgFile") List<MultipartFile>
    //                           reviewImgFileList, Model model){
    //     if(bindingResult.hasErrors()){
    //         return "item/reviewForm";
    //     }
    //     if(reviewImgFileList.get(0).isEmpty() && reviewFormDto.getId() == null){
    //         model.addAttribute("errorMessage", "첫번째 음료 이미지는 필수 입력 값입니다.");
    //         return "item/reviewForm";
    //     }

    //     try{
    //         reviewService.updateItem(reviewFormDto, reviewImgFileList);
    //     } catch (Exception e){
    //         model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
    //         return "item/reviewForm";
    //     }

    //     return "redirect:/";
    // }

    // @GetMapping(value = {"/search", "/search/{page}"})
    // public String drinkManage(DrinkSearchDto drinkSearchDto,
    //                           @PathVariable("page")Optional<Integer> page, Model model){
    //     Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
    //     Page<Drink> drinks =
    //             drinkService.getAdminDrinkPage(drinkSearchDto, pageable);
    //     model.addAttribute("drinks", drinks);
    //     model.addAttribute("drinkSearchDto", drinkSearchDto);
    //     model.addAttribute("maxPage", 5);
    //     return "item/drinkMng";
    // }

    @GetMapping(value= "/item/{drinkId}")
    public String drinkDtl(Model model, @PathVariable("drinkId") Long drinkId){
        ReviewFormDto reviewFormDto = reviewService.getReviewDtl(drinkId);
        model.addAttribute("review", reviewFormDto);
        return "item/drinkDtl";
    }
}
