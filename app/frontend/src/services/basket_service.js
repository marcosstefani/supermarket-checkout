import api from '@/api'

export default {
  async sendProduct(username, product) {
    const {
      data
    } = await api
      .post(`/basket/product?user=${username}`, product, {
        headers: {}
      })
    return data
  },
  async findCheckout(username) {
    const {
      data
    } = await api
      .get(`/basket/checkout?user=${username}`, {}, {
        headers: {}
      })
    return data
  },
  async conclude(id) {
    const {
      data
    } = await api
      .put(`/basket/checkout?id=${id}`, {}, {
        headers: {}
      })
    return data
  },
  async history(username) {
    const {
      data
    } = await api
      .get(`/basket/closed?user=${username}`, {}, {
        headers: {}
      })
    return data
  }
}