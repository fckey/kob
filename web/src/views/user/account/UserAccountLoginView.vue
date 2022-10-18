<template>
    <ContentField>
        <div class="row justify-content-md-center">
            <div class="col-3">
                <form @submit.prevent="login">
                    <div class="mb-3">
                        <label for="exampleFormControlInput1" class="form-label">用户名</label>
                        <input v-model="username" type="text" class="form-control" id="username" placeholder="请输入用户名">
                    </div>
                    <div class="mb-3">
                        <label for="exampleFormControlInput1" class="form-label">密码</label>
                        <input v-model="password" type="password" class="form-control" id="password"
                            placeholder="请输入密码">
                    </div>
                    <div class="error-message">{{error_message}}</div>
                    <div>
                        <button type="submit" class="btn btn-primary">登录</button>
                    </div>
                </form>
            </div>
        </div>
    </ContentField>
</template>
<script>
import ContentField from "../../../components/ContentField.vue";
import { useStore } from 'vuex';
import { ref } from 'vue';
import router from "../../../router/index"

export default {
    components: {
        ContentField
    },
    setup() {
        const store = useStore();
        let username = ref('');
        let password = ref('');
        let error_message = ref('');

        const login = () => {
            store.dispatch("login", {
                username: username.value,
                password: password.value,
                success() {
                    // 获取用户信息
                    store.dispatch("getinfo", {
                        success() {
                            router.push({ name: "home" });
                        }
                    })
                    router.push({ name: 'home' })
                },
                error() {
                    error_message.value = "用户名或密码错误";
                }
            })
        }

        return {
            username,
            password,
            error_message,
            login,
        }
    }
}
</script>
<style scoped>
button {
    width: 100%;
}

div.error-message {
    color: red;
}
</style>