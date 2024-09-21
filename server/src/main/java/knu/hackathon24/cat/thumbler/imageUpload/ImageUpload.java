package knu.hackathon24.cat.thumbler.imageUpload;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ImageUpload {
  @Value("${IMGBB_KEY}")
  private String IMGBB_KEY;

  public String uploadImage(String imageBase64) {
    String url = "https://api.imgbb.com/1/upload?expiration=300000&key=" + IMGBB_KEY;

    HttpHeaders headers = new HttpHeaders();

    MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
    body.add("image", imageBase64);

    HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

    RestTemplate restTemplate = new RestTemplate();
    ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);

    String jsonResponse = response.getBody();
    JSONObject jsonObject = new JSONObject(jsonResponse);
    String storedUrl = jsonObject.getJSONObject("data").getString("url");

    return storedUrl;
  }
}
