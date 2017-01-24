package com.example.shoplocator.util.sugar;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 24.01.17.
 */

public class CompareUtil {

    public static boolean isEqual(boolean arg1, boolean arg2) {
        return (arg1 && arg2) || (!arg1 && !arg2);
    }

}
