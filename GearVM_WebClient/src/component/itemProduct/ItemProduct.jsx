import React from 'react'
import classNames from 'classnames/bind'
import styles from './ItemProduct.modulo.scss'
import { Image } from 'antd';

const cx= classNames.bind(styles);
const ItemProduct = (props) => {
 
  return (
    <div className={cx('wrapItemProduct')}>
        <div  className={cx('imgProduct')}>
            <Image       
            src={props.laptop.img}
            />
        </div>   
        <div className={cx('textProduct')}>
            <h5>{props.laptop.name}</h5>
          <div className={cx('content_dis_price')}> 
            <h5 className={cx('Price')}>{props.laptop.price}đ</h5>
            <h5>{props.laptop.discount}%</h5>
          </div>
          <h5>Giá Thật</h5>
        </div>   
    </div>
  )
}

export default ItemProduct
