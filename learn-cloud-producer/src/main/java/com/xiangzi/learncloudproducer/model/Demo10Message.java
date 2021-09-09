package com.xiangzi.learncloudproducer.model;

import java.io.Serializable;

/**
 * <p></p >
 *
 * @author zhangxx
 * @since 1.0
 */
public class Demo10Message implements Serializable {

    public static final int QUEUE_COUNT = 4;

    public static final String EXCHANGE = "EXCHANGE_DEMO_10";

    /**
     * 编号
     */
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
