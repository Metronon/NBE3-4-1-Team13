"use client";

import React from "react";
import "./page.css";

const ManagePopup = ({ onClose, email }) => {
    const handleCancel = () => {
        console.log("주문 취소됨:", email);
        // 주문 취소 로직 추가예정
        onClose();
    };

    const handleModify = () => {
        console.log("주문 수정됨:", email);
        // 주문 수정 로직 추가예정
        onClose();
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <h1>이전 주문의 취소나 수정이 가능합니다</h1>
                <p className="email-text">이메일: {email}</p>
                <div className="button-container">
                    <button className="cancel-button" onClick={handleCancel}>
                        취소
                    </button>
                    <button className="confirm-button" onClick={handleModify}>
                        수정
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ManagePopup;
