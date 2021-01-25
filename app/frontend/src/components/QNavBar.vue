<script>
export default {
    name: "q-navbar",
    props: {
        basket: Array
    }
}
</script>

<template>
<div class="popover popover-bottom">
  <figure class="avatar mr-2 badge" data-badge="8">
    <img src="@/assets/img/basket.svg" alt="Basket">
  </figure>
  <div class="popover-container">
    <div class="panel">
      <h5 class="panel-header text-center text-primary">
        Basket
      </h5>
      <div class="panel-body">
        <div class="divider"></div>
        <div class="tile">
          <div class="tile-content">
            <div class="tile-title">Product XYZ</div>
            <h3 class="text-success">19.90<span>un</span></h3>
          </div>
          <div class="tile-action">
            <h6>qtd: 22x</h6>
            <button class="btn btn-action btn-sm"><h6>+</h6></button>
            <button class="btn btn-action btn-sm"><h6>-</h6></button>
          </div>
        </div>
        <div class="divider"></div>
        <div class="tile">
          <div class="tile-content">
            <div class="tile-title">Product XYZ</div>
            <h3 class="text-success">19.90</h3>
          </div>
          <div class="tile-action">
            <h6>qtd: 2x</h6>
            <button class="btn btn-action btn-sm"><h6>+</h6></button>
            <button class="btn btn-action btn-sm"><h6>-</h6></button>
          </div>
        </div>
        <div class="divider"></div>
        <div class="tile">
          <div class="tile-content">
            <div class="tile-title">Product XYZ</div>
            <h3 class="text-success">19.90</h3>
          </div>
          <div class="tile-action">
            <h6>qtd: 2x</h6>
            <button class="btn btn-action btn-sm"><h6>+</h6></button>
            <button class="btn btn-action btn-sm"><h6>-</h6></button>
          </div>
        </div>
        <div class="divider"></div>
        <div class="tile">
          <div class="tile-content">
            <div class="tile-title">Product XYZ</div>
            <h3 class="text-success">19.90</h3>
          </div>
          <div class="tile-action">
            <h6>qtd: 2x</h6>
            <button class="btn btn-action btn-sm"><h6>+</h6></button>
            <button class="btn btn-action btn-sm"><h6>-</h6></button>
          </div>
        </div>
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
    .tile-action button h6
      padding-top: 3px
</style>