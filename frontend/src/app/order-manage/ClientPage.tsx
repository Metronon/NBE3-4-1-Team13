"use client";

import React, { useState } from "react";
import "./page.css";

const ClientPage = ({ onClose, onSubmit }) => {
    const [email, setEmail] = useState("");

    const handleSubmit = (e) => {
        e.preventDefault();
        onSubmit(email); // 이메일 제출 시 부모 컴포넌트의 함수 호출
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <button className="close-btn" onClick={onClose}>
                    X
                </button>
                <h1>주문 조회</h1>
                <form onSubmit={handleSubmit} className="email-form">
                    <input
                        type="email"
                        placeholder="email을 입력해주세요"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <button type="submit">제출</button>
                </form>
            </div>
        </div>
    );
};

export default ClientPage;
