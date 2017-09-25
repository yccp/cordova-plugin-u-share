#import "UShare.h"

@implementation UShare

- (void)setup:(CDVInvokedUrlCommand *)command
{
    // 获取IOS的Appkey
    NSString *appKey = [[self.commandDelegate settings] objectForKey:@"ios_key"];
    NSLog(@"你的appKey为%@", appKey);
    // ...
    // 返回成功结果
    [self.commandDelegate sendPluginResult:[CDVPluginResult resultWithStatus:CDVCommandStatus_OK] callbackId:command.callbackId];
}

// 支持所有iOS系统
- (BOOL)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation
{
    //6.3的新的API调用，是为了兼容国外平台(例如:新版facebookSDK,VK等)的调用[如果用6.2的api调用会没有回调],对国内平台没有影响
    BOOL result = [[UMSocialManager defaultManager] handleOpenURL:url sourceApplication:sourceApplication annotation:annotation];
    if (!result) {
        // 其他如支付等SDK的回调
    }
    return result;
}

- (void)share:(CDVInvokedUrlCommand *)command
{
    // 获取参数
    NSError* jsonError;
    NSString* arguments = [command argumentAtIndex:0];
    NSData* objectData = [arguments dataUsingEncoding:NSUTF8StringEncoding];
    NSDictionary* options = [NSJSONSerialization JSONObjectWithData:objectData
                                                            options:NSJSONReadingMutableContainers
                                                              error:&jsonError];
    NSLog(@"你的分享参数%@", options);
    __weak __typeof__(self) weakSelf = self;

    
    [self.feedbackKit makeFeedbackViewControllerWithCompletionBlock:^(YWFeedbackViewController *viewController, NSError *error) {
        if (viewController != nil) {
            UINavigationController *nav = [[UINavigationController alloc] initWithRootViewController:viewController];
            CATransition *openAnimation = [CATransition animation];
            [openAnimation setDuration:0.5];
            [openAnimation setType: kCATransitionPush];
            [openAnimation setSubtype:kCATransitionFromRight];
            [openAnimation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionDefault]];
            [weakSelf.viewController presentViewController:nav animated:NO completion:nil];
            [weakSelf.viewController.view.window.layer addAnimation:openAnimation forKey:nil];
            
            CATransition *closeAnimation = [CATransition animation];
            [closeAnimation setDuration:0.5];
            [closeAnimation setType: kCATransitionPush];
            [closeAnimation setSubtype:kCATransitionFromLeft];
            [closeAnimation setTimingFunction:[CAMediaTimingFunction functionWithName:kCAMediaTimingFunctionDefault]];
            [viewController setCloseBlock:^(UIViewController *aParentController){
                [aParentController dismissViewControllerAnimated:NO completion:nil];
                [aParentController.view.window.layer addAnimation:closeAnimation forKey:nil];
            }];
            
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK];
            [weakSelf.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        } else {
            CDVPluginResult* pluginResult = [CDVPluginResult resultWithStatus:CDVCommandStatus_ERROR messageAsDictionary:error.userInfo];
            [weakSelf.commandDelegate sendPluginResult:pluginResult callbackId:command.callbackId];
        }
    }];
}

@end
