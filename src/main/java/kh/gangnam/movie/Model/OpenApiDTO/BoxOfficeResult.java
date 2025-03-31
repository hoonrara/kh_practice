package kh.gangnam.movie.Model.OpenApiDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BoxOfficeResult {
    private String boxofficeType;
    private String showRange;
    private List<DailyBoxOffice> dailyBoxOfficeList;
}
