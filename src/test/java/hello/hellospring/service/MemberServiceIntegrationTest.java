package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import hello.hellospring.repository.MemoryMemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertThrows;

// 통합 테스트
@SpringBootTest // 스프링 컨테이너와 테스트를 함께 실행한다.
@Transactional // 테스트 케이스에 이 어노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고, 테스트 완료 후에 항상 롤백한다.
                // => DB에 데이터가 반영되지 않으므로 다음 테스트에 영향을 주지 않는다.
class MemberServiceIntegrationTest {

    // 단순 테스트기 때문에 DI를 필드 주입으로 했음.
    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

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

    }
}