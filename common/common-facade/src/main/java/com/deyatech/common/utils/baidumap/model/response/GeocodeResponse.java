package com.deyatech.common.utils.baidumap.model.response;

/**
 * @author doukang
 * @description 地理编码出参
 * @date 2019/5/5 16:50
 */
public class GeocodeResponse {

    /**
     * 返回结果状态
     */
    private Integer status;

    /**
     * 返回结果
     */
    private GeocodeResult result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public GeocodeResult getResult() {
        return result;
    }

    public void setResult(GeocodeResult result) {
        this.result = result;
    }
}
