import { ACGameObject } from "./AcGameObject";
import { Wall } from "./Wall";

export class GameMap extends ACGameObject {
    constructor(ctx, parent) {
        // ctx是当前的画布，parent是当前画布的父级元素
        super();
        this.ctx = ctx;
        this.parent = parent;
        this.L = 0;

        this.rows = 20;
        this.cols = 20;

        // 内部随机的墙
        this.inner_wall_count = 20;
        // 定义所有的墙的数组
        this.walls = [];
    }

    // 判断右上和左下的点一定是连通的
    check_connectivity(g, sx, sy, tx, ty) {
        if (sx == tx && sy == ty) return true;
        g[sx][sy] = true;

        let dx = [-1, 0, 1, 0], dy = [0, 1, 0, -1];
        // 定义四个方向
        for (let i = 0; i < 4; i++) {
            let x = sx + dx[i], y = sy + dy[i];
            if (!g[x][y] && this.check_connectivity(g, x, y, tx, ty)) {
                return true;
            }
        }
        return false;
    }


    create_walls() {
        // 定义一个数组，记录当前是否是墙
        const g = [];
        for (let r = 0; r < this.rows; r++) {
            g[r] = [];
            for (let c = 0; c < this.cols; c++) {
                g[r][c] = false;
            }
        }

        // 给四周加上障碍物
        for (let r = 0; r < this.rows; r++) {
            g[r][0] = g[r][this.cols - 1] = true;
        }
        for (let c = 0; c < this.cols; c++) {
            g[0][c] = g[this.rows - 1][c] = true;
        }
        // 创建随机障碍物
        for (let i = 0; i < this.inner_wall_count / 2; i++) {
            for (let j = 0; j < 1000; j++) {
                let r = parseInt(Math.random() * this.rows);
                let c = parseInt(Math.random() * this.cols);
                // 如果已经有障碍物，在随机
                if (g[r][c] || g[c][r]) {
                    continue;
                }
                if (r == this.rows - 2 && c == 1 || r == 1 && c == this.cols - 2) {
                    continue;
                }
                // 对称的放
                g[r][c] = g[c][r] = true;
                break;
            }
        }
        // 判断是否一定是连通的
        const copy_g = JSON.parse(JSON.stringify(g));
        if (!this.check_connectivity(copy_g, this.rows - 2, 1, 1, this.cols - 2)) {
            return false;
        }
        // 创建对象
        for (let r = 0; r < this.rows; r++) {
            for (let c = 0; c < this.cols; c++) {
                if (g[r][c]) {
                    this.walls.push(new Wall(r, c, this));
                }
            }
        }

        return true;
    }

    start() {
        // 创建墙
        for (let i = 0; i < 1000; i++) {
            if (this.create_walls()) {
                break;
            }
        }
    }

    update_size() {
        // parseInt是为了无缝衔接
        this.L = parseInt(Math.min(this.parent.clientWidth / this.cols, this.parent.clientHeight / this.rows));
        // 计算画布的长宽和高
        this.ctx.canvas.width = this.L * this.cols;
        this.ctx.canvas.height = this.L * this.rows;
    }
    update() {
        this.update_size();
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