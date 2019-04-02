package com.sdm.spring.mybatis.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class TransactionAspect {

    @Autowired
    PlatformTransactionManager txManager;

    Map<String, TransactionStatus> storeOfTransaction; // 트랜잭션캐싱

    public TransactionAspect() {
        storeOfTransaction = new HashMap<String, TransactionStatus>();
    }

    @Before("execution(* *..*Service.transaction*(..))")
    public void startLog(JoinPoint jp) {
        // System.out.println("### AOP : method start ###");
        storeOfTransaction.put(jp.toString(), createTransaction(jp.toString()));
    }

    @AfterReturning("execution(* *..*Service.transaction*(..))")
    public void successLog(JoinPoint jp) {
        // System.out.println("### AOP : method success ###");
        txManager.commit(storeOfTransaction.get(jp.toString()));
        storeOfTransaction.remove(jp.toString());
    }

    @AfterThrowing("execution(* *..*Service.transaction*(..))")
    public void errorLog(JoinPoint jp) {
        // System.out.println("### AOP : method error ###");
        txManager.rollback(storeOfTransaction.get(jp.toString()));
        storeOfTransaction.remove(jp.toString());
        // System.out.println("cache memory size : " + storeOfTransaction.size());
    }

    public TransactionStatus createTransaction(String name) {
        /* Transaction 선언 */
        DefaultTransactionDefinition def = new DefaultTransactionDefinition();
        def.setName(name);
        def.setReadOnly(false);
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        /* Transaction 상태 */
        return txManager.getTransaction(def);
    }

}
