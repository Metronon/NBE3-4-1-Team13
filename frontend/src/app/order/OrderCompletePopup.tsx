"use client";

import React from "react";
import "../globals.css";
import { useRouter } from "next/navigation";

interface OrderCompletePopupProp {
    onClose: () => void;
}

const OrderCompletePopup = ({ onClose }: OrderCompletePopupProp) => {
    const router = useRouter();

    const handleConfirm = () => {
        onClose();
        router.replace("/");
    };

    return (
        <div className="popup">
            <div className="popup-inner">
                <h1>주문이 완료되었습니다!</h1>
                <div className="button-container">
                    <button className="confirm-button" onClick={handleConfirm}>
                        확인
                    </button>
                </div>
            </div>
        </div>
    );
};

export default OrderCompletePopup; 