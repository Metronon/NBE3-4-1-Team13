"use client";

import React from "react";

const ClientPage = () => {
    return (
        <div className="image-container">
            <div className="product">
                <img src="images/product_1.png" alt="커피콩1" />
                <div className="product-info">
                    <div className="product-name">커피콩1</div>
                    <p>구수한 맛입니다</p>
                </div>
            </div>
            <div className="product">
                <img src="images/product_2.png" alt="커피콩2" />
                <div className="product-info">
                    <div className="product-name">커피콩2</div>
                    <p>달큰한 맛입니다</p>
                </div>
            </div>
            <div className="product">
                <img src="images/product_3.png" alt="커피콩3" />
                <div className="product-info">
                    <div className="product-name">커피콩3</div>
                    <p>씁쓸한 맛입니다</p>
                </div>
            </div>
            <div className="product">
                <img src="images/product_4.png" alt="커피콩4" />
                <div className="product-info">
                    <div className="product-name">커피콩4</div>
                    <p>향만 좋습니다</p>
                </div>
            </div>
        </div>
    );
};

export default ClientPage;
