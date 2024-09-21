package knu.hackathon24.cat.thumbler.session;

import java.util.HashMap;
import java.util.UUID;

import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.storeMember.StoreMemberRepository;
import knu.hackathon24.cat.thumbler.userMember.UserMember;
import knu.hackathon24.cat.thumbler.userMember.UserMemberRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Session {
  final private UserMemberRepository userMemberRepository;
  final private StoreMemberRepository storeMemberRepository;

  private HashMap<String, UserMember> userSessionStorage = new HashMap<String, UserMember>();
  private HashMap<String, StoreMember> storeSessionStorage = new HashMap<String, StoreMember>();

  public void initForTest() {
    // 테스트를 위해 기본 계정 및 관련 영구 세션 생성
    UserMember testUserMember = userMemberRepository.findById(1L).orElse(null);
    this.userSessionStorage.put("testUserSessionId", testUserMember);

    StoreMember testStoreMember = storeMemberRepository.findById(1L).orElse(null);
    this.storeSessionStorage.put("testStoreSessionId", testStoreMember);
  }

  public String issueUserSessionId(UserMember userMember) {
    String newSessionId = generateSessionId();
    userSessionStorage.put(newSessionId, userMember);
    return newSessionId;
  }

  public String issueStoreSessionId(StoreMember storeMember) {
    String newSessionId = generateSessionId();
    storeSessionStorage.put(newSessionId, storeMember);
    return newSessionId;
  }

  public boolean verifyUserSessionId(String sessionId) {
    if (userSessionStorage.get(sessionId) == null)
      return false;
    return true;
  }

  public boolean verifyStoreSessionId(String sessionId) {
    if (storeSessionStorage.get(sessionId) == null)
      return false;
    return true;
  }

  public UserMember getUserMemberBySessionId(String sessionId) {
    UserMember userMemberTemp = userSessionStorage.get(sessionId);
    return userMemberRepository.findById(userMemberTemp.getId()).orElse(null);
  }

  public StoreMember getStoreMemberBySessionId(String sessionId) {
    StoreMember storeMemberTemp = storeSessionStorage.get(sessionId);
    return storeMemberRepository.findById(storeMemberTemp.getId()).orElse(null);
  }

  private String generateSessionId() {
    return UUID.randomUUID().toString();
  }
}
