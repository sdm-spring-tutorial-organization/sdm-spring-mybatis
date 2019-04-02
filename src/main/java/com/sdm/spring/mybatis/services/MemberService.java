package com.sdm.spring.mybatis.services;

import com.sdm.spring.mybatis.mappers.MemberMapper;
import com.sdm.spring.mybatis.models.MemberVO;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class MemberService {

    @Autowired
    MemberMapper memberMapper;

    @Autowired // 명시적 트랜잭션
    PlatformTransactionManager txManager;

    @Autowired // 명시적 트랜잭션
    TransactionTemplate transactionTemplate;

    // 선언적 트랜잭션
    @Transactional(readOnly = true)
    public MemberVO getMemberVO(int memberCode) {
        return memberMapper.findOne(memberCode);
    }

    // 선언적 트랜잭션
    @Transactional(readOnly = true)
    public List<MemberVO> getAllMemberVO() {
        return memberMapper.findAll();
    }

    // 명시적 트랜잭션 - PlatformTransactionManager
    public MemberVO createMemberVO(MemberVO memberVO) {

        // Transaction 선언
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName("createMemberVO");
        def.setReadOnly(false);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        // Transaction 상태
        TransactionStatus status = txManager.getTransaction(def);

        try {
            memberMapper.create(memberVO);
        } catch (Exception e) {

            // rollback
            System.out.println("### rollback call ###");
            txManager.rollback(status);
            throw new DataAccessException("dataAccessException") {
            };
        }

        // commit
        System.out.println("### commit call ###");
        txManager.commit(status);
        return memberVO;
    }

    // 명시적 트랜잭션 - TranactionTemplate
    public MemberVO updateMemberVO(MemberVO memberVO) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                memberMapper.update(memberVO);
            }
        });
        return memberVO;
    }

    // 커스텀 트랜잭션 - AOP TEST
    public MemberVO transactionTest(MemberVO memberVO) {
        try {
            memberMapper.create(memberVO); // Success

            memberVO.setScore("a"); // Create Error
            memberMapper.update(memberVO); // Fail
        } catch (Exception e) {
            throw e;
        }
        return memberVO;
    }

}
