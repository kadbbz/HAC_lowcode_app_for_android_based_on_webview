package com.huozige.lab.container.platform;

/**
 * 返回给WebView的回调参数
 */
public class CallbackParams {

    public static final String CANCEL_BY_USER = "用户取消了该操作。";
    public static final String OK = "操作已成功完成。";

    private CallbackParams(boolean isSuccess, String payload, String error) {
        this.isSuccess = isSuccess;
        this.error = error;
        this.payload = payload;
    }

    /**
     * 创建一个调用成功的参数
     *
     * @param payload 需要返回的数据
     * @return 参数
     */
    public static CallbackParams success(String payload) {
        return new CallbackParams(true, payload, null);
    }

    /**
     * 创建一个调用成功的参数
     *
     * @param payload  需要返回的数据
     * @param payload2 需要返回的数据（备选）
     * @return 参数
     */
    public static CallbackParams success(String payload, String payload2) {
        var data = new CallbackParams(true, payload, null);
        data.payload2 = payload2;
        return data;
    }

    /**
     * 创建一个调用失败的参数
     *
     * @param error 错误信息
     * @return 参数
     */
    public static CallbackParams error(String error) {
        return new CallbackParams(false, null, error);
    }

    /**
     * 是否成功
     */
    public boolean isSuccess;

    /**
     * 成功时返回的信息
     */
    public String payload;

    /**
     * 成功时返回的信息（备选）
     */
    public String payload2;

    /**
     * 失败时的错误信息
     */
    public String error;

}
