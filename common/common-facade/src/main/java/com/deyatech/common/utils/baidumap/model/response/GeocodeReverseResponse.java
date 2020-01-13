package com.deyatech.common.utils.baidumap.model.response;

/**
 * @author doukang
 * @description 逆地理编码出参
 * @date 2019/5/5 14:58
 */
public class GeocodeReverseResponse {

    /**
     * 返回结果状态
     */
    private Integer status;

    /**
     * 返回结果
     */
    private GeocodeReverseResult result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GeocodeReverseResult getResult() {
        return result;
    }

    public void setResult(GeocodeReverseResult result) {
        this.result = result;
    }
}
