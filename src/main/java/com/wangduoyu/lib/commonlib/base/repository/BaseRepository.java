package com.wangduoyu.lib.commonlib.base.repository;

import androidx.lifecycle.MutableLiveData;


import com.wangduoyu.lib.commonlib.utils.LogUtils;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 通用的数据仓库，实现网络数据存储到数据库
 *
 * @param <DB> 数据库存储的数据类型
 * @param <T>  网络拉取的数据类型
 */
public abstract class BaseRepository<DB, T> {

    public abstract Single<DB> getDataFromDao(QueryParam param);

    public abstract Single<T> getDataFromNet(QueryParam param);

    protected abstract T transFromDb(DB data);

    /**
     * 通用获取数据接口
     *
     * @param to      待监听数据
     * @param onError 异常事件
     * @param param   入口参数
     */
    public void getData(MutableLiveData<T> to, Consumer<Throwable> onError, QueryParam param) {
        getDataFromDao(param)
                .map(this::transFromDb)//类型转换
                .subscribeOn(Schedulers.from(AppExecutors.networkIO()))
                .filter(t -> isDBDataNotEmpty(t))
                .switchIfEmpty(getDataFromNet(param))
                .subscribe(to::postValue, onError);
    }

    private boolean isDBDataNotEmpty(Object data) {
        LogUtils.d(data);
        return false ;

//        LogUtils.d(data);
//        if (data != null && data instanceof List) {
//            return !((List) data).isEmpty();
//        } else {
//            return data != null;
//        }
    }


}
