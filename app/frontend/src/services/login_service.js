import api from '@/api'

export default {
  async verify(username) {
    const {
      data
    } = await api
      .get(`/user/verify?user=${username}`, {}, {
        headers: {}
      })
    return data
  },
  async create(username) {
    const {
      data
    } = await api
      .post(`/user/create?user=${username}`, {}, {
        headers: {}
      })
    return data
  }
}