package com.itheima.reggie.common;

import lombok.extern.slf4j.Slf4j;

/**
 * 基于ThreadLocal封装工具类，用于设置和获取当前登录用户的ID
 */
@Slf4j
public class BaseContext {
    private static ThreadLocal<Long> threadLocal = new ThreadLocal<>();

    public static void setCurrentId(Long id){
        log.info("setCurrentID " + id);
        threadLocal.set(id);
    }

    public static Long getCurrentId(){
        log.info("getCurrentID " + threadLocal.get());
        return threadLocal.get();
    }
}
