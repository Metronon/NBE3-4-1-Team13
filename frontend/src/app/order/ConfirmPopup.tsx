"use client";

import React from "react";
import "./page.css";
import "../globals.css";

const ConfirmPopup = ({ onClose, onConfirm }) => {
    return (
        <div className="popup">
            <div className="popup-inner">
                <h1>결제를 진행하시겠습니까?</h1>
                <div className="button-container">
                    <button className="confirm-button" onClick={onConfirm}>
                        네
                    </button>
                    <button className="cancel-button" onClick={onClose}>
                        아니오
                    </button>
                </div>
            </div>
        </div>
    );
};

export default ConfirmPopup;
