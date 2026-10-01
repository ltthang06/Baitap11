package vn.iotstar.service;

import java.util.List;

import vn.iotstar.entity.Rating_24162119;

public interface
        RatingService_24162119 {

    List<Rating_24162119>
            findByBookId(
                    Integer bookId);

    long countByBookId(
            Integer bookId);

    void addReview(
            Integer userId,
            Integer bookId,
            Byte rating,
            String reviewText);
}