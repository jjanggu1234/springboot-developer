package me.shinsunyoung.springbootdeveloper;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest         // 테스트용 애플리케이션 컨텍스트 생성
@AutoConfigureMockMvc   // MockMvc 생성 및 자동 구성
class TestControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach // 테스트 실행 전 실행하는 메서드
    public void mockMvcSetup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @AfterEach  // 테스트 실행 후 실행하는 메서드
    public void cleanUp() {
        memberRepository.deleteAll();
    }

    @DisplayName("getAllMembers: 아티클 조회에 성공한다.")
    @Test
    public void getAllMembers() throws Exception {
        //given
        final String url = "/test";
        Member savedMember = memberRepository.save(new Member(1L, "홍길동"));

        //when
        final ResultActions result = mockMvc.perform(get(url)   // 1. perform() 메서드는 요청을 전송하는 역할을 하는 메서드, 결과로 ResultActions 객체를 받으며, ResultActions 객체는 반환값을 검증하고 확인하는 andExpect() 메서드를 제공 
                .accept(MediaType.APPLICATION_JSON));           // 2. accept() 메서드는 요청을 보낼 때 무슨 타입으로 응답을 받을지 결정하는 메서드. JSON, XML 등 다양한 타입이 있지만. JSON으로 받겠다고 명시

        //then
        result.andExpect(status().isOk())                        // 3. andExpect()메서드는 응답을 검증. 컨트롤러에서 만든 API는 응답으로 Ok를 반환하므로 해당하는 메서드인 isOK를 사용해 응답 코드가 OK인지 확인
                // 4. 응답의 0번째 값이 DB에 저장한 값과 같은지 확인 / jsonPath("$[0].${필드명}")은 JSON 응답값의 값을 가져오는 역할을 하는 메서드. 0번째 배열에 들어 있는 객체의 id, name값을 가져오고, 저장된 값과 같은지 확인
                .andExpect(jsonPath("$[0].id").value(savedMember.getId()))
                .andExpect(jsonPath("$[0].name").value(savedMember.getName()));
    }
}