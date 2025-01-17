"use client";

import React, { useEffect, useState } from "react";
import "../globals.css";

const ManagePopup = ({ onClose, email }) => {
    const [orders, setOrders] = useState([]);

    /*useEffect(() => {
        const fetchOrders = async () => {
            try {
                const response = await fetch(`/orders?email=${email}`, {
                    method: "GET",
                    headers: {
                        "Content-Type": "application/json",
                    },
                });

                if (response.ok) {
                    const data = await response.json();
                    setOrders(data); // 주문 데이터 설정
                } else {
                    console.error("주문 요청 실패");
                }
            } catch (error) {
                console.error("주문 요청 중 오류 발생:", error);
            }
        };

        fetchOrders();
    }, [email]);*/

    const handleModify = async () => {
        // 수정 로직
        onClose();
    };

    const handleCancel = async () => {
        /*
        try {
            const response = await fetch(`/api/orders/`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email }), // 이메일을 포함한 요청
            });

            if (response.ok) {
                console.log("주문 취소 요청 성공:", email);
                // 추가적인 성공 처리 로직
            } else {
                console.error("주문 취소 요청 실패");
            }
        } catch (error) {
            console.error("주문 취소 중 오류 발생:", error);
        }
        */
        onClose();
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <button className="close-btn" onClick={onClose}>
                    X
                </button>
                <h1>이전 주문의 취소나 수정이 가능합니다</h1>
                <p className="email-text">이메일: {email}</p>
                <div className="order-list">
                    {orders.length > 0 ? (
                        orders.map((order) => (
                            <div key={order.orderId} className="order-item">
                                <span>주문 ID: {order.orderId}</span>
                                <span>
                                    총 금액: {order.menuPrice * order.count} 원
                                </span>
                            </div>
                        ))
                    ) : (
                        <p className="my-3">존재하는 주문이 없습니다.</p>
                    )}
                </div>
                <div className="button-container">
                    <button className="modify-button" onClick={handleModify}>
                        수정
                    </button>
                    <button className="delete-button" onClick={handleCancel}>
                        취소
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ManagePopup;
