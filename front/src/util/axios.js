import Axios from "axios";

export const kakaoAxios = Axios.create({
  baseURL: "https://kapi.kakao.com/v1/payment",
  timeout: 1000,
});

kakaoAxios.interceptors.request.use(
  (config) => {
    config.headers.Authorization = "KakaoAK cd3cb7ce209ba4523b488f2f1bfe012f";
    config.headers["Content-Type"] =
      "application/x-www-form-urlencoded;charset=utf-8";

    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);
