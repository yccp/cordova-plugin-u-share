#import <Cordova/CDVPlugin.h>
#import <UMSocialCore/UMSocialCore.h>

@interface UShare : CDVPlugin

- (void)setup:(CDVInvokedUrlCommand*)command;
- (void)share:(CDVInvokedUrlCommand *)command;

@end