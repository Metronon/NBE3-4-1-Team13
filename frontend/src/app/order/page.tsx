import React from "react";
import "./page.css";
import "../globals.css";
import ClientPage from "./ClientPage";
import client from "@/lib/backend/client";

interface Menu {
    menuId: number;
    menuName: string;
    menuType: string;
    menuPrice: number;
}

export default async function Page() {
    const response = await client.GET("/menu");

    const menuData = response.data.data!!;

    const formattedMenuData: Menu[] = menuData.map((item: any) => ({
        menuId: item.id,
        menuName: item.name,
        menuPrice: item.price,
        menuType: item.type,
    }));

    return (
        <>
            <ClientPage responseBody={formattedMenuData} />
        </>
    );
}
