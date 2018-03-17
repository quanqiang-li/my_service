package com.carl.vo;

/**
 * 错误信息枚举类型
 * 
 * @ClassName: Error
 * @Description: TODO
 *
 */
public enum Code {
	/**
	 * 成功
	 */
	OK(0, "成功"),
	/**
	 * 失败
	 */
	ERROR(9999, "失败"),
	/**
	 * 未知错误
	 */
	UNKNOWN_ERROR(9000, "未知错误"),

	/**
	 * 参数为空
	 */
	EMPTY_PARAMETER_ERROR(1001, "参数为空"),

	/**
	 * invalid_code
	 */
	INVALID_CODE(3006, "invalid_code"),

	/**
	 * tokenCode不正确
	 */
	TOKEN_CODE_INVALID(3009, "tokenCode不正确或已过期"),

	/**
	 * 未登录
	 */
	NOT_LOGIN_ERROR(3010, "未登录"),

	/**
	 * 上传文件异常
	 */
	FILE_EXCE(9002, "上传文件异常"),
	
	;
	/**
	 * 错误代码
	 */
	private final int code;

	/**
	 * 错误信息
	 */
	private final String msg;

	private Code(int code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public String getMsg() {
		return msg;
	}

}
