package com.example.shoplocator.buissines.shopsList.listTransformation;

import android.support.annotation.NonNull;

/**
 * Created by {@author yura.savchuk22@gmail.com} on 24.01.17.
 */

public interface ChangeItemsCommand {

    void executeWithListener(@NonNull CommandExecutedListener listener);

}
