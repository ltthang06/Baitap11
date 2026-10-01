package vn.iotstar.repository;

import vn.iotstar.entity.Book_24162119;
import vn.iotstar.model.PageResult_24162119;

public interface
        BookRepository_24162119 {

    PageResult_24162119<
            Book_24162119>
            findPage(
                    int page,
                    int size);

    Book_24162119 findById(
            Integer id);

    Book_24162119 save(
            Book_24162119 book);

    void delete(
            Integer id);
}