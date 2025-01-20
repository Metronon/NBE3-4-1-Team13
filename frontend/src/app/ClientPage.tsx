"use client";

import React from "react";
import "./globals.css";

const ClientPage = () => {
    return (
        <div className="image-container">
            <div className="product">
                <img className="main-image" src="images/커피콩_2.png" alt="Blue mountain" />
                <div className="product-info">
                    <div className="product-name">Blue mountain</div>
                    <p>구수한 맛이 자랑인 원두입니다.</p>
                </div>
            </div>
            <div className="product">
                <img className="main-image" src="images/커피콩_4.png" alt="Cafferio" />
                <div className="product-info">
                    <div className="product-name">Cafferio</div>
                    <p>씁쓸한 맛을 좋아하시는 분들을 위한 원두입니다.</p>
                </div>
            </div>
            <div className="product">
                <img className="main-image" src="images/커피_2.png" alt="caffè americano" />
                <div className="product-info">
                    <div className="product-name">caffè americano</div>
                    <p>본연의 맛을 원하시는 분들에게 추천합니다.</p>
                </div>
            </div>
            <div className="product">
                <img className="main-image" src="images/커피_1.png" alt="caffè latte" />
                <div className="product-info">
                    <div className="product-name">caffè latte</div>
                    <p>최고급 우유를 첨가해 부드러운 맛이 장점입니다.</p>
                </div>
            </div>
        </div>
    );
};

export default ClientPage;
