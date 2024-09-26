package kr.sujin.cafereview.lib.exception;

public class AlreadyExistBookmarkException extends IllegalStateException {
    
    public AlreadyExistBookmarkException(String message){
        super(message);
    }
}
