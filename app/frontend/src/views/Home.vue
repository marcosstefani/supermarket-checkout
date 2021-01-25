<script>
import login_service from "@/services/login_service";
import product_service from '@/services/product_service'
import QCard from "../components/QCard.vue";
import QNavBar from '../components/QNavBar.vue';

export default {
  name: "Home",
  components: {
    QNavBar,
    QCard
  },
  data: () => ({
    basket: [],
    products: [],
    basketQuantity: 0 
  }),
  mounted() {
    this.verify()
    product_service.all().then(response => {
      this.products = response
    })
  },
  methods: {
    async verify() {
      this.user = this.$route.query.identification
      try {
        await login_service.verify(this.user)
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
    updateBasketProduct(obj) {
      for (let i = 0; i < this.basket.length; i++) {
        const element = this.basket[i];
        if (element.id == obj.id) {
          this.basket[i] = obj
          break
        }
      }
    },
    removeBasketProduct(id) {
      for (let i = 0; i < this.basket.length; i++) {
        const element = this.basket[i];
        if (element.id == id) {
          this.basket.splice(i, 1)
        }
      }
    },
    addBasketProduct(obj) {
      var quantity = 1
      if (obj.quantity != undefined) {
        quantity = obj.quantity
      }
      this.basket.push({
        "id": obj.id,
        "name": obj.name,
        "price": obj.price,
        "quantity": quantity
      })
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
          this.removeBasketProduct(obj.id)
        }
        this.updateBasketProduct(obj)
      }
    }
  }
}
</script>

<template>
<div id="home">
  <q-nav-bar :basket="this.basket" :quantity="this.basketQuantity" @update-basket="updateBasket"/>
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