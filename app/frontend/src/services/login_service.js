import api from '@/api'

export default {
  async verify(username) {
    const {
      data
    } = await api
      .get(`/user/verify?user=${username}`, {}, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  },
  async create(username) {
    const {
      data
    } = await api
      .post(`/user/create?user=${username}`, {}, {
        headers: {
          'Authorization': 'Basic YWRtaW46YWRtaW4='
        }
      })
    return data
  }
}