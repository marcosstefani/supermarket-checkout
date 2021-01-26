import api from '@/api'

export default {
  async all() {
    const {
      data
    } = await api
      .get(`/products`, {}, {
        headers: {}
      })
    return data
  }
}