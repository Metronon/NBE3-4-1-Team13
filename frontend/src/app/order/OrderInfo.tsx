"use client";

import React from "react";

const OrderInfo = ({
    email,
    setEmail,
    address,
    setAddress,
    postal,
    setPostal,
    totalPrice,
}) => {
    return (
        <div className="order-info">
            <h2>이메일</h2>
            <input
                type="email"
                placeholder="email을 입력해주세요"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
            />
            <h2>주소</h2>
            <input
                type="text"
                placeholder="주소를 입력해주세요"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
            />
            <h2>우편번호</h2>
            <input
                type="number"
                placeholder="우편번호를 입력해주세요"
                value={postal}
                onChange={(e) => setPostal(e.target.value)}
            />
            <div className="notice-msg">
                당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </div>
            <span className="order-total">총 금액 {totalPrice}원</span>
            <button type="button">결제하기</button>
        </div>
    );
};

export default OrderInfo;
