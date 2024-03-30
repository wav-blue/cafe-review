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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import kr.sujin.cafereview.dto.ReviewFormDto;
import kr.sujin.cafereview.dto.ReviewReadDetailDto;
import kr.sujin.cafereview.dto.ReviewReadDto;
import kr.sujin.cafereview.dto.ReviewSearchDto;
import kr.sujin.cafereview.service.ReviewReadDetailService;
import kr.sujin.cafereview.service.ReviewReadService;
import kr.sujin.cafereview.service.ReviewService;
import kr.sujin.cafereview.service.ReviewUpdateService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestBody;


@RequestMapping("/review")
@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewReadService reviewReadService;
    private final ReviewUpdateService reviewUpdateService;
    private final ReviewReadDetailService reviewReadDetailService;

    // 새로 리뷰 등록
    @GetMapping(value = "/new")
    public String getCreateReviewForm(Model model){
        model.addAttribute("reviewFormDto", new ReviewFormDto());
        return "cafe/reviewForm";
    }

    @PostMapping(value = "/new")
    public String postCreateReviewForm(@Valid ReviewFormDto reviewFormDto, BindingResult
                           bindingResult, Model model, @RequestParam("reviewImgFile")List<MultipartFile>
                           reviewImgFileList){
        if(bindingResult.hasErrors()){
            return "cafe/reviewForm";
        }

        if(reviewImgFileList.get(0).isEmpty()){
                model.addAttribute("errorMessage", "첫번째 리뷰 이미지는 필수 입력 값입니다.");
            return "cafe/reviewForm";
        }

        try{
            reviewService.saveReview(reviewFormDto, reviewImgFileList);
        } catch (Exception e){
            model.addAttribute("errorMessage", "상품 등록 중 에러가 발생하였습니다.");
            return "cafe/reviewForm";
        }

        return "redirect:/";
    }

    @GetMapping(value = "")
    public String getReviewWithPaging(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<ReviewReadDto> reviews = reviewReadService.getReviewWithPaging(pageable);
        model.addAttribute("reviews", reviews);
        model.addAttribute("maxPage", 3);
        return "explore/review";
    }

    @GetMapping(value = {"/search"})
    public String searchReview(ReviewSearchDto reviewSearchDto,
        @RequestParam(value = "page",required = false)Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 3);
        Page<ReviewReadDto> reviews =
            reviewReadService.getReviewWithPagingBySearch(reviewSearchDto, pageable);
        model.addAttribute("reviews", reviews);
        // model.addAttribute("drinkSearchDto", drinkSearchDto);
        // model.addAttribute("maxPage", 5);
        return "explore/reviewSearch";
    }

    @GetMapping(value= "/item/{reviewId}")
    public String getReviewDetail(Model model, @PathVariable("drinkId") Long drinkId){
        // ReviewFormDto reviewFormDto = reviewService.getReviewDtl(drinkId);
        // model.addAttribute("review", reviewFormDto);
        return "explore/reviewDetail";
    }

    // 수정할 데이터 조회
    @GetMapping(value = "/edit/{reviewId}")
    public String getUpdateReviewForm(@PathVariable("reviewId") Long reviewId, Model model){

        try{
            ReviewFormDto reviewFormDto = reviewUpdateService.getReviewDetail(reviewId);
            model.addAttribute("reviewFormDto", reviewFormDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 리뷰입니다.");
            model.addAttribute("reviewFormDto", new ReviewFormDto());
            return "cafe/reviewForm";
        }
        return "cafe/reviewForm";
    }

    @PostMapping(value = "/edit/{reviewId}")
    public String postUpdateReviewForm(@Valid ReviewFormDto reviewFormDto, BindingResult bindingResult, @RequestParam("reviewImgFile") List<MultipartFile> reviewImgFileList, Model model) {
        if(bindingResult.hasErrors()){
            return "cafe/reviewForm";
        } 
        if(reviewImgFileList.get(0).isEmpty() && reviewFormDto.getId() == null){
            model.addAttribute("errorMessage", "관련 이미지를 하나 이상 등록해주세요");
            return "cafe/reviewForm";
        }       
        try{
            reviewUpdateService.updateReview((reviewFormDto), reviewImgFileList);
        }catch(Exception e){
            model.addAttribute("errorMessage", "리뷰 수정 중 에러가 발생했습니다.");
            return "cafe/reviewForm";
        }
        return "redirect:/";
    }

    // 상세 데이터 조회
    @GetMapping(value = "/{reviewId}")
    public String getReviewDetail(@PathVariable("reviewId") Long reviewId, Model model){

        try{
            ReviewReadDetailDto reviewReadDetailDto = reviewReadDetailService.getReviewDetail(reviewId);
            model.addAttribute("review", reviewReadDetailDto);
        } catch (EntityNotFoundException e){
            model.addAttribute("errorMessage", "존재하지 않는 리뷰입니다.");
            model.addAttribute("review", new ReviewReadDetailDto());
            return "explore/reviewDetail";
        }
        return "explore/reviewDetail";
    }
}
