package vn.iotstar.service;

import java.time.LocalDate;
import java.util.List;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.model.PageResult_24162119;

public interface
        AuthorService_24162119 {

    PageResult_24162119<
            Author_24162119>
            findPage(
                    int page,
                    int size);

    List<Author_24162119>
            findAll();

    Author_24162119 findById(
            Integer id);

    Author_24162119 save(
            Integer id,
            String authorName,
            LocalDate dateOfBirth);

    void delete(
            Integer id);
}