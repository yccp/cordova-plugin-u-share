#import <Cordova/CDVPlugin.h>
#import <UMSocialCore/UMSocialCore.h>

@interface UShare : CDVPlugin

- (void)share:(CDVInvokedUrlCommand *)command;

@end