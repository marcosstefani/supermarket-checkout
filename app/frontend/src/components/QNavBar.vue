<script>
export default {
  name: "q-nav-bar",
  props: ["basket", "quantity"],
  methods: {
    basketSize() {
      var quantity = 0
      for (let i = 0; i < this.basket.length; i++) {
        quantity += this.basket[i]['quantity']
      }
      return quantity
    },
    addToBasket(product) {
      product.quantity += 1
      this.$emit('update-basket', product)
    },
    subtractFromBasket(product) {
      product.quantity -= 1
      this.$emit('update-basket', product)
    }
  },
};
</script>

<template>
  <header class="navbar bg-secondary">
    <div class="navbar-section">
      <figure class="avatar avatar-xl mr-2">
        <img src="@/assets/img/logo.png" alt="Logo" />
      </figure>
    </div>
    <div class="navbar-center">
      <h5 class="text-primary">Supermarket</h5>
    </div>
    <div class="navbar-section">
      <div class="popover popover-bottom">
        <figure class="avatar mr-2 badge" :data-badge="`${basketSize()}`">
          <img src="@/assets/img/basket.svg" alt="Basket" />
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
              <div v-for="(product) in this.basket" :key=product.id>
                <div class="divider"></div>
                <div class="tile">
                  <div class="tile-content">
                    <div class="tile-title">{{ product.name }}</div>
                    <h3 class="text-success">{{ product.price }}<span>un</span></h3>
                  </div>
                  <div class="tile-action">
                    <h6>qtd: {{ product.quantity }}x</h6>
                    <button @click.prevent="addToBasket(product)" class="btn btn-action btn-sm"><h6>+</h6></button>
                    <button @click.prevent="subtractFromBasket(product)" class="btn btn-action btn-sm"><h6>-</h6></button>
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
    </div>
  </header>
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
    .tile .tile-content .text-success span
      font-size: 15px
</style>
