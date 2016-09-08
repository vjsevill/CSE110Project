package com.rip.roomies.functions;

import com.rip.roomies.models.User;

/**
 * Created by VinnysMacOS on 5/29/16.
 */
public interface UpdateProfileFunction {
    void updateProfileSuccess(User user);
    void updateProfileFailure();
}
