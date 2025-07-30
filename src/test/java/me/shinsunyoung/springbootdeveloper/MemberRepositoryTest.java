package me.shinsunyoung.springbootdeveloper;

import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import static org.assertj.core.api.Assertions.assertThat;


import java.util.List;


@DataJpaTest            // 테스트를 위한 설정을 제공하며, 자동으로 데이터베이스에 대한 트랜잭션 관리를 설정해준다. 하지만 서비스 코드에서 업데이트 기능을 사용하려면 서비스 메서드에 반드시 @Transactional을 붙여야 한다.
class MemberRepositoryTest {
    @Autowired
    MemberRepository memberRepository;

    @AfterEach
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @Sql("/insert-members.sql")
    @Test
    void getAllMembers() {
        //when
        List<Member> members = memberRepository.findAll();          //조회 메소드(전체조회)

        //then
        assertThat(members.size()).isEqualTo(3);        // size로 배열 전체의 길이 확인
    }

    @Sql("/insert-members.sql")
    @Test
    void getMemberById() {
        //when
        //Member member = memberRepository.findById(2L).get();      //조회 메소드(아이디로 조회)
        Member member = memberRepository.findByName("C").get();     //쿼리 메소드(특정 컬럼으로 조회)

        //then
        //assertThat(member.getName()).isEqualTo("B");              // id가 2인 멤버의 name이 'B'인지 확인
        assertThat(member.getId()).isEqualTo(3);            // name이 'C'인 멤버의 id가 3인지 확인
    }

    @Test
    void saveMember() {
        //given
        Member member = new Member(1L, "A");

        //when
        memberRepository.save(member);

        //then
        assertThat(memberRepository.findById(1L).get().getName()).isEqualTo("A");
    }
    /* 의도가 조회 단계와는 다르게 새로운 A 멤버 객체를 준비하고,
    when 절에 실제로 저장한 뒤에 then 절에서는 1번 아이디에 해당하는 멤버의 이름을 가져오고 있다.
    이번에는 이미 추가된 데이터가 있으면 안되기 때문에 @Sql 애너테이션을 사용하지 않는다.
    
    -> Member의 아이디 자동 생성하는 Generation을 주석처리해야 한다. 그러면 id 생성이 안됨
    */
    
    // 새로 추가하는 테스트 코드
    @Test
    void saveMembers() {
        //given
        List<Member> members = List.of(
                new Member(2L, "B"),new Member(3L, "C")
        );

        //when
        memberRepository.saveAll(members);

        //then
        assertThat(memberRepository.findAll().size()).isEqualTo(2);
    }

    // 삭제하는 테스트 코드 -> 왜 given 없는가? sql로 받아오기 때문에 설정할 필요가 없다. (전체 삭제 x, Id 삭제)
    @Sql("/insert-members.sql")
    @Test
    void deleteMemberById() {
        //when
        memberRepository.deleteById(2L);

        //then
        assertThat(memberRepository.findById(2L).isEmpty()).isTrue();
    }

    // 이렇게 설정하면 모든 데이터를 삭제하므로 실제 서비스 코드에서는 거의 사용하지 않는다. -> AfterEach 꼭 사용
    // 그래서 영향을 주지 않도록 AfterEach 애너테이션을 사용 = 위에 예시 사용
    @Sql("/insert-members.sql")
    @Test
    void deleteAll() {
        //when
        memberRepository.deleteAll();

        //then
        assertThat(memberRepository.findAll().size()).isZero();
    }

    // 수정 테스트 코드
    @Sql("/insert-members.sql")
    @Test
    void update() {
        //given
        Member member = memberRepository.findById(2L).get();

        //when
        member.changeName("BC");

        //then
        assertThat(memberRepository.findById(2L).get().getName()).isEqualTo("BC");

    }
}