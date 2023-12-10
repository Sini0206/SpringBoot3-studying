package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import com.example.firstproject.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service    // 서비스 객체 선언
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;    // 게시글 리파지터리 객체 주입

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();    // dto -> 엔티티로 변환 후 article에 저장

        if (article.getId() != null)    // 이미 있는 id의 글을 수정하지 못하도록(사용자가 id를 입력할 경우)
            return null;

        return articleRepository.save(article); // article을 DB에 저장
    }

    public Article update(Long id, ArticleForm dto) {
        // 1. 수정용 dto 작성
        Article article = dto.toEntity();
        log.info("id: {}, article: {}", id, article.toString());
        // 2. DB에 대상 엔티티 있는지 타깃 조회
        Article target = articleRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기(대상 엔티티가 없거나 수정하려는 id가 잘못된 경우)
        if(target == null || id != article.getId()) {
            // 400, 잘못된 요청 응답!
            log.info("잘못된 요청! id: {}, article: {}", id, article.toString());
            // ResponeEntity에 상태와 본문 실어 보내는(응답하는) 역할은 컨트롤러
            return null;
        }
        // 4. 대상 엔티티가 있으면 업데이트 및 정상 응답(200)하기
        target.patch(article);  // 기존 데이터에 새 데이터 붙이기
        return articleRepository.save(target);
    }

    public Article delete(Long id) {
        // 1. 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if (target == null)
            return null;
        // 3. 대상 삭제하기
        articleRepository.delete(target);
        return target;  //DB에서 삭제한 대상을 컨트롤러에 반환
    }
    @Transactional
    public List<Article> createArticles(List<ArticleForm> dtos) {
        // 1. dto 묶음을 엔티티 묶음으로 변환
        List<Article> articleList = dtos.stream()   // (1) dtos 스트림화 / (4) 리스트에 저장
                .map(dto -> dto.toEntity())     // (2) map으로 dto가 하나하나 올 때마다 dto.toEntity()를 수행해 매핑
                .collect(Collectors.toList());  // (3) 매핑한 것들을 리스트로 묶기
        // 2. 엔티티 묶음을 DB에 저장
        articleList.stream()    // 스트림화
                .forEach(article -> articleRepository.save(article)); // article이 올 때마다 리파지토리를 이용해 DB에 저장
        // 3. 강제 예외 발생시키기
        articleRepository.findById(-1L) // id가 -1인 데이터 찾기
                .orElseThrow(() -> new IllegalArgumentException("결제 실패!"));    // 찾는 데이터가 없으면 예외 발생
        // 4. 결과 값 반환하기
        return articleList;
    }
}
