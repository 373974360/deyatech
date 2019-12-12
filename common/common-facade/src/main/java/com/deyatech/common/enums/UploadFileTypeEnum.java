package com.deyatech.common.enums;

/**
 * 上传文件类型
 *
 * @author ycx
 */
public enum UploadFileTypeEnum implements IEnums<String>{
    JPG("jpg", "jpg"),
    JPEG("jpeg", "jpeg"),
    PNG("png", "png"),
    GIF("gif", "gif"),
    BMP("bmp", "bmp"),
    TXT("txt", "txt"),
    RAR("rar", "rar"),
    TAR("tar", "tar"),
    ZIP("zip", "zip"),
    GZ("gz", "gz"),
    DOC("doc", "doc"),
    DOCX("docx", "docx"),
    XLS("xls", "xls"),
    XLSX("xlsx", "xlsx"),
    PPT("ppt", "ppt"),
    PPTX("pptx", "pptx");

    private String code;
    private String value;

    UploadFileTypeEnum(final String code, final String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public String getCode() {
        return this.code;
    }
}
