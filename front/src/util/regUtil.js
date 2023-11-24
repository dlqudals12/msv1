export const regUtil = (type) => {
  switch (type) {
    case "loginId":
      return /^[a-z]+[a-z0-9]{5,19}$/g;
    case "password":
      return /^(?=.*\d)(?=.*[a-zA-Z])[0-9a-zA-Z]{6,16}$/;
    case "phone":
      return /^01(?:0|1|[6-9])(?:\d{3}|\d{4})\d{4}$/;
    case "email":
      return /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i;
    default:
      return null;
  }
};
