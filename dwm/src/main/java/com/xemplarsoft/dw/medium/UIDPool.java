package com.xemplarsoft.dw.medium;

import java.util.ArrayList;
import java.util.Random;

public final class UIDPool {
    private final ArrayList<Long> pool = new ArrayList<>();
    public ArrayList<Long> getPool(){
        return (ArrayList<Long>) pool.clone();
    }

    public long getNewUID(){
        Random ran = new Random();
        long UID = ran.nextLong();

        if(pool.contains(UID)) return getNewUID();
        else{
            pool.add(UID);
            return UID;
        }
    }
}
