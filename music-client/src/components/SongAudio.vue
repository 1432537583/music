<template>
    <div class="song-audio">
        <audio ref="player"
            :src="url"
            controls = "controls"
            preload = "true"
            @canplay = "startPlay"
            @ended = "ended"
            @timeupdate = "timeupdate"
        ></audio>
    </div>
</template>

<script>
import { mapGetters } from 'vuex'
export default {
    name:'song-audio',
    computed:{
        ...mapGetters([
            'id',               //歌曲id
            'url',              //歌曲地址
            'isPlay',           //播放状态
            'listOfSongs',      //当前歌曲列表
            'curTime',          //当前音乐的播放位置
            'changeTime',       //指定播放时刻
            'autoNext',         //用于自动触发播放下一首
            'volume'            //音量
        ])
    },
    watch:{
        isPlay(){               //监听当前播放状态
            this.togglePlay();  
        },
        changeTime(){           //跳转到制定播放位置
            this.$refs.player.currentTime = this.changeTime;
        },
        volume(val){            //控制播放音量
            this.$refs.player.volume = val;
        }
    },
    methods:{
        startPlay(){            //获取链接后准备播放
            let player = this.$refs.player;
            this.$store.commit('setDuration',player.duration);
            player.play();      //开始播放
            this.$store.commit('setIsPlay',true);
        },
        ended(){                //播放完之后触发
            this.$store.commit('setIsPlay',false);
            this.$store.commit('setCurTime',0);
            this.$store.commit('setAutoNext',!this.autoNext);
        },
        togglePlay(){           //开始、暂停
            let player = this.$refs.player;
            if(this.isPlay){
                player.play();
            }else{
                player.pause();
            }
        },
        timeupdate(){           //音乐播放时记录播放当前位置
            this.$store.commit('setCurTime',this.$refs.player.currentTime);
        }
    }
}
</script>

<style>
    .song-audio {
        display: none;
    }
</style>