package com.deyatech.common.utils.baidumap;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.deyatech.common.utils.baidumap.common.http.Response;
import com.deyatech.common.utils.baidumap.common.json.JSONConverter;
import com.deyatech.common.utils.baidumap.common.util.HttpUtils;
import com.deyatech.common.utils.baidumap.model.response.GeocodeResponse;
import com.deyatech.common.utils.baidumap.model.response.GeocodeResult;
import com.deyatech.common.utils.baidumap.model.response.GeocodeReverseResponse;
import com.deyatech.common.utils.baidumap.model.response.GeocodeReverseResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author doukang
 * @description 地图工具类
 * @date 2019/5/5 14:56
 */
public class BaiduMapUtils {

    private static final String HOST = "http://api.map.baidu.com";

//    private static final String PATH = "/geocoder/v2/";

    private static final String PATH_GEOCODE = "/geocoding/v3/";

    private static final String PATH_REVERSE_GEOCODE = "/reverse_geocoding/v3/";

//    private static final String APP_KEY_2 = "end6xXHXkwfNkxqhIZ18pTGiBQjAAFtv";

    private static final String APP_KEY_3 = "Gdk4qfec7oGONxBQvszHfEeFK3M9Xxal";

    private static final String OUTPUT = "json";

    private static Logger logger = LoggerFactory.getLogger(BaiduMapUtils.class);

    /**
     * 地理编码
     * @param address
     * @return
     * @throws Exception
     */
    public static GeocodeResult geocode(String address) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("ak", APP_KEY_3);
        params.put("output", OUTPUT);
        params.put("address", address);
        Response response = null;
        try {
            response = HttpUtils.doGet(HOST, PATH_GEOCODE, headers, params);
        } catch (Exception e) {
            logger.error("HTTP请求失败：" + e);
            return null;
        }
        GeocodeResponse geocodeResponse = JSONConverter.fromJson(GeocodeResponse.class, response.getResponseText());
        if (geocodeResponse == null || ObjectUtil.notEqual(0, geocodeResponse.getStatus())) {
            logger.error("地理编码失败：address=" + address);
            return null;
        }
        return geocodeResponse.getResult();
    }

    /**
     * 逆地理编码
     * @param lat
     * @param lng
     * @return
     * @throws Exception
     */
    public static GeocodeReverseResult geocodeReverse(Double lat, Double lng) {
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();
        params.put("ak", APP_KEY_3);
        params.put("output", OUTPUT);
        params.put("location", lat + "," + lng);
        params.put("latest_admin", "1");
//        params.put("pois", "1");
        Response response = null;
        try {
            response = HttpUtils.doGet(HOST, PATH_REVERSE_GEOCODE, headers, params);
        } catch (Exception e) {
            logger.error("HTTP请求失败：" + e);
            return null;
        }
        GeocodeReverseResponse reverseResponse = JSONConverter.fromJson(GeocodeReverseResponse.class, response.getResponseText());
        if (reverseResponse == null || ObjectUtil.notEqual(0, reverseResponse.getStatus())) {
            logger.error("逆地理编码失败：lat=" + lat + ", lng=" + lng);
            return null;
        }
        return reverseResponse.getResult();
    }

    public static void main(String[] args) {
        GeocodeResult geocodeResult = geocode("合肥市经开区繁华大道与翡翠路交口经典华城");
        GeocodeReverseResult geocodeReverseResult = geocodeReverse(31.78708944306621, 117.21777100671065);
        System.out.println(JSONUtil.toJsonStr(geocodeResult));
        System.out.println(JSONUtil.toJsonStr(geocodeReverseResult));
    }
}
