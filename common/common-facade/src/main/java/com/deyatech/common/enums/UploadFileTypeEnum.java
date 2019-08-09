package com.deyatech.common.enums;

/**
 * 上传文件类型
 *
 * @author ycx
 */
public enum UploadFileTypeEnum implements IEnums<String>{
    JPG("jpg", "jpg"),
    JPEG("jpeg", "jpeg"),
    GIF("gif", "gif"),
    BMP("bmp", "bmp"),
    DOC("doc", "doc"),
    DOCX("docx", "docx"),
    XLS("xls", "xls"),
    XLSX("xlsx", "xlsx"),
    TXT("txt", "txt");

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
