import { Link } from "react-router-dom";

export const Footer = () => {
  return (
    <>
      <div
        class="container"
        style={{ marginTop: "70px", marginBottom: "70px" }}
      >
        <div class="row">
          <div class="col-md-12 text-center d-md-flex align-items-center">
            <ul class="nav d-flex justify-content-center">
              <li class="nav-item">
                {" "}
                <Link class="nav-link active" to="/">
                  Home
                </Link>{" "}
              </li>
            </ul>{" "}
            <i class="d-block fa fa-stop-circle fa-3x mx-auto text-primary"></i>
            <p class="mb-0 py-1">©2023 All rights reserved</p>
          </div>
        </div>
      </div>
    </>
  );
};
