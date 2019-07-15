package com.deyatech.common.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 办件编号生成器
 * @author
 */
@Component
public class ProcessNumberSequence {

    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 生成办件号
     *
     * @param itemId 事项ID
     * @return
     */
    public String getNextProcessNumber(String itemId) {
        String date = DateUtil.format(new Date(), "yyyyMMdd");
        String seq = StrUtil.padPre(this.getNextSeq(itemId).toString(), 4, '0');
        return date + getPrefix(itemId) + seq;
    }

    final String prefix_pretrial_number = "PREFIX_PROCESS_NUMBER";
    final String prefix_pretrial_number_value = "PREFIX_PROCESS_NUMBER_VALUE";

    /**
     * 根据事项 id 返回对应的前缀
     *
     * @param itemId
     * @return
     */
    private String getPrefix(String itemId) {
        Object prefix = redisTemplate.opsForHash().get(prefix_pretrial_number, itemId);
        if (prefix == null) {
            prefix = RandomStringUtils.randomNumeric(4);
            // 如果已经存在就不能用这个作为前缀
            while (redisTemplate.opsForSet().isMember(prefix_pretrial_number_value, prefix)) {
                prefix = RandomStringUtils.randomNumeric(4);
            }
            redisTemplate.opsForSet().add(prefix_pretrial_number_value, prefix);
            redisTemplate.opsForHash().putIfAbsent(prefix_pretrial_number, itemId, prefix);
            return (String) prefix;
        }
        return prefix.toString();
    }

    /**
     * 根据事项 id 获取今日的下一个序列
     *
     * @param itemId
     * @return
     */
    private Long getNextSeq(String itemId) {
        String key = "SEQ_" + DateFormatUtils.format(new Date(), "yyMMdd") + "_" + itemId;
        Long increment = redisTemplate.opsForValue().increment(key, 1);
        return increment;
    }
}
