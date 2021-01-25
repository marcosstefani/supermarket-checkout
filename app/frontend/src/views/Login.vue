<script>
import service from "@/services/login_service";

export default {
  name: 'Home',
  data: () => ({
    notFound: false,
    checked: false,
  }),
  methods: {
    async login() {
      try {
        if (this.notFound == false) {
          console.log("notfound");
          this.user = this.$refs.username.value
          await service.verify(this.user)
        } else {
          console.log("found " + this.checked);
          this.notFound = false
          if (this.checked == true) {
            await service.create(this.user)
          }
        }
        var identification = this.user
        this.$router.push({
          path: "/home",
          query: {
            identification,
          },
        });
      } catch (error) {
        this.notFound = true
        this.checked = true
      }
    },
    select(numero) {
      this.selected = numero
    },
    isSelected(numero) {
      return this.selected == numero
    },
  },
}
</script>

<template>
<div class="panel p-centered col-3 min-400" style="margin-top: 10vh;">
  <div class="panel-header text-center">
    <figure class="avatar avatar-xl mr-2">
      <img src="@/assets/img/logo.png" alt="Logo">
    </figure>
    <div class="panel-title">Supermarket Challenge</div>
  </div>
  <form>
    <div class="panel-body">
      <div class="form-group">
        <input class="form-input" type="text" ref="username" placeholder="Indentification" autofocus="" autocomplete="off">
      </div>
      <div v-if="this.notFound" class="toast toast-error text-center">
        <div>Indentification not found.</div>
        <span>Click again the login button to register or correct the identification</span>
      </div>
    </div>
    <div class="panel-footer">
      <button @click.prevent="login()" class="btn btn-primary btn-block">Login</button>
    </div>
  </form>
</div>
</template>

<style lang="sass">
#app .app-body
  figure.avatar
    background-color: white
  .min-400
    min-width: 400px !important

  .toast span
    font-size: 11px
    font-weight: bold
</style>
