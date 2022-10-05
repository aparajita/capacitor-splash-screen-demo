import type { CapacitorConfig } from '@capacitor/cli'
import { KeyboardResize } from '@capacitor/keyboard'

/* eslint-disable @typescript-eslint/naming-convention */
const config: CapacitorConfig = {
  appId: 'com.aparajita.capacitor.splashscreen',
  appName: 'Splash',
  loggingBehavior: 'production',
  webDir: 'dist',
  plugins: {
    Keyboard: {
      resize: KeyboardResize.Ionic
    },
    Logger: {
      level: 'debug',
      useSyslog: false
    },
    SplashScreen: {
      showDuration: 700
    }
  }
}

export default config
