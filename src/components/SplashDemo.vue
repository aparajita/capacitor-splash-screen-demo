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
          v-model="showOptions.source"
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
            v-model.number="showOptions.delay"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Duration:</ion-label>
          <ion-input
            v-model.number="showOptions.showDuration"
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
            v-model.number="showOptions.fadeInDuration"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Fade out:</ion-label>
          <ion-input
            v-model.number="showOptions.fadeOutDuration"
            type="number"
            :disabled="!showOptions.autoHide || showOptions.animated"
          />
        </ion-item>
      </div>

      <ion-item
        lines="none"
        class="flex"
      >
        <ion-label>Background:</ion-label>
        <ion-input v-model="showOptions.backgroundColor" />
        <div
          class="relative w-5 h-5 mr-2 border border-solid"
          :style="showBackgroundStyle"
        >
          <svg
            id="backgroundSwatch"
            viewBox="0 0 20 20"
            xmlns="http://www.w3.org/2000/svg"
            class="stroke-2 text-red-600"
          >
            <line
              x1="0"
              y1="0"
              x2="20"
              y2="20"
              stroke="currentColor"
            />
          </svg>
        </div>
      </ion-item>

      <div class="flex w-full">
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-checkbox
            v-model="showOptions.autoHide"
            :disabled="showOptions.animated"
          />
          <ion-label>Autohide</ion-label>
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-checkbox
            v-model="showOptions.animated"
            :disabled="showOptions.autoHide || showOptions.source !== '*'"
          />
          <ion-label>Animated</ion-label>
        </ion-item>
      </div>
    </ion-item-group>

    <ion-item-group
      v-if="!showOptions.autoHide && !showOptions.animated"
      class="mt-5"
    >
      <ion-item-divider>
        <ion-label class="font-bold">
          Hide
        </ion-label>
      </ion-item-divider>

      <div
        v-if="!showOptions.autoHide && !showOptions.animated"
        class="flex"
      >
        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Delay:</ion-label>
          <ion-input
            v-model.number="hideOptions.delay"
            type="number"
          />
        </ion-item>

        <ion-item
          lines="none"
          class="flex-1"
        >
          <ion-label>Fade out:</ion-label>
          <ion-input
            v-model.number="hideOptions.fadeOutDuration"
            type="number"
          />
        </ion-item>
      </div>
    </ion-item-group>

    <ion-item-group
      v-if="showOptions.source === 'max'"
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
          v-model="showOptions.ios.imageDisplayMode"
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
          v-model="showOptions.android.imageDisplayMode"
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
        <ion-checkbox v-model="showOptions.showSpinner" />
        <ion-label>Show spinner</ion-label>
      </ion-item>

      <div v-if="showOptions.showSpinner">
        <ion-item lines="none">
          <ion-label class="flex-none mr-2">
            Style:
          </ion-label>

          <ion-select
            v-if="isIOS"
            v-model="showOptions.ios.spinnerStyle"
            interface="action-sheet"
            class="max-w-none pl-0"
          >
            <ion-select-option>small</ion-select-option>
            <ion-select-option>large</ion-select-option>
          </ion-select>

          <ion-select
            v-if="isAndroid"
            v-model="showOptions.android.spinnerStyle"
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
          <ion-input v-model="showOptions.spinnerColor" />
          <div
            class="w-5 h-5 mr-2 border border-solid"
            :style="spinnerColorStyle"
          >
            <svg
              id="spinnerSwatch"
              viewBox="0 0 20 20"
              xmlns="http://www.w3.org/2000/svg"
              class="invisible stroke-2 text-red-600"
            >
              <line
                x1="0"
                y1="0"
                x2="20"
                y2="20"
                stroke="currentColor"
              />
            </svg>
          </div>
        </ion-item>
      </div>
    </ion-item-group>
  </div>
</template>

