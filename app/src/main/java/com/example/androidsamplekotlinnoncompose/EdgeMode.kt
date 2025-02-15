package com.example.androidsamplekotlinnoncompose

enum class EdgeMode {
    NEW_ORIGINAL, NEW_EDGE_TO_EDGE, NEW_EDGE_TO_EDGE_NAVIGATION_GRAY,
    STEP1, STEP2, STEP3, STEP4_1, STEP4_2 ;
}

//NEW_ORIGINAL ->
//  1.剛 create a Activity 起初 。
//  2.等於過去作法留 status bar 跟 navigation bar。
//  3.保留 NAVIGATION GRAY (手勢 Mode 沒感覺)
//  4.滑動時資料不會延伸至 status bar 跟 navigation bar
//NEW_EDGE_TO_EDGE ->
//  1.滿版，且 status bar 跟 navigation bar 不會擋住資料
//  2.滑動時資料會延伸至 status bar 跟 navigation bar 。
//  3.完整無邊境作法。
//NEW_EDGE_TO_EDGE_NAVIGATION_GRAY
// 1.與 NEW_EDGE_TO_EDGE 相同，只是保留 NAVIGATION Bar for Buttons 不是透明 (手勢 Mode 沒感覺)