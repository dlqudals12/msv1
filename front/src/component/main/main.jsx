import { Link } from "react-router-dom";
import { Header } from "../common/header";
import { useCookies } from "react-cookie";
import { Footer } from "../common/footer";

export const Main = () => {
  const [cookies, ,] = useCookies("userId");

  return (
    <>
      <Header />
      <div
        className="py-5 text-center text-white h-100 align-items-center d-flex"
        style={{
          backgroundImage:
            "url(https://static.pingendo.com/cover-bubble-dark.svg)",
          backgroundPosition: "center center, center center",
          backgroundSize: "cover, cover",
          backgroundRepeat: "repeat, repeat",
          minHeight: "820px",
        }}
      >
        <div className="container py-5">
          <div className="row">
            <div className="mx-auto col-lg-8 col-md-10">
              <h1 className="display-3 mb-4" style={{ color: "#3B3B3B" }}>
                MyVocaSearvice
              </h1>
              <p className="lead mb-5"></p>
              <div id="mainToAny">
                {!cookies.userId && (
                  <>
                    <Link to="/signup" className="btn btn-lg btn-primary mx-1">
                      Sign up
                    </Link>
                    <Link
                      className="btn btn-lg mx-1 btn-outline-primary"
                      to="/login"
                    >
                      Login
                    </Link>
                  </>
                )}
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
