"use client";

import React, { useState } from "react";
import "./Navbar.css";
import Link from "next/link";
import ClientPage from "../app/order-manage/ClientPage";
import ManagePopup from "../app/order-manage/ManagePopup";

const Navbar: React.FC = () => {
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

    const handleOrderModify = (submittedEmail) => {
        setEmail(submittedEmail);
        console.log("주문 관리:", submittedEmail);
        togglePopup();
    };

    return (
        <>
            <nav className="navbar">
                <Link href="/" className="navbar-logo">
                    <img src="/images/logo.png" alt="Logo" />
                </Link>
                <ul className="navbar-menu">
                    <li>
                        <a onClick={togglePopup}>주문 관리</a>
                    </li>
                    <li>
                        <Link href="/order">주문</Link>
                    </li>
                </ul>
            </nav>
            {showPopup && (
                <ClientPage
                    onClose={togglePopup}
                    onSubmit={handleEmailSubmit}
                />
            )}
            {showManagePopup && (
                <ManagePopup onClose={toggleManagePopup} email={email} />
            )}
        </>
    );
};

export default Navbar;
