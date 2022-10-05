<template>
  <ion-app>
    <ion-router-outlet />
  </ion-app>
</template>

<script setup lang="ts">
import { DarkMode } from '@aparajita/capacitor-dark-mode'
import { IonApp, IonRouterOutlet } from '@ionic/vue'

DarkMode.addAppearanceListener(({ dark }) => {
  console.log('Dark mode is', dark ? 'on' : 'off')
})
</script>
