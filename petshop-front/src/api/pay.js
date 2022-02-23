import { fetch } from '@/utils/request';


export function getMyAccount() {
  return fetch({
    url: `/getMyAccount`
  });
}

/**
 * pay
 * @param data { {price, petId} }
 * @returns {Promise<unknown>}
 */
export function payForPet(data) {
  return fetch({
    url: `/payForPet`,
    data
  });
}