<script lang="ts">
import { Capacitor, Plugins } from '@capacitor/core'
import {
  computed,
  defineComponent,
  onMounted,
  reactive,
  ref,
  toRaw,
  watch
} from 'vue'
import {
  PluginError,
  WSSplashScreenHideOptions,
  WSSplashScreenShowOptions,
  WSSplashScreenWeb
} from '@aparajita/capacitor-splash-screen'

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
    onMounted(() => {
      if (isIOS.value) {
        Plugins.Keyboard.setAccessoryBarVisible({ isVisible: true })
      }
    })

    const darkModeQuery = window.matchMedia('(prefers-color-scheme: dark)')

    // ref
    const isDarkMode = ref(darkModeQuery.matches)

    const showOptions = reactive<WSSplashScreenShowOptions>({
      source: '*',
      delay: 0,
      fadeInDuration: 0.5,
      showDuration: 1.5,
      fadeOutDuration: 200,
      autoHide: false,
      animated: false,
      backgroundColor: '',
      showSpinner: false,
      spinnerColor: '777',
      ios: {
        spinnerStyle: 'large',
        imageDisplayMode: 'fit'
      },
      android: {
        spinnerStyle: 'medium',
        imageDisplayMode: 'fit',
        fullscreen: false
      }
    })

    const hideOptions = reactive<WSSplashScreenHideOptions>({
      delay: 0,
      fadeOutDuration: 200
    })

    // computed
    const isIOS = computed(() => Capacitor.getPlatform() === 'ios')
    const isAndroid = computed(() => Capacitor.getPlatform() === 'android')
    const spinnerColorStyle = computed(() => ({
      backgroundColor: parseColor(
        'spinnerSwatch',
        showOptions.spinnerColor || ''
      )
    }))
    const showBackgroundStyle = computed(() => ({
      backgroundColor: parseColor(
        'backgroundSwatch',
        showOptions.backgroundColor || ''
      )
    }))

    function setDarkMode(this: MediaQueryList, ev: MediaQueryListEvent): any {
      isDarkMode.value = this.matches
    }

    if (darkModeQuery.addEventListener) {
      darkModeQuery.addEventListener('change', setDarkMode)
    } else {
      darkModeQuery.addListener(setDarkMode)
    }

    // watch
    watch(
      () => showOptions.source,
      (value) => {
        if (value !== '*') {
          showOptions.animated = false
        }
      }
    )

    watch(
      () => showOptions.autoHide,
      (hide) => {
        if (hide) {
          showOptions.animated = false
        }
      }
    )

    const kDurationMsThreshold = 10

    function toMilliseconds(value: number): number {
      return value >= kDurationMsThreshold ? value : value * 1000
    }

    // event handlers
    async function onShow() {
      const splash = Plugins.WSSplashScreen as WSSplashScreenWeb

      try {
        await splash.show(toRaw(showOptions))
        const duration = toMilliseconds(showOptions.showDuration as number)

        if (showOptions.animated) {
          // If it's animated, after the show duration, animate it
          setTimeout(async () => {
            await splash.animate()
          }, duration)
        } else if (!showOptions.autoHide) {
          // If autoHide is not on, hide manually after the show duration
          setTimeout(async () => {
            await splash.hide(toRaw(hideOptions))
          }, duration)
        }
      } catch (e) {
        console.log(e.message)
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

    function parseColor(id: string, color: string): string {
      // If it's an invalid color, return transparent
      let parsedColor = 'transparent'

      if (/^system(Background|Text)$/.test(color)) {
        const black = '#000'
        const white = '#fff'

        if (color === 'systemBackground') {
          parsedColor = isDarkMode.value ? black : white
        } else {
          parsedColor = isDarkMode.value ? white : black
        }
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
            parsedColor = '#' + color
          }
      }

      const swatch = document.getElementById(id)

      if (swatch) {
        const classList = swatch.classList

        if (parsedColor === 'transparent') {
          classList.remove('invisible')
        } else {
          classList.add('invisible')
        }
      }

      return parsedColor
    }

    return {
      hideOptions,
      isAndroid,
      isIOS,
      onShow,
      showOptions,
      showBackgroundStyle,
      spinnerColorStyle
    }
  }
})
</script>
