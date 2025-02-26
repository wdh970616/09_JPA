package com.ohgiraffers.section01.problem;

import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProblemOfUsingDirectSQLTests {

    private Connection con;

    @BeforeEach
    void setConnection() throws ClassNotFoundException, SQLException {

        String driver = "com.mysql.cj.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/menudb";
        String user = "ohgiraffers";
        String password = "ohgiraffers";

        Class.forName(driver);

        con = DriverManager.getConnection(url, user, password);
        con.setAutoCommit(false);
    }

    @AfterEach
    void closeConnection() throws SQLException {
        con.rollback();
        con.close();
    }

    /*
     * 데이터베이스 테이블에 맞춘 객체 모델
       public class Menu {
        private int menuCode;
        private String menuName;
        private int menuPrice;
        private int categoryCode;
        private String orderableStatus;
       }
       public class Category {
        private int categoryCode;
        private String categoryName;
       }
     */

    /*
     * 객체 지향 언어에 맞춘 객체 모델
       public class Menu {
        private int menuCode;
        private String menuName;
        private int menuPrice;
        private Category category;
        private String orderableStatus
       }
       public class Category {
        private int categoryCode;
        private String categoryName;
       }
     */

    /*
     * JDBC API를 이용해 직접 SQL을 다룰 때 발생할 수 있는 문제점
     * 1. 데이터 변환, SQL 작성, JDBC API 코드 등의 중복 작성
     * 2. SQL에 의존하여 개발
     * 3. 패러다임 불일치 (상속, 연관관계, 객체 그래프 탐색)
     * 4. 동일성 보장문제
     */

    // 1. 데이터 변환, SQL 작성 등의 중복 작성 문제
    @DisplayName("직접 SQL을 작성하여 메뉴를 조회할 때 발생하는 문제 확인")
    @Test
    void testDirectSelectSQL() throws SQLException {

        // given
        String query = "select menu_code, menu_name, menu_price, category_code, orderable_status from tbl_menu";

        // when
        Statement stmt = con.createStatement();
        ResultSet rset = stmt.executeQuery(query);

        List<Menu> menuList = new ArrayList<>();

        while (rset.next()) {
            Menu menu = new Menu();
            menu.setMenuCode(rset.getInt("menu_code"));
            menu.setMenuName(rset.getString("menu_name"));
            menu.setMenuPrice(rset.getInt("menu_price"));
            menu.setCategoryCode(rset.getInt("category_code"));
            menu.setOrderableStatus(rset.getString("orderable_status"));

            menuList.add(menu);
        }

        // then
        assertNotNull(menuList);    // notNull이면 패스
        menuList.forEach(System.out::println);
        rset.close();
        stmt.close();
    }

    @DisplayName("직접 SQL을 작성하여 신규 메뉴를 추가할 때 발생하는 문제 확인")
    @Test
    void testDirectInsertSql() throws SQLException {

        // given
        Menu menu = new Menu();
        menu.setMenuName("민트초코짜장면");
        menu.setMenuPrice(30000);
        menu.setCategoryCode(1);
        menu.setOrderableStatus("Y");

        String query = "insert into tbl_menu(menu_name, menu_price, category_code, orderable_status) values(?, ?, ?, ?)";

        // when
        PreparedStatement pstmt = con.prepareStatement(query);
        pstmt.setString(1, menu.getMenuName());
        pstmt.setDouble(2, menu.getMenuPrice());
        pstmt.setInt(3, menu.getCategoryCode());
        pstmt.setString(4, menu.getOrderableStatus());

        int result = pstmt.executeUpdate();

        // then
        Assertions.assertEquals(1, result);

        pstmt.close();
    }

    /*
     * JPA
     * Menu menu = entityManager.find(Menu.class, menuCode);
     * menu.getCategory().getCategoryName();    // NPE가 발생하지 않음
     */

    @DisplayName("조회한 두 개의 행을 담은 객체의 도일성 비교 테스트")
    @Test
    void testEquals() throws SQLException {

        // given
        String query = "select menu_code, menu_name from tbl_menu where menu_code = 12";

        // when
        Statement stmt1 = con.createStatement();
        ResultSet rset1 = stmt1.executeQuery(query);

        Menu menu1 = null;

        while (rset1.next()) {
            menu1 = new Menu();
            menu1.setMenuCode(rset1.getInt("menu_code"));
            menu1.setMenuName(rset1.getString("menu_name"));
        }

        Statement stmt2 = con.createStatement();
        ResultSet rset2 = stmt2.executeQuery(query);

        Menu menu2 = null;

        while (rset2.next()) {
            menu2 = new Menu();
            menu2.setMenuCode(rset2.getInt("menu_code"));
            menu2.setMenuName(rset2.getString("menu_name"));
        }

        rset1.close();
        rset2.close();
        stmt1.close();
        stmt2.close();

        // then
        Assertions.assertEquals(menu1.getMenuCode(), menu2.getMenuCode());
        Assertions.assertFalse(menu1 == menu2);  // menu1과 menu2가 같은 객체가 아님

        /*
         * 영속성 컨텍스트는 하나의 트렌젝션에 한해 사용한다.
         * Menu menu1 = entityManager.find(Menu.class, 12);
         * Menu menu2 = entityManager.find(Menu.class, 12);
         * menu1 == menu2 성립함
         */
    }
}
