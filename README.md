# capacitor-splash-screen demo

This [Ionic](https://ionicframework.com)/[Capacitor](https://capacitorjs.com) app is a test bed for all of the features of [@aparajita/capacitor-splash-screen](https://github.com/aparajita/capacitor-splash-screen).

## Installation

```shell
git clone https://github.com/aparajita/capacitor-splash-screen-demo.git
cd capacitor-splash-screen-demo
pnpm install  # npm install
pnpm build  # npm run build
cap sync
```

## Usage

To run the app on iOS:

```shell
pnpm ios  # npm run ios
```

To run the app on Android:

```shell
pnpm android  # npm run android
```

### Interface

Play around with different options and click "Show". A few things of note:

* Durations may be specified as seconds (anything < 10) or as milliseconds (anything >= 10).
* Background colors may be RGB[A], RRGGBB[AA], `systemBackground`, or `systemText`. See `WSSplashScreenColor` for more information. To the right of the color input is a color swatch with the current color. If the input is empty or an invalid color, the color is effectively transparent.
* On iOS, the launch screen and storyboard adapt to dark/light mode.
* Options that are not active based on other options are hidden or disabled.
