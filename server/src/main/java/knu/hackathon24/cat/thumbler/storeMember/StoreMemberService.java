package knu.hackathon24.cat.thumbler.storeMember;

import knu.hackathon24.cat.thumbler.location.Location;
import knu.hackathon24.cat.thumbler.location.LocationRepository;
import knu.hackathon24.cat.thumbler.location.LocationService; // 추가
import knu.hackathon24.cat.thumbler.session.Session;
import knu.hackathon24.cat.thumbler.store.Store;
import knu.hackathon24.cat.thumbler.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService; // 추가
    private final Session session;

    @Transactional
    public StoreMemberRegisterResponseDto registerStoreMember(StoreMemberRegisterRequestDto requestDto) {
        // 주소 검증
        if (requestDto.getStore() == null ||
                requestDto.getStore().getAddress() == null ||
                requestDto.getStore().getAddress().trim().isEmpty()) {
            throw new IllegalArgumentException("Store address cannot be null or empty");
        }

        // 주소로부터 Location 정보 가져오기
        Location location = locationService.getLocationFromAddress(requestDto.getStore().getAddress()); // 수정
        locationRepository.save(location); // 먼저 Location 저장

        // Store 엔티티 생성
        Store store = new Store();
        store.setName(requestDto.getStore().getName());
        store.setAddress(requestDto.getStore().getAddress());
        store.setPhone(requestDto.getStore().getPhone());
        store.setCert(requestDto.getStore().getCert());
        store.setLocation(location);
        storeRepository.save(store);

        // 비밀번호 해싱
        String hashedPassword = hashPassword(requestDto.getPassword());

        // StoreMember 엔티티 생성 및 저장
        StoreMember storeMember = new StoreMember(
                requestDto.getOwnerName(),
                requestDto.getOwnerPhone(),
                requestDto.getOwnerEmail(),
                requestDto.getUserId(),
                hashedPassword,
                store
        );
        storeMemberRepository.save(storeMember);

        return new StoreMemberRegisterResponseDto(
                storeMember.getUserId()
        );
    }

    public StoreMember loginStoreMember(String userId, String password, HttpServletResponse response) {
        StoreMember storeMember = storeMemberRepository.findByUserId(userId);
        if (storeMember != null && storeMember.getPassword().equals(hashPassword(password))) {
            String sessionId = issueStoreSessionId(storeMember);

            // 쿠키에 세션 ID 추가
            Cookie cookie = new Cookie("sessionId", sessionId);
            cookie.setHttpOnly(true); // JavaScript에서 접근하지 못하게 설정
            cookie.setPath("/"); // 쿠키의 유효 범위를 설정 (기본적으로 모든 경로에서 유효)
            response.addCookie(cookie); // 응답에 쿠키 추가

            return storeMember; // 로그인 성공
        }
        throw new IllegalArgumentException("Invalid userId or password"); // 로그인 실패
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Password hashing failed", e);
        }
    }

    public String issueStoreSessionId(StoreMember storeMember) {
        return session.issueStoreSessionId(storeMember);
    }

}
