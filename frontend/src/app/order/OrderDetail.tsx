"use client";

import React from "react";

interface Product {
    menuId: number;
    menuName: string;
    menuPrice: number;
    menuCount: number;
    image: string;
}

interface OrderDetailProps {
    filteredProducts: Product[]; // Product의 타입 명시로 결정
}

const OrderDetail = ({ filteredProducts } : OrderDetailProps) => {
    return (
        <div className="order-detail">
            {filteredProducts.length === 0 ? (
                <p className="product-notice">장바구니가 비어있습니다</p>
            ) : (
                filteredProducts.map((product) => (
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
                ))
            )}
        </div>
    );
};

export default OrderDetail;
