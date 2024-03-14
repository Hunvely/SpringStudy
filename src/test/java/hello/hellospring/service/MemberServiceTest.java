package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MemberServiceTest {

    MemberService memberService;
    MemoryMemberRepository memberRepository;

    @BeforeEach
    public void beforeEach() {
        // DI
        memberRepository = new MemoryMemberRepository();
        memberService = new MemberService(memberRepository);
    }

    @AfterEach
    public void afterEach() {
        memberRepository.clearStore();
    }

    @Test // 테스트 코드는 빌드할 때 실제 코드에 포함되지 않기 때문에 메서드명 한글로 써도 무방함.
    void 회원가입() {

        // given
        Member member = new Member();
        member.setName("spring");

        // when
        Long saveId = memberService.join(member);

        // then
        Member findMember = memberService.findOne(saveId).get();
        Assertions.assertThat(member.getName()).isEqualTo(findMember.getName());
    }

    @Test
    public void 중복_회원_가입_예외() {

        // given
        Member member1 = new Member();
        member1.setName("jihoon");

        Member member2 = new Member();
        member2.setName("jihoon");

        // when
        memberService.join(member1);

        // () -> memberService.join(member2) 상황일 때 IllegalStateException 예외가 터져야 한다.
        IllegalStateException e = assertThrows(IllegalStateException.class, () -> memberService.join(member2));
        Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");

        /*try {
            memberService.join(member2);
            fail();
        } catch (IllegalStateException e) {
             Assertions.assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.12");
        }*/
        // then
    }

    @Test
    void findMembers() {
    }

    @Test
    void findOne() {
    }
}