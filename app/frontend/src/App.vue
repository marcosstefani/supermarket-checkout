<template>
  <div id="app">
    <section class="app-body">
      <router-view/>
    </section>
  </div>
</template>

<style lang="sass">
#app
  .app-body
    width: 75vw
    margin: 0 auto
    justify-content: center
    padding: 10px 20px

  .btn-lg .icon.icon-basket
    background: url('~@/assets/img/basket.svg') !important
</style>