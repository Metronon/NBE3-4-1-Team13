"use client";

import React, { useEffect, useState } from "react";
import client from "@/lib/backend/client";
import menuList from "@/lib/backend/menulist";
import "../globals.css";

const ManagePopup = ({ onClose, email }) => {
  const [orders, setOrders] = useState([]);
  const [menus, setMenus] = useState([]);

  useEffect(() => {
    const fetchOrders = async () => {
      try {
        const response = await client.GET(`/order`, {
          params: {
            query: {
              email,
            },
          },
        });

        if (response.response.ok) {
          const data = response.data;

          const newData = data?.map((order) => ({ ...order, menuCount: [] }));

          for (let i = 0; i < newData.length; i++) {
            for (let j = 0; j < menuList.length; j++) {
              let cnt = 0;
              for (let k = 0; k < newData[i].menuData.length; k++) {
                if (menuList[j].id == newData[i].menuData[k].menuId)
                  cnt = newData[i].menuData[k].menuCount;
              }
              if (newData[i].menuCount.length < menuList.length) {
                newData[i].menuCount.push(cnt);
              }
            }
          }

          console.log(newData);

          setOrders(newData); // 주문 데이터 설정
        } else {
          console.error("주문 요청 실패");
        }
      } catch (error) {
        console.error("주문 요청 중 오류 발생:", error);
      }
    };

    fetchOrders();
  }, [email]);

  const handleModify = async () => {
    // 수정 로직
    onClose();
  };

  const handleCancel = async () => {
    /*
        try {
            const response = await fetch(`/api/orders/`, {
                method: "DELETE",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ email }), // 이메일을 포함한 요청
            });

            if (response.ok) {
                console.log("주문 취소 요청 성공:", email);
                // 추가적인 성공 처리 로직
            } else {
                console.error("주문 취소 요청 실패");
            }
        } catch (error) {
            console.error("주문 취소 중 오류 발생:", error);
        }
        */
    onClose();
  };

  console.log(orders);

  return (
    <div className="popup">
      <div className="popup-inner">
        <button className="close-btn" onClick={onClose}>
          X
        </button>
        <h1>이전 주문의 취소나 수정이 가능합니다</h1>
        <p className="email-text">이메일: {email}</p>
        <div className="order-list">
          {orders.length > 0 ? (
            <table className="table">
              <thead>
                <tr>
                  <th>주문 ID</th>
                  {menuList.map((menu) => (
                    <th key={menu.id}>{menu.name}</th>
                  ))}
                  <th>총 금액</th>
                </tr>
              </thead>
              <tbody>
                {orders.map((order) => (
                  <tr key={order.orderId}>
                    <td>{order.orderId}</td>
                    {order.menuCount.map((count) => (
                      <td key={count}>{count}</td>
                    ))}
                    <td>{order.totalPrice.toLocaleString("KO-KR")} 원</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p className="my-3">존재하는 주문이 없습니다.</p>
          )}
        </div>
        <div className="button-container">
          <button className="modify-button" onClick={handleModify}>
            수정
          </button>
          <button className="delete-button" onClick={handleCancel}>
            취소
          </button>
        </div>
      </div>
    </div>
  );
};

export default ManagePopup;
