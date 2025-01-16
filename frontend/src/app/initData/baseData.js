const baseData = {
    email: "test@test.com",
    address: "테스트",
    postalCode: "11111",
    orders: Array.from({ length: 5 }, (_, index) => {
        const orderId = index + 1; // 1~5까지의 orderId
        const count = Math.floor(Math.random() * 10); // 0~9 사이의 랜덤 수량
        const menuData = [
            {
                menuId: 1,
                menuName: "Mocha",
                menuPrice: 5000,
            },
            {
                menuId: 2,
                menuName: "Blue Mountain",
                menuPrice: 6000,
            },
            {
                menuId: 3,
                menuName: "Havana",
                menuPrice: 7000,
            },
            {
                menuId: 4,
                menuName: "Um café",
                menuPrice: 8000,
            },
        ];

        // 랜덤으로 메뉴 선택
        const selectedMenu = menuData[Math.floor(Math.random() * menuData.length)];

        return {
            orderId,
            email: baseData.email,
            address: baseData.address,
            postalCode: baseData.postalCode,
            menuId: selectedMenu.menuId,
            menuName: selectedMenu.menuName,
            menuPrice: selectedMenu.menuPrice,
            count: count,
            orderTime: new Date().toISOString(), // 현재 시간
        };
    }),
    totalPrice: 0,
    after2pm: false,
};

// 총금액 계산
baseData.totalPrice = baseData.orders.reduce((total, order) => {
    return total + order.menuPrice * order.count;
}, 0);

// 오후 2시 이후 주문건인지 확인
const currentTime = new Date();
const currentHour = currentTime.getHours();
baseData.after2pm = currentHour >= 14;

export default baseData;