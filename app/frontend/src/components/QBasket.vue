<script>
export default {
  name: "q-basket",
  props: {
      basket: String
  },
  methods: {
    basketList() {
      return JSON.parse(this.basket)
    },
    basketSize() {
      return Object.keys(this.basketList()).length
    }
  }
}
</script>

<template>
<div class="popover popover-bottom">
  <figure class="avatar mr-2 badge" :data-badge="`${basketSize()}`">
    <img src="@/assets/img/basket.svg" alt="Basket">
  </figure>
  <div class="popover-container">
    <div class="panel">
      <h5 class="panel-header text-center text-primary">
        Basket
      </h5>
      <div v-if="basketSize() == 0" class="panel-body">
        <div class="divider"></div>
        <h5 class="text-center">The basket is empty</h5>
      </div>
      <div v-if="basketSize() > 0" class="panel-body">
        <div v-for="(product) in basketList()" :key=product.id>
          <div class="divider"></div>
          <div class="tile">
            <div class="tile-content">
              <div class="tile-title">{{ product.name }}</div>
              <h3 class="text-success">{{ product.price }}<span>un</span></h3>
            </div>
            <div class="tile-action">
              <h6>qtd: {{ product.quantity }}x</h6>
              <button class="btn btn-action btn-sm"><h6>+</h6></button>
              <button class="btn btn-action btn-sm"><h6>-</h6></button>
            </div>
          </div>
        </div>
      </div>
      <div v-if="basketSize() > 0" class="panel-footer">
        <button class="btn btn-primary btn-block">Checkout</button>
      </div>
    </div>
  </div>
</div>
</template>

<style lang="sass">
#app .app-body
  .navbar
    figure
      background-color: $color-secondary
    h5
      padding-top: 10px
  
  .navbar .panel
    background-color: white !important
</style>