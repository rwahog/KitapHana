package com.kitaphana.Service;

import com.kitaphana.Entities.User;

import java.util.Comparator;

public class PriorityComparator implements Comparator<User> {


    @Override
    public int compare(User o1, User o2) {
        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        } else if (o1.getPriority() > o2.getPriority()) {
            return 1;
        }
        return 0;
    }
}
