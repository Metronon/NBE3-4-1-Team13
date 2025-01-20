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
  image: string;
  menuCount: number;
}

export default async function Page() {
  const response = await client.GET("/menu");

  const menuData = response.data!.data!!;

  const formattedMenuData: Menu[] = menuData.map((item: any) => {
    const imageId = item.id % 4;

    const image = item.type === "커피"
      ? `/images/coffee_${imageId}.png`
      : `/images/커피콩_${imageId}.png`;

    return {
      menuId: item.id,
      menuName: item.name,
      menuPrice: item.price,
      menuType: item.type,
      image: image,
      menuCount: 0,
    };
  });

  return (
    <>
      <ClientPage responseBody={formattedMenuData} />
    </>
  );
}
