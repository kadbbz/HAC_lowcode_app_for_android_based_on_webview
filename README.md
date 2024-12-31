# HAC（活字格安卓容器）
HAC是[活字格低代码开发平台](https://www.grapecity.com.cn/solutions/huozige)[PDA解决方案](https://gcdn.grapecity.com.cn/forum.php?mod=viewthread&tid=153537&page=1&extra=#pid577272)的重要组成部分，能帮助开发者快速构建可运行于Android设备的原生应用，充分硬件设备的技术能力，提升最终用户体验。

## 技术能力
* 加载利用活字格低代码开发的Web应用
* 支持用户切换硬件加速和软件加速模式
* 提供支持现场拍摄的照片上传能力，适配活字格“图片上传单元格”
* 提供支持预览的视频上传能力，适配活字格“附件单元格”
* 提供文件上传能力，适配活字格“附件单元格”
* 提供发送和监听系统广播的能力
* 提供拍摄照片和视频的能力
* 提供震动和响铃的能力
* 提供给照片加水印的能力
* 提供读取本地文件的能力
* 提供使用摄像头扫码的能力
* 提供调用激光头扫码的能力，支持单次扫描和持续扫描两种模式
* 提供通过GPS等精确定位方式获取当前位置的能力
* 提供调用蓝牙打印机（ESC通用方案和DothanTech定制方案）的能力
* 提供读取NFC标签的能力
* 提供读写蓝牙BLE设备的能力
* 提供下载和预览PDF文件的能力
* 提供获取设备唯一标识（SSAID）的能力
* 支持离线模式（锁定返回键等页面导航操作）
* 适配活字格[“PDA交互命令”（插件）](https://marketplace.grapecity.com.cn/ApplicationDetails?productID=SP2209070004) 

## 可供Web应用调用的JavaScript接口清单
/app/src/main/java/com/huozige/lab/container/proxy

## 兼容性
* Android >= 8.0
* WebView（Chrome）版本 >= 87
* 运行内存 >= 2GB

## 使用方法
- Step 1：在Android手机上安装HAC（业务平台）APP
- Step 2：打开APP，按照界面提示，点击并扫描包含有Web应用URL、扫描头配置和其他个性化配置信息的“配置码”（推荐使用 [配置码生成器](https://hac.app.hzgcloud.cn/config) 快速生成）
- Step 3：配置完成，登录Web应用，开始使用

## 重要提示
本项目基于Gradle 8.0/JDK17开发，使用最新版本Android Stdio时，需要手动确认上述两个版本，才能确保正常编译。

[了解详情](https://gcdn.grapecity.com.cn/showtopic-153537-1-1.htm)
