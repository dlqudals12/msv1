export const ValidationMsg = (boolMsg, msg) => {
  return (
    <>
      {boolMsg && (
        <p
          style={{
            color: "red",
            textAlign: "left",
            marginTop: "3px",
            marginBottom: "0px",
            fontSize: "12px",
          }}
        >
          {msg}
        </p>
      )}
    </>
  );
};
