package knu.hackathon24.cat.thumbler.point;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PointRequestDto {
    private Long point;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class PointResponseDto {
    private Long point;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class PointHistoryDto {
    private Long date;
    private Long point;
    private String text;
}

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class PointHistoryResponseDto {
    private List<PointHistoryDto> history;
}
