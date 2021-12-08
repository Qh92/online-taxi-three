package com.qinhao.serviceverificationcode.service.impl;

import com.qinhao.internalcommon.constant.IdentityConstant;
import com.qinhao.internalcommon.dto.ResponseResult;
import com.qinhao.internalcommon.dto.serviceverificationcode.VerifyCodeResponse;
import com.qinhao.serviceverificationcode.constant.VerifyCodeConstant;
import com.qinhao.serviceverificationcode.service.VerifyCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 验证码服务
 *
 * @author Qh
 * @version 1.0
 * @date 2021-12-04 21:40
 */
@Service("verifyCodeService")
public class VerifyCodeServiceImpl implements VerifyCodeService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public ResponseResult<VerifyCodeResponse> generate(int identity, String phoneNumber) {
        /*
        校验 三档验证。乌云安全检测。业务方控制，不能无限制发短信。
        redis 过期时间设置2分钟，假如1分钟发了三次，限制你5分钟不能发。1小时发了10次，限制24小时内不能发
         */
        VerifyCodeResponse data = new VerifyCodeResponse();
        String redisKey = getRedisKey(identity,phoneNumber);
        if (Objects.isNull(redisKey)) {
            data.setCode(null);
            return ResponseResult.fail(data);
        }
        String redisValue = stringRedisTemplate.opsForValue().get(redisKey);
        if (Objects.isNull(redisValue)) {
            String code = String.valueOf((int) ((Math.random() * 9 + 1) * Math.pow(10, 5)));

            // 设置2分钟过期时间
            stringRedisTemplate.opsForValue().set(redisKey, code, 120,TimeUnit.SECONDS);
            stringRedisTemplate.opsForValue().set(phoneNumber + "_" + code,String.valueOf(1),120,TimeUnit.SECONDS);
            data.setCode(code);
            return ResponseResult.success(data);
        }
        // redis还未过期，校验是否存在重复获取验证码
        else {
            // 再一次获取验证码
            stringRedisTemplate.opsForValue().increment(phoneNumber + "_" + redisValue);
            // 获取验证码的次数
            int count = Integer.parseInt(stringRedisTemplate.opsForValue().get(phoneNumber + "_" + redisValue));
            if (count >= 3) {

            }
            return null;
        }
    }


    private String getRedisKey(int identity,String phoneNumber) {
        if (IdentityConstant.PASSENGER == identity) {
            return VerifyCodeConstant.PASSENGER_LOGIN_KEY_PRE + identity + "_" + phoneNumber;
        } else if(IdentityConstant.DRIVER == identity) {
            return VerifyCodeConstant.DRIVER_LOGIN_KEY_PRE + identity+ "_" + phoneNumber;
        }
        return null;
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
     *      1.能用多线程用多线程
     *      2.增加各种连接数：tomcat mysql redis等等
     *      3.服务无状态，便于横向扩展，扩机器
     *      4.让服务能力对等（serviceUrl:打乱顺序）
     *
     * 2.减少响应时间
     *      1.异步（最终一致性，不需要及时响应），流量削峰
     *      2.缓存（减少db读取，减少磁盘io，读多，写少）
     *      3.数据库优化
     *      4.多的数据，分批次返回
     *      5.减少调用链
     *      6.长连接。不要轮询
     */
}
