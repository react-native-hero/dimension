#import "RNTDimension.h"

@implementation RNTDimension

static CGFloat getStatusBarHeight() {
    return UIApplication.sharedApplication.statusBarFrame.size.height;
}

static CGFloat getNavigationBarHeight() {
    return 0;
}

static NSDictionary* getScreenSize() {

    CGRect bounds = UIScreen.mainScreen.bounds;

    return @{
        @"width": @(bounds.size.width),
        @"height": @(bounds.size.height),
    };

}

static NSDictionary* getSafeArea() {

    if (@available(iOS 11.0, *)) {
        UIWindow* window = [[UIApplication sharedApplication] keyWindow];
        UIEdgeInsets safeAreaInsets = window.safeAreaInsets;
        return @{
            @"top": @(safeAreaInsets.top),
            @"right": @(safeAreaInsets.right),
            @"bottom": @(safeAreaInsets.bottom),
            @"left": @(safeAreaInsets.left),
        };
    }

    return @{
        @"top": @(getStatusBarHeight()),
        @"right": @(0),
        @"bottom": @(0),
        @"left": @(0),
    };

}

+ (BOOL)requiresMainQueueSetup {
    return YES;
}

- (dispatch_queue_t)methodQueue {
    return dispatch_get_main_queue();
}

- (NSDictionary *)constantsToExport {
    NSDictionary *screenSize = getScreenSize();
    NSDictionary *safeArea = getSafeArea();
    return @{
        @"STATUS_BAR_HEIGHT": @(getStatusBarHeight()),
        @"NAVIGATION_BAR_HEIGHT": @(getNavigationBarHeight()),
        @"SCREEN_WIDTH": screenSize[@"width"],
        @"SCREEN_HEIGHT": screenSize[@"height"],
        @"SAFE_AREA_TOP": safeArea[@"top"],
        @"SAFE_AREA_RIGHT": safeArea[@"right"],
        @"SAFE_AREA_BOTTOM": safeArea[@"bottom"],
        @"SAFE_AREA_LEFT": safeArea[@"left"],
     };
}

RCT_EXPORT_MODULE(RNTDimension);

RCT_EXPORT_METHOD(getStatusBarHeight:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {

    resolve(@{
        @"height": @(getStatusBarHeight()),
    });

}

RCT_EXPORT_METHOD(getNavigationBarHeight:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {

    resolve(@{
        @"height": @(getNavigationBarHeight()),
    });

}

RCT_EXPORT_METHOD(getScreenSize:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {

    resolve(getScreenSize());

}

RCT_EXPORT_METHOD(getSafeArea:(RCTPromiseResolveBlock)resolve reject:(RCTPromiseRejectBlock)reject) {

    resolve(getSafeArea());

}

@end
