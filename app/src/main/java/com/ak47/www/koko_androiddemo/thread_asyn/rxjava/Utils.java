package com.ak47.www.koko_androiddemo.thread_asyn.rxjava;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 1796 on 2018/6/7.
 */

public class Utils {

    public static List<ApiUser> getApiUserList() {
        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstname = "Amit";
        apiUserOne.lastname = "Shekhar";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstname = "Manish";
        apiUserTwo.lastname = "Kumar";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstname = "Sumit";
        apiUserThree.lastname = "Kumar";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstname = apiUser.firstname;
            user.lastname = apiUser.lastname;
            userList.add(user);
        }

        return userList;
    }

    public static List<User> getUserList1() {
        List<User> userList = new ArrayList<>();

        User userone = new User("smile", "koko1");
        User usertwo = new User("smile", "koko2");
        User userthree = new User("smile", "koko3");
        userList.add(userone);
        userList.add(usertwo);
        userList.add(userthree);
        return userList;
    }

    public static List<User> getUserList2() {
        List<User> userList = new ArrayList<>();

        User userone = new User("smile", "jojo1");
        User usertwo = new User("smile", "jojo2");
        User userthree = new User("smile", "jojo3");
        userList.add(userone);
        userList.add(usertwo);
        userList.add(userthree);
        return userList;
    }

    public static List<User> filterUser1andUser2(List<User> userlist1, List<User> userlist2) {
        List<User> userList = new ArrayList<>();
        for (User user1 : userlist1) {
            for (User user2 : userlist2) {
                if (user1.getFirstname() == user2.getFirstname()) {
                    userList.add(new User(user1.getFirstname(), user1.getLastname() + user2.getLastname()));
                }
            }
        }
        return userList;
    }
}
