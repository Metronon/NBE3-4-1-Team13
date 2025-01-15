"use client";

import React, { useMemo, useState } from "react";
import ProductList from "./ProductList";
import OrderDetail from "./OrderDetail";
import OrderInfo from "./OrderInfo";

const ClientPage = () => {
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

    const updateQuantity = (id: number, setQuantity: number) => {
        setProducts(
            products.map((product) =>
                product.id === id
                    ? { ...product, quantity: product.quantity + setQuantity }
                    : product
            )
        );
    };

    const [email, setEmail] = useState("");
    const [address, setAddress] = useState("");
    const [postal, setPostal] = useState("");

    const totalPrice = useMemo(
        () =>
            products.reduce(
                (sum, product) => sum + product.price * product.quantity,
                0
            ),
        [products]
    );

    const filteredProducts = products.filter((product) => product.quantity > 0);

    return (
        <div className="container">
            <ProductList products={products} updateQuantity={updateQuantity} />
            <div className="order-list">
                <OrderDetail filteredProducts={filteredProducts} />
                <OrderInfo
                    email={email}
                    setEmail={setEmail}
                    address={address}
                    setAddress={setAddress}
                    postal={postal}
                    setPostal={setPostal}
                    totalPrice={totalPrice}
                />
            </div>
        </div>
    );
};

export default ClientPage;
