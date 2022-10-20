<template>
    <PlayGround v-if="$store.state.pk.status === 'playing'" />
    <MatchGround v-if="$store.state.pk.status === 'matching'" />
</template>

<script>
import PlayGround from "../../components/PlayGround.vue";
import { onMounted, onUnmounted } from 'vue';
import { useStore } from 'vuex';
import MatchGround from '../../components/MatchGround.vue';

export default {
    components: {
        PlayGround,
        MatchGround
    },
    setup() {
        const store = useStore();
        const socketUrl = `ws://localhost:8085/websocket/${store.state.user.token}/`;

        let socket = null;
        onMounted(() => {
            store.commit("updateOpponent", {
                username: "我的对手",
                photo: "https://cdn.acwing.com/media/article/image/2022/08/09/1_1db2488f17-anonymous.png"
            })
            socket = new WebSocket(socketUrl);
            // socket定义的几个函数
            socket.onopen = () => {
                console.log("connected!");
                store.commit("updateSocket", socket);
            }
            // 后端发送消息的时候，会调用这个函数
            socket.onmessage = msg => {
                const data = JSON.parse(msg.data);
                if (data.event === 'start_matching') { // 匹配成功
                    store.commit("updateOpponent", {
                        username: data.opponent_username,
                        photo: data.opponent_photo,
                    });
                    setTimeout(() => {
                        store.commit("updateStatus", "playing");
                    }, 2000);
                }
            }
            socket.onclose = () => {
                console.log("disconnected!");
            }
        });

        onUnmounted(() => {
            socket.close();
            store.commit("updateStatus", "matching");
        })
    }
}

</script>

<style scoped>

</style>