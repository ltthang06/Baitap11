package vn.iotstar.repository;

import java.util.List;

import vn.iotstar.entity.Author_24162119;
import vn.iotstar.model.PageResult_24162119;

public interface
        AuthorRepository_24162119 {

    PageResult_24162119<
            Author_24162119>
            findPage(
                    int page,
                    int size);

    List<Author_24162119>
            findAll();

    List<Author_24162119>
            findAllByIds(
                    List<Integer> ids);

    Author_24162119 findById(
            Integer id);

    Author_24162119 save(
            Author_24162119 author);

    void delete(
            Integer id);
}