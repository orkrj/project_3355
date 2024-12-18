package elice.webshopping.exception.common;

import org.springframework.http.HttpStatus;

public class NoContentsException extends ServiceCustomException {

    public NoContentsException() {
        super(
                "조회 결과 없음",
                HttpStatus.NO_CONTENT
        );
    }
}

