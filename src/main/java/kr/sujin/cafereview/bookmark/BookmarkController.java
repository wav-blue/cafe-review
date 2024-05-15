package kr.sujin.cafereview.bookmark;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.sujin.cafereview.bookmark.repository.dto.BookmarkCreateDto;
import kr.sujin.cafereview.bookmark.repository.dto.BookmarkReadDto;
import kr.sujin.cafereview.bookmark.service.BookmarkCreateService;
import kr.sujin.cafereview.bookmark.service.BookmarkDeleteService;
import kr.sujin.cafereview.bookmark.service.BookmarkReadService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@RequestMapping("/bookmark")
@Controller
@RequiredArgsConstructor
public class BookmarkController {

    private final BookmarkCreateService bookmarkCreateService;
    private final BookmarkReadService bookmarkReadService;
    private final BookmarkDeleteService bookmarkDeleteService;

    @PostMapping(value = "/new")
    public ResponseEntity<Long> postCreateReviewForm(@RequestBody @Valid BookmarkCreateDto bookmarkCreateDto, Principal principal){

        String email = principal.getName();
        
        Long reviewId = bookmarkCreateService.addBookmark(bookmarkCreateDto, email);

        return new ResponseEntity<Long>(reviewId, HttpStatus.OK);
    }

    @GetMapping(value = "/my")
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

    // 북마크 삭제 처리
    @DeleteMapping(value = "/my/{bookmarkId}")
    public ResponseEntity<String> deleteBookmark(@PathVariable("bookmarkId") Long bookmarkId, Model model) {
        try{
            bookmarkDeleteService.deleteBookmark(bookmarkId);
        } catch (EntityNotFoundException e){
            System.out.println("e");
            System.out.println(e);
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Complete", HttpStatus.NO_CONTENT);
    }
}
