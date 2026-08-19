package com.huozige.lab.container.offlineform.model.formitem.signature;

import com.huozige.lab.container.offlineform.model.formitem.image.ImageCompressionOptions;
import com.huozige.lab.container.offlineform.model.formitem.image.ImageWatermarkOptions;

import lombok.Getter;
import lombok.Setter;

/**
 * 签名字段配置。watermark 的 JSON 结构与图片字段保持一致：
 * {"enableTimestamp": true, "items": [{"key": "...", "value": "..."}]}。
 */
@Getter
@Setter
public class SignatureFormItemOptions {
    private ImageCompressionOptions compression;
    private ImageWatermarkOptions watermark;
}
