package knu.hackathon24.cat.thumbler.storeMember;

import knu.hackathon24.cat.thumbler.location.Location;
import knu.hackathon24.cat.thumbler.location.LocationRepository;
import knu.hackathon24.cat.thumbler.location.LocationService; // 추가
import knu.hackathon24.cat.thumbler.store.Store;
import knu.hackathon24.cat.thumbler.store.StoreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreMemberService {

    private final StoreMemberRepository storeMemberRepository;
    private final StoreRepository storeRepository;
    private final LocationRepository locationRepository;
    private final LocationService locationService; // 추가

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

        // StoreMember 엔티티 생성 및 저장
        StoreMember storeMember = new StoreMember(
                requestDto.getOwnerName(),
                requestDto.getOwnerPhone(),
                requestDto.getOwnerEmail(),
                requestDto.getUserId(),
                requestDto.getPassword(),
                store
        );
        storeMemberRepository.save(storeMember);

        return new StoreMemberRegisterResponseDto(
                storeMember.getUserId()
        );
    }
}
