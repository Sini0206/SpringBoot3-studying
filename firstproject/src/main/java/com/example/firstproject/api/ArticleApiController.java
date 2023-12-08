package com.example.firstproject.api;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class ArticleApiController {
    @Autowired
    ArticleRepository articleRepository;
    // GET
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }
    @GetMapping("/api/articles/{id}")
    public Article show(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("/api/articles")
    public Article create(@RequestBody ArticleForm dto){
        Article article = dto.toEntity();
        return articleRepository.save(article);
    }
    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id, @RequestBody ArticleForm dto){
        // 1. 수정용 dto 작성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        // 2. DB에 대상 엔티티 있는지 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기(대상 엔티티가 없거나 수정하려는 id가 잘못된 경우)
        if(target == null || target == article) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            // ResponeEntity에 담아서 반환해야만 반환 데이터에 상태 코드를 실어 보낼 수 있음
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);    // 200=OK, 201=CREATED, 400=BAD_REQUEST
        }
        // 4. 대상 엔티티가 있으면 업데이트 및 정상 응답(200)하기
        target.patch(article);  // 기존 데이터에 새 데이터 붙이기
        Article updated = articleRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);  // 수정 내용 DB에 최종 저장
    }
    // DELETE
}
