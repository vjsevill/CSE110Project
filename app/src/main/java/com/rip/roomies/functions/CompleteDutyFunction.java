package com.rip.roomies.functions;

import com.rip.roomies.models.Duty;
import com.rip.roomies.models.DutyLog;

/**
 * Created by Daniel on 5/23/2016.
 */
public interface CompleteDutyFunction {
	void completeDutyFail();
	void completeDutySuccess(Duty duty);
}
