package com.deyatech.common.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * Ueditor上传返回结果
 *
 * @author : lee.
 * @since : 2018-12-14 13:27
 */
@Data
@Accessors(chain = true)
@NoArgsConstructor
public class FileUploadResult implements Serializable {
    @ApiModelProperty(value = "状态")
    private String state = "";
    @ApiModelProperty(value = "原始URL")
    private String original = "";
    @ApiModelProperty(value = "文件名称")
    private String title = "";
    @ApiModelProperty(value = "访问URL")
    private String url = "";
    @ApiModelProperty(value = "物理路径")
    private String filePath = "";
}
