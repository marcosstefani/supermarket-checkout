<script>
import login_service from "@/services/login_service"
import product_service from '@/services/product_service'
import basket_service from '@/services/basket_service'
import QCard from "../components/QCard.vue"
import QNavBar from '../components/QNavBar.vue'

export default {
  name: "Home",
  components: {
    QNavBar,
    QCard
  },
  data: () => ({
    basket: [],
    products: [],
    basketQuantity: 0,
    identification: ''
  }),
  mounted() {
    this.verify()
    product_service.all().then(response => {
      this.products = response
    })
    basket_service.startCheckout(this.identification).then(response => {
      for (let i = 0; i < response.products.length; i++) {
        const element = response.products[i];
        if (element.quantity > 0) {
          this.basket.push(element)
        }
      }
    })
  },
  methods: {
    async verify() {
      this.identification = this.$route.query.identification
      try {
        await login_service.verify(this.identification)
      } catch (error) {
        this.$router.push({
          path: "/",
        });
      }
    },
    findBasketProduct(id) {
      for (let i = 0; i < this.basket.length; i++) {
        const element = this.basket[i];
        if (element.id == id) {
          return {
            "id": element.id,
            "name": element.name,
            "price": element.price,
            "quantity": element.quantity
          }
        }
      }
      return null
    },
    async updateBasketProduct(obj) {
      for (let i = 0; i < this.basket.length; i++) {
        const element = this.basket[i];
        if (element.id == obj.id) {
          var response = await basket_service.sendProduct(this.identification, obj)
          this.basket[i] = response
          break
        }
      }
    },
    async removeBasketProduct(obj) {
      for (let i = 0; i < this.basket.length; i++) {
        const element = this.basket[i];
        if (element.id == obj.id) {
          await basket_service.sendProduct(this.identification, obj)
          this.basket.splice(i, 1)
        }
      }
    },
    async addBasketProduct(obj) {
      var quantity = 1
      if (obj.quantity != undefined) {
        quantity = obj.quantity
      }
      var product = {
        "id": obj.id,
        "name": obj.name,
        "price": obj.price,
        "quantity": quantity
      }
      var response = await basket_service.sendProduct(this.identification, product)
      this.basket.push(response)
    },
    updateBasket(product) {
      var obj = this.findBasketProduct(product.id)
      if (obj == null) {
        this.addBasketProduct(product)
      } else {
        if (product.quantity == undefined) {
          obj.quantity += 1
        } else {
          obj.quantity = product.quantity
        }
        if (obj.quantity == 0) {
          this.removeBasketProduct(obj)
        } else {
          this.updateBasketProduct(obj)
        }
      }
    }
  }
}
</script>

<template>
<div id="home">
  <q-nav-bar :basket="this.basket" :quantity="this.basketQuantity" :user=identification @update-basket="updateBasket"/>
  <div class="cards">
    <div v-for="product in products" :key=product.id class="card">
      <q-card :product="product" @update-basket="updateBasket"/>
    </div>
  </div>
</div>
</template>

<style lang="sass">
#home
  .cards
    display: flex
    flex-wrap: wrap
    justify-content: center
    padding-top: 50px
    .card
      width: 300px
      margin-right: 30px
      margin-bottom: 30px
</style>