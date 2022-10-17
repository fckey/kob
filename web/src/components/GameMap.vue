<template>
    <div ref="parent" class="gamemap">
        <!-- 实现一个canvas -->
        <canvas ref="canvas"></canvas>
    </div>
</template>

<script>
import { GameMap } from "../assets/scripts/GameMap"
import { ref, onMounted } from 'vue'

export default {
    setup() {
        let parent = ref(null);
        let canvas = ref(null);
        // 当我们组建挂载完之后，要执行哪些操作
        onMounted(() => {
            new GameMap(canvas.value.getContext('2d'), parent.value)
        });

        return {
            parent,
            canvas
        }
    }
}
</script>

<style scoped>
div.gamemap {
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
}
</style>