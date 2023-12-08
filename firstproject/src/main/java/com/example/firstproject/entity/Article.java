package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor //  Article() 생성자 대체하는 어노테이션 추가
@NoArgsConstructor  //  기본 생성자(매개변수X)
@ToString   //  toString() 메서드를 대체하는 어노테이션 추가
@Entity //  엔티티 선언
@Getter
public class Article {
    @Id //  엔티티의 대푯값 설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //  DB가 id 자동 생성
    private Long id;
    @Column     //  title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;
    @Column     //  content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;

    public void patch(Article article) {
        if (article.title != null)  // 수정할 내용이 있을 때만 갱신
            this.title = article.title;
        if (article.content != null)
            this.content = article.content;
    }

    /*public Long getId() {
        return id;
    }*/
}
