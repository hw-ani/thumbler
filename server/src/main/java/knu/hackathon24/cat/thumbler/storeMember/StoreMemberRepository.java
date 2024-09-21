package knu.hackathon24.cat.thumbler.storeMember;

import knu.hackathon24.cat.thumbler.userMember.UserMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreMemberRepository extends JpaRepository<StoreMember, Long> {
    StoreMember findByUserId(String userId);
}
