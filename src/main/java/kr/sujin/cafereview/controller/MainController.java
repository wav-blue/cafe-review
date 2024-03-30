package kr.sujin.cafereview.controller;

import groovy.util.logging.Slf4j;
import kr.sujin.cafereview.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ReviewService reviewService;

    @GetMapping("/")
    public String index(Model model){
        return "main";
    }
    @GetMapping("/about")
    public String about(Model model){
        return "info/about";
    }
}
