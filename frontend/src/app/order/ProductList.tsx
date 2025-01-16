"use client";

import React from "react";

const ProductList = ({ products, updateCount }) => {
    return (
        <div className="product-list">
            {products.map((product) => (
                <div key={product.menuId} className="product-details">
                    <img
                        src={product.image}
                        alt={product.menuName}
                        className="product-img"
                    />
                    <div className="product-info">
                        <p className="product-category">{product.category}</p>
                        <h2 className="product-name">{product.menuName}</h2>
                        <p className="product-price">
                            {product.menuPrice.toLocaleString("en-US")} 원
                        </p>
                    </div>
                    <div className="quantity-control">
                        <button
                            className="decrease-button"
                            onClick={() => updateCount(product.menuId, -1)}
                            disabled={
                                product.count === 0
                            } /* 수량이 0일때 -버튼 비활성화 */
                        >
                            -
                        </button>
                        <span className="quantity-input">
                            {product.count}
                        </span>
                        <button
                            className="increase-button"
                            onClick={() => updateCount(product.menuId, 1)}
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
