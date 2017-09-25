# 友盟社会化分享 cordova 插件

> 支持ios, android
开通服务: [http://mobile.umeng.com/social](http://mobile.umeng.com/social)

## 安装

```
cordova plugin add cordova-plugin-u-share --variable IOS_KEY=你的IOS_KEY --variable ANDROID_KEY=你的ANDROID_SECRET --save
```
或
```
ionic cordova plugin add cordova-plugin-u-share --variable IOS_KEY=你的IOS_KEY --variable ANDROID_KEY=你的ANDROID_SECRET
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
window.AlicloudFeedback.open({
  // 自定义参数，目前仅ios有用
}, function() {
  console.log('成功');
}, function(e) {
  console.error(e);
});

```
> 获取未读数量
```js
window.AlicloudFeedback.fetchUnreadCount(function(x) {
  console.log(x);
}, function(e) {
  console.error(e);
});
```