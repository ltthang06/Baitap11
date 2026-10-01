package vn.iotstar.repository;

import java.util.List;
import java.util.Optional;

import vn.iotstar.entity.RatingId_24162119;
import vn.iotstar.entity.Rating_24162119;

public interface
        RatingRepository_24162119 {

    List<Rating_24162119>
            findByBookId(
                    Integer bookId);

    long countByBookId(
            Integer bookId);

    Optional<Rating_24162119>
            findById(
                    RatingId_24162119 id);

    Rating_24162119 save(
            Rating_24162119 rating);
}