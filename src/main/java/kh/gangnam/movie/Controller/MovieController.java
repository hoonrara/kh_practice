package kh.gangnam.movie.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import kh.gangnam.movie.Model.OpenApiDTO.BoxOfficeResponse;
import kh.gangnam.movie.Service.MovieService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    // TODO JSON 직접 변환 방법 ObjectMapper 이용
    @GetMapping("/open/object-mapper/daily-box-office/{day}")
    public JsonNode daily(@PathVariable(value = "day") String day) throws JsonProcessingException {
        return movieService.getBoxOfficeData1(day);
    }

    @GetMapping("/open/responseentity/daily-box-office/{day}")
    public BoxOfficeResponse daily2(@PathVariable(value = "day") String day) throws JsonProcessingException {

        return movieService.getBoxOfficeData2(day);
    }

    @GetMapping("/save/{day}")
    public void save(@PathVariable(value = "day") String day) throws JsonProcessingException {
        movieService.save(day);
    }
}
