package com.deyatech.common.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import com.deyatech.admin.feign.AdminFeign;
import com.deyatech.common.Constants;
import com.deyatech.common.base.BaseController;
import com.deyatech.common.context.SpringContextHelper;
import com.deyatech.common.entity.EnumsResult;
import com.deyatech.common.entity.FileUploadResult;
import com.deyatech.common.entity.RestResult;
import com.deyatech.common.enums.IEnums;
import com.deyatech.common.exception.BusinessException;
import com.deyatech.common.submail.SubMailTemplate;
import com.deyatech.common.submail.SubMailUtil;
import com.deyatech.common.utils.ClassUtil;
import com.deyatech.common.utils.VerifyCodeUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * common通用服务
 * </p>
 *
 * @author: lee.
 * @since 2018-12-21
 */
@Slf4j
@RestController
@RequestMapping("/common")
@Api(tags = {"通用服务接口"})
public class CommonController extends BaseController {

    @Value("${uploadPath}")
    private String uploadPath;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private AdminFeign adminFeignApi;


    /**
     * 获取验证码图片
     *
     * @return CommonResult.ok()
     */
    @GetMapping("/getVerifyCode")
    @ApiOperation(value = "获取验证码图片", notes = "根据随机数获取验证码图片")
    @ApiImplicitParam(name = "random", value = "随机数", required = true, dataType = "String", paramType = "query")
    public void getVerifyCode(HttpServletResponse response, String random) throws IOException {
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");
        String verifyCode = VerifyCodeUtils.generateVerifyCode(4);
        String verifyCodeKey = Constants.PREFIX_KEY_VERIFY_CODE + random;
        redisTemplate.opsForValue().set(verifyCodeKey, verifyCode);
        redisTemplate.expire(verifyCodeKey, 300, TimeUnit.SECONDS);
        //生成图片
        int w = 200, h = 80;
        VerifyCodeUtils.outputImage(w, h, response.getOutputStream(), verifyCode);
    }

