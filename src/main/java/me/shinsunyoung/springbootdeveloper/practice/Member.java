package me.shinsunyoung.springbootdeveloper.practice;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

//name을 설정하지 않으면 클래스 이름과 같은 테이블과 매핑, 지정하면 name의 값을 가진 테이블의 이름 과 매핑
@Entity(name = "member_list")
@NoArgsConstructor(access = AccessLevel.PROTECTED)  //protected 기본 생성자
@AllArgsConstructor
@Getter
public class Member {

    @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) 추가,삭제 메서드 테스트 코드를 위해 주석처리
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)     // null 허용 설정
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    public void changeName(String name) {
        this.name = name;
    }
}
