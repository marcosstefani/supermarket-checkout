<script>
export default {
  name: "q-card",
  props: ['product'],
  methods: {
    updateBasket(product) {
      this.$emit('update-basket', product)
    }
  }
}
</script>

<template>
<div class="panel panel-card">
  <div class="panel-header text-center">
    <div class="panel-title h5 mt-10">{{ product.name }}</div>
  </div>
  <div class="panel-body">
    <div class="tile">
      <div class="tile-content text-center">
        <h3 class="text-success">{{ product.price.toFixed(2) }}<span>un</span></h3>
      </div>
    </div>
    <p>
      <button @click.prevent="updateBasket(product)" class="btn btn-primary btn-block">Add to basket</button>
    </p>
  </div>
</div>
</template>

<style lang="sass">
.panel.panel-card .panel-body
    .tile-content
      .text-success
        margin-bottom: 0px
        span
          font-size: 15px
    p
      margin-top: 1em
</style>