package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.IdGeneratorType;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Comment {
    @Id // 대표키 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 자동으로 1씩 증가
    private Long id;            // 대표키
    @ManyToOne
    @JoinColumn(name = "article_id")    // 외래키 생성, Article 엔티티의 기본키(id)와 매핑
    private Article article;    // 해당 댓글의 부모 게시글
    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String nickname;     // 댓글을 단 사람
    @Column // 해당 필드를 테이블의 속성으로 매핑
    private String body;        // 댓글 본문
}
