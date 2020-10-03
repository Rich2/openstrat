/* Copyright 2018-20 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, Colour._

object PapuaNewGuinea extends Flag
{ val name = "Papua New Guinea"
  val ratio = 4.0/3
  val apply: Arr[GraphicElem] =
  { val base = Rectangle.applyOld(ratio, 1).fill(Black)
    val topRightTriangle = Triangle.fill(-2.0/3 vv 0.5, 2.0/3 vv 0.5, 2.0/3 vv -0.5, Colour(0xFFce1126))

    val bird = PolyCurve(LineTail(0.04247 vv 0.2211), BezierTail(0.03302 vv 0.2089, 0.01065 vv 0.2334, -0.01615 vv 0.2339),
      BezierTail(-0.04295 vv 0.2345, -0.06194 vv 0.2082, -0.06975 vv 0.1993), BezierTail(-0.07757 vv 0.1904, -0.1027 vv 0.1435, -0.1021 vv 0.1384),
      BezierTail(-0.1016 vv 0.1334, -0.0865 vv 0.1524, -0.08092 vv 0.1558), BezierTail(-0.08036 vv 0.1491, -0.08706 vv 0.1412, -0.08538 vv 0.139),
      BezierTail(-0.08371 vv 0.1368, -0.06584 vv 0.1524, -0.0597 vv 0.153), BezierTail(-0.05803 vv 0.1485, -0.06305 vv 0.144, -0.06138 vv 0.1373),
      BezierTail(-0.05914 vv 0.1351, -0.04909 vv 0.1485, -0.04239 vv 0.1513), BezierTail(-0.04128 vv 0.1463, -0.04853 vv 0.1368, -0.04463 vv 0.1306),
      BezierTail(-0.04183 vv 0.1301, -0.02508 vv 0.1496, -0.0195 vv 0.1518), BezierTail(-0.01392 vv 0.1541, -0.02229 vv 0.1334, -0.0195 vv 0.1317),
      BezierTail(-0.01448 vv 0.1351, 0.001159 vv 0.153, 0.006184 vv 0.1552), BezierTail(0.01121 vv 0.1574, 0.005626 vv 0.1323, 0.007859 vv 0.1323),
      BezierTail(0.014 vv 0.1368, 0.02963 vv 0.158, 0.03131 vv 0.1613), BezierTail(0.03298 vv 0.1647, 0.02963 vv 0.129, 0.04694 vv 0.11),
      BezierTail(0.06425 vv 0.09098, 0.09049 vv 0.06921, 0.09328 vv 0.06921), BezierTail(0.09663 vv 0.07535, 0.09217 vv 0.08819, 0.09496 vv 0.08764),
      BezierTail(0.1028 vv 0.07926, 0.1173 vv 0.06698, 0.1206 vv 0.06698), BezierTail(0.124 vv 0.06698, 0.1184 vv 0.08038, 0.1229 vv 0.08038),
      BezierTail(0.1318 vv 0.072, 0.1575 vv 0.03906, 0.2077 vv 0.02399), BezierTail(0.2044 vv 0.03348, 0.1971 vv 0.04185, 0.1966 vv 0.04632),
      BezierTail(0.1999 vv 0.04576, 0.2117 vv 0.04018, 0.2139 vv 0.04074), BezierTail(0.2161 vv 0.04129, 0.1893 vv 0.07424, 0.191 vv 0.0787),
      BezierTail(0.1971 vv 0.07926, 0.1999 vv 0.07535, 0.2022 vv 0.07814), BezierTail(0.1994 vv 0.08261, 0.1759 vv 0.1089, 0.1759 vv 0.115),
      BezierTail(0.1759 vv 0.1211, 0.1832 vv 0.1139, 0.1854 vv 0.115), BezierTail(0.1876 vv 0.1161, 0.1765 vv 0.1396, 0.1787 vv 0.1424),
      BezierTail(0.1809 vv 0.1451, 0.2055 vv 0.1273, 0.2116 vv 0.1273), BezierTail(0.2178 vv 0.1273, 0.2133 vv 0.1491, 0.2161 vv 0.1502),
      BezierTail(0.2189 vv 0.1513, 0.2396 vv 0.1384, 0.244 vv 0.1384), BezierTail(0.2485 vv 0.1384, 0.2479 vv 0.1479, 0.2496 vv 0.1468),
      BezierTail(0.2513 vv 0.1457, 0.2999 vv 0.09713, 0.2831 vv 0.04018), BezierTail(0.2664 vv -0.000582, 0.2329 vv -0.02236, 0.1837 vv -0.00449),
      BezierTail(0.1865 vv -0.008957, 0.2128 vv -0.03073, 0.2412 vv -0.02291), BezierTail(0.2697 vv -0.0151, 0.3043 vv 0.007235, 0.3004 vv 0.06307),
      BezierTail(0.2965 vv 0.1189, 0.2781 vv 0.1267, 0.2753 vv 0.1351), BezierTail(0.2725 vv 0.1435, 0.2798 vv 0.1329, 0.2859 vv 0.1306),
      BezierTail(0.292 vv 0.1284, 0.2848 vv 0.1424, 0.2831 vv 0.1485), BezierTail(0.2814 vv 0.1546, 0.3015 vv 0.1357, 0.3066 vv 0.134),
      BezierTail(0.3116 vv 0.1323, 0.2948 vv 0.1798, 0.2898 vv 0.1831), BezierTail(0.2848 vv 0.1865, 0.2948 vv 0.182, 0.2982 vv 0.1809),
      BezierTail(0.3015 vv 0.1798, 0.2675 vv 0.2328, 0.2602 vv 0.2328), BezierTail(0.253 vv 0.2328, 0.2664 vv 0.2356, 0.2703 vv 0.2356),
      BezierTail(0.2742 vv 0.2356, 0.2312 vv 0.2702, 0.2239 vv 0.2702), BezierTail(0.2167 vv 0.2702, 0.2396 vv 0.2791, 0.2418 vv 0.2797),
      BezierTail(0.2457 vv 0.2791, 0.1921 vv 0.3082, 0.1441 vv 0.2791), BezierTail(0.1413 vv 0.2814, 0.1667 vv 0.299, 0.1675 vv 0.3065),
      BezierTail(0.1678 vv 0.3085, 0.1592 vv 0.3126, 0.1586 vv 0.3149), BezierTail(0.158 vv 0.3171, 0.172 vv 0.3238, 0.1726 vv 0.3288),
      BezierTail(0.1692 vv 0.3333, 0.1631 vv 0.3316, 0.1597 vv 0.3327), BezierTail(0.1589 vv 0.3369, 0.1715 vv 0.3473, 0.1703 vv 0.3517),
      BezierTail(0.1695 vv 0.3542, 0.1603 vv 0.3551, 0.158 vv 0.3534), BezierTail(0.1558 vv 0.3517, 0.1639 vv 0.3651, 0.1625 vv 0.369),
      BezierTail(0.1614 vv 0.3707, 0.1569 vv 0.3696, 0.1525 vv 0.3685), BezierTail(0.1497 vv 0.369, 0.167 vv 0.393, 0.1636 vv 0.3964),
      BezierTail(0.1603 vv 0.3997, 0.1134 vv 0.3718, 0.09607 vv 0.3406), BezierTail(0.07877 vv 0.3093, 0.09217 vv 0.2842, 0.08993 vv 0.2736),
      BezierTail(0.0877 vv 0.263, 0.08491 vv 0.2523, 0.07765 vv 0.2523), BezierTail(0.07039 vv 0.2523, 0.05364 vv 0.2635, 0.04806 vv 0.2691),
      BezierTail(0.04247 vv 0.2747, 0.04136 vv 0.2786, 0.03354 vv 0.2808), BezierTail(0.02572 vv 0.2831, 0.007859 vv 0.2764, 0.001159 vv 0.2758),
      BezierTail(-0.005541 vv 0.2752, -0.03178 vv 0.2808, -0.03346 vv 0.2791),
      BezierTail(-0.03513 vv 0.2775, -0.007776 vv 0.2674, -0.001633 vv 0.2641),
      BezierTail(0.004509 vv 0.2607, 0.005068 vv 0.2563, 0.009534 vv 0.2535), BezierTail(0.014 vv 0.2507, 0.05587 vv 0.2384, 0.04247 vv 0.2211),
      LineTail(0.04247 vv 0.2211)).fill(Colour(0xFFfcd116))
    
    val tail = PolyCurve(LineTail(0.2061 vv 0.1424), BezierTail(0.2195 vv 0.129, 0.3099 vv 0.06865, 0.3099 vv -0.005048),
      BezierTail(0.3099 vv -0.07875, 0.244 vv -0.07652, 0.225 vv -0.07428), BezierTail(0.2463 vv -0.09215, 0.3267 vv -0.0888, 0.3267 vv -0.005048),
      BezierTail(0.3267 vv 0.06865, 0.2228 vv 0.1424, 0.2094 vv 0.1491), BezierTail(0.196 vv 0.1558, 0.1927 vv 0.1558, 0.2061 vv 0.1424),
      LineTail(0.2061 vv 0.1424)).fill(Colour(0xFFfcd116))
    
    val starSmall = PolyCurve(LineTail(-0.2843 vv -0.2267), LineTail(-0.2644 vv -0.1628), LineTail(-0.2445 vv -0.2274), LineTail(-0.2969 vv -0.1878),
      LineTail(-0.2319 vv -0.1878), LineTail(-0.2843 vv -0.2267)).fill(Colour(0xFFFFFFFF))
    
    val starLarge = PolyCurve(LineTail(-0.3326 vv 0.1901), LineTail(-0.3724 vv 0.06216), LineTail(-0.2676 vv 0.14), LineTail(-0.3976 vv 0.14),
      LineTail(-0.2928 vv 0.06076), LineTail(-0.3326 vv 0.1901)).fill(Colour(0xFFFFFFFF))

    Arr[GraphicElem](
      base,
      topRightTriangle,
      bird,
      tail,
      starSmall,
      starLarge,
      starLarge.slate(0 vv -0.4592),
      starLarge.slate(-0.1528 vv -0.1953),
      starLarge.slate(0.1521 vv -0.1814)
    )
  }
}