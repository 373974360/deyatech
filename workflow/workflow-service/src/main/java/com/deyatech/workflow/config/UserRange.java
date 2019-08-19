package com.deyatech.workflow.config;

import java.util.HashMap;
import java.util.Map;

public class UserRange {

    private Map<String, Range> range;

    public Range getRange(String key) {
        Range r = null;
        if(null != range) {
            r = range.get(key);
        }
        return r;
    }
    

    public void setRange(Range r) {
        if(null == range) {
            range = new HashMap<String, Range>();
        }
        range.put(r.getStep(), r);
    }
    
    
    public Map<String, Range> getAllRange() {
        return range;
    }
    
    public void setAllRange(Map<String, Range> range) {
        this.range = range;
    }
}
