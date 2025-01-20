"use client";

import React, { useState } from "react";
import "./Navbar.css";
import Link from "next/link";
import ClientPage from "../app/order-manage/ClientPage";
import ManagePopup from "../app/order-manage/ManagePopup";
import { cursorTo } from "readline";

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

  return (
    <>
      <nav className="navbar">
        <a href="/" className="navbar-logo">
          <img src="/images/logo.png" alt="Logo" />
        </a>
        <ul className="navbar-menu">
          <li style={{ cursor: "pointer" }}>
            <a onClick={togglePopup}>주문 조회</a>
          </li>
          <li style={{ cursor: "pointer" }}>
            <Link href="/order">주문</Link>
          </li>
        </ul>
      </nav>
      {showPopup && (
        <ClientPage onClose={togglePopup} onSubmit={handleEmailSubmit} />
      )}
      {showManagePopup && (
        <ManagePopup onClose={toggleManagePopup} email={email} />
      )}
    </>
  );
};

export default Navbar;
