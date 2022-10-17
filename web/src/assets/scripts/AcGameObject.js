const AC_GAME_OBJECTS = [];

export class ACGameObject {
    // 将所有对象放到我们的类中
    constructor() {
        AC_GAME_OBJECTS.push(this);
        this.timedelta = 0; // 这一帧距离上一帧的时间间隔
        this.has_called_start = false;
    }

    start() { // 只执行一次

    }

    update() { // 每一帧执行一次，但是除了第一次

    }

    on_destory() { // 删除

    }
    destory() {
        this.on_destory();


        for (let i in AC_GAME_OBJECTS) {
            const obj = AC_GAME_OBJECTS[i];
            if (obj == this) {
                AC_GAME_OBJECTS.splice(i);
                break;
            }
        }
    }

}

// 设置的是将每一帧的刷新都执行这个回调函数
let last_timestamp; // 上一次执行的时刻
const step = timestamp => {
    for (let obj of AC_GAME_OBJECTS) {
        // 如果是第一次执行，就执行start函数
        if (!obj.has_called_start) {
            obj.has_called_start = true;
            obj.start();
        } else {
            obj.timedelta = timestamp - last_timestamp;
            obj.update();
        }
    }
    last_timestamp = timestamp;
    requestAnimationFrame(step);
}
requestAnimationFrame(step);