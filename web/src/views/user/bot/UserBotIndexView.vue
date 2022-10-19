<template>
    <div class="container">
        <div class="row">
            <div class="col-3">
                <div class="card" style="margin-top: 20px">
                    <div class=" card-body">
                        <img :src="$store.state.user.photo" alt="" style="width: 100%;">
                    </div>
                </div>
            </div>

            <div class="col-9">
                <div class="card" style="margin-top: 20px;">
                    <div class="card-header">
                        <span style="font-size: 120%">我的bot</span>
                        <button type="button" class="btn btn-outline-primary float-end" data-bs-toggle="modal"
                            data-bs-target="#add-bot-btn">创建Bot</button>
                    </div>
                    <!-- Modal -->
                    <div class="modal fade modal-xl" id="add-bot-btn" tabindex="-1">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <div class="modal-header">
                                    <h5 class="modal-title">创建Bot</h5>
                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                        aria-label="Close"></button>
                                </div>
                                <div class="modal-body">
                                    <!-- 具体的表单 -->
                                    <div class="mb-3">
                                        <label for="add-bot-title" class="form-label">名称</label>
                                        <input v-model="botadd.title" type="text" class="form-control"
                                            id="add-bot-title" placeholder="请输入你要创建的bot名称">
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-remark" class="form-label">Bot的简介</label>
                                        <textarea v-model="botadd.remark" class="form-control" id="add-bot-remark"
                                            rows="2" placeholder="请输入Bot的简单介绍"></textarea>
                                    </div>
                                    <div class="mb-3">
                                        <label for="add-bot-code" class="form-label">代码</label>
                                        <textarea v-model="botadd.content" class="form-control" id="add-bot-code"
                                            rows="8" placeholder="请编写Bot的代码"></textarea>
                                    </div>
                                </div>
                                <div class="modal-footer">
                                    <div class="error-message">{{botadd.error_message}}</div>
                                    <button type="button" class="btn btn-primary" @click="add_bot">创建</button>
                                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">取消</button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="card-body">
                        <table class="table table-striped table-hover">
                            <thead>
                                <tr>
                                    <th>名称</th>
                                    <th>创建时间</th>
                                    <th>操作</th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr v-for="bot in bots" :key="bot.id">
                                    <td>{{bot.title}}</td>
                                    <td>{{bot.createTime}}</td>
                                    <td>
                                        <button type="button" class="btn btn-secondary" style="margin-right: 10px"
                                            data-bs-toggle="modal"
                                            :data-bs-target="'#update-bot-modal-' + bot.id">修改</button>
                                        <button type="button" class="btn btn-danger"
                                            @click="remove_bot(bot)">删除</button>
                                        <!-- 修改的模态框 -->
                                        <div class="modal fade modal-xl" :id="'update-bot-modal-' + bot.id"
                                            tabindex="-1">
                                            <div class="modal-dialog">
                                                <div class="modal-content">
                                                    <div class="modal-header">
                                                        <h5 class="modal-title">创建Bot</h5>
                                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <!-- 具体的表单 -->
                                                        <div class="mb-3">
                                                            <label for="add-bot-title" class="form-label">名称</label>
                                                            <input v-model="bot.title" type="text" class="form-control"
                                                                id="add-bot-title" placeholder="请输入你要创建的bot名称">
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-remark"
                                                                class="form-label">Bot的简介</label>
                                                            <textarea v-model="bot.remark" class="form-control"
                                                                id="add-bot-remark" rows="2"
                                                                placeholder="请输入Bot的简单介绍"></textarea>
                                                        </div>
                                                        <div class="mb-3">
                                                            <label for="add-bot-code" class="form-label">代码</label>
                                                            <textarea v-model="bot.content" class="form-control"
                                                                id="add-bot-code" rows="8"
                                                                placeholder="请编写Bot的代码"></textarea>
                                                        </div>
                                                    </div>
                                                    <div class="modal-footer">
                                                        <div class="error-message">{{bot.error_message}}</div>
                                                        <button type="button" class="btn btn-primary"
                                                            @click="update_bot(bot)">保存修改</button>
                                                        <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">取消</button>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>

                                    </td>
                                </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import { ref, reactive } from "vue"
import $ from 'jquery'
import { useStore } from "vuex"
import { Modal } from 'bootstrap/dist/js/bootstrap'
export default {
    setup() {
        const store = useStore();
        let bots = ref([]);

        const botadd = reactive({
            title: "",
            remark: "",
            content: "",
            error_message: "",
        });

        const refresh_bots = () => {
            $.ajax({
                url: "http://localhost:8085/user/bot/getlist",
                type: "get",
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    bots.value = resp;
                }
            });
        }
        refresh_bots();
        // 添加之后，向后端发送数据
        const add_bot = () => {
            botadd.error_message = "";
            $.ajax({
                url: "http://localhost:8085/user/bot/add",
                type: "post",
                data: {
                    title: botadd.title,
                    remark: botadd.remark,
                    content: botadd.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        // 清空添加之后的框框
                        botadd.title = "";
                        botadd.remark = "";
                        botadd.content = "";
                        Modal.getInstance("#add-bot-btn").hide();
                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }
        // 更新之后，向后端发送数据
        const update_bot = (bot) => {
            botadd.error_message = "";
            $.ajax({
                url: "http://localhost:8085/user/bot/update",
                type: "post",
                data: {
                    bot_id: bot.id,
                    title: bot.title,
                    remark: bot.remark,
                    content: bot.content,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        // 清空添加之后的框框
                        Modal.getInstance('#update-bot-modal-' + bot.id).hide();
                        refresh_bots();
                    } else {
                        botadd.error_message = resp.error_message;
                    }
                }
            });
        }
        // 删除功能
        const remove_bot = (bot) => {
            $.ajax({
                url: "http://localhost:8085/user/bot/remove",
                type: "post",
                data: {
                    bot_id: bot.id,
                },
                headers: {
                    Authorization: "Bearer " + store.state.user.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        refresh_bots();
                    }
                }
            });
        }

        return {
            bots,
            botadd,
            add_bot,
            update_bot,
            remove_bot,
        }
    }
}
</script>
<style scoped>
div.error-message {
    color: red;
}
</style>