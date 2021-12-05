package com.qinhao.serviceverificationcode.controller;

import com.qinhao.internalcommon.dto.ResponseResult;
import com.qinhao.internalcommon.dto.serviceverificationcode.VerifyCodeResponse;
import com.qinhao.serviceverificationcode.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

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

    @GetMapping("/generate/{identity}/{phoneNumber}")
    public ResponseResult generate(@PathVariable("identity") int identity ,@PathVariable ("phoneNumber") String phoneNumber ) {
        return verifyCodeService.generate(identity, phoneNumber);
    }

}
