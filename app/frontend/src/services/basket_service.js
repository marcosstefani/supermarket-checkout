import api from '@/api'

export default {
  async sendProduct(username, product) {
    const {
      data
    } = await api
      .post(`/basket/product?user=${username}`, product, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  },
  async findCheckout(username) {
    const {
      data
    } = await api
      .get(`/basket/checkout?user=${username}`, {}, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  },
  async conclude(id) {
    const {
      data
    } = await api
      .put(`/basket/checkout?id=${id}`, {}, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  }
}