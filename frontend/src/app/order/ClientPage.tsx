"use client";

import React, { useMemo, useState, useEffect } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";
import ConfirmPopup from "./ConfirmPopup";
import OrderCompletePopup from "./OrderCompletePopup";
import type { components } from "@/lib/backend/apiV1/schema";
import client from "@/lib/backend/client";

// MenuDataDto : Menu의 Id, Name, Price, Count
const ClientPage = ({
    responseBody,
}: {
    responseBody: components["schemas"]["MenuDataDto"][];
}) => {
    const [products, setProducts] = useState<components["schemas"]["MenuDataDto"][]>([]);
    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [postalCode, setPostalCode] = useState("");
    const [showConfirmPopup, setShowConfirmPopup] = useState(false);
    const [showCompletePopup, setShowCompletePopup] = useState(false);

    useEffect(() => {
        const formattedData = responseBody.map((item) => {
            const imageId = (item.menuId % 4) + 1;

            return {
            menuId: item.menuId,
            menuName: item.menuName,
            menuPrice: item.menuPrice,
            menuType: item.menuType,
            image: item.menuType === "커피" 
                ? `/images/커피_${imageId}.png` 
                : `/images/커피콩_${imageId}.png`,
            menuCount: 0,
        }
        });
        setProducts(formattedData);
    }, [responseBody]);

    // 상품 수량 수정
    const updateCount = (id: number, setMenuCount: number) => {
        setProducts(
            products.map((product) =>
                product.menuId === id
                    ? { ...product, menuCount: product.menuCount! + setMenuCount }
                    : product
            )
        );
    };

    // 총 가격 계산
    const totalPrice = useMemo(
        () =>
            products.reduce(
                (sum, product) => sum + product.menuPrice! * product.menuCount!,
                0
            ),
        [products]
    );

    const filteredProducts = products.filter((product) => product.menuCount! > 0);

    // ConfirmPopup에서 "네"를 눌렀을때 POST 요청
    const handleConfirm = async () => {
        const orders: Record<number, number> = {};

        if (filteredProducts.length === 0) {
            alert("상품을 최소 1개 이상 선택해야 합니다.");
            return;
        }

        filteredProducts.forEach((product) => {
            if (product.menuId !== undefined) {
                orders[product.menuId] = product.menuCount!;
            }
        });

        const requestBody = {
            email,
            address,
            postalCode,
            orders,
        };

        try {
            const response = await client.POST("/order", {
                body: requestBody,
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
                <OrderCompletePopup
                    onClose={() => setShowCompletePopup(false)}
                />
            )}
        </div>
    );
};

export default ClientPage;

