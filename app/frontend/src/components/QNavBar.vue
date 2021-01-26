<script>
export default {
  name: "q-nav-bar",
  props: ["basket", "quantity", 'user'],
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
    },
    logout() {
      this.$router.push({
        path: "/",
      });
    },
    checkout() {
      var identification = this.user
      this.$router.push({
        path: "/checkout",
        query: {
          identification,
        },
      });
    },
    history() {
      var identification = this.user
      this.$router.push({
        path: "/history",
        query: {
          identification,
        },
      });
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
                    <h3 class="text-success"><h6>£ </h6>{{ product.total.toFixed(2) }}  
                      <span v-if="product.total.toFixed(2) != product.price.toFixed(2)"><s><h6>£ </h6>{{ product.price.toFixed(2) }}</s></span>
                    </h3>
                  </div>
                  <div class="tile-action">
                    <h6>qtd: {{ product.quantity }}</h6>
                    <button @click.prevent="addToBasket(product)" class="btn btn-action btn-primary btn-sm"><h6 class="icon-center">+</h6></button>
                    <button @click.prevent="subtractFromBasket(product)" class="btn btn-action btn-primary btn-sm"><h6 class="icon-center">-</h6></button>
                  </div>
                </div>
              </div>
            </div>
            <div v-if="basketSize() > 0" class="panel-footer">
              <button @click.prevent="checkout()" class="btn btn-primary btn-block">Checkout</button>
            </div>
          </div>
        </div>
      </div>
      <div class="popover popover-bottom menu-user">
        <button class="chip btn btn-primary">User: {{ this.user }}</button>
        <div class="popover-container">
          <ul class="menu">
            <li class="menu-item">
              <a @click.prevent="history()" href="">Closed Orders</a>
              <a @click.prevent="logout()" class="text-error">Logout</a>
            </li>
          </ul>
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
    
    .menu-user
      padding: .25rem 1rem

      .popover-container
        width: 196px

  .navbar .panel
    background-color: white !important

    .panel-header.text-center
      padding: 0px
      margin-top: 15px

    .tile .tile-content .text-success
      span
        font-size: 18px
        color: gray
        h6
          font-size: 11px
      h6
        display: inline

    .tile-action button
      margin-right: 3px
      .icon-center
        padding-top: 3px !important
</style>
