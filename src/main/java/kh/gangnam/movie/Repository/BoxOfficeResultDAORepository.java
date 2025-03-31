package kh.gangnam.movie.Repository;

import kh.gangnam.movie.Model.OpenApiDAO.BoxOfficeResultDAO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoxOfficeResultDAORepository extends
        JpaRepository<BoxOfficeResultDAO, Long> {
}
