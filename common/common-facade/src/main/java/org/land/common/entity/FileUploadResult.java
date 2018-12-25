package org.land.common.entity;

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
public class FileUploadResult implements Serializable{
    private String state = "";
    private String original = "";
    private String title = "";
    private String url = "";
}
