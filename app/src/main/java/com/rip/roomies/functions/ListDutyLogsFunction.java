package com.rip.roomies.functions;

import com.rip.roomies.models.DutyLog;

/**
 * Created by johndoney on 5/29/16.
 */
public interface ListDutyLogsFunction {
	void ListDutyLogsFail();
	void ListDutyLogsSuccess(DutyLog[] duties);
}
