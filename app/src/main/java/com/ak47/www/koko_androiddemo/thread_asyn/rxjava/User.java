package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

/**
 * Created by 1796 on 2018/6/7.
 */

public class User {
    public long id;
    public String firstname;

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public String lastname;
    public boolean isFollowing;

    public User() {
    }

    public User(String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public User(ApiUser apiUser) {
        this.id = apiUser.id;
        this.firstname = apiUser.firstname;
        this.lastname = apiUser.lastname;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", isFollowing=" + isFollowing +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != user.id) return false;
        if (isFollowing != user.isFollowing) return false;
        if (firstname != null ? !firstname.equals(user.firstname) : user.firstname != null)
            return false;
        return lastname != null ? lastname.equals(user.lastname) : user.lastname == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (firstname != null ? firstname.hashCode() : 0);
        result = 31 * result + (lastname != null ? lastname.hashCode() : 0);
        result = 31 * result + (isFollowing ? 1 : 0);
        return result;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}
