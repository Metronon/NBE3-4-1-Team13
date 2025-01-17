"use client";

import React, { useMemo, useState, useEffect } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";
import ConfirmPopup from "./ConfirmPopup";
import type { components } from "@/lib/backend/apiV1/schema";

const ClientPage = ({
    responseBody,
}: {
    responseBody: components["schemas"]["MenuDataDto"][];
}) => {
    const [products, setProducts] = useState<
        components["schemas"]["MenuDataDto"][]
    >([]);

    useEffect(() => {
        const formattedData = responseBody.map((item) => ({
            menuId: item.menuId,
            menuName: item.menuName,
            menuPrice: item.menuPrice,
            image: `images/product_1.png`,
            count: 0, // 초기화
        }));
        setProducts(formattedData);
    }, [responseBody]);

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
