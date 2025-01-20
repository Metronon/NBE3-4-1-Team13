import client from "@/lib/backend/client";

const response = await client.GET("/menu");
const menuList = response.data?.data;

export default menuList;