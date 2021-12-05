package com.qinhao.serviceverificationcode.service.impl;

import com.qinhao.internalcommon.dto.ResponseResult;
import com.qinhao.internalcommon.dto.serviceverificationcode.VerifyCodeResponse;
import com.qinhao.serviceverificationcode.service.VerifyCodeService;
import org.springframework.stereotype.Service;

/**
 * @author Qh
 * @version 1.0
 * @date 2021-12-04 21:40
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {
    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {
        /*
        校验 三档验证。乌云安全检测。业务方控制，不能无限制发短信。
        redis 1分钟发了三次，限制你5分钟不能发。1小时发了10次，限制24小时内不能发
         */
        String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));
        VerifyCodeResponse data = new VerifyCodeResponse();
        data.setCode(code);
        return ResponseResult.success(data);
    }


    /**
     * 测试随机生成6位验证码的速度
     * @param args
     */
    public static void main(String[] args) {

        int num = 10000000;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            String code = (Math.random() + "").substring(2, 8);
        }
        long end1 = System.currentTimeMillis();
        System.out.println(end1 - start1);

        long start2 = System.currentTimeMillis();
        for (int i = 0; i < num; i++) {
            String code = String.valueOf((int) (Math.random() * 9 + 1) * Math.pow(10, 5));
        }
        long end2 = System.currentTimeMillis();
        System.out.println(end2 - start2);

    }

    /*
     * 估算线程数
     * 16核 应该开几个线程
     * 线程数 = CPU可用核数 / （1 - 阻塞系数（io密集型接近1，计算密集型接近0））
     *
     * eg.假如是io密集型，假定阻塞系数0.9 线程数 = 16 / 0.1 = 160
     * 假如是计算密集型，假定阻塞系数0.1 线程数 = 16 / 0.9 = 17
     *
     * 阻塞系数没有定量的值只有定性的分析！！
     */

    /*
     * 提升QPS
     * 1.提高并发数
     *  1.能用多线程用多线程
     *  2.增加各种连接数：tomcat mysql redis等等
     *  3.服务无状态，便于横向扩展，扩机器
     *  4.让服务能力对等（serviceUrl:打乱顺序）
     */
}
