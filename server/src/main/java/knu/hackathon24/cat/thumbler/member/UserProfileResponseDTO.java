package knu.hackathon24.cat.thumbler.member;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserProfileResponseDTO {
    private String nickname;
    private Long points;
    private String treeImage;
}