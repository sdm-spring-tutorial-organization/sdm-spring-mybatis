package com.sdm.spring.mybatis.services;

import org.mybatis.spring.support.SqlSessionDaoSupport;

public class MemberServiceFromSqlSession extends SqlSessionDaoSupport {

    public void selectOne(int code) {
        getSqlSession().selectOne("member-mapper.findOne", code);
    }

}
