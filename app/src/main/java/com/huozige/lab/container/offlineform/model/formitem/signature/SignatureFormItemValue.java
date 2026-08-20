package com.huozige.lab.container.offlineform.model.formitem.signature;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignatureFormItemValue {
    private String userName = "";
    private String fileName = "";
    // 单张签名图片最后一次保存的 ODate 时间。
    private Double updateTime;
}
