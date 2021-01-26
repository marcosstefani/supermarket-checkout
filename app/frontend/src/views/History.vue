<script>
import basket_service from '@/services/basket_service'

export default {
  name: 'History',
  data: () => ({
    identification: '',
    orders: []
  }),
  mounted() {
    this.identification = this.$route.query.identification
    basket_service.history(this.identification).then(response => {
      this.orders = response
    }).catch(error => {
      console.log(error);
      if (this.identification == '') {
        this.$router.push({
          path: "/",
        });
      } else {
        var identification = this.identification
        this.$router.push({
          path: "/home",
          query: {
            identification,
          },
        });
      }
    });
  },
  methods: {
    keepBuying() {
      var identification = this.identification
      this.$router.push({
        path: "/home",
        query: {
          identification,
        },
      });
    },
    formatDate(date) {
      return date.substr(8, 2) + "/" + date.substr(5, 2) + "/" + date.substr(0, 4)
    }
  }
}
</script>

<template>
<div id="history">
  <h3 class="text-center history-title">Concluded Orders</h3>
  <div class="p-centered col-3 resume-history">
    <div class="float-right">
      <button @click.prevent="keepBuying()" class="btn">Keep buying</button>
    </div>
  </div>
  <div v-for="order in this.orders" :key="order.orderNumber" class="panel p-centered col-3 resume-history">
    <h4 class="text-center text-primary">Order {{ order.orderNumber }}</h4>
    <h6 class="text-center">{{ this.formatDate(order.checkoutAt) }}</h6>
    <div class="columns">
      <div class="column">
        <table class="table">
          <caption class="text-center bg-secondary"><h6>Basket Contents</h6></caption>
          <tbody>
            <tr v-for="product in order.products" :key="product.id">
              <td>{{ product.quantity }}x {{ product.name }}</td>
            </tr>
          </tbody>
        </table>
      </div>
      <div class="divider-vert"></div>
      <div class="column">
        <table class="table">
          <caption class="text-center bg-secondary"><h6>Totals</h6></caption>
          <tbody>
            <tr>
              <td>Raw Total:</td>
              <td class="text-right">£ {{ order.price }}</td>
            </tr>
            <tr>
              <td>Total Promos:</td>
              <td class="text-right">£ {{ order.discount }}</td>
            </tr>
            <tr>
              <td>Total Payable:</td>
              <td class="text-right">£ {{ order.total }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</div>
</template>

<style lang="sass">
#app #history
  .history-title
    margin-top: 10vh
  
  .bottom-history
    padding-top: 16px
    table
      caption
        padding-top: 10px
      button.btn
        margin-left: 10px
  
  .resume-history
    min-width: 780px !important
    margin-top: 30px
    padding: 16px
</style>