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
                    <p className="order-item-name">{product.name}</p>
                    <span className="order-quantity">{product.quantity}</span>
                    <span className="order-price">
                        {(product.price * product.quantity).toLocaleString(
                            "en-US"
                        )}{" "}
                        원
                    </span>
                </div>
            ))}
        </div>
    );
};

export default OrderDetail;
