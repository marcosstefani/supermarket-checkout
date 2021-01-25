<script>
import service from "@/services/login_service";
import QNavBar from '../components/QNavBar.vue';

export default {
  name: "Home",
  components: {
    QNavBar
  },
  data: () => ({
    user: "",
    basket: [{"name": "Amazing Pizza!", "price": 19.99, "quantity": 2}, {"name": "Amazing Burger!", "price": 9.99, "quantity": 1}]
  }),
  mounted() {
    this.verify()
  },
  methods: {
    async verify() {
      this.user = this.$route.query.identification
      try {
        await service.verify(this.user)
      } catch (error) {
        this.$router.push({
          path: "/",
        });
      }
    }
  }
}
</script>

<template>
  <header class="navbar bg-secondary">
    <div class="navbar-section">
      <figure class="avatar avatar-xl mr-2">
        <img src="@/assets/img/logo.png" alt="Logo">
      </figure>
    </div>
    <div class="navbar-center">
      <h5 class="text-primary">Supermarket</h5>
    </div>
    <div class="navbar-section">
      <q-nav-bar />
    </div>
  </header>
</template>

<style lang="sass">
</style>