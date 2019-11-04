package com.shiroDemo.shiroProject.tools;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerInstance extends AtomicInteger{

	/** 
	* @Fields serialVersionUID : TODO
	*/ 
	private static final long serialVersionUID = -1546536474941672647L;
	//类初始化时，不初始化这个对象(延时加载，真正用的时候再创建)
    private static AtomicInteger instance;
    
    //构造器私有化
    private AtomicIntegerInstance(){}
    
  //方法同步，调用效率低
    public static synchronized AtomicInteger getInstance(){
        if(instance==null){
            instance=new AtomicInteger();
        }
        return instance;
    }
}
