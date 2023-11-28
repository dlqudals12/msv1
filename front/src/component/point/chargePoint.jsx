import { Footer } from "../common/footer";
import { Header } from "../common/header";

export const ChargePoint = () => {
  return (
    <>
      <Header />
      <div className="py-5">
        <div className="container">
          <div className="row">
            <div className="text-center col-md-12">
              <h1>Exchange</h1>
            </div>
          </div>
          <div className="row">
            <div className="col-lg-12 p-3">
              <div className="card bg-light">
                <div className="card-body p-4">
                  <div className="row">
                    <div className="col-8">
                      <h3 className="mb-0">ExchangeMoney</h3>
                    </div>
                  </div>
                  <br />
                  <div className="pl-3">
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="5000"
                      />
                      <span>5,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="10000"
                      />
                      <span>10,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="15000"
                      />
                      <span>15,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="20000"
                      />
                      <span>20,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="25000"
                      />
                      <span>25,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="30000"
                      />
                      <span>30,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="35000"
                      />
                      <span>35,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="40000"
                      />
                      <span>40,000원</span>
                    </label>
                    <label className="btn btn-secondary">
                      <input
                        className="btn-check"
                        type="radio"
                        name="money"
                        value="50000"
                      />
                      <span>50,000원</span>
                    </label>
                    <p style={{ color: "#ac2925", marginTop: "30px" }}>
                      카카오페이의 최소 충전금액은 5,000원이며 <br />
                      최대 충전금액은 50,000원 입니다.
                    </p>

                    <button className="btn btn-primary mt-3" type="submit">
                      충 전 하 기
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
};
