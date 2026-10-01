package vn.iotstar.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import vn.iotstar.entity.Book_24162119;
import vn.iotstar.model.PageResult_24162119;

public interface
        BookService_24162119 {

    PageResult_24162119<
            Book_24162119>
            findPage(
                    int page,
                    int size);

    Book_24162119 findById(
            Integer id);

    Book_24162119 save(
            Integer id,
            Integer isbn,
            String title,
            String publisher,
            BigDecimal price,
            String description,
            LocalDate publishDate,
            String coverImage,
            Integer quantity,
            List<Integer> authorIds);

    void delete(
            Integer id);
}