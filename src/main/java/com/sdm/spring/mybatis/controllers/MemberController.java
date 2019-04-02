package com.sdm.spring.mybatis.controllers;


import com.sdm.spring.mybatis.models.MemberVO;
import com.sdm.spring.mybatis.mappers.MemberMapper;
import com.sdm.spring.mybatis.services.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@EnableAutoConfiguration
@RequestMapping("member")
public class MemberController {

    @Autowired
    MemberService memberService;

    @RequestMapping(path = "{memberCode}", method = RequestMethod.GET)
    public MemberVO getMember(@PathVariable int memberCode) {
        return memberService.getMemberVO(memberCode);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MemberVO> getMembers() {
        return memberService.getAllMemberVO();
    }

    @RequestMapping(method = RequestMethod.POST)
    public MemberVO postMember(@RequestBody MemberVO memberVO) {
        return memberService.createMemberVO(memberVO);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public MemberVO putMember(@RequestBody MemberVO memberVO) {
        return memberService.updateMemberVO(memberVO);
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    public MemberVO testMember(@RequestBody MemberVO memberVO) {
        return memberService.transactionTest(memberVO);
    }

}
