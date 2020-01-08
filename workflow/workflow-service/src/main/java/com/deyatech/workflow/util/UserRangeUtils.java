package com.deyatech.workflow.util;

import com.deyatech.common.Constants;
import com.deyatech.common.context.SpringContextHelper;
import com.deyatech.workflow.config.FlowForm;
import com.deyatech.workflow.config.Range;
import com.deyatech.workflow.config.WorkFlowConfig;
import com.deyatech.workflow.constant.ProcessConstant;
import org.apache.commons.lang.StringUtils;

public class UserRangeUtils {

    /**
     * 根据配置获取查询用户帐号规则
     * 
     * @param key
     * @return
     */
    public static AbstractUserRange getUserRange(String key) {
        AbstractUserRange rule = null;
        String[] keys = StringUtils.split(key, ProcessConstant.WORKFLOW_RANG_KEY_SPLIT);
        if (null != keys && keys.length == 3) {
            FlowForm form = WorkFlowConfig.getEntityForm(keys[0] + ProcessConstant.WORKFLOW_RANG_KEY_SPLIT + keys[1]);
            if (null != form && null != form.getUserRange()) {
                Range range = form.getUserRange().getRange(keys[2]);
                if(null != range) {
                    rule = (AbstractUserRange) SpringContextHelper.getBean(range.getRefRule());
                }
            }
        }
        if (null == rule) {
            rule = (AbstractUserRange) SpringContextHelper.getBean("defaultUserRange");
        }
        return rule;
    }

}
