package com.jiangdong.sunshine.entity;

import java.io.Serializable;
import java.util.concurrent.Future;

/**
 * @program: Sunshine
 * @description:
 * @author: JD
 * @create: 2019-07-05 11:58
 **/
public class CacheEntity implements Serializable {

    private static final long serialVersionUID = 8735132092273200831L;

    public Object value;

    public Future future;

    public CacheEntity(Object value, Future future) {
        this.value = value;
        this.future = future;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Future getFuture() {
        return future;
    }

    public void setFuture(Future future) {
        this.future = future;
    }

    @Override
    public String toString() {
        return "Entity{" +
                "value=" + value +
                ", future=" + future +
                '}';
    }

}
