"use client";

import React, { useMemo, useState, useEffect } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";
import ConfirmPopup from "./ConfirmPopup";

const ClientPage = () => {
    const [products, setProducts] = useState([]);

    useEffect(() => {
        const fetchMenus = async () => {
            try {
                const response = await fetch('/api/menus'); // 백엔드 API 호출
                const data = await response.json();
                const formattedData = data.map((item) => ({
                    ...item,
                    image: `images/product_${item.menuId}.png`, // 이미지 파일명 설정
                    count: 0, // 수량 초기화
                }));
                setProducts(formattedData);
            } catch (error) {
                console.error("메뉴를 가져오는 중 오류 발생:", error);
            }
        };

        fetchMenus();
    }, []);

    const updateCount = (id: number, setCount: number) => {
        setProducts(
            products.map((product) =>
                product.menuId === id
                    ? { ...product, count: product.count + setCount }
                    : product
            )
        );
    };

    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [showConfirmPopup, setShowConfirmPopup] = useState(false);

    const totalPrice = useMemo(
        () =>
            products.reduce(
                (sum, product) => sum + product.menuPrice * product.count,
                0
            ),
        [products]
    );

    const filteredProducts = products.filter((product) => product.count > 0);

    const handleConfirm = () => {
        console.log("결제 진행");
        // 결제 로직 추가
        setShowConfirmPopup(false);
    };

    return (
        <div className="container">
            <ProductList products={products} updateCount={updateCount} />
            <div className="order-list">
                <OrderDetail filteredProducts={filteredProducts} />
                <OrderInfo
                    email={email}
                    setEmail={setEmail}
                    address={address}
                    setAddress={setAddress}
                    postalCode={postalCode}
                    setPostalCode={setPostalCode}
                    totalPrice={totalPrice}
                    onConfirm={() => setShowConfirmPopup(true)}
                />
            </div>
            {showConfirmPopup && (
                <ConfirmPopup
                    onClose={() => setShowConfirmPopup(false)}
                    onConfirm={handleConfirm}
                />
            )}
        </div>
    );
};

export default ClientPage;
