package com.sdm.spring.mybatis.mappers;

import com.sdm.spring.mybatis.models.MemberVO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface MemberMapper {

    /* 1. xml */

    MemberVO findOne(int code);

    /* 2. annotation */

    @Select("SELECT code, name, team FROM member")
    List<MemberVO> findAll();

    @Insert("INSERT INTO member(code, name, team, score) VALUES(#{code}, #{name}, #{team}, #{score})")
    int create(MemberVO member);

    @Update("UPDATE member SET name=#{name}, team=#{team}, score=#{score} WHERE code=#{code}")
    int update(MemberVO member);

}
