package kh.gangnam.movie.Repository;

import kh.gangnam.movie.Model.OpenApiDAO.DailyBoxOfficeDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DailyBoxOfficeDAORepository
        extends JpaRepository<DailyBoxOfficeDAO, Long> {
}
