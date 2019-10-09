package com.wangduoyu.lib.commonlib.base.repository;

public abstract class NameRunnable implements Runnable {
    protected final String name;


    protected NameRunnable(String format,Object...args) {
        if(args!=null&&args.length!=0){
            this.name = String.format(format,args);
        }else {
            this.name = format;
        }
    }

    public final void run(){
        String oldName = Thread.currentThread().getName();
        Thread.currentThread().setName(this.name);
        try {
            this.execute();
        }finally {
            Thread.currentThread().setName(oldName);
        }
    }

    protected abstract void execute();
}
