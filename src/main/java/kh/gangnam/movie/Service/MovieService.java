package kh.gangnam.movie.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import kh.gangnam.movie.Model.OpenApiDAO.BoxOfficeResultDAO;
import kh.gangnam.movie.Model.OpenApiDAO.DailyBoxOfficeDAO;
import kh.gangnam.movie.Model.OpenApiDTO.BoxOfficeResponse;
import kh.gangnam.movie.Model.OpenApiDTO.DailyBoxOffice;
import kh.gangnam.movie.Repository.BoxOfficeResultDAORepository;
import kh.gangnam.movie.Repository.DailyBoxOfficeDAORepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final BoxOfficeResultDAORepository boxOfficeResultDAORepository;
    private final DailyBoxOfficeDAORepository dailyBoxOfficeDAORepository;

    public MovieService(RestTemplate restTemplate, ObjectMapper objectMapper, BoxOfficeResultDAORepository boxOfficeResultDAORepository, DailyBoxOfficeDAORepository dailyBoxOfficeDAORepository) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
        this.boxOfficeResultDAORepository = boxOfficeResultDAORepository;
        this.dailyBoxOfficeDAORepository = dailyBoxOfficeDAORepository;
    }

    @Value("${openapi.api-key}")
    private String apikey;
    @Value("${openapi.daily}")
    private String url;

    // ============================================
    // 1. JSON 그대로 보기용 (JsonNode 사용)
    // ============================================

    // Open API 호출해서 JSON 데이터를 받아온 후 JsonNode 로 반환
    public JsonNode getBoxOfficeData1(String day) throws JsonProcessingException {
        // URL 만들기
        String URL = url + "?key=" + apikey + "&targetDt=" + day;

        // 외부 API 호출 → JSON 문자열 받기
        String data = restTemplate.getForObject(URL, String.class);

        // 문자열을 트리 구조(JSON 처럼 생긴 객체)로 변환
        return objectMapper.readTree(data);
    }

    // ============================================
    // 2. JSON → DTO 클래스(BoxOfficeResponse)로 변환
    // ============================================

    public BoxOfficeResponse getBoxOfficeData2(String day) throws JsonProcessingException {
        // URL 만들기
        String URL = url + "?key=" + apikey + "&targetDt=" + day;

        // 외부 API 호출 → JSON 문자열 받기
        String response = restTemplate.getForObject(URL, String.class);

        // JSON 문자열을 우리가 만든 DTO 클래스(BoxOfficeResponse)로 변환
        return objectMapper.readValue(response, BoxOfficeResponse.class);
    }

    // ============================================
    // 3. API 데이터 → DTO → DAO → DB 저장
    // ============================================

    public void save(String day) throws JsonProcessingException {
        // URL 만들기
        String URL = url + "?key=" + apikey + "&targetDt=" + day;

        // 1. 외부 API 요청해서 JSON 데이터 받기
        String response = restTemplate.getForObject(URL, String.class);

        // 2. JSON → DTO로 파싱
        BoxOfficeResponse result = objectMapper.readValue(response, BoxOfficeResponse.class);

        // 3. DTO → Entity로 변환 (부모 객체)
        BoxOfficeResultDAO boxOfficeResultDAO = BoxOfficeResultDAO.fromDTO(result.getBoxOfficeResult());

        // 4. 자식 영화 리스트도 DTO → DAO로 변환
        List<DailyBoxOfficeDAO> dailyBoxOfficeDAOList = new ArrayList<>();
        for (DailyBoxOffice dto : result.getBoxOfficeResult().getDailyBoxOfficeList()) {
            DailyBoxOfficeDAO dailyBoxOfficeDAO = DailyBoxOfficeDAO.fromDTO(dto);
            dailyBoxOfficeDAOList.add(dailyBoxOfficeDAO);
        }

        // 5. 부모에 자식 리스트 연결
        boxOfficeResultDAO.setDailyBoxOfficeList(dailyBoxOfficeDAOList);

        // 6. 부모 먼저 저장 (ID 자동 생성)
        BoxOfficeResultDAO saveResult = boxOfficeResultDAORepository.save(boxOfficeResultDAO);

        // 7. 자식들도 저장하면서 부모 ID 연결
        for (DailyBoxOfficeDAO daily : boxOfficeResultDAO.getDailyBoxOfficeList()) {
            daily.setBoxOfficeResult(saveResult);
            dailyBoxOfficeDAORepository.save(daily);
        }

        // 8. 저장 완료 메시지 출력
        System.out.println("저장 성공");
    }
}