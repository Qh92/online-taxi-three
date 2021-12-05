package com.qinhao.serviceverificationcode.service;

import com.qinhao.internalcommon.dto.ResponseResult;
import com.qinhao.internalcommon.dto.serviceverificationcode.VerifyCodeResponse;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-12-04 21:36
 */
public interface VerifyCodeService {

    ResponseResult<VerifyCodeResponse> generate(int identity,String phoneNumber);
}
