package vn.iotstar.service.impl;

import java.util.List;

import vn.iotstar.entity.RatingId_24162119;
import vn.iotstar.entity.Rating_24162119;
import vn.iotstar.repository.RatingRepository_24162119;
import vn.iotstar.repository.impl.RatingRepositoryImpl_24162119;
import vn.iotstar.service.RatingService_24162119;

public class
        RatingServiceImpl_24162119
        implements
        RatingService_24162119 {

    private final
            RatingRepository_24162119
            ratingRepository;

    public RatingServiceImpl_24162119() {

        this.ratingRepository =
                new RatingRepositoryImpl_24162119();
    }

    @Override
    public List<Rating_24162119>
            findByBookId(
                    Integer bookId) {

        return ratingRepository
                .findByBookId(
                        bookId
                );
    }

    @Override
    public long countByBookId(
            Integer bookId) {

        return ratingRepository
                .countByBookId(
                        bookId
                );
    }

    @Override
    public void addReview(
            Integer userId,
            Integer bookId,
            Byte rating,
            String reviewText) {

        RatingId_24162119 id =
                new RatingId_24162119(
                        userId,
                        bookId
                );

        Rating_24162119 entity =
                ratingRepository
                        .findById(id)
                        .orElse(
                                new Rating_24162119()
                        );

        entity.setId(id);
        entity.setRating(rating);
        entity.setReviewText(
                reviewText
        );

        ratingRepository.save(entity);
    }
}