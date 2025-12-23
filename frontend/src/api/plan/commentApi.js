import api from "../user/api";

export const commentApi = {
  // 댓글 목록 조회
  getComments: async (groupId) => {
    const response = await api.get(`/api/v1/groups/${groupId}/comments`);
    return response.data;
  },

  // 댓글 추가
  addComment: async (groupId, content) => {
    const response = await api.post(`/api/v1/groups/${groupId}/comments`, {
      content,
    });
    return response.data;
  },

  // 댓글 삭제
  deleteComment: async (groupId, commentId) => {
    const response = await api.delete(`/api/v1/groups/${groupId}/comments/${commentId}`);
    return response.data;
  },
};
