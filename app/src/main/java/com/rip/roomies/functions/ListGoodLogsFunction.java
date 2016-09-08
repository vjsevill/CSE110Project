package com.rip.roomies.functions;

import com.rip.roomies.models.DutyLog;
import com.rip.roomies.models.GoodLog;

/**
 * Created by johndoney on 5/29/16.
 */
public interface ListGoodLogsFunction {
	void ListGoodLogsFail();
	void ListGoodLogsSuccess(GoodLog[] goods);
}
