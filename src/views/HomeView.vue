<template>
  <ion-page>
    <ion-header>
      <ion-toolbar>
        <ion-title>Splash screen workshop</ion-title>
      </ion-toolbar>
    </ion-header>
    <ion-content
      class="ion-padding-vertical"
      :scroll-y="true"
    >
      <SplashDemo />
    </ion-content>
  </ion-page>
</template>

<script setup lang="ts">
import {
  IonContent,
  IonHeader,
  IonPage,
  IonTitle,
  IonToolbar
} from '@ionic/vue'
import SplashDemo from '@/components/SplashDemo.vue'
</script>
