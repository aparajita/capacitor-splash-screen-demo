import{D as s}from"./base.62a872d6.js";import"./index.7966e040.js";class o extends s{constructor(e){super(),this.setNativeDarkModeListener=e.setNativeDarkModeListener,this.isDarkMode=e.isDarkMode}async registerDarkModeListener(){const e=r=>{this.update(r).catch(console.error)};await this.setNativeDarkModeListener({},e),this.registeredListener=!0}async isDarkMode(){return Promise.resolve({dark:!1})}}export{o as DarkModeNative};
