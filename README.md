# [DCS SDK（Java）开发者指南](https://github.com/dueros/dcs-sdk-java/blob/master/README.md)

## 这个Demo是在[Dueros官方Demo](https://github.com/dueros/dcs-sdk-java)的基础上做了一下修改，将Dueros的功能抽取裁剪为一个Android Library

### DuerosPlatformManager 作为平台的统一管理类，在初始化是需要传递IDuerosPlatform抽象类示例，完成具体的自定义功能。library本身包含Demo的语音输入输出模块，系统模块，peakerController模块。抽出音乐控制模块进行单独管理。

### IDuerosPlatform 用来完成平台的鉴权，支持自带的登录鉴权，新增Client Credentials鉴权方式，免除用户登录操作，新增自定义模块功能的管理

