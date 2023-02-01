import React, { useEffect ,useContext} from "react";
import styles from "./Header.modulo.scss";
import classNames from "classnames/bind";   
import { Card, Image,Input } from 'antd';
import {UserOutlined,ShoppingCartOutlined } from "@ant-design/icons";
import { Link, useNavigate } from "react-router-dom";
import Nav from '../nav/Nav';
import Tippy from '@tippyjs/react/headless';
import MenuCard from "../../card/Card";
import MenuAcount from "../../MenuAcount/MenuAcount";
import Acount from "../../Custom/Acount/Acount";
// const {  AudioOutlined  } = icons;
const { Search } = Input;
const cx = classNames.bind(styles);
const Header = () => {

  document.addEventListener("scroll", () => {
    const top = document.documentElement.scrollTop;
    if (top != 0) {
      document.querySelector(".wrapnav_about").classList.add("sticky");
      // document.querySelector('.wrapnav_about').setAttribute('class','sticky')
    } else {
      document.querySelector(".wrapnav_about").classList.remove("sticky");
    }
  });
  const navigate = useNavigate();
  const handleShow = () => { navigate("/login", { replace: true });}
  const handleHome=() => { navigate("/", { replace: true });}
  return (

    

    <div className={cx("wrap")}>
      <div className={cx("commercial")}>
        <Image 
        src={require("../../../assets/commercial.png")}
        //  src={'https://bucketname.s3.ap-southeast-1.amazonaws.com/gearvm/80b58a0b-211c-43db-ad2b-f4cdbeb1c007.jpg'}
        />
      </div>
      <div className="wrapnav_about">
        <div className={cx("wrapNavbar")}>
          <div className={cx("navbar")}>
            <div className={cx("Logo")} onClick={handleHome}>
              <img src={require("../../../assets/logoGear.jpg")} 
              className={cx("Logo")}
              />
            </div>
            <div className={cx("SearchInput")}>
              <Search placeholder="input search text" enterButton />
            </div>
            <Tippy 
              interactive
              placement="top"
              render={attrs => (
              <div className="box" tabIndex="-1" {...attrs}>
                <MenuAcount/>
              </div>
            )}
            >
            <div className={cx("iconAccess")} onClick={handleShow}>
              {/* <UserOutlined style={{ fontSize: 30}} />
              <div className={cx("textAccess")}>
                <h5 className={cx("lblAccess")} >Đăng Nhập</h5>
                <h5 className={cx("lblAccess")}>Đăng Ký</h5>
              </div> */}
              <Acount />
            </div>
            </Tippy>
            <div className={cx("sale")}>
              <img
                src={require("../../../assets/iconSale.jpg")}
              // src={'https://bucketname.s3.ap-southeast-1.amazonaws.com/gearvm/80b58a0b-211c-43db-ad2b-f4cdbeb1c007.jpg'}
                className={cx("saleLogo")}
              />
              <h5 className={cx("lblAccess")}>Khuyến Mãi</h5>
            </div>
          
            <Tippy
              interactive
          
              placement="top-end"
              render={attrs => (
              <div className="box" tabIndex="-1" {...attrs}>
                <MenuCard />
                {/* <ItemCard /> */}
              </div>
            )}
            >      
            <div className={cx("cart")}>
              <ShoppingCartOutlined style={{ fontSize: 35 }} />
              <h5 className={cx("lblAccess")}>Giỏ hàng </h5>
            </div>                       
              </Tippy>
          
          </div>
          
          <Nav></Nav>
        </div>
      </div>
     
    </div>
  );
};

export default Header;
