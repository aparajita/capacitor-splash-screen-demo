<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Splash screen</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content
      class="w-full h-full ion-padding-vertical"
      scroll-y="true"
    >
      <SplashDemo />
    </ion-content>
  </ion-page>
</template>

<script lang="ts">
import { Capacitor, Plugins } from '@capacitor/core'
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/vue'
import { onMounted } from 'vue'
import { WSSplashScreenWeb } from '@aparajita/capacitor-splash-screen'
import SplashDemo from '@/components/SplashDemo.vue'

export default {
  name: 'Home',

  components: {
    IonContent,
    IonHeader,
    IonPage,
    IonTitle,
    IonToolbar,
    SplashDemo
  },

  setup() {
    // computed
    const isNative = () => Capacitor.isNative

    // lifecycle
    onMounted(() => {
      const splash = Plugins.WSSplashScreen as WSSplashScreenWeb
      splash.animate({ delay: 200 })
    })

    return {
      isNative
    }
  }
}
</script>
