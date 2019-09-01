package com.mhandharbeni.perumda.utils;

import java.io.Serializable;

public class SingletonClass implements Serializable {
    private static volatile SingletonClass singletonClass;
    private SingletonClass(){
        if(singletonClass != null){
            throw new RuntimeException("Use getInstance() method to get the singleton instance of this class");
        }
    }
    public static SingletonClass getInstance(){
        if(singletonClass == null){
            synchronized (SingletonClass.class){
                if(singletonClass == null) singletonClass = new SingletonClass();
            }
        }
        return singletonClass;
    }
    protected SingletonClass readResolve(){
        return getInstance();
    }
}
