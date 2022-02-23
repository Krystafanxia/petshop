import { fetch } from '@/utils/request';
import Const from "@/utils/Const";

export const uploadUrl = process.env.VUE_APP_BASE_API + '/upload';
export const downloadUrl = process.env.VUE_APP_BASE_API + '/download';

export function delPetFile(id) {
  return fetch({
    url: `/delPetFile?fileid=${id}`,
  });
}

export function getPetFiles(id) {
  return fetch({
    url: `/getPetFiles?id=${id}`,
  });
}

export function getAvatar(id) {
  return fetch({
    url: `/getPetFiles?id=${id}&type=${Const.FILE_TYPE.AVATAR}`,
  });
}
