package com.example.firstproject.controller;

import com.example.firstproject.dto.ArticleForm;
import com.example.firstproject.dto.CommentDto;
import com.example.firstproject.entity.Article;
import com.example.firstproject.entity.Comment;
import com.example.firstproject.repository.ArticleRepository;
import com.example.firstproject.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Slf4j  //  로깅 기능을 위한 어노테이션 추가
@Controller     //  컨트롤러 선언
public class ArticleController {
    @Autowired  //  스프링 부트가 미리 생성해 놓은 리파지터리 객체 주입(DI,Dependency Injection)
    private ArticleRepository articleRepository;
    @Autowired
    private CommentService commentService;
    @GetMapping("/articles/new")    //  URL 요청 접수
    public String newArticleForm() {    //  메서드 생성 및 반환 값 요청
        return "articles/new";
    }
    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form){  //  폼 데이터를 DTO로 받기
        log.info(form.toString());
        //System.out.println(form.toString());        //  DTO에 폼 데이터가 잘 담겼는지 확인
        // 1. DTO를 엔티티로 변환
        Article article = form.toEntity();
        log.info(article.toString());
        //System.out.println(article.toString());
        // 2. 리파지토리로 엔티티를 DB에 저장
        Article saved = articleRepository.save(article);    //  article 엔티티를 저장해 saved 객체에 반환
        log.info(saved.toString());
        //System.out.println(saved.toString());
        return "redirect:/articles/" + saved.getId();  // 리다이렉트를 작성할 위치
    }

    @GetMapping("/articles/{id}")   //  데이터 조회 요청 접수    (컨트롤러에서 URL 변수 사용 시 중괄호 하나만 씀)
    //  @PathVariable: URL 요청으로 들어온 전달값을 컨트롤러의 매개변수로 가져옴
    public String Show(@PathVariable Long id, Model model){
        log.info("id = " + id); //  id를 잘 받았는지 확인하는 로그 찍기
        //  1. id를 조회해 DB에서 데이터 가져오기
        //Optional<Article> articleEntity = articleRepository.findById(id);
        Article articleEntity = articleRepository.findById(id).orElse(null);    //  해당 id값이 없으면 null 반환
        List<CommentDto> commentsDtos = commentService.comments(id);
        //  2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);    //  머스테치에서 인식할 속성값
        model.addAttribute("commentsDtos",commentsDtos);    // 댓글 목록 모델에 등록
        //  3. 뷰 페이지 반한
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //  1. 모든 데이터 가져오기
        // List<Article> articleEntityList = (List<Article>) articleRepository.findAll(); //  다운캐스팅(Iterable -> List)
        // Iterable<Article> articleEntityList = articleRepository.findAll(); //  업캐스팅(List<Article> -> Iterable<Article>)
        ArrayList<Article> articleEntityList = articleRepository.findAll();
        //  2. 모델에 데이터 등록하기
        model.addAttribute("articleList",articleEntityList);    //  articleEntityList 등록
        //  3. 뷰 페이지 설정하기
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){  //  id를 매개변수로 가져오기
        //  1. 수정할 데이터 가져오기(리파지토리 이용)
        Article articleEntity = articleRepository.findById(id).orElse(null);    //  DB에서 수정할 데이터 가져오기
        //  2. 모델에 데이터 등록하기
        model.addAttribute("article",articleEntity);
        //  3. 뷰 페이지 설정하기
        return "articles/edit";
    }
    @PostMapping("/articles/update")    //  원래는 PatchMapping
    public String update(ArticleForm form){ //  매개변수로 폼 데이터를 DTO로 받아오기
        log.info(toString());   //  수정 데이터를 잘 받았는지 확인
        //  1. DTO -> ENTITY
        Article articleEntity= form.toEntity();
        log.info(articleEntity.toString()); //  엔티티로 잘 변환되었는지 확인
        //  2. 엔티티를 리파지토리를 이용해 DB에 저장
        //  2-1. DB에서 기존 데이터 가져오기
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);
        //  2-2. 기존 데이터 값 갱신
        if(target != null) {
            articleRepository.save(articleEntity);  //  엔티티를 DB에 저장(갱신)
        }

        return "redirect:/articles/" + articleEntity.getId();
    }

    @GetMapping("/articles/{id}/delete")    //  원래는 DeleteMapping
    public String delete(@PathVariable Long id, RedirectAttributes rttr){
        log.info("삭제 요청이 들어왔습니다!");
        //  1. 삭제 대상 가져오기
        Article target = articleRepository.findById(id).orElse(null);
        log.info(target.toString());
        //  2. 대상 엔티티 삭제하기
        if (target != null) {
            articleRepository.delete(target);
            //  휘발성 데이터, 리다이렉트 시점에서 한 번만 사용 가능
            rttr.addFlashAttribute("message","삭제됐습니다!");
        }
        //  3. 결과 페이지로 리다이렉트하기
        return "redirect:/articles";
    }

}

