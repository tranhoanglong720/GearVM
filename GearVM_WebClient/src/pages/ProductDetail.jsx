import React, { useContext } from "react";
import Header from "../component/Home/header/Header";
import Footer from "../component/Home/footer/Footer";
import ProductView from "../component/productDetail/ProductView";
import styles from "../sass/Productdetail.module.scss";
import classNames from "classnames/bind";
import Review from "../component/review/Review";
import itemReviewComment from "../component/itemReview/itemReview";
import ListProductHome from "../component/Home/listProductofHome/ListProductHome";
import { AppContext } from "../component/context/AppProvider";
import ChatBox from "../component/chatBox/ChatBox";
import ScrolltoTop from "../component/Home/ScrolltoTop/ScrolltoTop";
const cx = classNames.bind(styles);
const PageProductDetail = () => {
  const { showChat } = useContext(AppContext);

  return (
    <div className={cx("wrapProductDetail")}>
      <Header />
      <ProductView />
      {/* <Review></Review> */}
      <ListProductHome name="SẢN PHẨM BÁN CHẠY" />
      <ScrolltoTop />
      {showChat ? <ChatBox /> : null}
      <Footer />
    </div>
  );
};

export default PageProductDetail;
