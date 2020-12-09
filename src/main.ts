import { createApp } from 'vue'
import App from './App.vue'
import router from './router'

import { Plugins } from '@capacitor/core'
import { IonicVue, isPlatform } from '@ionic/vue'
import 'ws-capacitor-splashscreen'
import { WSSplashScreenWeb, listenToAppState } from 'ws-capacitor-splashscreen'

/* Core CSS required for Ionic components to work properly */
import '@ionic/vue/css/core.css'

/* Basic CSS for apps built with Ionic */
import '@ionic/vue/css/normalize.css'
import '@ionic/vue/css/structure.css'
import '@ionic/vue/css/typography.css'

/* Optional CSS utils that can be commented out */
import '@ionic/vue/css/padding.css'
import '@ionic/vue/css/float-elements.css'
import '@ionic/vue/css/text-alignment.css'
import '@ionic/vue/css/text-transformation.css'
import '@ionic/vue/css/flex-utils.css'
import '@ionic/vue/css/display.css'

/* Theme variables */
import './theme/variables.css'
import './assets/css/styles.pcss'

const config: { [key: string]: any } = {}

if (isPlatform('desktop')) {
  config.mode = 'ios'
}

const app = createApp(App).use(IonicVue, config).use(router)

// Show the spash screen when we resume.
// On Android the suspend app state change comes too late,
// we can't seem to change the screen at all.
if (isPlatform('ios')) {
  const splashscreen = Plugins.WSSplashScreen as WSSplashScreenWeb

  listenToAppState(true, {
    async onSuspend() {
      await splashscreen.show({
        fadeInDuration: 0.2,
        autoHide: false,
        backgroundColor: 'systemBackground'
      })
    },

    async onResume() {
      await splashscreen.animate({ delay: 0.2 })
    }
  })
}

router.isReady().then(() => {
  app.mount('#app')
})
