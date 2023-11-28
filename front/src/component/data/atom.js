import { atom } from "jotai";

export const PaginationData = atom({
  total: 0,
  start: 0,
  maxPage: 0,
});

export const IsLogin = atom(false);
