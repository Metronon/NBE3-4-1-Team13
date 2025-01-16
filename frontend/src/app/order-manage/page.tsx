import React, { useState } from "react";
import ClientPage from "./ClientPage";
import ManagePopup from "./ManagePopup";
import "./page.css";

export default function OrderManage() {
    const [showPopup, setShowPopup] = useState(false);
    const [showManagePopup, setShowManagePopup] = useState(false);
    const [email, setEmail] = useState("");

    const togglePopup = () => {
        setShowPopup(!showPopup);
    };

    const toggleManagePopup = () => {
        setShowManagePopup(!showManagePopup);
    };

    const handleEmailSubmit = (submittedEmail) => {
        setEmail(submittedEmail);
        toggleManagePopup();
    };

    return (
        <div>
            <button onClick={togglePopup}>주문 관리</button>
            {showPopup && (
                <ClientPage
                    onClose={togglePopup}
                    onSubmit={handleEmailSubmit}
                />
            )}
            {showManagePopup && (
                <ManagePopup onClose={toggleManagePopup} email={email} />
            )}
        </div>
    );
}
