package com.qinhao.serviceverificationcode.controller;

import com.qinhao.internalcommon.dto.ResponseResult;
import com.qinhao.serviceverificationcode.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-12-04 21:19
 */
@RestController
@RequestMapping("/verify-code")
public class VerifyCodeController {

    @Autowired
    @Qualifier("verifyCodeService")
    private VerifyCodeService verifyCodeService;

    /**
     * 生成验证码
     * @param identity 乘客/司机 标识
     * @param phoneNumber 电话号码
     * @return
     */
    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity ,@PathVariable ("phoneNumber") String phoneNumber ) {
        return verifyCodeService.generate(identity, phoneNumber);
    }

}
