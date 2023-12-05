package com.stu.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class FieldMetaObjectHandler implements MetaObjectHandler {
    private final static String CREATE_TIME = "createTime";
    private final static String UPDATE_TIME = "updateTime";

    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();

        // 创建时间
        strictInsertFill(metaObject, CREATE_TIME, Date.class, date);
        // 更新时间
        strictInsertFill(metaObject, UPDATE_TIME, Date.class, date);
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间
        strictUpdateFill(metaObject, UPDATE_TIME, Date.class, new Date());
    }

}