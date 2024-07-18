package gift.repository;

import gift.model.option.Option;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OptionRepository extends JpaRepository<Option, Long> {

    @Query("select o from Option o join o.product p where p.id = :productId")
    List<Option> findAllByProductId(Long productId);
}