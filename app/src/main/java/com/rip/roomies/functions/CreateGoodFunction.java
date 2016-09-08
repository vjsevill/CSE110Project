package com.rip.roomies.functions;

import com.rip.roomies.models.Good;

/**
 * Created by johndoney on 5/29/16.
 */
public interface CreateGoodFunction {
	void createGoodFail();
	void createGoodSuccess(Good good);
}
