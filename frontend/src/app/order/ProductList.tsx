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
                        <p className="product-category">{product.menuType}</p>
                        <h2 className="product-name">{product.menuName}</h2>
                        <p className="product-price">
                            {product.menuPrice.toLocaleString("ko-kr")} 원
                        </p>
                    </div>
                    <div className="quantity-control">
                        <button
                            className="decrease-button"
                            onClick={() => updateCount(product.menuId, -1)}
                            disabled={product.count === 0}
                        >
                            -
                        </button>
                        <span className="quantity-input">{product.count}</span>
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
