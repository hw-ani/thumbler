package knu.hackathon24.cat.thumbler.userMember;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMemberRepository extends JpaRepository<UserMember, Long> {
    UserMember findByUserId(String userId);
}
