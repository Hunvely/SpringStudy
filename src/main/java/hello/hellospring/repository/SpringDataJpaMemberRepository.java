package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataJpaMemberRepository extends JpaRepository<Member, Long>, MemberRepository {

    // JPQL - select m from Member m where m.name = ? => 메서드 이름 규칙을 통해 메서드 이름만으로 구현 가능하다.
    @Override
    Optional<Member> findByName(String name);
}
