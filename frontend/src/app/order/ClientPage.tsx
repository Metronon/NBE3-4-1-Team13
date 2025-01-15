"use client";

import React, { useState } from 'react';

interface ClientPageProps {
    value: number;
    setValue: (newValue: number) => void;
}

const ClientPage = () => {
    const [value, setValue] = useState(0); // 상품 수량 시작값 0

    return (
        <div className="container">
            <div className="product-list">
                <div className="product-details">
                {/* 상품 목록 및 상품 수량 추가, 제거 */}
                <img src="images/product_1.png" alt='커피콩1' className="product-img"/>
                <div className="product-info">
                    <h2>커피콩1</h2>
                    <div className="quntity-controller">
                        <button className="decrease-button"
                        onClick={() => setValue(value - 1)}
                        disabled={ value === 0 }>-</button>
                        <span className="quntity-input">{value}</span>
                        <button className="increase-button" onClick={() => setValue(value + 1)}>+</button>
                    </div>
                </div>
            </div>
        </div>
            <div className="order-list">
                {/* 선택 상품 명세내역, 총금액 안내 */}
            </div>
        </div>
    );
};

export default ClientPage;