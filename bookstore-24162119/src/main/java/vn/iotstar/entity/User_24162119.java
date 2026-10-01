package vn.iotstar.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User_24162119 {

    @Id
    @GeneratedValue(
            strategy =
                    GenerationType.IDENTITY
    )
    private Integer id;

    @Column(
            name = "email",
            nullable = false,
            length = 50
    )
    private String email;

    @Column(
            name = "fullname",
            length = 50
    )
    private String fullname;

    @Column(name = "phone")
    private Integer phone;

    @Column(
            name = "passwd",
            nullable = false,
            length = 32
    )
    private String passwd;

    @Column(name = "signup_date")
    private LocalDateTime signupDate;

    @Column(name = "last_login")
    private LocalDateTime lastLogin;

    @Column(name = "is_admin")
    private Boolean isAdmin;

    public User_24162119() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(
            Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(
            String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(
            String fullname) {
        this.fullname = fullname;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(
            Integer phone) {
        this.phone = phone;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(
            String passwd) {
        this.passwd = passwd;
    }

    public LocalDateTime
            getSignupDate() {
        return signupDate;
    }

    public void setSignupDate(
            LocalDateTime signupDate) {
        this.signupDate = signupDate;
    }

    public LocalDateTime
            getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(
            LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(
            Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}