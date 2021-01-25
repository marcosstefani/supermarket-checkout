import api from '@/api'

export default {
  async verify(username) {
    const {
      data
    } = await api
      .get(`/user/verify?user=${username}`, {}, {
        headers: {
          'Authorization': 'Basic c3VwZXI6MTc2ZWFjMjUtYWZmNC00NjVkLWI4M2MtMTQ2YjY0ODA1N2Y4' 
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
          'Authorization': 'Basic c3VwZXI6MTc2ZWFjMjUtYWZmNC00NjVkLWI4M2MtMTQ2YjY0ODA1N2Y4' 
        }
      })
    return data
  }
}