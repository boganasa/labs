package ru.bogachenko.reposirories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bogachenko.models.CatModel;
import ru.bogachenko.models.OwnerModel;

@Repository
public interface CatModelRepository extends JpaRepository<CatModel, Long> {
    @Query("select b from CatModel b where b.id = :id")
    CatModel findByID(@Param("id") Long id);
}