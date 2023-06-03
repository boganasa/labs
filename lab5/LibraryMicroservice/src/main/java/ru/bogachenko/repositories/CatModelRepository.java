package ru.bogachenko.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bogachenko.DAOModel.CatModel;

import java.util.List;

@Repository
public interface CatModelRepository extends JpaRepository<CatModel, Long> {
    @Query("select b from CatModel b where b.id = :id")
    CatModel findByID(@Param("id") Long id);

    @Query("select b from CatModel b where b.owner.id = :id")
    List<CatModel> getMyCats(@Param("id") Long myId);
}