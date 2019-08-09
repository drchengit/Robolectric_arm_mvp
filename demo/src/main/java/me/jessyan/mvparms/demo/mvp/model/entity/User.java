package me.jessyan.mvparms.demo.mvp.model.entity;

/**
 * @author DrChen
 * @Date 2019/2/20 0020.
 * qq:1414355045
 */
public class User {
    public long id;
    public String name;
    public String blog;
    private String message;

    /**
     * 是否登录失败
     */
    public boolean isLoginFaild() {
       if(message!=null&&message.equals("Not Found")){
           return true;
       }else {
           return false;
       }
    }
}
