package me.shinsunyoung.springbootdeveloper.practice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class QuizController {

    @GetMapping("/quiz")    // 1. quiz 패스로 요청이 오면 quiz()라는 메소드에서 요청을 처리. 이 메서드는 요청 파라미터의 키가 "code"이면 int 자료형의 code 변수와 매핑되며, code 값에 따라 다른 응답을 보낸다.
    public ResponseEntity<String> quiz(@RequestParam("code") int code) {
        switch (code) {
            case 1:
                return ResponseEntity.created(null).body("Created!");
            case 2:
                return ResponseEntity.badRequest().body("Bad Request!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }

    @PostMapping("/quiz")   // 2. quiz 패스로 요청이 오면 quiz2라는 메서드에서 요청을 처리. 이 메서드는 요청 값을 Code라는 객체로 매핑한 후에 value 값에 따라 다른 응답을 보낸다
    public ResponseEntity<String> quiz2(@RequestBody Code code) {

        switch (code.value()) {
            case 1:
                return ResponseEntity.status(403).body("Forbidden!");
            default:
                return ResponseEntity.ok().body("OK!");
        }
    }
}

record Code(int value){}    // 3.
