package vn.iotstar.model;

import java.util.List;

public class PageResult_24162119<T> {

    private List<T> content;

    private int page;

    private int size;

    private long totalElements;

    private int totalPages;

    public PageResult_24162119(
            List<T> content,
            int page,
            int size,
            long totalElements) {

        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements =
                totalElements;

        this.totalPages =
                size == 0
                        ? 0
                        : (int) Math.ceil(
                                (double)
                                totalElements
                                / size
                        );
    }

    public List<T> getContent() {
        return content;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}