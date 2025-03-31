package kh.gangnam.movie.Model.OpenApiDAO;

import jakarta.persistence.*;
import kh.gangnam.movie.Model.OpenApiDTO.DailyBoxOffice;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class DailyBoxOfficeDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String rnum;
    private String dailyRank;
    private String rankInten;
    private String rankOldAndNew;
    private String movieCd;
    private String movieNm;
    private String openDt;
    private String salesAmt;
    private String salesShare;
    private String salesInten;
    private String salesChange;
    private String salesAcc;
    private String audiCnt;
    private String audiInten;
    private String audiChange;
    private String audiAcc;
    private String scrnCnt;
    private String showCnt;

    @ManyToOne
    @JoinColumn(name = "daily_id")
    private BoxOfficeResultDAO boxOfficeResult;

    public static DailyBoxOfficeDAO fromDTO(DailyBoxOffice dto) {
        DailyBoxOfficeDAO dao = new DailyBoxOfficeDAO();
        dao.rnum = dto.getRnum();
        dao.dailyRank = dto.getRank();
        dao.rankInten = dto.getRankInten();
        dao.rankOldAndNew = dto.getRankOldAndNew();
        dao.movieCd = dto.getMovieCd();
        dao.movieNm = dto.getMovieNm();
        dao.openDt = dto.getOpenDt();
        dao.salesAmt = dto.getSalesAmt();
        dao.salesShare = dto.getSalesShare();
        dao.salesInten = dto.getSalesInten();
        dao.salesChange = dto.getSalesChange();
        dao.salesAcc = dto.getSalesAcc();
        dao.audiCnt = dto.getAudiCnt();
        dao.audiInten = dto.getAudiInten();
        dao.audiChange = dto.getAudiChange();
        dao.audiAcc = dto.getAudiAcc();
        dao.scrnCnt = dto.getScrnCnt();
        dao.showCnt = dto.getShowCnt();
        return dao;
    }
}
