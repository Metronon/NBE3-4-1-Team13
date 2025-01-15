import React from 'react';
import './Navbar.css';
import Link from 'next/link';

const Navbar: React.FC = () => {
  return (
    <nav className="navbar">
      <a href="/" className="navbar-logo">
        <img src="/images/logo.png" alt="Logo" />
      </a>
      <ul className="navbar-menu">
        <li><Link href="/order">주문하기</Link></li>
      </ul>
    </nav>
  );
};

export default Navbar;