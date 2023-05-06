package ru.bogachenko.reposirories;
        import org.springframework.data.jpa.repository.JpaRepository;
        import org.springframework.data.jpa.repository.Query;
        import org.springframework.data.repository.query.Param;
        import org.springframework.stereotype.Repository;
        import ru.bogachenko.models.CatModel;
        import ru.bogachenko.models.OwnerModel;

@Repository
public interface OwnerModelRepository  extends JpaRepository<OwnerModel, Long> {
    @Query("select b from OwnerModel b where b.id = :id")
    OwnerModel findByID(@Param("id") long id);
}
