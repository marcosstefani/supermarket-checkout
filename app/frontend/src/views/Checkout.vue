<script>
import basket_service from '@/services/basket_service'

export default {
  name: "Checkout",
  data: () => ({
    orderNumber: 0,
    contents: [],
    totals: [],
    identification: ''
  }),
  mounted() {
    this.identification = this.$route.query.identification
    basket_service.findCheckout(this.identification).then(response => {
      var quantity = 0
      for (let i = 0; i < response.products.length; i++) {
        const element = response.products[i];
        quantity += element.quantity
        if (element.quantity > 0) {
          this.contents.push(element.quantity + "x " + element.name)
        }
      }
      if (quantity == 0) {
        this.$router.push({
          path: "/",
        });
      } else {
        this.orderNumber = response.orderNumber
        this.totals.push({"name": "Raw Total:", "value": "£ " + response.price})
        this.totals.push({"name": "Total Promos:", "value": "£ " + response.discount})
        this.totals.push({"name": "Total Payable:", "value": "£ " + response.total}) 
      }
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
    })
  },
  methods: {
    login() {
      this.$router.push({
        path: "/",
      });
    },
    keepBuying() {
      var identification = this.identification
      this.$router.push({
        path: "/home",
        query: {
          identification,
        },
      });
    },
    async completeOrder() {
      if (this.orderNumber == 0) {
        this.login()
      }
      try {
        await basket_service.conclude(this.orderNumber)
        this.keepBuying()
      } catch (error) {
        console.log(error);
        this.login()
      }
    }
  }
}
</script>

<template>
<div class="panel p-centered col-3 resume-checkout">
  <h4 class="text-center text-primary">Checkout</h4>
  <div class="columns">
    <div class="column">
      <table class="table">
        <caption class="text-center bg-secondary"><h6>Basket Contents</h6></caption>
        <tbody>
          <tr v-for="(content, index) in this.contents" :key="index">
            <td>{{ content }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="divider-vert"></div>
    <div class="column">
      <table class="table">
        <caption class="text-center bg-secondary"><h6>Totals</h6></caption>
        <tbody>
          <tr v-for="(total, index) in this.totals" :key="index">
            <td>{{ total.name }}</td>
            <td class="text-right">{{ total.value }}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="column col-12 bottom-checkout">
      <div class="float-right">
        <button @click.prevent="keepBuying()" class="btn">Keep buying</button>
        <button @click.prevent="completeOrder()" class="btn btn-primary">Complete Order</button>
      </div>
    </div>
  </div>
</div>
</template>

<style lang="sass">
#app .app-body
  .resume-checkout
    min-width: 780px !important
    margin-top: 10vh
    padding: 16px
    table
      caption
        padding-top: 10px
    .bottom-checkout
      padding-top: 16px
      button.btn
        margin-left: 10px
</style>