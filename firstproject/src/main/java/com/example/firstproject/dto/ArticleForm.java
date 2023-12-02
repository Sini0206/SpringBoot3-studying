package com.example.firstproject.dto;

import com.example.firstproject.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

//  롬복으로 리팩토링
@AllArgsConstructor //  클래스 안쪽 필드를 매개변수로 하는 생성자가 자동으로 만들어짐
@ToString   //  ToString 패키지 자동 임포트
public class ArticleForm {
    private Long id;
    private String title;   //  제목을 받을 필드
    private String content; //  내용을 받을 필드

    // 전송 받은 제목과 내용을 필드에 저장하는 생성자 추가

    //  데이터를 잘 받았는지 확인할 toString() 메서드 추가

    public Article toEntity() { return new Article(id,title,content);}  // null -> id로 수정
}
