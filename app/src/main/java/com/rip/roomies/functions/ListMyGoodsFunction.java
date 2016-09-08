package com.rip.roomies.functions;

import com.rip.roomies.models.Good;

/**
 * Created by johndoney on 5/29/16.
 */
public interface ListMyGoodsFunction {
	void listMyGoodsFail();
	void listMyGoodsSuccess(Good[] goods);
}