    /**
     * 验证图片验证码
     *
     * @param verifyCode
     * @return CommonResult.ok()
     */
    @GetMapping("/validateVerifyCode")
    @ApiOperation(value = "根据随机数验证验证码是否正确", notes = "根据随机数验证验证码是否正确")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "verifyCode", value = "验证码", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "random", value = "随机数", required = true, dataType = "String", paramType = "query")
    })
    public RestResult validateVerifyCode(String verifyCode, String random) {
        if (validateVerifyCode(redisTemplate, verifyCode, random)) {
            return RestResult.ok(true);
        }
        return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "验证码错误", false);
    }

    /**
     * 类型,状态,各个枚举类型的javascript对象
     */
    @GetMapping("/getEnums")
    @ApiOperation(value = "获取系统枚举类型的javascript对象", notes = "获取系统枚举类型的javascript对象")
    public RestResult enums() {
        EnumsResult[] result = null;
        try {
            List<Class> allClassByInterface = ClassUtil.getAllClassByInterface(IEnums.class, "com.deyatech.common.enums");
            if (CollectionUtil.isNotEmpty(allClassByInterface)) {
                result = new EnumsResult[allClassByInterface.size()];
                for (int i = 0; i < allClassByInterface.size(); i++) {
                    Class<? extends IEnums> anEnum = allClassByInterface.get(i);
                    EnumsResult enumsResult = new EnumsResult();
                    List<Map<String, Object>> value = CollectionUtil.newArrayList();
                    IEnums[] enumConstants = anEnum.getEnumConstants();
                    for (IEnums enumConstant : enumConstants) {
                        Map<String, Object> map = CollectionUtil.newHashMap();
                        map.put("code", enumConstant.getCode());
                        map.put("value", enumConstant.getValue());
                        map.put("var", enumConstant.toString());
                        value.add(map);
                    }
                    enumsResult.setName(anEnum.getSimpleName());
                    enumsResult.setValue(value);
                    result[i] = enumsResult;
                }
            }
        } catch (Exception e) {
            ExceptionUtil.getMessage(e);
        }
        return RestResult.ok(result);
    }

    /**
     * 获取系统数据字典javascript对象
     */
    @GetMapping("/getDictionaries")
    @ApiOperation(value = "获取系统数据字典javascript对象", notes = "获取系统数据字典javascript对象")
    public RestResult dicts() {
        return adminFeignApi.getDictsAll();
    }

    /**
     * 上传文件
     *
     * @param file
     * @return UeditorResult
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件", notes = "上传文件")
    @ApiImplicitParam(name = "file", value = "验证码", required = true, dataType = "MultipartFile", paramType = "query")
    public RestResult uploadFile(@RequestParam("file") MultipartFile file) {
        FileUploadResult result = new FileUploadResult();
        //判断图片是否为空
        if (file.isEmpty()) {
            log.error("上传的文件是空文件");
            return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "上传的文件是空文件");
        }
        try {
            String originalFilename = file.getOriginalFilename();
            int index = originalFilename.lastIndexOf(".");
            //获取文件扩展名
            String extName;
            if (index != -1) {
                extName = originalFilename.substring(index);
            } else {
                log.error("文件类型无法识别");
                return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "文件类型无法识别");
            }
            String fileName = DateUtil.format(new Date(), DatePattern.PURE_DATETIME_FORMAT) + RandomUtil.randomNumbers(4) + extName;
            //调用文件处理类FileUtil，处理文件，将文件写入指定位置
            uploadFile(file.getBytes(), uploadPath, fileName);
            String url = Constants.UPLOAD_DEFAULT_PREFIX_URL.concat(fileName);
            if (StrUtil.isNotBlank(url)) {
                //转存文件
                result.setState("SUCCESS");
                result.setOriginal(originalFilename);
                result.setTitle(originalFilename);
                result.setUrl(url);
                result.setFilePath(uploadPath+fileName);
                return RestResult.build(HttpStatus.HTTP_OK, "上传成功", result);
            } else {
                result.setState("ERROR");
                log.error("上传失败");
                return RestResult.build(HttpStatus.HTTP_INTERNAL_ERROR, "上传失败", result);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("上传失败");
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "上传失败");
        }
    }

    /**
     * 查看图片
     *
     * @param filePath
     * @param response
     */
    @GetMapping("/showPicImg")
    @ApiOperation(value = "查看图片", notes = "查看图片")
    @ApiImplicitParam(name = "filePath", value = "图片路径", required = true, dataType = "String", paramType = "query")
    public void showPicImg(String basePath, String filePath, HttpServletResponse response) {
        if (StrUtil.isBlank(basePath)) {
            basePath = uploadPath;
        }
        String fileSeparator = System.getProperty("file.separator");
        if (!basePath.endsWith(fileSeparator)) {
            basePath += fileSeparator;
        }
        FileInputStream in = null;
        OutputStream out = null;
        try {
            response.setContentType("image/jpeg");
            in = new FileInputStream(basePath + filePath.replaceAll(Constants.UPLOAD_DEFAULT_PREFIX_URL,""));
            out = response.getOutputStream();
            IOUtils.copy(in, out);
        } catch (IOException e) {
            log.error("读取文件失败", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "读取文件失败");
        } finally {
            close(in);
            close(out);
        }
    }

    /**
     * 获取短信模板列表
     */
    @GetMapping("/getSubMailMessageTemplates")
    @ApiOperation(value = "获取短信模板列表", notes = "获取短信模板列表")
    private RestResult getSubMailMessageTemplates() {
        SubMailUtil subMailUtil = SpringContextHelper.getBean(SubMailUtil.class);
        List<SubMailTemplate> list = subMailUtil.getSubMailMessageTemplates();
        if (CollectionUtil.isNotEmpty(list)) {
            return RestResult.ok(list);
        } else {
            return RestResult.error("获取短信模板列表失败");
        }
    }

    /**
     * 下载文件
     *
     * @param filePath
     * @param response
     */
    @GetMapping("/download")
    @ApiOperation(value = "下载文件", notes = "下载文件")
    @ApiImplicitParam(name = "filePath", value = "文件路径", required = true, dataType = "String", paramType = "query")
    public void downloadFile(String basePath, String filePath, HttpServletRequest request, HttpServletResponse response) {
        if (StrUtil.isBlank(basePath)) {
            basePath = uploadPath;
        }
        basePath = basePath.replace("\\\\", "/");
        if (!basePath.endsWith("/")) {
            basePath += "/";
        }
        FileInputStream in = null;
        OutputStream out = null;
        try {
            String fileName = basePath + filePath.replaceAll(Constants.UPLOAD_DEFAULT_PREFIX_URL,"");
            response.setHeader("Content-Disposition", "attachment;filename=" + filePath.replaceAll(Constants.UPLOAD_DEFAULT_PREFIX_URL,""));
            response.setContentType(request.getServletContext().getMimeType(fileName));
            in = new FileInputStream(fileName);
            out = response.getOutputStream();
            IOUtils.copy(in, out);
        } catch (IOException e) {
            log.error("读取文件失败", e);
            throw new BusinessException(HttpStatus.HTTP_INTERNAL_ERROR, "读取文件失败");
        } finally {
            close(in);
            close(out);
        }
    }
}
