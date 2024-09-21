package knu.hackathon24.cat.thumbler.location;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LocationService {

    @Value("${KAKAO_KEY}")
    private String KAKAO_REST_API_KEY; // 여기에 API 키를 넣으세요
    private static final String KAKAO_ADDRESS_URL = "https://dapi.kakao.com/v2/local/search/address.json";

    public Location getLocationFromAddress(String address) {
        if (address == null || address.trim().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be null or empty");
        }

        RestTemplate restTemplate = new RestTemplate();

        String url = UriComponentsBuilder.fromHttpUrl(KAKAO_ADDRESS_URL)
                .queryParam("query", address)
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + KAKAO_REST_API_KEY);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<Map> response = restTemplate.exchange(url, HttpMethod.GET, entity, Map.class);
            List<Map> documents = (List<Map>) response.getBody().get("documents");
            if (!documents.isEmpty()) {
                Map<String, Object> locationData = documents.get(0);
                Map<String, Object> addressInfo = (Map<String, Object>) locationData.get("address");

                // Location 객체 생성
                return new Location(
                        String.valueOf(addressInfo.get("y")), // 위도
                        String.valueOf(addressInfo.get("x"))  // 경도
                );
            } else {
                throw new IllegalArgumentException("Could not find location for address: " + address);
            }
        } catch (HttpClientErrorException e) {
            throw new IllegalArgumentException("Error occurred while calling Kakao API: " + e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred: " + e.getMessage(), e);
        }
    }
}
