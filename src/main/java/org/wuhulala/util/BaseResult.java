package org.wuhulala.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;


public class BaseResult<T> implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ReturnCode returnCode = ReturnCode.SUCCESS;

	private String resultCode;

	private String resultMsg;

	private T data;

	@JSONField(serialize = false)
	private String language;

	@Override
	public String toString(){
		return JSON.toJSONString(this);
	}

	public ReturnCode getReturnCode() {
		return returnCode;
	}

	public BaseResult<?> setReturnCode(ReturnCode returnCode) {
		this.returnCode = returnCode;
		return this;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getResultCode() {
		resultCode = returnCode.getResultCode();
		return resultCode;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public String getResultMsg() {
		resultMsg = returnCode.getResultMsg();

		return resultMsg ;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}


	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}
}
