package g60177.qwirkle.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import static g60177.qwirkle.view.View.display;
import static org.junit.jupiter.api.Assertions.*;
import static g60177.qwirkle.model.Color.*;
import static g60177.qwirkle.model.Direction.*;
import static g60177.qwirkle.model.Shape.*;
import static g60177.qwirkle.model.QwirkleTestUtils.*;


class GridTest {

    private Grid grid;

    @BeforeEach
    void setUp() {
        grid = new Grid();
    }

    @Test
    void firstAdd_one_tile2() {
        var tile = new Tile(BLUE, CROSS);
        grid.firstAdd(RIGHT, tile);
        assertSame(get(grid, 0, 0), tile);
    }

    @Test
    void rules_sonia_a2() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        assertEquals(t1, get(grid, 0, 0));
        assertEquals(t2, get(grid, -1, 0));
        assertEquals(t3, get(grid, -2, 0));

    }


    @Test
    void rules_sonia_a_adapted_to_fail2() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, DIAMOND);
        assertThrows(QwirkleException.class, () -> {
            grid.firstAdd(UP, t1, t2, t3);
        });
        assertNull(get(grid, 0, 0));
        assertNull(get(grid, -1, 0));
        assertNull(get(grid, -2, 0));
    }

    @Test
    void firstAdd_cannot_be_called_twice2() {
        Tile redcross = new Tile(RED, CROSS);
        Tile reddiamond = new Tile(RED, DIAMOND);
        grid.firstAdd(UP, redcross, reddiamond);
        assertThrows(QwirkleException.class, () -> grid.firstAdd(DOWN, redcross, reddiamond));
    }


    @Test
    void firstAdd_must_be_called_first_simple2() {
        Tile redcross = new Tile(RED, CROSS);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 0, redcross));
    }


    @Test
    @DisplayName("get outside the grid should return null, not throw")
    void can_get_tile_outside_virtual_grid2() {
        var g = new Grid();
        assertDoesNotThrow(() -> get(g, -250, 500));
        assertNull(get(g, -250, 500));
    }


    @Test
    void add_a_Tile() {
        var g = new Grid();
        g.firstAdd(RIGHT, new Tile(RED, STAR), new Tile(RED, SQUARE));
        g.add(45, 47, new Tile(RED, ROUND));
        assertEquals(g.get(45, 47), new Tile(RED, ROUND));
    }

    @Test
    void add_a_Tile_At_position_not_empty() {
        var g = new Grid();
        g.firstAdd(RIGHT, new Tile(RED, STAR), new Tile(RED, SQUARE));
        assertThrows(QwirkleException.class, () -> g.add(45, 46, new Tile(RED, ROUND)));

    }

    @Test
    void add_a_Tile_when_the_Grid_isEmpty() {
        var g = new Grid();
        assertThrows(QwirkleException.class, () -> g.add(45, 46, new Tile(RED, ROUND)));

    }

    @Test
    void add_a_Tile_out_of_the_limit_of_the_grid() {
        var g = new Grid();
        g.firstAdd(RIGHT, new Tile(RED, STAR), new Tile(RED, SQUARE));
        assertThrows(QwirkleException.class, () -> g.add(450, 460, new Tile(RED, ROUND)));

    }

    @Test
    void add_a_Tile_witch_have_any_characteristics_in_common_with_the_one_around_her() {
        var g = new Grid();
        g.firstAdd(RIGHT, new Tile(RED, STAR));
        assertThrows(QwirkleException.class, () -> g.add(45, 44, new Tile(BLUE, ROUND)));

    }

    @Test
    void cédric_b() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        assertEquals(t4, get(grid, 1, 0));
        assertEquals(t5, get(grid, 1, 1));
        assertEquals(t6, get(grid, 1, 2));
    }

    @Test
    void cédric_b_adapted_to_fall() {
        Tile t1 = new Tile(RED, PLUS);
        Tile t2 = new Tile(RED, SQUARE);
        Tile t3 = new Tile(PURPLE, SQUARE);
        assertThrows(QwirkleException.class, () -> add(grid, 1, 0, RIGHT, t1, t2, t3));

    }

    @Test
    void cédric_b_adapted_to_fall_two() {
        Tile t1 = new Tile(RED, SQUARE);
        Tile t2 = new Tile(RED, SQUARE);
        Tile t3 = new Tile(PURPLE, SQUARE);
        assertThrows(QwirkleException.class, () -> add(grid, 1, 0, RIGHT, t1, t2, t3));

    }

    @Test
    void elvire_c() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        assertEquals(t7, get(grid, 0, 1));
    }

    @Test
    void elvire_c_adapted_to_fail() {
        Tile t7 = new Tile(BLUE, ROUND);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 1, t7));
    }

    @Test
    void elvire_c_adapted_to_fail2_same_in_the_X_axe() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(RED, ROUND);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 1, t7));
    }


    @Test
    void elvire_c_adapted_to_fail3_same_in_the_Y_axe() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, SQUARE);
        assertThrows(QwirkleException.class, () -> add(grid, 0, 1, t7));
    }

    @Test
    void vincent_d() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        assertEquals(t8, get(grid, -2, -1));
    }

    @Test
    void vincent_d_adapted_to_fail_put_two_same_tiles() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, PLUS);
        assertThrows(QwirkleException.class, () -> add(grid, -2, -1, DOWN, t8, t9));

    }

    @Test
    void vincent_d_adapted_to_fail2_at_position_with_out_tile_around() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, ROUND);
        assertThrows(QwirkleException.class, () -> add(grid, -20, -15, DOWN, t8, t9));

    }

    @Test
    void vincent_d_adapted_to_fail3_same_tiles_in_the_param() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, PLUS);
        assertThrows(QwirkleException.class, () -> add(grid, -6, -1, DOWN, t8, t9));

    }

    @Test
    void sonia_e() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));

        grid.add(t10, t11);
        assertEquals(new Tile(GREEN, ROUND), get(grid, 0, -1));
        assertEquals(new Tile(GREEN, STAR), get(grid, -3, -1));

    }

    @Test
    void sonia_e_adapted_to_fail_put_same_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, ROUND));
        grid.add(t10);
        assertThrows(QwirkleException.class, () -> grid.add(t11));
    }

    @Test
    void adaptedToFailputATileAtPositionWithAnyTileAroundHer_one_tile() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        TileAtPosition t5 = createTileAtpos(-4, 15, new Tile(GREEN, STAR));
        assertThrows(QwirkleException.class, () -> grid.add(t5));

    }

    @Test
    void adaptedToFailputATileAtPositionWithAnyTileAroundHer_several_tiles() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        TileAtPosition t5 = createTileAtpos(-4, 15, new Tile(GREEN, STAR));
        TileAtPosition t6 = createTileAtpos(-4, 16, new Tile(GREEN, CROSS));
        TileAtPosition t7 = createTileAtpos(-4, 17, new Tile(GREEN, SQUARE));
        assertThrows(QwirkleException.class, () -> grid.add(t5, t6, t7));

    }

    @Test
    void sonia_e_adapted_to_fail2_out_of_bound_position() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(-49, -1, new Tile(GREEN, ROUND));

        assertThrows(QwirkleException.class, () -> grid.add(t10));
    }

    @Test
    void sonia_e_adapted_to_fail3_put_tile_with_out_shared_characteristic() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(RED, ROUND));
        assertThrows(QwirkleException.class, () -> grid.add(t10));
    }

    @Test
    void cédric_f() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        assertEquals(t12, get(grid, 1, 3));
        assertEquals(t13, get(grid, 2, 3));

    }

    @Test
    void cédric_f_addapted_to_fail_not_good_shape() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, CROSS);
        Tile t13 = new Tile(RED, SQUARE);
        assertThrows(QwirkleException.class, () -> add(grid, 1, 3, DOWN, t12, t13));
    }

    @Test
    void elvire_G() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        assertEquals(t14, get(grid, -3, -2));
        assertEquals(t15, get(grid, -3, -3));


    }

    @Test
    void elvire_G_addapted_to_fail_not_good_position_so_no_shared_characteristic() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        assertThrows(QwirkleException.class, () -> add(grid, -2, -2, LEFT, t14, t15));
    }

    @Test
    void vincent_h() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        assertEquals(t16, get(grid, -2, -3));
        assertEquals(t17, get(grid, -1, -3));


    }

    @Test
    void vincent_h_addapted_to_fail_try_to_put_a_tile_witch_is_already_in_the_Y_axe() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, STAR);

        assertThrows(QwirkleException.class, () -> add(grid, -2, -3, DOWN, t16, t17));


    }

    @Test
    void sonia_i() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        assertEquals(t18, get(grid, -1, -2));
        assertEquals(t19, get(grid, 0, -2));
    }

    @Test
    void sonia_i_adapted_to_fail_try_to_put_a_tile_witch_have_not_shared_characteristic() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        assertThrows(QwirkleException.class, () -> add(grid, -1, -2, UP, t18, t19));
    }

    @Test
    void cedric_j() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, 0, new Tile(RED, STAR));
        grid.add(t20);
        assertEquals(new Tile(RED, STAR), get(grid, -3, 0));
    }

    @Test
    void cedric_j_adapted_to_fail_the_position_is_not_free() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, -1, new Tile(RED, STAR));

        assertThrows(QwirkleException.class, () -> grid.add(t20));
    }

    @Test
    void elvire_k() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, 0, new Tile(RED, STAR));
        grid.add(t20);
        Tile t21 = new Tile(BLUE, CROSS);
        Tile t22 = new Tile(RED, CROSS);
        Tile t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        assertEquals(t21, get(grid, 2, 1));
        assertEquals(t22, get(grid, 2, 0));
        assertEquals(t23, get(grid, 2, -1));
    }

    @Test
    void elvire_k_adapted_to_fail_put_in_second_a_blue_square() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, 0, new Tile(RED, STAR));
        grid.add(t20);
        Tile t21 = new Tile(BLUE, CROSS);
        Tile t22 = new Tile(BLUE, SQUARE);
        Tile t23 = new Tile(ORANGE, CROSS);
        assertThrows(QwirkleException.class, () -> add(grid, 2, 1, LEFT, t21, t22, t23));

    }

    @Test
    void vincent_l() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, 0, new Tile(RED, STAR));
        grid.add(t20);
        Tile t21 = new Tile(BLUE, CROSS);
        Tile t22 = new Tile(RED, CROSS);
        Tile t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        Tile t24 = new Tile(YELLOW, SQUARE);
        Tile t25 = new Tile(BLUE, SQUARE);
        add(grid, 1, 4, DOWN, t24, t25);
        assertEquals(t24, get(grid, 1, 4));
        assertEquals(t25, get(grid, 2, 4));
    }

    @Test
    void vincent_l_adapted_to_fail_tile_aready_present_in_the_x_axe() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        Tile t4 = new Tile(RED, SQUARE);
        Tile t5 = new Tile(BLUE, SQUARE);
        Tile t6 = new Tile(PURPLE, SQUARE);
        add(grid, 1, 0, RIGHT, t4, t5, t6);
        Tile t7 = new Tile(BLUE, ROUND);
        add(grid, 0, 1, t7);
        Tile t8 = new Tile(GREEN, PLUS);
        Tile t9 = new Tile(GREEN, DIAMOND);
        add(grid, -2, -1, DOWN, t8, t9);
        TileAtPosition t10 = createTileAtpos(0, -1, new Tile(GREEN, ROUND));
        TileAtPosition t11 = createTileAtpos(-3, -1, new Tile(GREEN, STAR));
        grid.add(t10);
        grid.add(t11);
        Tile t12 = new Tile(ORANGE, SQUARE);
        Tile t13 = new Tile(RED, SQUARE);
        add(grid, 1, 3, DOWN, t12, t13);
        Tile t14 = new Tile(YELLOW, STAR);
        Tile t15 = new Tile(ORANGE, STAR);
        add(grid, -3, -2, LEFT, t14, t15);
        Tile t16 = new Tile(ORANGE, CROSS);
        Tile t17 = new Tile(ORANGE, DIAMOND);
        add(grid, -2, -3, DOWN, t16, t17);
        Tile t18 = new Tile(YELLOW, DIAMOND);
        Tile t19 = new Tile(YELLOW, ROUND);
        add(grid, -1, -2, DOWN, t18, t19);
        TileAtPosition t20 = createTileAtpos(-3, 0, new Tile(RED, STAR));
        grid.add(t20);
        Tile t21 = new Tile(BLUE, CROSS);
        Tile t22 = new Tile(RED, CROSS);
        Tile t23 = new Tile(ORANGE, CROSS);
        add(grid, 2, 1, LEFT, t21, t22, t23);
        Tile t24 = new Tile(YELLOW, SQUARE);
        Tile t25 = new Tile(RED, SQUARE); //here
        assertThrows(QwirkleException.class, () -> add(grid, 1, 4, DOWN, t24, t25));


    }

    @Test
    @DisplayName("Complete a line leaving holes during intermediary steps")
    void canCompleteAline_Left_Middle_Right() {
        var g = new Grid();
        Tile TILE_RED_CROSS = new Tile(RED, CROSS);
        Tile TILE_RED_PLUS = new Tile(RED, PLUS);
        Tile TILE_RED_DIAMOND = new Tile(RED, DIAMOND);
        g.firstAdd(RIGHT, TILE_RED_CROSS, TILE_RED_PLUS, TILE_RED_DIAMOND);

        Tile TILE_GREEN_CROSS = new Tile(GREEN, CROSS);
        Tile TILE_YELLOW_CROSS = new Tile(YELLOW, CROSS);
        Tile TILE_GREEN_DIAMOND = new Tile(GREEN, DIAMOND);
        Tile TILE_YELLOM_DIAMOND = new Tile(YELLOW, DIAMOND);

        add(g, 1, 0, TILE_GREEN_CROSS);
        add(g, 2, 0, TILE_YELLOW_CROSS);
        add(g, 1, 2, TILE_GREEN_DIAMOND);
        add(g, 2, 2, TILE_YELLOM_DIAMOND);
        Tile TILE_YELLOM_PLUS = new Tile(YELLOW, PLUS);
        Tile TILE_YELLOM_ROUND = new Tile(YELLOW, ROUND);
        Tile TILE_YELLOW_STAR = new Tile(YELLOW, STAR);
        TileAtPosition plus_left = createTileAtpos(2, -1, TILE_YELLOM_PLUS);
        TileAtPosition round_center = createTileAtpos(2, 1, TILE_YELLOM_ROUND);
        TileAtPosition star_right = createTileAtpos(2, 3, TILE_YELLOW_STAR);
        assertDoesNotThrow(() -> {
            g.add(plus_left, star_right, round_center);// nake sur having the center tile last does not throw.
        });
        GridView v = new GridView(g);
        display(v);
        assertEquals(plus_left.tile(), get(g, 2, -1));
        assertEquals(round_center.tile(), get(g, 2, 1));
        assertEquals(star_right.tile(), get(g, 2, 3));


    }
    // test pour les points

    @Test
    void point_test() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        assertEquals(3, grid.firstAdd(UP, t1, t2, t3));

    }

    @Test
    void point_test3() {
        GridView g = new GridView(grid);
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, STAR);
        var t5 = new Tile(RED, CROSS);
        var t6 = new Tile(RED, SQUARE);
        assertEquals(4, grid.add(45, 46, RIGHT, t4, t5, t6));
        display(g);


    }
    @Test
    void point_test_Qwirkle() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, STAR);
        var t5 = new Tile(RED, CROSS);
        var t6 = new Tile(RED, SQUARE);
        assertEquals(12, grid.add(46, 45, DOWN, t4, t5, t6));

    }

    @Test
    void point_test4_one_tile() {
        GridView g = new GridView(grid);
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, STAR);
        var t5 = new Tile(RED, CROSS);
        var t6 = new Tile(RED, SQUARE);
        grid.add(45, 46, RIGHT, t4, t5, t6);
        var t7 = new Tile(RED, DIAMOND);
        display(g);
        assertEquals(5, grid.add(45, 49, t7));

    }

    @Test
    void point_test5_complete_2_line() {
        GridView g = new GridView(grid);
        var t1 = new Tile(YELLOW, ROUND);
        var t2 = new Tile(YELLOW, DIAMOND);
        var t3 = new Tile(YELLOW, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE, DIAMOND);
        var t5 = new Tile(RED, DIAMOND);
        grid.add(44, 46, RIGHT, t4, t5);
        var t6 = new Tile(PURPLE, PLUS);
        var t7 = new Tile(PURPLE, SQUARE);
        grid.add(43, 44, UP, t6, t7);
        var t8 = new Tile(PURPLE, DIAMOND);
        assertEquals(7, grid.add(44, 44, t8));

    }

    @Test
    void point_test5_complete_2_line_double_Qwirkle() {
        GridView g = new GridView(grid);
        var t1 = new Tile(YELLOW, ROUND);
        var t2 = new Tile(YELLOW, DIAMOND);
        var t3 = new Tile(YELLOW, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(BLUE, DIAMOND);
        var t5 = new Tile(RED, DIAMOND);
        var t12 = new Tile(ORANGE, DIAMOND);
        var t13 = new Tile(GREEN, DIAMOND);

        grid.add(44, 46, RIGHT, t4, t5, t12, t13);
        var t6 = new Tile(PURPLE, PLUS);
        var t7 = new Tile(PURPLE, SQUARE);
        var t8 = new Tile(PURPLE, STAR);
        var t9 = new Tile(PURPLE, ROUND);
        var t10 = new Tile(PURPLE, CROSS);
        grid.add(43, 44, UP, t6, t7, t8, t9, t10);
        var t11 = new Tile(PURPLE, DIAMOND);

        assertEquals(24, grid.add(44, 44, t11));
        display(g);
    }
    @Test
    public void tap_score(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4= createTileAtpos(1,0,new Tile(RED,STAR));
        var t5=  createTileAtpos(-3,0,new Tile(RED,SQUARE));

        assertEquals(5, grid.add(t4,t5));
    }


    @Test
    public void test_score_add_one_tile_on_y_axe(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, STAR);


        assertEquals(4,grid.add(46,45,t4));
    }
    @Test
    public void test_score_add_on_y_axe(){
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        grid.firstAdd(UP, t1, t2, t3);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, STAR);
        var t6 = new Tile(RED, CROSS);
        grid.add(46,45,t4);
        grid.add(47,45,t5);

        assertEquals(12 ,grid.add(48,45,t6));

    }
    @Test
    void rules_sonia_a_score() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);

        assertEquals(3, grid.firstAdd(UP, t1, t2, t3));

    }

    @Test
    void rules_sonia_a_score_whit_qwirkle() {
        var t1 = new Tile(RED, ROUND);
        var t2 = new Tile(RED, DIAMOND);
        var t3 = new Tile(RED, PLUS);
        var t4 = new Tile(RED, SQUARE);
        var t5 = new Tile(RED, STAR);
        var t6 = new Tile(RED, CROSS);

        assertEquals(12, grid.firstAdd(UP, t1, t2, t3, t4, t5, t6));

    }


}