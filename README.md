# 友盟社会化分享 cordova 插件

> 支持ios, android
开通服务: [http://mobile.umeng.com/social](http://mobile.umeng.com/social)

## 安装

```
cordova plugin add cordova-plugin-u-share --variable IOS_KEY=你的IOS_KEY --variable ANDROID_KEY=你的ANDROID_KEY --save
```
或
```
ionic cordova plugin add cordova-plugin-u-share --variable IOS_KEY=你的IOS_KEY --variable ANDROID_KEY=你的ANDROID_KEY
```

> 相关依赖
[cordova-plugin-cocoapod-support](https://www.npmjs.com/package/cordova-plugin-cocoapod-support)
```
cordova plugin add cordova-plugin-cocoapod-support --save
```
或
```
ionic cordova plugin add cordova-plugin-cocoapod-support
```

## 使用方法
>打开反馈页面
```js
window.UShare.share({
  image: 'https://xxx.png', // 缩略图 必须为https协议
  url: 'http://xxx.xxx/xxx', // 链接
  title: 'xxx', // 标题
  desc: 'xxx' // 简介
}, success => {
  console.log(success); // success为bool类型
}, e => {
  console.error(e);
});

```