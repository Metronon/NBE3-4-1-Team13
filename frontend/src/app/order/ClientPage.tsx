"use client";

import React, { useMemo, useState } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";
import ConfirmPopup from "./ConfirmPopup";

const menuData = [
    {
        menuId: 1,
        menuName: "Mocha",
        image: "images/product_1.png",
        menuPrice: 5000,
        category: "커피콩",
    },
    {
        menuId: 2,
        menuName: "Blue Mountain",
        image: "images/product_2.png",
        menuPrice: 6000,
        category: "커피콩",
    },
    {
        menuId: 3,
        menuName: "Havana",
        image: "images/product_3.png",
        menuPrice: 7000,
        category: "커피콩",
    },
    {
        menuId: 4,
        menuName: "Um café",
        image: "images/product_4.png",
        menuPrice: 8000,
        category: "커피콩",
    },
];

const ClientPage = () => {
    const [products, setProducts] = useState(
        menuData.map((item) => ({
            ...item,
            count: 0, // 수량 초기화
        }))
    );

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
