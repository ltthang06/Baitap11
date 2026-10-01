package vn.iotstar.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class RatingId_24162119
        implements Serializable {

    private static final long
            serialVersionUID = 1L;

    @Column(name = "userid")
    private Integer userid;

    @Column(name = "bookid")
    private Integer bookid;

    public RatingId_24162119() {
    }

    public RatingId_24162119(
            Integer userid,
            Integer bookid) {

        this.userid = userid;
        this.bookid = bookid;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(
            Integer userid) {
        this.userid = userid;
    }

    public Integer getBookid() {
        return bookid;
    }

    public void setBookid(
            Integer bookid) {
        this.bookid = bookid;
    }

    @Override
    public boolean equals(
            Object obj) {

        if (this == obj) {
            return true;
        }

        if (!(obj
                instanceof
                RatingId_24162119)) {

            return false;
        }

        RatingId_24162119 other =
                (RatingId_24162119)
                        obj;

        return Objects.equals(
                userid,
                other.userid
        )
        &&
        Objects.equals(
                bookid,
                other.bookid
        );
    }

    @Override
    public int hashCode() {

        return Objects.hash(
                userid,
                bookid
        );
    }
}