"use client";

import React, { useMemo, useState, useEffect } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";
import ConfirmPopup from "./ConfirmPopup";
import OrderCompletePopup from "./OrderCompletePopup";
import type { components } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";

const ClientPage = ({
    responseBody,
}: {
    responseBody: components["schemas"]["MenuDataDto"][];
}) => {
    const [products, setProducts] = useState<
        components["schemas"]["MenuDataDto"][]
    >([]);
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [showConfirmPopup, setShowConfirmPopup] = useState(false);
    const [showCompletePopup, setShowCompletePopup] = useState(false);

    useEffect(() => {
        const formattedData = responseBody.map((item) => ({
            menuId: item.menuId,
            menuName: item.menuName,
            menuPrice: item.menuPrice,
            menuType: item.menuType,
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

    const totalPrice = useMemo(
        () =>
            products.reduce(
                (sum, product) => sum + product.menuPrice * product.count,
                0
            ),
        [products]
    );

    const filteredProducts = products.filter((product) => product.count > 0);

    // ConfirmPopup에서 "네"를 눌렀을때 POST 요청
    const handleConfirm = async () => {
        const orders: Record<number, number> = {};
        filteredProducts.forEach((product) => {
            orders[product.menuId] = product.count;
        });

        const requestBody = {
            email,
            address,
            postalCode,
            orders,
        };

        try {
            const response = await client.POST("/order", {
                body: requestBody, // requestBody를 body 속성에 전달
            });
            console.log("주문이 성공적으로 전송되었습니다:", response);
            setShowCompletePopup(true);
        } catch (error) {
            console.error("주문 전송 중 오류 발생:", error);
            alert("주문 전송에 실패했습니다. 다시 시도해 주세요.");
        }

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
            {showCompletePopup && (
                <OrderCompletePopup onClose={() => setShowCompletePopup(false)} />
            )}
        </div>
    );
};

export default ClientPage;
