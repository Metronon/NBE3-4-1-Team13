"use client";

import React from "react";

const OrderDetail = ({ filteredProducts }) => {
    return (
        <div className="order-detail">
            {filteredProducts.map((product) => (
                <div key={product.menuId} className="order-item">
                    <img
                        src={product.image}
                        alt={product.menuName}
                        className="product-img"
                    />
                    <p className="order-item-name">{product.menuName}</p>
                    <span className="order-quantity">{product.menuCount}</span>
                    <span className="order-price">
                        {(product.menuPrice * product.menuCount).toLocaleString("ko-kr")} 원
                    </span>
                </div>
            ))}
        </div>
    );
};

export default OrderDetail;
