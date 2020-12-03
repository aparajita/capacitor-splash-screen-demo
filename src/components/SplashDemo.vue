<template>
  <div class="w-full">
    <div class="flex items-center">
      <ion-item
        lines="none"
        class="flex-1"
      >
        <ion-label class="flex-none mr-2">
          Source:
        </ion-label>
        <ion-select
          v-model="source"
          interface="action-sheet"
          class="max-w-none pl-0"
        >
          <ion-select-option value="max">
            Image
          </ion-select-option>
          <ion-select-option value="*">
            Launch screen
          </ion-select-option>
          <ion-select-option value="hi">
            <span v-if="isIOS">Storyboard</span>
            <span v-if="isAndroid">Layout</span>
          </ion-select-option>
          <ion-select-option value="foo">
            Non-existent
          </ion-select-option>
        </ion-select>
      </ion-item>

      <ion-button
        class="ion-padding-end"
        @click="onShow"
      >
        Show
      </ion-button>
    </div>

    <ion-item-group class="mt-3">
      <ion-item-divider>
        <ion-label class="font-bold">
          Show
        </ion-label>
      </ion-item-divider>

      <div class="flex">
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Delay:</ion-label>
          <ion-input
            v-model.number="showDelay"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Duration:</ion-label>
          <ion-input
            v-model.number="showDuration"
            type="number"
          />
        </ion-item>
      </div>

      <div class="flex">
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Fade in:</ion-label>
          <ion-input
            v-model.number="showFadeInDuration"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Fade out:</ion-label>
          <ion-input
            v-model.number="showFadeOutDuration"
            type="number"
            :disabled="!autoHide"
          />
        </ion-item>
      </div>

      <ion-item
        lines="none"
        class="flex"
      >
        <ion-label>Background:</ion-label>
        <ion-input v-model="backgroundColor" />
        <div
          class="w-5 h-5 mr-2 border border-solid"
          :style="showBackgroundStyle"
        />
      </ion-item>

      <div class="flex w-full">
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-checkbox
            v-model="autoHide"
            :disabled="animated"
          />
          <ion-label>Autohide</ion-label>
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-checkbox
            v-model="animated"
            :disabled="autoHide || source !== '*'"
          />
          <ion-label>Animated</ion-label>
        </ion-item>
      </div>
    </ion-item-group>

    <ion-item-group
      v-if="!autoHide && !animated"
      class="mt-5"
    >
      <ion-item-divider>
        <ion-label class="font-bold">
          Hide
        </ion-label>
      </ion-item-divider>

      <div
        v-if="!autoHide && !animated"
        class="flex"
      >
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Delay:</ion-label>
          <ion-input
            v-model.number="hideDelay"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Fade out:</ion-label>
          <ion-input
            v-model.number="hideFadeOutDuration"
            type="number"
          />
        </ion-item>
      </div>
    </ion-item-group>

    <ion-item-group class="mt-5">
      <ion-item-divider>
        <ion-label class="font-bold">
          Spinner
        </ion-label>
      </ion-item-divider>

      <ion-item
        lines="none"
        class="flex-1"
      >
        <ion-checkbox v-model="showSpinner" />
        <ion-label>Show spinner</ion-label>
      </ion-item>

      <div v-if="showSpinner">
        <ion-item lines="none">
          <ion-label class="flex-none mr-2">
            Style:
          </ion-label>

          <ion-select
            v-if="isIOS"
            v-model="iosSpinnerStyle"
            interface="action-sheet"
            class="max-w-none pl-0"
          >
            <ion-select-option>small</ion-select-option>
            <ion-select-option>large</ion-select-option>
          </ion-select>

          <ion-select
            v-if="isAndroid"
            v-model="androidSpinnerStyle"
            interface="action-sheet"
            class="max-w-none pl-0"
          >
            <ion-select-option>small</ion-select-option>
            <ion-select-option>smallInverse</ion-select-option>
            <ion-select-option>medium</ion-select-option>
            <ion-select-option>mediumInverse</ion-select-option>
            <ion-select-option>large</ion-select-option>
            <ion-select-option>largeInverse</ion-select-option>
            <ion-select-option>horizontal</ion-select-option>
          </ion-select>
        </ion-item>

        <ion-item lines="none">
          <ion-label>Color:</ion-label>
          <ion-input v-model="spinnerColor" />
          <div
            class="w-5 h-5 mr-2 border border-solid"
            :style="spinnerColorStyle"
          />
        </ion-item>
      </div>
    </ion-item-group>

    <ion-item-group
      v-if="source === 'max'"
      class="mt-5"
    >
      <ion-item-divider>
        <ion-label class="font-bold">
          Image
        </ion-label>
      </ion-item-divider>

      <ion-item
        lines="none"
        class="flex"
      >
        <ion-label class="flex-none mr-2">
          Display mode:
        </ion-label>
        <ion-select
          v-if="isIOS"
          v-model="iosImageMode"
          interface="action-sheet"
          class="max-w-none pl-0"
        >
          <ion-select-option>fill</ion-select-option>
          <ion-select-option>aspectFill</ion-select-option>
          <ion-select-option>fit</ion-select-option>
          <ion-select-option>center</ion-select-option>
          <ion-select-option>top</ion-select-option>
          <ion-select-option>bottom</ion-select-option>
          <ion-select-option>left</ion-select-option>
          <ion-select-option>right</ion-select-option>
          <ion-select-option>topLeft</ion-select-option>
          <ion-select-option>topRight</ion-select-option>
          <ion-select-option>bottomLeft</ion-select-option>
          <ion-select-option>bottomRight</ion-select-option>
        </ion-select>

        <ion-select
          v-if="isAndroid"
          v-model="androidImageMode"
          interface="action-sheet"
          class="max-w-none pl-0"
        >
          <ion-select-option>fill</ion-select-option>
          <ion-select-option>aspectFill</ion-select-option>
          <ion-select-option>fit</ion-select-option>
          <ion-select-option>fitTop</ion-select-option>
          <ion-select-option>fitBottom</ion-select-option>
          <ion-select-option>center</ion-select-option>
        </ion-select>
      </ion-item>
    </ion-item-group>
  </div>
