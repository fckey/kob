import { ACGameObject } from "./AcGameObject";
import { Snake } from "./Snack";
import { Wall } from "./Wall";

export class GameMap extends ACGameObject {
    constructor(ctx, parent, store) {
        // ctx是当前的画布，parent是当前画布的父级元素
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.store = store;
        this.L = 0;
        this.rows = 25;
        this.cols = 26;

        // 内部随机的墙
        this.inner_wall_count = 60;
        // 定义所有的墙的数组
        this.walls = [];
        // 定义所有的蛇
        this.snakes = [
            // 定义第一条蛇
            new Snake({ id: 0, color: "#4876EC", r: this.rows - 2, c: 1 }, this),
            // 定义第二条蛇
            new Snake({ id: 1, color: "#F94848", r: 1, c: this.cols - 2 }, this)
        ];

    }

    create_walls() {
        const g = this.store.state.pk.gamemap;
        // 创建对象
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }
    }

    add_listening_events() {
        this.ctx.canvas.focus();

        const [snake0, snake1] = this.snakes;
        // 绑定键盘事件
        this.ctx.canvas.addEventListener("keydown", e => {
            if (e.key === 'w') snake0.set_direction(0);
            else if (e.key === 'd') snake0.set_direction(1);
            else if (e.key === 's') snake0.set_direction(2);
            else if (e.key === 'a') snake0.set_direction(3);
            else if (e.key === 'ArrowUp') snake1.set_direction(0);
            else if (e.key === 'ArrowRight') snake1.set_direction(1);
            else if (e.key === 'ArrowDown') snake1.set_direction(2);
            else if (e.key === 'ArrowLeft') snake1.set_direction(3);
            console.log(e.key)
        });
    }

    start() {
        this.create_walls();
        this.add_listening_events();
    }

    update_size() {
        // parseInt是为了无缝衔接
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        // 计算画布的长宽和高
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }

    // 验证是否右移动到下一步的必要  
    check_ready() {
        for (const snake of this.snakes) {
            if (snake.status !== "idle") return false;
            if (snake.direction === -1) return false;
        }
        return true;
    }
    // 让两条蛇都进行到下一步
    next_step() {
        for (const snake of this.snakes) {
            snake.next_step();
        }
    }
    // 检测目标位置是否合法
    check_valid(cell) {
        for (const wall of this.walls) {
            // 撞到墙了
            if (wall.r === cell.r && wall.c === cell.c) {
                return false;
            }
        }
        for (const snake of this.snakes) {
            let k = snake.cells.length;
            if (!snake.check_tail_increasing()) { // 当蛇尾前进的时候，蛇尾不要判断
                k--;
            }
            for (let i = 0; i < k; i++) {
                if (snake.cells[i].r === cell.r && snake.cells[i].c === cell.c) {
                    return false;
                }
            }

        }

        return true;
    }

    update() {
        this.update_size();
        // 表示可以让蛇进入到下一步
        if (this.check_ready()) {
            this.next_step();
        }
        this.render();
    }
    // 画的操作
    render() {
        const color_even = "#AAD751", color_odd = "#A2D149";
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if ((r + c) % 2 == 0) {
                    this.ctx.fillStyle = color_even;
                } else {
                    this.ctx.fillStyle = color_odd;
                }
                this.ctx.fillRect(c * this.L, r * this.L, this.L, this.L);
            }
        }
    }
}