"use client";

import React from "react";

const ProductList = ({ products, updateQuantity }) => {
    return (
        <div className="product-list">
            {products.map((product) => (
                <div key={product.id} className="product-details">
                    <img
                        src={product.image}
                        alt={product.name}
                        className="product-img"
                    />
                    <div className="product-info">
                        <p className="product-category">{product.category}</p>
                        <h2 className="product-name">{product.name}</h2>
                        <p className="product-price">
                            {product.price.toLocaleString("en-US")} 원
                        </p>
                    </div>
                    <div className="quantity-control">
                        <button
                            className="decrease-button"
                            onClick={() => updateQuantity(product.id, -1)}
                            disabled={
                                product.quantity === 0
                            } /* 수량이 0일때 -버튼 비활성화 */
                        >
                            -
                        </button>
                        <span className="quantity-input">
                            {product.quantity}
                        </span>
                        <button
                            className="increase-button"
                            onClick={() => updateQuantity(product.id, 1)}
                        >
                            +
                        </button>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default ProductList;
