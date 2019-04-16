package me.jessyan.mvparms.demo.mvp.ui.activity.integral;

import org.junit.Before;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

/**
 * @author DrChen
 * @Date 2019/3/4 0004.
 * qq:1414355045
 */
public class IntegralActivityTest {

    @Test
    public void setUp() throws Exception {
        String INPUT = "- sd  + -   100 ";
        String regEx = "^\\d+(\\.\\d+)?";
//        Pattern p = Pattern.compile(regEx);
//        Matcher m = p.matcher(str);
//        if(m.find()){
//            System.out.println(m.group());
//        }
        String REGEX = "[^\\+\\-\\D]*(\\-*\\+*\\s*\\d+\\.*\\d*)";
//          String  = "aabfooaabfooabfoobkkk";
        Pattern p = Pattern.compile(REGEX);
// 获取 matcher 对象
        Matcher m = p.matcher(INPUT);
        StringBuffer sb = new StringBuffer();
        while(m.find()){
            m.appendReplacement(sb,"撸"+m.group()+"撸");
            System.out.println(m.group());
        }
        m.appendTail(sb);
        System.out.println(sb.toString());





    }
}