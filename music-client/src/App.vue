<template>
  <div id="app">
    <the-header/>    
    <router-view class="music-content"/>
    <song-audio/>
    <the-aside/>
    <play-bar/>
    <scroll-top/> 
    <the-footer/>
  </div>
</template>

<script>
import ScrollTop from './components/ScrollTop.vue'
import SongAudio from './components/SongAudio.vue'
import TheHeader from './components/TheHeader.vue'
import PlayBar from './components/PlayBar.vue'
import TheAside from './components/TheAside';
import TheFooter from './components/TheFooter';

export default {
  name: 'App',
  components: {
    TheHeader,
    ScrollTop,
    SongAudio,
    PlayBar,
    TheAside,
    TheFooter

  }
}
</script>

<style  lang="scss" scoped>
@import './assets/css/app.scss';
</style>
