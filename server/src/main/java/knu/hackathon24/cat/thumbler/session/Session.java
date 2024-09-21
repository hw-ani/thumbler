package knu.hackathon24.cat.thumbler.session;

import java.util.HashMap;
import java.util.UUID;

import knu.hackathon24.cat.thumbler.point.Point;
import knu.hackathon24.cat.thumbler.storeMember.StoreMember;
import knu.hackathon24.cat.thumbler.userMember.UserMember;

public class Session {
  private static Session singletonSession = null;

  private Session() {
    userSessionStorage = new HashMap<String, UserMember>();
    storeSessionStorage = new HashMap<String, StoreMember>();

    // 테스트를 위해 기본 계정 및 관련 영구 세션 생성
    Point point1 = new Point();
    UserMember testUserMember = new UserMember("hwan", "010-1234-5678", "hwani", "jh1", "hwan", "kakao", "3333", point1);
    this.userSessionStorage.put("testUserSessionId", testUserMember);
  }

  public static Session getInstance() {
    if (singletonSession == null)
      singletonSession = new Session();
    return singletonSession;
  }

  private HashMap<String, UserMember> userSessionStorage;
  private HashMap<String, StoreMember> storeSessionStorage;

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
    return userSessionStorage.get(sessionId);
  }

  public StoreMember getStoreMemberBySessionId(String sessionId) {
    return storeSessionStorage.get(sessionId);
  }

  private String generateSessionId() {
    return UUID.randomUUID().toString();
  }
}
