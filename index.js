
import { NativeModules } from 'react-native'

const { RNTDimension } = NativeModules

export const statusBarHeight = RNTDimension.STATUS_BAR_HEIGHT

export const navigationBarHeight = RNTDimension.NAVIGATION_BAR_HEIGHT

export const screenSize = {
  width: RNTDimension.SCREEN_WIDTH,
  height: RNTDimension.SCREEN_HEIGHT,
}

export const safeArea = {
  top: RNTDimension.SAFE_AREA_TOP,
  right: RNTDimension.SAFE_AREA_RIGHT,
  bottom: RNTDimension.SAFE_AREA_BOTTOM,
  left: RNTDimension.SAFE_AREA_LEFT,
}

export function getStatusBarHeight() {
  return RNTDimension.getStatusBarHeight()
}

export function getNavigationBarHeight() {
  return RNTDimension.getNavigationBarHeight()
}

export function getScreenSize() {
  return RNTDimension.getScreenSize()
}

export function getSafeArea() {
  return RNTDimension.getSafeArea()
}