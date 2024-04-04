package kr.sujin.cafereview.lib;

import java.util.NoSuchElementException;

import javax.persistence.EntityNotFoundException;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<String> handle(IllegalArgumentException exception) {
        return ResponseEntity.badRequest().body("IllegalArgumentException 발생!");
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public String handle(EntityNotFoundException exception) {
        return "error/NotFound";
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handle(IllegalStateException exception) {
        return ResponseEntity.badRequest().body("이미 처리된 요청입니다!");
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<String> handle(AccessDeniedException exception) {
        return ResponseEntity.badRequest().body("권한이 없습니다!");
    }
}