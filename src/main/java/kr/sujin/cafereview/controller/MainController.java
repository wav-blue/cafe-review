package kr.sujin.cafereview.controller;

import groovy.util.logging.Slf4j;
import kr.sujin.cafereview.dto.ReviewReadRandomDto;
import kr.sujin.cafereview.service.ReviewReadService;
import kr.sujin.cafereview.service.ReviewService;
import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReviewReadService reviewReadService;

    @GetMapping("/")
    public String index(Model model){
        List<ReviewReadRandomDto> reviews = reviewReadService.getReviewByRandom(3);
        model.addAttribute("reviews", reviews);
        return "main";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "info/about";
    }
}
