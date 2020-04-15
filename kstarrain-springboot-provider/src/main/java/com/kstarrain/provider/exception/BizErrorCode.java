package com.kstarrain.provider.exception;

public enum BizErrorCode {

    SYSTEM_ERROR                                   ("9999", "服务器正忙，请稍后再试"),
    THIRD_SYSTEM_ERROR                             ("0001", "第三方系统异常"),
    INCORRECT_PARAMETERS                           ("0002", "Incorrect parameters"),
    IMPORT_DATA_EMPTY                              ("0003", "导入的数据不能为空"),
    EXPORT_CSV_DATA_ERROR                          ("0004", "导入csv文件失败，可能原因：1.标题重复【请检查csv文件首行标题是否有重复内容】；2.编码异常【请尝试用记事本打开文件，另存为编码为UTF-8的csv文件重新上传】"),

    ;

    private String code;
    private String message;

    BizErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }







}
