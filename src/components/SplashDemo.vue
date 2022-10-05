<template>
  <ion-item lines="none">
    <ion-label class="flex-initial mr-2">Source:</ion-label>
    <ion-select
      v-model="showOptions.source"
      interface="action-sheet"
      :interface-options="{ header: 'Select a splash screen source:' }"
      class="max-w-none pl-0"
    >
      <ion-select-option value="*">Default</ion-select-option>
      <ion-select-option
        v-for="screen in screens"
        :key="screen"
        :value="screen"
      >
        {{ screen }}
      </ion-select-option>
      <ion-select-option value="foo">Non-existent</ion-select-option>
    </ion-select>

    <ion-button
      slot="end"
      size="default"
      class="my-2"
      @click="onShow"
    >
      Show
    </ion-button>
  </ion-item>

  <ion-list>
    <ion-item
      slot="header"
      color="light"
      lines="full"
    >
      <ion-label class="font-semibold">Show</ion-label>
    </ion-item>

    <div slot="content">
      <div class="flex">
        <ion-item
          class="w-1/2"
          lines="full"
        >
          <ion-label>Delay:</ion-label>
          <ion-input
            v-model.number="showOptions.delay"
            type="number"
          />
        </ion-item>

        <ion-item
          class="w-1/2 [--padding-start:4px]"
          lines="full"
        >
          <ion-label>Duration:</ion-label>
          <ion-input
            v-model.number="showOptions.showDuration"
            type="number"
          />
        </ion-item>
      </div>

      <ion-item lines="none">
        <ion-label>Fade in:</ion-label>
        <ion-input
          v-model.number="showOptions.fadeInDuration"
          type="number"
        />
      </ion-item>
    </div>

    <ion-item
      slot="header"
      color="light"
      lines="full"
    >
      <ion-label class="font-semibold">Animation</ion-label>
    </ion-item>

    <div
      slot="content"
      class="flex"
    >
      <ion-item
        class="w-1/2"
        lines="none"
      >
        <ion-checkbox
          v-model="animated"
          class="my-0"
        />
        <ion-label>Animated</ion-label>
      </ion-item>

      <ion-item
        class="w-1/2"
        lines="none"
      >
        <ion-label>Duration:</ion-label>
        <ion-input
          v-model.number="animateOptions.animationDuration"
          type="number"
        />
      </ion-item>
    </div>

    <ion-item
      v-show="!animated"
      color="light"
      lines="full"
    >
      <ion-label class="font-semibold">Hide</ion-label>
    </ion-item>

    <div
      v-show="!animated"
      class="flex"
    >
      <ion-item
        class="w-1/2"
        lines="none"
      >
        <ion-label>Delay:</ion-label>
        <ion-input
          v-model.number="hideOptions.delay"
          type="number"
        />
      </ion-item>

      <ion-item
        class="w-1/2"
        lines="none"
      >
        <ion-label>Fade out:</ion-label>
        <ion-input
          v-model.number="hideOptions.fadeOutDuration"
          type="number"
        />
      </ion-item>
    </div>
  </ion-list>
</template>

<script setup lang="ts">
import type {
  PluginError,
  SplashScreenAnimateOptions,
  SplashScreenHideOptions
} from '@aparajita/capacitor-splash-screen'
import { durationToMs, SplashScreen } from '@aparajita/capacitor-splash-screen'
import type { PluginListenerHandle } from '@capacitor/core'
import { Capacitor } from '@capacitor/core'
import { Keyboard } from '@capacitor/keyboard'
import {
  alertController,
  IonButton,
  IonCheckbox,
  IonInput,
  IonItem,
  IonLabel,
  IonList,
  IonSelect,
  IonSelectOption
} from '@ionic/vue'
import {
  onBeforeMount,
  onMounted,
  onUnmounted,
  reactive,
  ref,
  toRaw
} from 'vue'

let listenerHandle: PluginListenerHandle

const animated = ref(false)
const showOptions = reactive({
  source: '*',
  delay: 0,
  fadeInDuration: 0.2,
  showDuration: 1,
  ios: {},
  android: {}
})

const hideOptions = reactive<SplashScreenHideOptions>({
  delay: 0,
  fadeOutDuration: 300
})

const animateOptions = reactive<SplashScreenAnimateOptions>({
  animationDuration: 700,
  foo: 'bar' // We can pass arbitrary data to animate()
})

const screens = ref<string[]>([])

onBeforeMount(() => {
  if (Capacitor.getPlatform() === 'ios') {
    screens.value = ['LaunchScreen', 'LaunchScreen_pt', 'LaunchScreen_hi']
  } else {
    screens.value = ['news']
  }
})

onMounted(async () => {
  if (Capacitor.getPlatform() === 'ios') {
    await Keyboard.setAccessoryBarVisible({ isVisible: true })
    const onSuspend = (): void => {
      // We want to show the splash screen when we suspend
      // so it will already be there on resume.
      SplashScreen.show({
        source: 'LaunchScreen_hi',
        delay: 0,
        showDuration: 100
      }).catch(console.error)
    }

    const onResume = (): void => {
      SplashScreen.hide({
        delay: 300,
        fadeOutDuration: 300
      }).catch(console.error)
    }

    listenerHandle = await SplashScreen.listenToAppState({
      onSuspend,
      onResume
    })
  }

  await SplashScreen.animate(toRaw(animateOptions))
})

onUnmounted(async () => {
  if (listenerHandle) {
    await listenerHandle.remove()
  }
})

async function onShow(): Promise<void> {
  // Put show() in a try block so we don't try to animate/hide if show() fails
  try {
    await SplashScreen.show(toRaw(showOptions))
    const duration = durationToMs(showOptions.showDuration)

    if (animated.value) {
      // If it's animated, after the show duration, animate it
      setTimeout(() => {
        SplashScreen.animate(toRaw(animateOptions)).catch(showErrorAlert)
      }, duration)
    } else {
      // Hide manually after the show duration
      setTimeout(() => {
        SplashScreen.hide(toRaw(hideOptions)).catch(showErrorAlert)
      }, duration)
    }
  } catch (error) {
    await showErrorAlert(error)
  }
}

async function showErrorAlert(error: unknown): Promise<void> {
  await showAlert(makeErrorMessage(error))
}

function makeErrorMessage(error: unknown): string {
  let message: string

  if (error instanceof Error) {
    message = error.message

    if (isPluginError(error)) {
      message += ` [${error.code}]`
    }
  } else {
    message = 'An unknown error occurred'
  }

  return message
}

function isPluginError(err: Error | PluginError): err is PluginError {
  return 'code' in err
}

async function showAlert(message: string): Promise<void> {
  const alert = await alertController.create({
    header: `The plugin says:`,
    subHeader: '',
    message,
    buttons: ['OK']
  })
  await alert.present()
}
</script>
