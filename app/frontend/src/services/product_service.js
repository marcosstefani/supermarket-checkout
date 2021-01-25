import api from '@/api'

export default {
  async all() {
    const {
      data
    } = await api
      .get(`/products`, {}, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  }
}