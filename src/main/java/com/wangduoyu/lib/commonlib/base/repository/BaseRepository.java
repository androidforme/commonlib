package com.wangduoyu.lib.commonlib.base.repository;

import io.reactivex.MaybeSource;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public abstract class BaseRepository<B> {

    public abstract Single getDataFromDao();

    public abstract Single<B> getDataFromNet(QueryParam param);

    protected abstract boolean isDBDataNotEmpty(Object t);

    protected abstract Object transFromDb(Object o);

    /**
     * 通用获取数据接口
     */
    public void getData() {
//        getDataFromDao()
////                .map(this::transFromDb)//类型转换
////                .subscribeOn(Schedulers.io())
////                .filter(t->isDBDataNotEmpty(t))
////                .switchIfEmpty(getDataFromNet())
////                .subscribe();
    }



}
