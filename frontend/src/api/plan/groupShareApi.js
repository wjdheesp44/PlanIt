import api from "../user/api";

export const groupShareApi = {
  // 그룹 참여자 목록 조회
  getGroupUsers: async (groupId) => {
    const response = await api.get(`/api/v1/groups/${groupId}/share/users`);
    return response.data;
  },

  // 유저를 그룹에 추가
  addUserToGroup: async (groupId, userId, role) => {
    const response = await api.post(`/api/v1/groups/${groupId}/share/users`, {
      userId,
      role, // 'EDITOR' 또는 'VIEWER'
    });
    return response.data;
  },

  // 참여자 권한 변경
  updateUserRole: async (groupId, groupUserId, role) => {
    const response = await api.patch(
      `/api/v1/groups/${groupId}/share/users/${groupUserId}/role`,
      null,
      {
        params: { role },
      }
    );
    return response.data;
  },

  // 참여자 제거
  removeUserFromGroup: async (groupId, groupUserId) => {
    const response = await api.delete(`/api/v1/groups/${groupId}/share/users/${groupUserId}`);
    return response.data;
  },

  // 공유 링크 생성
  createShareLink: async (groupId) => {
    const response = await api.post(`/api/v1/groups/${groupId}/share/link`);
    return response.data;
  },

  // 공유 링크 비활성화
  deactivateShareLink: async (groupId) => {
    const response = await api.delete(`/api/v1/groups/${groupId}/share/link`);
    return response.data;
  },

  // 공유 링크로 그룹 참여
  joinByShareLink: async (shareToken) => {
    const response = await api.post(`/api/v1/public/groups/join/${shareToken}`);
    return response.data;
  },
};