</template>

<script lang="ts">
import { Capacitor, Plugins } from '@capacitor/core'
import { computed, defineComponent, ref, watch } from 'vue'
import {
  PluginError,
  WSSplashScreenAndroidImageDisplayMode,
  WSSplashScreenAndroidSpinnerStyle,
  WSSplashScreenHideOptions,
  WSSplashScreenIosImageDisplayMode,
  WSSplashScreenIosSpinnerStyle,
  WSSplashScreenShowOptions,
  WSSplashScreenWeb
} from 'ws-capacitor-splashscreen'

import {
  alertController,
  IonButton,
  IonCheckbox,
  IonInput,
  IonItem,
  IonItemDivider,
  IonItemGroup,
  IonLabel,
  IonSelect,
  IonSelectOption
} from '@ionic/vue'

export default defineComponent({
  name: 'SplashDemo',

  components: {
    IonButton,
    IonCheckbox,
    IonInput,
    IonItem,
    IonItemDivider,
    IonItemGroup,
    IonLabel,
    IonSelect,
    IonSelectOption
  },

  setup() {
    const darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)')
    darkModeQuery.addEventListener('change', (query) => {
      isDarkMode.value = query.matches
    })

    // ref
    const isDarkMode = ref(darkModeQuery.matches)
    const androidImageMode = ref<WSSplashScreenAndroidImageDisplayMode>('fit')
    const androidSpinnerStyle = ref<WSSplashScreenAndroidSpinnerStyle>('medium')
    const animated = ref(false)
    const autoHide = ref(false)
    const backgroundColor = ref('')
    const hideFadeOutDuration = ref(200)
    const hideDelay = ref(0)
    const iosImageMode = ref<WSSplashScreenIosImageDisplayMode>('fit')
    const iosSpinnerStyle = ref<WSSplashScreenIosSpinnerStyle>('large')
    const showDelay = ref(0)
    const showDuration = ref(1.5)
    const showFadeInDuration = ref(0.5)
    const showFadeOutDuration = ref(200)
    const showSpinner = ref(false)
    const spinnerColor = ref('555')
    const source = ref('*')

    // computed
    const isIOS = computed(() => Capacitor.getPlatform() === 'ios')
    const isAndroid = computed(() => Capacitor.getPlatform() === 'android')
    const spinnerColorStyle = computed(() => ({
      backgroundColor: parseColor(spinnerColor.value)
    }))
    const showBackgroundStyle = computed(() => ({
      backgroundColor: parseColor(backgroundColor.value)
    }))

    // watch
    watch(source, (value) => {
      if (value !== '*') {
        animated.value = false
      }
    })

    watch(autoHide, (hide) => {
      if (hide) {
        animated.value = false
      }
    })

    // event handlers
    async function onShow() {
      const splash = Plugins.WSSplashScreen as WSSplashScreenWeb
      const options: WSSplashScreenShowOptions = {
        source: source.value,
        delay: showDelay.value,
        fadeInDuration: showFadeInDuration.value,
        showDuration: showDuration.value,
        fadeOutDuration: showFadeOutDuration.value,
        backgroundColor: backgroundColor.value,
        autoHide: autoHide.value,
        animated: animated.value,
        showSpinner: showSpinner.value,
        spinnerColor: spinnerColor.value,
        ios: {
          imageDisplayMode: iosImageMode.value,
          spinnerStyle: iosSpinnerStyle.value
        },
        android: {
          spinnerStyle: androidSpinnerStyle.value,
          imageDisplayMode: androidImageMode.value,
          fullscreen: false
        }
      }

      try {
        await splash.show(options)
        const duration = splash.toMilliseconds(showDuration.value)

        if (animated.value) {
          // If it's animated, after the show duration, animate it
          setTimeout(async () => {
            await splash.animate()
          }, duration)
        } else if (!autoHide.value) {
          // If autoHide is not on, hide manually after the show duration
          setTimeout(async () => {
            const hideOptions: WSSplashScreenHideOptions = {
              delay: hideDelay.value,
              fadeOutDuration: hideFadeOutDuration.value
            }

            await splash.hide(hideOptions)
          }, duration)
        }
      } catch (e) {
        await showErrorAlert(e)
      }
    }

    async function showErrorAlert(error: Error) {
      let message = error.message

      if (isPluginError(error)) {
        message += ` [${error.code}]`
      }

      await showAlert(message + '.')
    }

    async function showAlert(message: string) {
      const alert = await alertController.create({
        header: `The plugin says:`,
        subHeader: '',
        message,
        buttons: ['OK']
      })
      await alert.present()
    }

    // utils
    function isPluginError(err: Error | PluginError): err is PluginError {
      return (err as PluginError).code !== undefined
    }

    function parseColor(color: string): string {
      if (/^system(Background|Text)$/.test(color)) {
        const black = '#000'
        const white = '#fff'

        if (color === 'systemBackground') {
          return isDarkMode.value ? black : white
        }

        return isDarkMode.value ? white : black
      }

      if (color.startsWith('#')) {
        color = color.slice(1)
      }

      switch (color.length) {
        case 3:
        case 4:
        case 6:
        case 8:
          // If it's the right length and all hex digits, return it
          if (/^[0-9a-fA-F]+$/.test(color)) {
            return '#' + color
          }
      }

      // Invalid color, return transparent
      return 'transparent'
    }

    return {
      androidImageMode,
      androidSpinnerStyle,
      animated,
      autoHide,
      backgroundColor,
      hideDelay,
      hideFadeOutDuration,
      iosImageMode,
      iosSpinnerStyle,
      isAndroid,
      isIOS,
      onShow,
      showBackgroundStyle,
      showDelay,
      showDuration,
      showFadeInDuration,
      showFadeOutDuration,
      showSpinner,
      source,
      spinnerColor,
      spinnerColorStyle
    }
  }
})
</script>
