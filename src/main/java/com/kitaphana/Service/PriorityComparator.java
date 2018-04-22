package com.kitaphana.Service;

import com.kitaphana.Entities.Patron;
import com.kitaphana.Entities.User;

import java.util.Comparator;

public class PriorityComparator implements Comparator<Patron> {


    @Override
    public int compare(Patron o1, Patron o2) {
        if (o1.getPriority() < o2.getPriority()) {
            return -1;
        } else if (o1.getPriority() > o2.getPriority()) {
            return 1;
        }
        return 0;
    }
}
