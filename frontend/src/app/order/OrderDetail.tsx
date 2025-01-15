"use client";

import React from "react";

const OrderDetail = ({ filteredProducts }) => {
    return (
        <div className="order-detail">
            {filteredProducts.map((product) => (
                <div key={product.id} className="order-item">
                    <img
                        src={product.image}
                        alt={product.name}
                        className="product-img"
                    />
                    <div className="order-item-info">
                        <p>{product.name}</p>
                        <span className="order-price">
                            {product.price * product.quantity}원
                        </span>
                        <span className="order-quantity">
                            {product.quantity}
                        </span>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default OrderDetail;
