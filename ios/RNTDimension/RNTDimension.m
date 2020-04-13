#import "RNTDimension.h"

CGFloat getStatusBarHeight() {
    return UIApplication.sharedApplication.statusBarFrame.size.height;
}

CGFloat getNavigationBarHeight() {
    return 0;
}

NSDictionary* getScreenSize() {

    CGRect bounds = UIScreen.mainScreen.bounds;

    return @{
        @"width": @(bounds.size.width),
        @"height": @(bounds.size.height),
    };

}

NSDictionary* getSafeArea() {

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

@implementation RNTDimension

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
        @"DIMENSION_STATUS_BAR_HEIGHT": @(getStatusBarHeight()),
        @"DIMENSION_NAVIGATION_BAR_HEIGHT": @(getNavigationBarHeight()),
        @"DIMENSION_SCREEN_WIDTH": screenSize[@"width"],
        @"DIMENSION_SCREEN_HEIGHT": screenSize[@"height"],
        @"DIMENSION_SAFE_AREA_TOP": safeArea[@"top"],
        @"DIMENSION_SAFE_AREA_RIGHT": safeArea[@"right"],
        @"DIMENSION_SAFE_AREA_BOTTOM": safeArea[@"bottom"],
        @"DIMENSION_SAFE_AREA_LEFT": safeArea[@"left"],
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