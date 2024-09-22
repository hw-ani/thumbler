package knu.hackathon24.cat.thumbler.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import knu.hackathon24.cat.thumbler.session.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthCheckInterceptor implements HandlerInterceptor {
  final private Session session;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    // for test
    session.initForTest();

    Cookie[] list = request.getCookies();

    log.info("request in interceptor : ", request.toString());

    if (list != null && list.length != 0)
      for (Cookie cookie:list)
        if (cookie.getName().equals("sessionId")
            && (session.verifyUserSessionId(cookie.getValue()) || session.verifyStoreSessionId(cookie.getValue())))
          return true;
    response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    return false;
  }
}
