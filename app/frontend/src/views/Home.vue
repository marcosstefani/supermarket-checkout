<script>
import service from "@/services/login_service";
import QCard from "../components/QCard.vue";
import QNavBar from '../components/QNavBar.vue';

export default {
  name: "Home",
  components: {
    QNavBar,
    QCard
  },
  data: () => ({
    basket: [{"id": "Dwt5F7KAhi", "name": "Amazing Pizza!", "price": 19.99, "quantity": 2}, {"id": "PWWe3w1SDU", "name": "Amazing Burger!", "price": 9.99, "quantity": 1}],
    products: [{"id":"Dwt5F7KAhi","name":"Amazing Pizza!","price":1099},{"id":"PWWe3w1SDU","name":"Amazing Burger!","price":999},{"id":"C8GDyLrHJb","name":"Amazing Salad!","price":499},{"id":"4MB7UfpTQs","name":"Boring Fries!","price":199}]
  }),
  mounted() {
    this.verify()
  },
  methods: {
    async verify() {
      this.user = this.$route.query.identification
      try {
        await service.verify(this.user)
      } catch (error) {
        this.$router.push({
          path: "/",
        });
      }
    },
    basketStr() {
      return JSON.stringify(this.basket)
    },
    stringfy(obj) {
      return JSON.stringify(obj)
    }
  }
}
</script>

<template>
<div id="home">
  <q-nav-bar :basket="`${basketStr()}`" />
  <div class="cards">
    <div v-for="(product) in products" :key=product.id class="card">
      <q-card :product="`${stringfy(product)}`"/>
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