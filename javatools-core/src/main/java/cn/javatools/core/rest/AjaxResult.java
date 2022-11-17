package cn.javatools.core.rest;

import java.io.Serializable;

public class AjaxResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private int code;

	private String msg;

	private T data;

	public static <T> AjaxResult<T> ok() {
		return restResult(null, 0, "操作成功");
	}

	public static <T> AjaxResult<T> ok(T data) {
		return restResult(data, 0, "操作成功");
	}

	public static <T> AjaxResult<T> ok(T data, String msg) {
		return restResult(data, 0, msg);
	}

	public static <T> AjaxResult<T> failed() {
		return restResult(null, 1, "操作失败");
	}

	public static <T> AjaxResult<T> failed(String msg) {
		return restResult(null, 1, msg);
	}

	public static <T> AjaxResult<T> failed(T data) {
		return restResult(data, 1, null);
	}

	public static <T> AjaxResult<T> failed(T data, String msg) {
		return restResult(data,1, msg);
	}

	public static <T> AjaxResult<T> error() {
		return restResult(null, 1, "系统异常,请联系管理员");
	}

	public static <T> AjaxResult<T> restResult(T data, int code, String msg) {
		AjaxResult<T> apiResult = new AjaxResult<>();
		apiResult.setCode(code);
		apiResult.setData(data);
		apiResult.setMsg(msg);
		return apiResult;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
