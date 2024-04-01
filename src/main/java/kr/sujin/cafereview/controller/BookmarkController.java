package kr.sujin.cafereview.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.sujin.cafereview.dto.BookmarkCreateDto;
import kr.sujin.cafereview.dto.BookmarkReadDto;
import kr.sujin.cafereview.entity.Review;
import kr.sujin.cafereview.service.BookmarkCreateService;
import kr.sujin.cafereview.service.BookmarkReadService;
import kr.sujin.cafereview.service.ReviewReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;


@RequestMapping("/bookmark")
@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkCreateService bookmarkCreateService;
    private final BookmarkReadService bookmarkReadService;
    private final ReviewReadService reviewReadService;

    @PostMapping(value = "/new")
    public ResponseEntity postCreateReviewForm(@RequestBody @Valid BookmarkCreateDto bookmarkCreateDto, BindingResult bindingResult, Principal principal){
        // if(bindingResult.hasErrors()){
        //     return "cafe/reviewForm";
        // }

        String email = principal.getName();
        Long reviewId = bookmarkCreateDto.getReviewId();
        
        try{
            Review review = reviewReadService.getReview(reviewId);
            reviewId = bookmarkCreateService.addBookmark(bookmarkCreateDto, email, review);
        } catch (Exception e){
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
    }

    @GetMapping(value = "")
    public String getBookmarkWithPaging(Optional<Integer> page, Model model){
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() - 1 : 0, 6);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String email = userDetails.getUsername();

        Page<BookmarkReadDto> bookmarks = bookmarkReadService.readBookmarkWithPaging(email, pageable);
        model.addAttribute("bookmarks", bookmarks);
        model.addAttribute("maxPage", 3);
        return "member/bookmarkList";
    }
}
