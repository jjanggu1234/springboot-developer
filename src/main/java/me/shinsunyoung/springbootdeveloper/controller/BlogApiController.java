package me.shinsunyoung.springbootdeveloper.controller;

import lombok.RequiredArgsConstructor;
import me.shinsunyoung.springbootdeveloper.domain.Article;
import me.shinsunyoung.springbootdeveloper.dto.AddArticleRequest;
import me.shinsunyoung.springbootdeveloper.dto.ArticleResponse;
import me.shinsunyoung.springbootdeveloper.dto.UpdateArticleRequest;
import me.shinsunyoung.springbootdeveloper.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController //HTTP Response Body에 객체 데이터를 JSON 형식으로 반환하는 컨트롤러
public class BlogApiController {

    private final BlogService blogService;

    // C(생성) - 요청을 받고 서비스로 생성처리하고 사용자에게는 완료되었다고 전해준다.
    @PostMapping("/api/articles") // HTTP 메서드가 POST일 때 전달받은 URL과 동일하면 메서드로 매핑
    public ResponseEntity<Article> addArticle(@RequestBody AddArticleRequest request) {
        Article savedArticle = blogService.save(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedArticle);
    }


    //R(읽기)-전체리스트를 서비스로 찾고 데이터를 가져와서 리턴으로 JSON화 시켜(스트림) 사용자에게 전해준다.
    @GetMapping("/api/articles")
    public ResponseEntity<List<ArticleResponse>> findAllArticles() {
    List<ArticleResponse> articles = blogService.findAll()
            .stream()
            .map(ArticleResponse::new)
            .toList();

    return ResponseEntity.ok()
            .body(articles);
    }

    //R(읽기)-상세리스트를 ID를 서비스로 찾고 데이터를 가져와서 리턴으로 JSON화 시켜(스트림) 값을 사용자에게 전해준다.
    @GetMapping("/api/articles/{id}")
    public ResponseEntity<ArticleResponse> findArticle(@PathVariable long id) {
        Article article = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new ArticleResponse(article));
    }

    //D(삭제)-삭제요청이오면 id를 삭제한다. 그리고 리턴으로 사용자에게 완료되었다고 전해준다.
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Void> deleteArticle(@PathVariable long id) {
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //U(수정)-수정요청이 들어오면 새로 만든 생성자에 값을 넣어 내용을 수정한다. 그리고 리턴으로 사용자에게 JSON화 시켜 값을 전달한다.
    @PutMapping("/api/articles/{id}")
    public ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody UpdateArticleRequest request) {
        Article updatedArticle = blogService.update(id, request);

        return ResponseEntity.ok()
                .body(updatedArticle);
    }
}
