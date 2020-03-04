package edu.psu.lionx.utils;

import edu.psu.lionx.domain.User;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<User> getDummyUsers() {
        User user1 = new User(
                "PETER",
                "PAN",
                "PETER",
                "PAN",
                "PETER.PAN@MAGIC.COM",
                "+19999999999",
                "",
                false
        );
        User user2 = new User(
                "JOHN",
                "DOE",
                "JOHN",
                "DOE",
                "JOHN.DOE@MAGIC.COM",
                "+19999999999",
                "",
                false
        );
        User user3 = new User(
                "JANE",
                "DOE",
                "JANE",
                "DOE",
                "JANE.DOE@MAGIC.COM",
                "+19999999999",
                "",
                false
        );
        User user4 = new User(
                "DENNIS",
                "RITCHIE",
                "DENNIS",
                "RITCHIE",
                "DENNIS.RITCHIE@MAGIC.COM",
                "+19999999999",
                "",
                false
        );
        User user5 = new User(
                "JAMES",
                "GOSLING",
                "JAMES",
                "GOSLING",
                "JAMES.GOSLING@MAGIC.COM",
                "+19999999999",
                "",
                false
        );
        User user6 = new User(
                "BILL",
                "JOY",
                "BILL",
                "JOY",
                "BILL.JOY@MAGIC.COM",
                "+19999999999",
                "",
                false
        );

        List<User> users = new ArrayList<>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        users.add(user5);
        users.add(user6);

        return users;
    }
}
