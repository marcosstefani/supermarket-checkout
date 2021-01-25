<script>
import QBasket from '../components/QBasket.vue';

export default {
  name: "q-nav-bar",
  components: {
    QBasket
  },
  props: {
      basket: String
  },
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
    <q-basket :basket="`${basket}`"/>
  </div>
</header>
</template>