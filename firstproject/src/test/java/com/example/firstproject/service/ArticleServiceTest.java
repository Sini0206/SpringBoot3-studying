package com.example.firstproject.service;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.entity.Article;
import org.junit.jupiter.api.Test;  // Test 패키지 임포트
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.datasource.JdbcTransactionObjectSupport;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest // 테스트 코드에서 스프링 부트가 관리하는 다양한 객체를 주입 받을 수 있음
class ArticleServiceTest {
    @Autowired
    ArticleService articleService;  // articleService 객체 주입

    @Test   // 해당 메소드가 테스트 코드임을 선언
    void index() {
        // 1. 예상 데이터 작성하기
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나" , "2222");
        Article c = new Article(3L, "다다다다", "3333");
        List<Article> expected = new ArrayList<Article>(Arrays.asList(a,b,c)); // Arrays.asList는 정적메서드. 일반 리스트로 만들려면 새로
        // 2. 실제 데이터 획득하기
        List<Article> articles = articleService.index();
        // 3. 비교 및 검증
        assertEquals(expected.toString(),articles.toString());
    }

    @Test
    void show_성공_존재하는_id_입력() {
        // 1. 예상 데이터 작성하기
        Long id = 1L;
        Article expected = new Article(id, "가가가가", "1111");
        // 2. 실제 데이터 획득하기
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());
    }
    @Test
    void show_실패_존재하지_하지_않는_id_입력() {
        // 1. 예상 데이터 작성하기
        Long id = -1L;
        Article expected = null;
        // 2. 실제 데이터 획득하기
        Article article = articleService.show(id);
        // 3. 비교 및 검증
        assertEquals(expected, article);    // null은 toString()을 호출할 수 없으므로
    }

    @Test
    @Transactional
    void create_성공_title과_content만_있는_dto_입력() {
        // 1. 예상 데이터 작성하기
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(null, title, content);    // dto 생성
        Article expected = new Article(4L, title, content);     // 예상 데이터 저장
        // 2. 실제 데이터 획득하기
        Article article = articleService.create(dto);   // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expected.toString(), article.toString());  // 비교
    }
    @Test
    @Transactional
    void create_실패_id가_포함된_dto() {
        // 1. 예상 데이터 작성하기
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id, title, content);    // dto 생성
        Article expected = null;  // 예상 데이터 저장
        // 2. 실제 데이터 획득하기
        Article article = articleService.create(dto);   // 실제 데이터 저장
        // 3. 비교 및 검증
        assertEquals(expected, article);  // 비교
    }


    @Test
    @Transactional
    void update_성공_존재하는_id와_title_content가_있는_dto_입력() {
        Long id = 2L;
        String title = "가123";
        String content = "1123";
        Article expected = new Article(2L,title,content);

        ArticleForm dto = new ArticleForm(id,title,content);
        Article article = articleService.update(id,dto);

        assertEquals(expected.toString(),article.toString());
    }
    @Test
    @Transactional
    void update_성공_존재하는_id와_title만_있는_dto_입력() {
        Long id = 2L;
        String title = "가123";
        String content = null;
        Article expected = new Article(2L,title,"2222");

        ArticleForm dto = new ArticleForm(id,title,content);
        Article article = articleService.update(id,dto);

        assertEquals(expected.toString(),article.toString());
    }
    @Test
    @Transactional
    void update_실패_존재하지_않는_id의_dto_입력() {
        Long id = 4L;
        String title = "라라라라";
        String content = "4444";
        ArticleForm dto = new ArticleForm(id,title,content);

        Article expected = null;
        Article article = articleService.update(id,dto);

        assertEquals(expected, article);
    }
    @Test
    @Transactional
    void delete_성공_존재하는_id_입력() {
        Long id = 2L;
        String title = "나나나나";
        String content = "2222";

        Article expected = new Article(2L, title, content);
        Article article = articleService.delete(id);

        assertEquals(expected.toString(), article.toString());
    }
    @Test
    @Transactional
    void delete_실패_존재하지_않는_id_입력() {
        Long id = 4L;

        Article expected = null;
        Article article = articleService.delete(id);

        assertEquals(expected, article);
    }
}