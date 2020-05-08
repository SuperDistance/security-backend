package com.platform.security.common.enums;

/**
*@Description:
 * * 规定:
 *  * #1表示成功
 *  * #1001～1999 区间表示参数错误
 *  * #2001～2999 区间表示用户错误
 *  * #3001～3999 区间表示接口异常
*@Author: Tianshi Chen
*@date: 4/22/2020
*/
public enum ResultCode {
    /* 成功 */
    SUCCESS(200, "Success"),

    /* 默认失败 */
    COMMON_FAIL(999, "Fail"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "invalid parameter"),
    PARAM_IS_BLANK(1002, "empty parameter"),
    PARAM_TYPE_ERROR(1003, "parameter type error"),
    PARAM_NOT_COMPLETE(1004, "missing parameter"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "user hasn't log in"),
    USER_ACCOUNT_EXPIRED(2002, "account expired"),
    USER_CREDENTIALS_ERROR(2003, "error password"),
    USER_CREDENTIALS_EXPIRED(2004, "password expired"),
    USER_ACCOUNT_DISABLE(2005, "account disabled"),
    USER_ACCOUNT_LOCKED(2006, "account locked"),
    USER_ACCOUNT_NOT_EXIST(2007, "account not exist"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "account already exist"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "account use by others"),

    /* 业务错误 */
    NO_PERMISSION(3001, "have no access"),
    UPDATE_ERROR(3002, "internal update error"),
    SAVE_ERROR(3002, "internal save error"),
    DELETE_ERROR(3003, "internal delete error");

    private Integer code;
    private String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 根据code获取message
     *
     * @param code
     * @return
     */
    public static String getMessageByCode(Integer code) {
        for (ResultCode ele : values()) {
            if (ele.getCode().equals(code)) {
                return ele.getMessage();
            }
        }
        return null;
    }
}
