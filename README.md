# @react-native-hero/dimension

## Getting started

Install the library using either Yarn:

```
yarn add @react-native-hero/dimension
```

or npm:

```
npm install --save @react-native-hero/dimension
```

## Link

- React Native v0.60+

For iOS, use `cocoapods` to link the package.

run the following command:

```
$ cd ios && pod install
```

For android, the package will be linked automatically on build.

- React Native <= 0.59

run the following command to link the package:

```
$ react-native link @react-native-hero/dimension
```

## Example

```js
import {
  init,
  addListener,

  statusBarHeight,
  navigationBarHeight,
  screenSize,
  safeArea,

  getStatusBarHeight,
  getNavigationBarHeight,
  getScreenSize,
  getSafeArea,
} from '@react-native-hero/dimension'

// In your App.js, call init()
init()

// The getXxx methods is used to get the latest dimension info asynchronously.
// If your app will not change the dimension info, you can just use the corresponding variables.

getStatusBarHeight().then(data => {
  data.height
})

getNavigationBarHeight().then(data => {
  data.height
})

getScreenSize().then(data => {
  data.width
  data.height
})

getSafeArea().then(data => {
  data.top
  data.right
  data.bottom
  data.left
})

// You have to listen dimension change in android app.
addListener('change', function (data) {
  data.statusBarHeight
  data.navigationBarHeight
  data.screenSize
  data.safeArea
})
```
