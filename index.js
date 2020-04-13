
import { NativeModules } from 'react-native'

const { RNTDimension } = NativeModules

export const statusBarHeight = RNTDimension.DIMENSION_STATUS_BAR_HEIGHT

export const navigationBarHeight = RNTDimension.DIMENSION_NAVIGATION_BAR_HEIGHT

export const screenSize = {
  width: RNTDimension.DIMENSION_SCREEN_WIDTH,
  height: RNTDimension.DIMENSION_SCREEN_HEIGHT,
}

export const safeArea = {
  top: RNTDimension.DIMENSION_SAFE_AREA_TOP,
  right: RNTDimension.DIMENSION_SAFE_AREA_RIGHT,
  bottom: RNTDimension.DIMENSION_SAFE_AREA_BOTTOM,
  left: RNTDimension.DIMENSION_SAFE_AREA_LEFT,
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