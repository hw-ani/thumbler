package knu.hackathon24.cat.thumbler.userMember;

import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.point.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserMemberService {

    @Autowired
    private UserMemberRepository userMemberRepository;

    @Autowired
    private PointRepository pointRepository;

    public UserMember registerUser(UserMemberRequest userMemberRequest) {
        // 기본 포인트 생성 (초기값 0)
        Point point = new Point(0L);
        pointRepository.save(point);

        // 사용자 생성
        UserMember userMember = new UserMember(
                userMemberRequest.getName(),
                userMemberRequest.getPhone(),
                userMemberRequest.getNickname(),
                userMemberRequest.getUserId(),
                userMemberRequest.getPassword(),
                userMemberRequest.getBank(),
                userMemberRequest.getAccount(),
                point // 포인트를 함께 저장
        );

        return userMemberRepository.save(userMember);
    }
}

