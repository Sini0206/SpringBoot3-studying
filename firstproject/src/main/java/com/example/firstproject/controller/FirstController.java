package com.example.firstproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller // 해당 클래스가 컨트롤러임을 선언
public class FirstController {

    @GetMapping("/hi")  //  URL 요청 접수
    public String niceToMeetYou(Model model){   //  Alt + Enter 자동 임포트
        //model.addAttribute("username","수아"); //  모델에서 변수 등록시 사용하는 메소드
        model.addAttribute("username","SUA"); //  수아 -> SUA
        return "greetings"; //  greetings.mustache 파일 반환
    }

    @GetMapping("/bye")
    public String seeYouNext(Model model){  //  model 객체 받아오기
        model.addAttribute("nickname","홍길동");   //  모델 변수 등록하기
        return "goodbye";
    }

}
