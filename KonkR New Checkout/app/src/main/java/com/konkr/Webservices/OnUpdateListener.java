package com.konkr.Webservices;

import org.json.JSONObject;

public interface OnUpdateListener {

	void onUpdateComplete(JSONObject jsonObject, boolean isSuccess);
}
