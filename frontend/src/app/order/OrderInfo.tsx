"use client";

import React, { useState } from "react";
import "./page.css";

// 매개 변수 타입 명시
interface OrderInfoProps {
    email: string;
    setEmail: (email: string) => void;
    address: string;
    setAddress: (address: string) => void;
    postalCode: string;
    setPostalCode: (postalCode: string) => void;
    totalPrice: number;
    onConfirm: () => void;
}

const OrderInfo= ({
    email,
    setEmail,
    address,
    setAddress,
    postalCode,
    setPostalCode,
    totalPrice,
    onConfirm,
} : OrderInfoProps) => {
    const [emailError, setEmailError] = useState("");
    const [addressError, setAddressError] = useState("");
    const [postalCodeError, setPostalCodeError] = useState("");

    const handlePayment = () => {
        setEmailError("");
        setAddressError("");
        setPostalCodeError("");

        // 이메일란 정규표현식 및 공백 확인
        const emailPattern = /^[\w\.-]+@[a-zA-Z\d\.-]+\.[a-zA-Z]{2,}$/;
        if (!emailPattern.test(email) || !email) {
            setEmailError("올바른 이메일 주소를 작성해주세요");
            return;
        }

        // 주소란 공백 확인
        if (!address) {
            setAddressError("주소를 작성해주세요");
            return;
        }

        // 우편번호란 공백 확인
        if (!postalCode) {
            setPostalCodeError("우편번호를 작성해주세요");
            return;
        }

        // 우편번호란 자리수 확인
        const postalCodePattern = /^\d{5}$/;
        if (!postalCodePattern.test(postalCode)) {
            setPostalCodeError("우편번호는 5자리 숫자여야 합니다.");
            return;
        }

        // 총 금액이 0원이 아닌지 확인
        if (totalPrice <= 0) {
            alert("상품을 최소 1개 이상 선택해야 합니다.");
            return;
        }

        // 조건을 만족하면 결제 진행
        onConfirm();
    };

    return (
        <div className="order-info">
            <h2>이메일</h2>
            {emailError && <p className="error-message">{emailError}</p>}
            <input
                type="email"
                placeholder="email을 입력해주세요"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
            />

            <h2>주소</h2>
            {addressError && <p className="error-message">{addressError}</p>}
            <input
                type="text"
                placeholder="주소를 입력해주세요"
                value={address}
                onChange={(e) => setAddress(e.target.value)}
                required
            />

            <h2>우편번호</h2>
            {postalCodeError && (
                <p className="error-message">{postalCodeError}</p>
            )}
            <input
                type="string"
                placeholder="우편번호를 입력해주세요"
                value={postalCode}
                onChange={(e) => setPostalCode(e.target.value)}
                required
            />

            <div className="notice-msg">
                당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
            </div>
            <span className="order-total">
                총 금액 {totalPrice.toLocaleString("en-US")}원
            </span>
            <button type="button" onClick={handlePayment}>
                결제하기
            </button>
        </div>
    );
};

export default OrderInfo;
