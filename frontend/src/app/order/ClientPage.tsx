"use client";

import React, { useMemo, useState } from "react";

const ClientPage = () => {
    // 현재 4개의 상품 객체 배열로 관리 (상품 추가시 배열 추가)
    const [products, setProducts] = useState([
        {
            id: 1,
            name: "커피콩1",
            image: "images/product_1.png",
            price: 5000,
            quantity: 0,
            category: "커피콩",
        },
        {
            id: 2,
            name: "커피콩2",
            image: "images/product_2.png",
            price: 6000,
            quantity: 0,
            category: "커피콩",
        },
        {
            id: 3,
            name: "커피콩3",
            image: "images/product_3.png",
            price: 7000,
            quantity: 0,
            category: "커피콩",
        },
        {
            id: 4,
            name: "커피콩4",
            image: "images/product_4.png",
            price: 8000,
            quantity: 0,
            category: "커피콩",
        },
    ]);

    // 상품 객체 배열에 있는 상품 배열
    const updateQuantity = (id, delta) => {
        setProducts(
            products.map((product) =>
                product.id === id
                    ? { ...product, quantity: product.quantity + delta }
                    : product
            )
        );
    };

    // email, address, postal 관리 상태 훅
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [postal, setPostal] = useState("");

    // 총 금액 계산
    const totalPrice = useMemo(
        () =>
            products.reduce(
                (sum, product) => sum + product.price * product.quantity,
                0
            ),
        [products]
    );

    // order-detail을 위해 수량이 1 이상인 상품 필터링
    const filteredProducts = products.filter((product) => product.quantity > 0);

    return (
        <div className="container">
            {/* 상품 목록 및 상품 수량 추가, 제거 */}
            <div className="product-list">
                {products.map((product) => (
                    <div key={product.id} className="product-details">
                        <img
                            src={product.image}
                            alt={product.name}
                            className="product-img"
                        />
                        <div className="product-info">
                            <p className="product-category">
                                {product.category}
                            </p>{" "}
                            <h2 className="product-name">{product.name}</h2>{" "}
                            <p className="product-price">{product.price}원</p>{" "}
                            <div className="quantity-control">
                                <button
                                    className="decrease-button"
                                    onClick={() =>
                                        updateQuantity(product.id, -1)
                                    }
                                    disabled={
                                        product.quantity === 0
                                    } /* 수량이 0일때 -버튼 비활성화 */
                                >
                                    -
                                </button>
                                <span className="quantity-input">
                                    {product.quantity}
                                </span>
                                <button
                                    className="increase-button"
                                    onClick={() =>
                                        updateQuantity(product.id, 1)
                                    }
                                >
                                    +
                                </button>
                            </div>
                        </div>
                    </div>
                ))}
            </div>
            {/* 선택 상품 명세내역, 총금액 안내 */}
            <div className="order-list">
                <div className="order-detail">
                    {filteredProducts.map((product) => (
                        <div key={product.id} className="order-item">
                            <img
                                src={product.image}
                                alt={product.name}
                                className="product-img"
                            />
                            <div className="order-item-info">
                                <p>{product.name}</p>
                                <span className="order-price">
                                    {product.price * product.quantity}원
                                </span>
                                <span className="order-quantity">
                                    {product.quantity}
                                </span>
                            </div>
                        </div>
                    ))}
                </div>
                <div className="order-info">
                    <h2>이메일</h2>
                    <input
                        type="email"
                        placeholder="email을 입력해주세요"
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                    />
                    <h2>주소</h2>
                    <input
                        type="text"
                        placeholder="주소를 입력해주세요"
                        value={address}
                        onChange={(e) => setAddress(e.target.value)}
                    />
                    <h2>우편번호</h2>
                    <input
                        type="number"
                        placeholder="우편번호를 입력해주세요"
                        value={postal}
                        onChange={(e) => setPostal(e.target.value)}
                    />
                    <div className="notice-msg">
                        당일 오후 2시 이후의 주문은 다음날 배송을 시작합니다.
                    </div>
                    <span className="order-total">총 금액 {totalPrice}원</span>
                    <button type="button">결제하기</button>
                </div>
            </div>
        </div>
    );
};

export default ClientPage;
