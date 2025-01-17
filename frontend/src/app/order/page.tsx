import React from "react";
import "./page.css";
import "../globals.css";
import ClientPage from "./ClientPage";

interface Menu {
    menuId: number; // id를 menuId로 변환
    menuName: string; // name을 menuName으로 변환
    menuPrice: number; // price를 menuPrice로 변환
}

export default async function Page({
  searchParams,
}: {
  searchParams: {
    searchKeywordType?: "title" | "content";
    searchKeyword?: string;
    pageSize?: number;
    page?: number;
  };
}) {
  const {
    searchKeyword = "",
    searchKeywordType = "title",
    pageSize = 10,
    page = 1,
  } = searchParams;

  const response = await client.GET("/api/v1/posts", {
    params: {
      query: {
        searchKeyword,
        searchKeywordType,
        pageSize,
        page,
      },
    },
  });

  const responseBody = response.data!!;

  // 메뉴 데이터 변환
  const menuData: Menu[] = responseBody.map((item: any) => ({
    menuId: item.id,
    menuName: item.name,
    menuPrice: item.price,
  }));

  return (
    <>
      <ClientPage
        searchKeyword={searchKeyword}
        searchKeywordType={searchKeywordType}
        page={page}
        pageSize={pageSize}
        responseBody={menuData} // 변환된 메뉴 데이터 전달
      />
    </>
  );
}
