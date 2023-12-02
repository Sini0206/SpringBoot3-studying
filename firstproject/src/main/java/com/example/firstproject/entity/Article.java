package com.example.firstproject.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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
    @GeneratedValue //  자동 생성 기능 추가(숫자가 1부터 자동으로 매겨짐)
    private Long id;
    @Column     //  title 필드 선언, DB 테이블의 title 열과 연결됨
    private String title;
    @Column     //  content 필드 선언, DB 테이블의 content 열과 연결됨
    private String content;

    /*public Long getId() {
        return id;
    }*/
}
