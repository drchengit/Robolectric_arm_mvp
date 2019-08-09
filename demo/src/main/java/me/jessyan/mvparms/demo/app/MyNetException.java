package me.jessyan.mvparms.demo.app;

/**
 * @author DrChen
 * @Date 2019/5/28 0028.
 * qq:1414355045
 */
public class MyNetException extends RuntimeException{
    private int code;
    private String msg;

    public MyNetException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;

    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }
}
