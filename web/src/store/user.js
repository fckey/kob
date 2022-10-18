import $ from "jquery";

export default ({
    state: {
        id: "",
        username: "",
        photo: "",
        token: "",
        is_login: false, // 是否是登录状态
        pulling_info: true, // 是否正在拉取信息
    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.photo = user.photo;
            state.is_login = user.is_login;
        },
        updateToken(state, token) {
            state.token = token;
        },
        logout(state) {
            state.id = "";
            state.username = "";
            state.photo = "";
            state.token = "";
            state.is_login = false;
        },
        // 判断当前是否还是向远程拉取信息
        updatePullingInfo(state, pulling_info) {
            state.pulling_info = pulling_info
        }
    },
    actions: {
        login(context, data) {
            $.ajax({
                url: "http://localhost:8085/user/account/token/",
                type: "post",
                data: {
                    username: data.username,
                    password: data.password,
                },
                success(resp) {
                    if (resp.error_message === "success") {
                        localStorage.setItem("jwt_token", resp.token);
                        context.commit("updateToken", resp.token);
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    data.error(resp);
                }
            });
        },
        // 获取信息的逻辑
        getinfo(context, data) {
            $.ajax({
                url: "http://localhost:8085/user/account/info/",
                type: "get",
                headers: {
                    Authorization: "Bearer " + context.state.token,
                },
                success(resp) {
                    if (resp.error_message === 'success') {
                        context.commit("updateUser", {
                            // 解析resp
                            ...resp,
                            is_login: true,
                        });
                        data.success(resp);
                    } else {
                        data.error(resp);
                    }
                },
                error(resp) {
                    // 调用回到函数
                    data.error(resp);
                }
            });
        },
        // 处理的是退出的逻辑
        logout(context) {
            localStorage.removeItem("jwt_token");
            context.commit("logout")
        }
    },
    modules: {
    }
})
