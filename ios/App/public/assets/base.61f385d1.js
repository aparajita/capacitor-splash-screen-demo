import{o as t,_ as i,q as l}from"./index.7966e040.js";const p=t("App",{web:()=>i(()=>import("./web.34226b6e.js"),["assets/web.34226b6e.js","assets/index.7966e040.js","assets/index.ab34cb0f.css"]).then(n=>new n.AppWeb)});class u extends l{async show(e){return Promise.resolve()}async hide(e){return Promise.resolve()}async animate(e){return Promise.resolve()}async listenToAppState(e){const a=({isActive:o})=>{var s,r;e&&(o?(s=e.onResume)===null||s===void 0||s.call(e):(r=e.onSuspend)===null||r===void 0||r.call(e))};return p.addListener("appStateChange",a)}}export{u as SplashScreenBase};