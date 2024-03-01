/* Copyright 2018-24 w0d, Rich Oliver. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, Colour._

object Cyprus extends Flag
{ val name = "Cyprus"
  val ratio = 1.5

  def apply() =
  {
    val background = Rect(1.5, 1).fill(White)
    
    val cMap = ShapeGenOld(LineTail(0.4619 pp 0.3267), LineTail(0.458 pp 0.3252), LineTail(0.4564 pp 0.3245), LineTail(0.4517 pp 0.3248),
      LineTail(0.4472 pp 0.3227), LineTail(0.4393 pp 0.3173), LineTail(0.439 pp 0.317), LineTail(0.436 pp 0.3163), LineTail(0.4333 pp 0.3174),
      LineTail(0.4317 pp 0.3167), LineTail(0.4312 pp 0.3135), LineTail(0.4297 pp 0.3112), LineTail(0.4279 pp 0.3099), LineTail(0.4216 pp 0.3093),
      LineTail(0.4163 pp 0.3066), LineTail(0.4094 pp 0.3084), LineTail(0.4061 pp 0.3072), LineTail(0.3993 pp 0.301), LineTail(0.3958 pp 0.2993),
      LineTail(0.3946 pp 0.2994), LineTail(0.39 pp 0.2997), LineTail(0.388 pp 0.299), LineTail(0.3851 pp 0.2962), LineTail(0.3796 pp 0.2958),
      LineTail(0.3778 pp 0.2946), LineTail(0.3752 pp 0.2891), LineTail(0.3722 pp 0.2861), LineTail(0.3706 pp 0.2858), LineTail(0.3687 pp 0.2864),
      LineTail(0.3674 pp 0.2862), LineTail(0.3668 pp 0.2827), LineTail(0.3654 pp 0.2813), LineTail(0.3617 pp 0.28), LineTail(0.3588 pp 0.2775),
      LineTail(0.3562 pp 0.2762), LineTail(0.3527 pp 0.2763), LineTail(0.3527 pp 0.2771), LineTail(0.3493 pp 0.2752), LineTail(0.3428 pp 0.2746),
      LineTail(0.3401 pp 0.272), LineTail(0.3395 pp 0.2715), LineTail(0.3382 pp 0.2707), LineTail(0.3361 pp 0.2695), LineTail(0.3356 pp 0.2692),
      LineTail(0.3331 pp 0.2696), LineTail(0.3302 pp 0.2685), LineTail(0.329 pp 0.2703), LineTail(0.327 pp 0.2691), LineTail(0.3243 pp 0.2689),
      LineTail(0.321 pp 0.2704), LineTail(0.3185 pp 0.2716), LineTail(0.3166 pp 0.2713), LineTail(0.316 pp 0.2681), LineTail(0.3159 pp 0.2677),
      LineTail(0.314 pp 0.2651), LineTail(0.3103 pp 0.2629), LineTail(0.3098 pp 0.2623), LineTail(0.3057 pp 0.2569), LineTail(0.2978 pp 0.2486),
      LineTail(0.2912 pp 0.2453), LineTail(0.2843 pp 0.2432), LineTail(0.2794 pp 0.2392), LineTail(0.2668 pp 0.2329), LineTail(0.2468 pp 0.223),
      LineTail(0.2427 pp 0.2217), LineTail(0.2368 pp 0.2207), LineTail(0.2264 pp 0.2169), LineTail(0.2171 pp 0.2141), LineTail(0.2166 pp 0.2139),
      LineTail(0.2148 pp 0.2134), LineTail(0.2018 pp 0.2094), LineTail(0.1957 pp 0.2101), LineTail(0.1921 pp 0.2088), LineTail(0.1828 pp 0.2098),
      LineTail(0.1763 pp 0.2097), LineTail(0.1722 pp 0.2088), LineTail(0.1644 pp 0.2051), LineTail(0.1514 pp 0.1989), LineTail(0.1471 pp 0.1949),
      LineTail(0.1404 pp 0.1914), LineTail(0.1325 pp 0.1889), LineTail(0.1325 pp 0.1922), LineTail(0.1325 pp 0.1924), LineTail(0.1298 pp 0.1915),
      LineTail(0.1278 pp 0.1909), LineTail(0.1214 pp 0.1899), LineTail(0.1214 pp 0.1934), LineTail(0.1192 pp 0.1941), LineTail(0.1189 pp 0.194),
      LineTail(0.1058 pp 0.1897), LineTail(0.09132 pp 0.1883), LineTail(0.08405 pp 0.1859), LineTail(0.07861 pp 0.186), LineTail(0.07522 pp 0.185),
      LineTail(0.06856 pp 0.1842), LineTail(0.06618 pp 0.1848), LineTail(0.06564 pp 0.185), LineTail(0.04606 pp 0.1841), LineTail(0.0371 pp 0.1851),
      LineTail(0.03283 pp 0.1842), LineTail(0.02567 pp 0.1866), LineTail(0.01522 pp 0.1878), LineTail(0.01278 pp 0.1885), LineTail(0.00729 pp 0.1902),
      LineTail(0.004805 pp 0.1887), LineTail(0.002945 pp 0.1885), LineTail(-0.0015 pp 0.1901), LineTail(-0.0031 pp 0.1902),
      LineTail(-0.00677 pp 0.1889), LineTail(-0.008882 pp 0.1894), LineTail(-0.01084 pp 0.191), LineTail(-0.01545 pp 0.1916),
      LineTail(-0.01825 pp 0.194), LineTail(-0.03392 pp 0.1919), LineTail(-0.03817 pp 0.1932), LineTail(-0.05243 pp 0.1973),
      LineTail(-0.0547 pp 0.1972), LineTail(-0.05752 pp 0.1951), LineTail(-0.06177 pp 0.1935), LineTail(-0.06546 pp 0.1926),
      LineTail(-0.07048 pp 0.1924), LineTail(-0.0763 pp 0.1939), LineTail(-0.08231 pp 0.1969), LineTail(-0.08454 pp 0.1974),
      LineTail(-0.08949 pp 0.1969), LineTail(-0.09101 pp 0.1967), LineTail(-0.1001 pp 0.2016), LineTail(-0.1119 pp 0.209),
      LineTail(-0.1198 pp 0.2128), LineTail(-0.1228 pp 0.2133), LineTail(-0.1232 pp 0.2119), LineTail(-0.1217 pp 0.2072),
      LineTail(-0.1212 pp 0.2026), LineTail(-0.1214 pp 0.1983), LineTail(-0.1215 pp 0.1956), LineTail(-0.1207 pp 0.1933),
      LineTail(-0.1178 pp 0.1901), LineTail(-0.1166 pp 0.1869), LineTail(-0.1158 pp 0.1781), LineTail(-0.1158 pp 0.1691), LineTail(-0.1172 pp 0.1549),
      LineTail(-0.1176 pp 0.1527), LineTail(-0.1193 pp 0.1453), LineTail(-0.1208 pp 0.1386), LineTail(-0.1267 pp 0.1205), LineTail(-0.1284 pp 0.1183),
      LineTail(-0.1324 pp 0.1155), LineTail(-0.1414 pp 0.1094), LineTail(-0.148 pp 0.1056), LineTail(-0.1501 pp 0.1047), LineTail(-0.1555 pp 0.1043),
      LineTail(-0.1589 pp 0.1045), LineTail(-0.1631 pp 0.1067), LineTail(-0.1672 pp 0.1078), LineTail(-0.1726 pp 0.1117), LineTail(-0.1786 pp 0.1129),
      LineTail(-0.1851 pp 0.1167), LineTail(-0.1869 pp 0.1187), LineTail(-0.1904 pp 0.1191), LineTail(-0.1954 pp 0.1206), LineTail(-0.1954 pp 0.1206),
      LineTail(-0.1972 pp 0.1211), LineTail(-0.1985 pp 0.1215), LineTail(-0.1985 pp 0.1215), LineTail(-0.2048 pp 0.1217), LineTail(-0.2107 pp 0.1244),
      LineTail(-0.2138 pp 0.1251), LineTail(-0.2181 pp 0.1254), LineTail(-0.2225 pp 0.1233), LineTail(-0.2225 pp 0.1233), LineTail(-0.2247 pp 0.1222),
      LineTail(-0.2276 pp 0.1234), LineTail(-0.2297 pp 0.1232), LineTail(-0.232 pp 0.1197), LineTail(-0.2323 pp 0.1192), LineTail(-0.2339 pp 0.1183),
      LineTail(-0.2359 pp 0.1184), LineTail(-0.2375 pp 0.1184), LineTail(-0.2392 pp 0.1177), LineTail(-0.2415 pp 0.1167), LineTail(-0.2415 pp 0.1167),
      LineTail(-0.2435 pp 0.1158), LineTail(-0.2445 pp 0.1154), LineTail(-0.2445 pp 0.1154), LineTail(-0.2451 pp 0.1153), LineTail(-0.2462 pp 0.1151),
      LineTail(-0.2475 pp 0.1148), LineTail(-0.2498 pp 0.1162), LineTail(-0.2511 pp 0.1169), LineTail(-0.2532 pp 0.1174), LineTail(-0.2543 pp 0.1165),
      LineTail(-0.2545 pp 0.1127), LineTail(-0.2556 pp 0.1104), LineTail(-0.2588 pp 0.1075), LineTail(-0.2622 pp 0.1046), LineTail(-0.2645 pp 0.1012),
      LineTail(-0.2706 pp 0.0872), LineTail(-0.2744 pp 0.08156), LineTail(-0.2756 pp 0.08024), LineTail(-0.2796 pp 0.07581),
      LineTail(-0.2832 pp 0.07305), LineTail(-0.2914 pp 0.06669), LineTail(-0.2992 pp 0.06379), LineTail(-0.3059 pp 0.06243),
      LineTail(-0.3091 pp 0.06241), LineTail(-0.3153 pp 0.06323), LineTail(-0.3208 pp 0.06493), LineTail(-0.3269 pp 0.06874),
      LineTail(-0.3336 pp 0.0741), LineTail(-0.3432 pp 0.08364), LineTail(-0.3446 pp 0.08446), LineTail(-0.3449 pp 0.08465),
      LineTail(-0.3479 pp 0.08659), LineTail(-0.3502 pp 0.08654), LineTail(-0.3511 pp 0.08508), LineTail(-0.3515 pp 0.08297),
      LineTail(-0.352 pp 0.08111), LineTail(-0.3531 pp 0.0705), LineTail(-0.353 pp 0.06988), LineTail(-0.3524 pp 0.06551),
      LineTail(-0.3451 pp 0.05526), LineTail(-0.3428 pp 0.04982), LineTail(-0.3424 pp 0.04919), LineTail(-0.3409 pp 0.04665),
      LineTail(-0.3398 pp 0.04471), LineTail(-0.3396 pp 0.04452), LineTail(-0.3371 pp 0.03704), LineTail(-0.3389 pp 0.03281),
      LineTail(-0.338 pp 0.02999), LineTail(-0.3402 pp 0.02826), LineTail(-0.3406 pp 0.02582), LineTail(-0.3341 pp 0.01756),
      LineTail(-0.3326 pp 0.01392), LineTail(-0.3342 pp 0.008871), LineTail(-0.3368 pp 0.006086), LineTail(-0.3375 pp 0.005324),
      LineTail(-0.3371 pp 0.003191), LineTail(-0.3341 pp 0.0004657), LineTail(-0.329 pp -0.004118), LineTail(-0.3262 pp -0.01093),
      LineTail(-0.3241 pp -0.01275), LineTail(-0.3217 pp -0.01224), LineTail(-0.3204 pp -0.01327), LineTail(-0.3183 pp -0.01323),
      LineTail(-0.3163 pp -0.01525), LineTail(-0.315 pp -0.01588), LineTail(-0.3129 pp -0.0169), LineTail(-0.3113 pp -0.01902),
      LineTail(-0.3109 pp -0.02554), LineTail(-0.3079 pp -0.03364), LineTail(-0.3079 pp -0.03803), LineTail(-0.3078 pp -0.03866),
      LineTail(-0.306 pp -0.0419), LineTail(-0.3054 pp -0.04447), LineTail(-0.3064 pp -0.05125), LineTail(-0.304 pp -0.05343),
      LineTail(-0.3013 pp -0.05289), LineTail(-0.2997 pp -0.05316), LineTail(-0.2969 pp -0.05542), LineTail(-0.2937 pp -0.06042),
      LineTail(-0.2905 pp -0.05995), LineTail(-0.2884 pp -0.06093), LineTail(-0.2786 pp -0.06982), LineTail(-0.2764 pp -0.07095),
      LineTail(-0.2762 pp -0.07102), LineTail(-0.2746 pp -0.07182), LineTail(-0.2726 pp -0.07383), LineTail(-0.2695 pp -0.07178),
      LineTail(-0.2692 pp -0.07175), LineTail(-0.2658 pp -0.07145), LineTail(-0.2644 pp -0.07244), LineTail(-0.2615 pp -0.07436),
      LineTail(-0.2578 pp -0.07431), LineTail(-0.2497 pp -0.07679), LineTail(-0.2462 pp -0.07769), LineTail(-0.2391 pp -0.0821),
      LineTail(-0.2361 pp -0.08397), LineTail(-0.2343 pp -0.08542), LineTail(-0.2319 pp -0.08736), LineTail(-0.2272 pp -0.0894),
      LineTail(-0.2231 pp -0.09037), LineTail(-0.2209 pp -0.09089), LineTail(-0.2192 pp -0.09153), LineTail(-0.2192 pp -0.09143),
      LineTail(-0.2192 pp -0.09144), LineTail(-0.2192 pp -0.09154), LineTail(-0.2104 pp -0.09491), LineTail(-0.2056 pp -0.09603),
      LineTail(-0.2012 pp -0.09765), LineTail(-0.198 pp -0.09882), LineTail(-0.1957 pp -0.09886), LineTail(-0.1923 pp -0.09606),
      LineTail(-0.1891 pp -0.09587), LineTail(-0.1864 pp -0.09654), LineTail(-0.1838 pp -0.0961), LineTail(-0.1796 pp -0.09355),
      LineTail(-0.1788 pp -0.09235), LineTail(-0.1759 pp -0.09095), LineTail(-0.166 pp -0.08972), LineTail(-0.1631 pp -0.09066),
      LineTail(-0.1557 pp -0.08743), LineTail(-0.1507 pp -0.08882), LineTail(-0.1463 pp -0.08725), LineTail(-0.1366 pp -0.08939),
      LineTail(-0.1341 pp -0.09119), LineTail(-0.1312 pp -0.09447), LineTail(-0.1309 pp -0.09448), LineTail(-0.1277 pp -0.09455),
      LineTail(-0.129 pp -0.09676), LineTail(-0.1248 pp -0.1012), LineTail(-0.121 pp -0.1069), LineTail(-0.1208 pp -0.1077),
      LineTail(-0.118 pp -0.1158), LineTail(-0.1159 pp -0.1187), LineTail(-0.1145 pp -0.1227), LineTail(-0.1144 pp -0.1253),
      LineTail(-0.1167 pp -0.127), LineTail(-0.117 pp -0.128), LineTail(-0.1172 pp -0.1283), LineTail(-0.1167 pp -0.1294),
      LineTail(-0.1159 pp -0.1289), LineTail(-0.1143 pp -0.1281), LineTail(-0.1123 pp -0.1278), LineTail(-0.1089 pp -0.1281),
      LineTail(-0.1066 pp -0.1284), LineTail(-0.103 pp -0.1266), LineTail(-0.1006 pp -0.1254), LineTail(-0.09682 pp -0.127),
      LineTail(-0.09262 pp -0.127), LineTail(-0.09085 pp -0.1278), LineTail(-0.08586 pp -0.13), LineTail(-0.08331 pp -0.1303),
      LineTail(-0.08237 pp -0.1297), LineTail(-0.08177 pp -0.1282), LineTail(-0.08208 pp -0.1266), LineTail(-0.08399 pp -0.1244),
      LineTail(-0.08821 pp -0.1195), LineTail(-0.08993 pp -0.117), LineTail(-0.09125 pp -0.1133), LineTail(-0.09166 pp -0.1087),
      LineTail(-0.09195 pp -0.1055), LineTail(-0.09142 pp -0.1014), LineTail(-0.09029 pp -0.09991), LineTail(-0.08946 pp -0.09663),
      LineTail(-0.08946 pp -0.09665), LineTail(-0.08924 pp -0.09579), LineTail(-0.0869 pp -0.09268), LineTail(-0.08069 pp -0.08869),
      LineTail(-0.07402 pp -0.08272), LineTail(-0.06875 pp -0.07925), LineTail(-0.06332 pp -0.07689), LineTail(-0.06318 pp -0.07623),
      LineTail(-0.06257 pp -0.07648), LineTail(-0.04875 pp -0.07178), LineTail(-0.04852 pp -0.07175), LineTail(-0.04237 pp -0.07068),
      LineTail(-0.0008352 pp -0.0736), LineTail(0.0007048 pp -0.07314), LineTail(0.0007115 pp -0.07313), LineTail(0.002095 pp -0.06925),
      LineTail(0.00284 pp -0.06853), LineTail(0.003335 pp -0.06806), LineTail(0.007901 pp -0.06605), LineTail(0.01004 pp -0.06574),
      LineTail(0.01596 pp -0.0672), LineTail(0.01839 pp -0.0678), LineTail(0.02228 pp -0.06562), LineTail(0.02503 pp -0.06567),
      LineTail(0.03073 pp -0.06256), LineTail(0.03425 pp -0.06265), LineTail(0.03558 pp -0.06211), LineTail(0.04244 pp -0.05719),
      LineTail(0.04904 pp -0.0553), LineTail(0.05076 pp -0.05438), LineTail(0.05136 pp -0.05405), LineTail(0.05813 pp -0.05048),
      LineTail(0.06243 pp -0.04718), LineTail(0.06614 pp -0.04521), LineTail(0.0702 pp -0.04416), LineTail(0.08083 pp -0.04341),
      LineTail(0.08297 pp -0.03985), LineTail(0.08754 pp -0.03924), LineTail(0.09031 pp -0.03612), LineTail(0.09347 pp -0.03483),
      LineTail(0.09573 pp -0.03157), LineTail(0.09763 pp -0.02884), LineTail(0.1003 pp -0.0273), LineTail(0.1088 pp -0.02677),
      LineTail(0.1189 pp -0.02802), LineTail(0.1202 pp -0.02721), LineTail(0.1225 pp -0.01908), LineTail(0.1248 pp -0.01756),
      LineTail(0.1313 pp -0.008204), LineTail(0.1313 pp -0.004796), LineTail(0.1313 pp -0.001949), LineTail(0.1323 pp 0.002104),
      LineTail(0.1316 pp 0.009422), LineTail(0.1325 pp 0.01683), LineTail(0.1365 pp 0.02625), LineTail(0.14 pp 0.03005), LineTail(0.1457 pp 0.03388),
      LineTail(0.1485 pp 0.03514), LineTail(0.1526 pp 0.03582), LineTail(0.1526 pp 0.03582), LineTail(0.1532 pp 0.03593), LineTail(0.1542 pp 0.0361),
      LineTail(0.1683 pp 0.0363), BezierTail(0.1685 pp 0.03644, 0.1734 pp 0.03637, 0.1734 pp 0.03637), LineTail(0.1781 pp 0.03643),
      LineTail(0.19 pp 0.03432), LineTail(0.1908 pp 0.03417), LineTail(0.1948 pp 0.03288), LineTail(0.1994 pp 0.02928), LineTail(0.2043 pp 0.0245),
      LineTail(0.2052 pp 0.02361), LineTail(0.2084 pp 0.02227), LineTail(0.2094 pp 0.02186), LineTail(0.212 pp 0.02227), LineTail(0.2157 pp 0.02422),
      LineTail(0.2185 pp 0.02659), LineTail(0.2224 pp 0.02869), LineTail(0.2225 pp 0.02882), LineTail(0.2246 pp 0.03207), LineTail(0.2247 pp 0.03214),
      LineTail(0.2247 pp 0.03212), LineTail(0.2247 pp 0.03214), LineTail(0.2247 pp 0.03215), LineTail(0.2256 pp 0.03344), LineTail(0.233 pp 0.03633),
      LineTail(0.2417 pp 0.03684), LineTail(0.2427 pp 0.03737), LineTail(0.2432 pp 0.03766), LineTail(0.2455 pp 0.04036), LineTail(0.2477 pp 0.04038),
      LineTail(0.2529 pp 0.03849), LineTail(0.2566 pp 0.03922), LineTail(0.2595 pp 0.03806), LineTail(0.2617 pp 0.03832), LineTail(0.266 pp 0.03883),
      LineTail(0.2706 pp 0.0356), LineTail(0.2733 pp 0.03526), LineTail(0.2827 pp 0.02974), LineTail(0.2832 pp 0.02966), LineTail(0.2835 pp 0.02962),
      LineTail(0.284 pp 0.02953), LineTail(0.2851 pp 0.02986), LineTail(0.2868 pp 0.03034), LineTail(0.287 pp 0.03041), LineTail(0.2872 pp 0.03012),
      LineTail(0.2883 pp 0.0286), LineTail(0.2898 pp 0.02839), LineTail(0.2915 pp 0.03072), LineTail(0.2908 pp 0.03177), LineTail(0.2905 pp 0.03215),
      LineTail(0.2872 pp 0.03284), LineTail(0.2841 pp 0.03801), LineTail(0.287 pp 0.04111), LineTail(0.2825 pp 0.04665), LineTail(0.2818 pp 0.04757),
      LineTail(0.2815 pp 0.04796), LineTail(0.2799 pp 0.05033), LineTail(0.272 pp 0.06239), LineTail(0.2617 pp 0.07062), LineTail(0.2617 pp 0.07061),
      LineTail(0.2581 pp 0.0735), LineTail(0.2581 pp 0.07351), LineTail(0.2568 pp 0.07451), LineTail(0.2513 pp 0.0801), LineTail(0.2474 pp 0.08532),
      LineTail(0.2469 pp 0.08644), LineTail(0.2461 pp 0.08821), LineTail(0.2437 pp 0.09334), LineTail(0.2396 pp 0.0965), LineTail(0.2363 pp 0.1002),
      LineTail(0.2361 pp 0.1004), LineTail(0.2292 pp 0.1097), LineTail(0.2281 pp 0.1113), LineTail(0.2264 pp 0.1123), LineTail(0.223 pp 0.1123),
      LineTail(0.2227 pp 0.1126), LineTail(0.2225 pp 0.1128), LineTail(0.2226 pp 0.1129), LineTail(0.2243 pp 0.1146), LineTail(0.2261 pp 0.115),
      LineTail(0.2269 pp 0.1169), LineTail(0.2234 pp 0.127), LineTail(0.2233 pp 0.1274), LineTail(0.2231 pp 0.1306), LineTail(0.226 pp 0.1454),
      LineTail(0.2263 pp 0.1472), LineTail(0.2312 pp 0.1571), LineTail(0.2342 pp 0.1596), LineTail(0.2375 pp 0.1661), LineTail(0.2403 pp 0.1702),
      LineTail(0.2431 pp 0.1729), LineTail(0.2435 pp 0.1731), LineTail(0.2484 pp 0.1761), LineTail(0.2525 pp 0.1767), LineTail(0.2564 pp 0.1772),
      LineTail(0.2633 pp 0.1751), LineTail(0.2698 pp 0.1753), LineTail(0.271 pp 0.1754), LineTail(0.274 pp 0.1757), LineTail(0.2788 pp 0.1773),
      LineTail(0.2811 pp 0.1787), LineTail(0.2823 pp 0.1805), LineTail(0.2848 pp 0.1888), LineTail(0.2856 pp 0.1915), LineTail(0.2871 pp 0.1943),
      LineTail(0.296 pp 0.2045), LineTail(0.3027 pp 0.2107), LineTail(0.3177 pp 0.2217), LineTail(0.325 pp 0.2261), LineTail(0.3288 pp 0.2284),
      LineTail(0.3623 pp 0.2433), LineTail(0.3711 pp 0.2521), LineTail(0.3756 pp 0.2567), LineTail(0.3836 pp 0.2621), LineTail(0.393 pp 0.2659),
      LineTail(0.4011 pp 0.2723), LineTail(0.4029 pp 0.2746), LineTail(0.4051 pp 0.2817), LineTail(0.407 pp 0.2822), LineTail(0.4087 pp 0.2858),
      LineTail(0.4091 pp 0.2868), LineTail(0.4157 pp 0.2915), LineTail(0.4165 pp 0.2919), LineTail(0.4421 pp 0.3047), LineTail(0.4459 pp 0.3042),
      LineTail(0.4478 pp 0.3072), LineTail(0.4553 pp 0.3084), LineTail(0.4567 pp 0.3087), LineTail(0.4585 pp 0.3096), LineTail(0.4601 pp 0.3123),
      LineTail(0.4596 pp 0.3123), LineTail(0.4598 pp 0.3194), LineTail(0.4614 pp 0.3213), LineTail(0.4622 pp 0.326), LineTail(0.4631 pp 0.3271),
      LineTail(0.4639 pp 0.3282), LineTail(0.466 pp 0.3297), LineTail(0.4657 pp 0.3306), LineTail(0.4619 pp 0.3267)).fill(Colour(0xFFd57800))

    val leaf01 =  ShapeGenOld(LineTail(0.02184 pp -0.3446), BezierTail(0.02154 pp -0.3444, 0.02147 pp -0.3444, 0.02126 pp -0.3442),
      LineTail(0.02105 pp -0.344), BezierTail(0.02051 pp -0.3435, 0.01961 pp -0.3426, 0.01841 pp -0.3412),
      BezierTail(0.01619 pp -0.3387, 0.01321 pp -0.335, 0.0106 pp -0.3318),
      BezierTail(0.005405 pp -0.3253, 0.001115 pp -0.3197, 0.0003065 pp -0.3187), LineTail(-0.001015 pp -0.3169), LineTail(-0.01396 pp -0.3129),
      LineTail(-0.02341 pp -0.3085), LineTail(-0.01836 pp -0.3029), LineTail(-0.001015 pp -0.3103), LineTail(0.01193 pp -0.3129),
      LineTail(0.03587 pp -0.3305), LineTail(0.03585 pp -0.3306), BezierTail(0.0358 pp -0.3306, 0.03237 pp -0.3335, 0.02896 pp -0.3366),
      BezierTail(0.02743 pp -0.3379, 0.02552 pp -0.3397, 0.02416 pp -0.3412), BezierTail(0.02354 pp -0.3419, 0.02299 pp -0.3426, 0.02261 pp -0.3431),
      BezierTail(0.02238 pp -0.3434, 0.02225 pp -0.3437, 0.02219 pp -0.3439), LineTail(0.02368 pp -0.343), LineTail(0.02529 pp -0.3442),
      LineTail(0.02201 pp -0.3445), LineTail(0.02186 pp -0.3446), LineTail(0.02184 pp -0.3446)).fill(Colour(0xFF4e5b31))
    
    val leaf02 =  ShapeGenOld(LineTail(-0.06704 pp -0.3179), BezierTail(-0.07834 pp -0.3179, -0.08949 pp -0.3147, -0.1002 pp -0.3086),
      LineTail(-0.1002 pp -0.3086), LineTail(-0.1002 pp -0.3086), BezierTail(-0.1002 pp -0.3086, -0.09881 pp -0.3059, -0.09541 pp -0.3034),
      BezierTail(-0.09217 pp -0.301, -0.08634 pp -0.2982, -0.07691 pp -0.2981), LineTail(-0.07691 pp -0.2983),
      BezierTail(-0.06574 pp -0.2983, -0.05207 pp -0.3021, -0.03629 pp -0.3096),
      BezierTail(-0.03682 pp -0.31, -0.04921 pp -0.318, -0.06723 pp -0.318), LineTail(-0.06721 pp -0.3181),
      LineTail(-0.06704 pp -0.3179)).fill(Colour(0xFF4e5b31))
    
    val leaf03 =  ShapeGenOld(LineTail(-0.1162 pp -0.3072), BezierTail(-0.1183 pp -0.3072, -0.1201 pp -0.3064, -0.1214 pp -0.305),
      BezierTail(-0.1225 pp -0.3038, -0.1233 pp -0.3023, -0.1238 pp -0.3002), BezierTail(-0.1247 pp -0.2968, -0.1244 pp -0.2935, -0.1244 pp -0.2932),
      BezierTail(-0.1235 pp -0.2928, -0.1226 pp -0.2926, -0.1218 pp -0.2926), LineTail(-0.1216 pp -0.2926),
      BezierTail(-0.1174 pp -0.2926, -0.1141 pp -0.2962, -0.1127 pp -0.2998), BezierTail(-0.1119 pp -0.3018, -0.1118 pp -0.3036, -0.1123 pp -0.3049),
      BezierTail(-0.1128 pp -0.3063, -0.1143 pp -0.3072, -0.1162 pp -0.3072), LineTail(-0.1162 pp -0.3072)).fill(Colour(0xFF4e5b31))
    
    val leaf04 =  ShapeGenOld(LineTail(-0.03003 pp -0.2972), BezierTail(-0.05896 pp -0.2931, -0.07122 pp -0.2826, -0.07641 pp -0.2746),
      BezierTail(-0.08195 pp -0.266, -0.08082 pp -0.2581, -0.0808 pp -0.258), LineTail(-0.0808 pp -0.258), LineTail(-0.08078 pp -0.2579),
      BezierTail(-0.08077 pp -0.2579, -0.07901 pp -0.2575, -0.07635 pp -0.2575),
      BezierTail(-0.06865 pp -0.2575, -0.04896 pp -0.2613, -0.03002 pp -0.2972), LineTail(-0.03001 pp -0.2972), LineTail(-0.03004 pp -0.2972),
      LineTail(-0.03003 pp -0.2972)).fill(Colour(0xFF4e5b31))
    
    val leaf05 =  ShapeGenOld(LineTail(-0.08934 pp -0.2915), BezierTail(-0.1017 pp -0.2915, -0.1111 pp -0.2893, -0.1173 pp -0.2851),
      BezierTail(-0.1209 pp -0.2827, -0.1226 pp -0.2801, -0.1235 pp -0.2783), BezierTail(-0.1242 pp -0.2766, -0.1244 pp -0.2752, -0.1244 pp -0.2749),
      BezierTail(-0.1241 pp -0.2747, -0.1191 pp -0.2725, -0.1115 pp -0.2721), BezierTail(-0.1109 pp -0.2721, -0.1102 pp -0.2721, -0.1095 pp -0.2721),
      BezierTail(-0.09999 pp -0.2721, -0.08546 pp -0.2752, -0.07013 pp -0.29), LineTail(-0.07012 pp -0.2901), LineTail(-0.07014 pp -0.2901),
      BezierTail(-0.07714 pp -0.291, -0.0836 pp -0.2915, -0.08935 pp -0.2915), LineTail(-0.08934 pp -0.2915)).fill(Colour(0xFF4e5b31))
    
    val leaf06 =  ShapeGenOld(LineTail(-0.1492 pp -0.2892), BezierTail(-0.1622 pp -0.2892, -0.1801 pp -0.2852, -0.1913 pp -0.274),
      LineTail(-0.1913 pp -0.274), LineTail(-0.1913 pp -0.274), BezierTail(-0.1912 pp -0.274, -0.1791 pp -0.2709, -0.1654 pp -0.2706),
      BezierTail(-0.1646 pp -0.2705, -0.1638 pp -0.2705, -0.163 pp -0.2705), BezierTail(-0.1507 pp -0.2705, -0.1414 pp -0.2729, -0.1352 pp -0.2776),
      BezierTail(-0.133 pp -0.2792, -0.1318 pp -0.2807, -0.1315 pp -0.2822), BezierTail(-0.1313 pp -0.2835, -0.132 pp -0.2848, -0.1333 pp -0.2858),
      BezierTail(-0.1361 pp -0.2879, -0.1411 pp -0.289, -0.1482 pp -0.2892), LineTail(-0.1492 pp -0.2892),
      LineTail(-0.1492 pp -0.2892)).fill(Colour(0xFF4e5b31))
    
    val leaf07 =  ShapeGenOld(LineTail(-0.1914 pp -0.2675), BezierTail(-0.193 pp -0.2675, -0.1945 pp -0.2668, -0.1956 pp -0.2656),
      BezierTail(-0.1964 pp -0.2646, -0.197 pp -0.2632, -0.1974 pp -0.2614), BezierTail(-0.1981 pp -0.2583, -0.1979 pp -0.2553, -0.1979 pp -0.2553),
      LineTail(-0.1979 pp -0.2552), BezierTail(-0.1973 pp -0.2549, -0.1966 pp -0.2548, -0.1959 pp -0.2548), LineTail(-0.1959 pp -0.2548),
      BezierTail(-0.1939 pp -0.2548, -0.1925 pp -0.2558, -0.1915 pp -0.2567), BezierTail(-0.1903 pp -0.2579, -0.1893 pp -0.2594, -0.1887 pp -0.2611),
      BezierTail(-0.1881 pp -0.2628, -0.188 pp -0.2643, -0.1884 pp -0.2655), BezierTail(-0.1888 pp -0.2665, -0.1899 pp -0.2675, -0.1914 pp -0.2675),
      LineTail(-0.1914 pp -0.2675)).fill(Colour(0xFF4e5b31))
    
    val leaf08 =  ShapeGenOld(LineTail(-0.09388 pp -0.2653), BezierTail(-0.09597 pp -0.2653, -0.09785 pp -0.2645, -0.09916 pp -0.2631),
      BezierTail(-0.1002 pp -0.2619, -0.101 pp -0.2603, -0.1015 pp -0.2583), BezierTail(-0.1024 pp -0.2549, -0.1021 pp -0.2516, -0.1021 pp -0.2513),
      BezierTail(-0.1012 pp -0.2509, -0.1003 pp -0.2507, -0.09952 pp -0.2507), LineTail(-0.09933 pp -0.2507),
      BezierTail(-0.09514 pp -0.2507, -0.09185 pp -0.2543, -0.09043 pp -0.2579),
      BezierTail(-0.08963 pp -0.2599, -0.08948 pp -0.2617, -0.08998 pp -0.263),
      BezierTail(-0.09048 pp -0.2644, -0.09206 pp -0.2653, -0.09388 pp -0.2653), LineTail(-0.09388 pp -0.2653)).fill(Colour(0xFF4e5b31))
    
    val leaf09 =  ShapeGenOld(LineTail(-0.1414 pp -0.2633), BezierTail(-0.1676 pp -0.2624, -0.1812 pp -0.255, -0.188 pp -0.2489),
      BezierTail(-0.1919 pp -0.2454, -0.194 pp -0.242, -0.1951 pp -0.2398), BezierTail(-0.1961 pp -0.2377, -0.1965 pp -0.236, -0.1966 pp -0.2357),
      LineTail(-0.1966 pp -0.2357), BezierTail(-0.1966 pp -0.2357, -0.1941 pp -0.2348, -0.1901 pp -0.2347), LineTail(-0.1895 pp -0.2347),
      BezierTail(-0.1806 pp -0.2347, -0.1626 pp -0.2384, -0.1414 pp -0.2633), LineTail(-0.1414 pp -0.2633),
      LineTail(-0.1414 pp -0.2633)).fill(Colour(0xFF4e5b31))
    
    val leaf10 =  ShapeGenOld(LineTail(-0.1129 pp -0.2607), BezierTail(-0.134 pp -0.2575, -0.146 pp -0.2484, -0.1523 pp -0.2414),
      BezierTail(-0.1589 pp -0.234, -0.1609 pp -0.2271, -0.161 pp -0.2268), BezierTail(-0.1609 pp -0.2268, -0.1583 pp -0.2261, -0.1545 pp -0.226),
      BezierTail(-0.1542 pp -0.226, -0.1539 pp -0.226, -0.1536 pp -0.226), BezierTail(-0.1533 pp -0.226, -0.153 pp -0.226, -0.1527 pp -0.226),
      BezierTail(-0.1421 pp -0.2262, -0.1228 pp -0.2311, -0.1129 pp -0.2607), LineTail(-0.1129 pp -0.2607),
      LineTail(-0.1129 pp -0.2607)).fill(Colour(0xFF4e5b31))
    
    val leaf11 =  ShapeGenOld(LineTail(-0.2212 pp -0.2505), BezierTail(-0.2282 pp -0.2505, -0.2349 pp -0.2497, -0.2412 pp -0.2482),
      BezierTail(-0.2522 pp -0.2454, -0.257 pp -0.2412, -0.259 pp -0.2381), BezierTail(-0.2601 pp -0.2364, -0.2606 pp -0.2349, -0.2607 pp -0.2339),
      BezierTail(-0.2609 pp -0.233, -0.2608 pp -0.2322, -0.2608 pp -0.2321), LineTail(-0.2607 pp -0.2321),
      BezierTail(-0.2607 pp -0.2321, -0.2534 pp -0.23, -0.2433 pp -0.2299), BezierTail(-0.2429 pp -0.2299, -0.2425 pp -0.2299, -0.2421 pp -0.2299),
      BezierTail(-0.2372 pp -0.2299, -0.2324 pp -0.2303, -0.2278 pp -0.2313), BezierTail(-0.2102 pp -0.2348, -0.2011 pp -0.248, -0.201 pp -0.2482),
      LineTail(-0.201 pp -0.2482), LineTail(-0.2011 pp -0.2482), BezierTail(-0.2012 pp -0.2482, -0.2095 pp -0.2505, -0.2213 pp -0.2505),
      LineTail(-0.2212 pp -0.2505)).fill(Colour(0xFF4e5b31))
    
    val leaf12 =  ShapeGenOld(LineTail(-0.1723 pp -0.2327), BezierTail(-0.1744 pp -0.2327, -0.1763 pp -0.232, -0.1776 pp -0.2306),
      BezierTail(-0.1786 pp -0.2294, -0.1794 pp -0.2278, -0.1799 pp -0.2257), BezierTail(-0.1808 pp -0.2224, -0.1805 pp -0.2191, -0.1805 pp -0.2187),
      BezierTail(-0.1796 pp -0.2184, -0.1787 pp -0.2182, -0.1779 pp -0.2182), LineTail(-0.1777 pp -0.2182),
      BezierTail(-0.1735 pp -0.2182, -0.1702 pp -0.2218, -0.1688 pp -0.2254), BezierTail(-0.168 pp -0.2274, -0.1679 pp -0.2291, -0.1684 pp -0.2304),
      BezierTail(-0.1689 pp -0.2318, -0.1704 pp -0.2327, -0.1723 pp -0.2327), LineTail(-0.1723 pp -0.2327)).fill(Colour(0xFF4e5b31))
    
    val leaf13 =  ShapeGenOld(LineTail(-0.2091 pp -0.2277), BezierTail(-0.2093 pp -0.2277, -0.2191 pp -0.2251, -0.229 pp -0.219),
      BezierTail(-0.2383 pp -0.2133, -0.2496 pp -0.2032, -0.251 pp -0.1876), LineTail(-0.251 pp -0.1876), LineTail(-0.2509 pp -0.1876),
      BezierTail(-0.2509 pp -0.1876, -0.2501 pp -0.1874, -0.2488 pp -0.1873), BezierTail(-0.2486 pp -0.1873, -0.2485 pp -0.1873, -0.2483 pp -0.1873),
      BezierTail(-0.2455 pp -0.1873, -0.2399 pp -0.1881, -0.233 pp -0.1933), BezierTail(-0.2243 pp -0.1999, -0.2163 pp -0.2115, -0.2091 pp -0.2277),
      LineTail(-0.2091 pp -0.2277)).fill(Colour(0xFF4e5b31))
    
    val leaf14 =  ShapeGenOld(LineTail(-0.185 pp -0.2268), BezierTail(-0.1851 pp -0.2268, -0.1921 pp -0.224, -0.2008 pp -0.2167),
      BezierTail(-0.209 pp -0.2099, -0.2204 pp -0.1976, -0.2287 pp -0.1778), BezierTail(-0.2287 pp -0.1778, -0.2268 pp -0.1768, -0.2239 pp -0.1766),
      BezierTail(-0.2237 pp -0.1766, -0.2235 pp -0.1766, -0.2234 pp -0.1766), BezierTail(-0.2231 pp -0.1766, -0.2229 pp -0.1766, -0.2226 pp -0.1766),
      BezierTail(-0.2186 pp -0.1767, -0.2125 pp -0.1784, -0.2057 pp -0.1857), BezierTail(-0.1979 pp -0.1941, -0.1909 pp -0.208, -0.185 pp -0.2268),
      LineTail(-0.185 pp -0.2269), LineTail(-0.185 pp -0.2268), LineTail(-0.185 pp -0.2268)).fill(Colour(0xFF4e5b31))
    
    val leaf15 =  ShapeGenOld(LineTail(-0.2474 pp -0.225), BezierTail(-0.2554 pp -0.225, -0.2581 pp -0.2207, -0.2589 pp -0.2188),
      BezierTail(-0.2603 pp -0.2157, -0.2599 pp -0.2126, -0.2599 pp -0.2126), LineTail(-0.2599 pp -0.2126),
      BezierTail(-0.2586 pp -0.2123, -0.2572 pp -0.2121, -0.2559 pp -0.2121), LineTail(-0.2556 pp -0.2121),
      BezierTail(-0.2493 pp -0.2121, -0.2443 pp -0.2153, -0.2421 pp -0.2185), BezierTail(-0.2417 pp -0.2191, -0.2404 pp -0.2213, -0.2415 pp -0.223),
      BezierTail(-0.2423 pp -0.2243, -0.2443 pp -0.225, -0.2474 pp -0.225), LineTail(-0.2474 pp -0.225)).fill(Colour(0xFF4e5b31))
    
    val leaf16 =  ShapeGenOld(LineTail(-0.2652 pp -0.2026), BezierTail(-0.2696 pp -0.2026, -0.2765 pp -0.2023, -0.2837 pp -0.201),
      BezierTail(-0.2909 pp -0.1997, -0.2949 pp -0.1936, -0.2971 pp -0.1887), BezierTail(-0.2994 pp -0.1834, -0.3001 pp -0.1784, -0.3001 pp -0.1783),
      LineTail(-0.3001 pp -0.1783), BezierTail(-0.2779 pp -0.179, -0.2675 pp -0.1854, -0.2626 pp -0.1908),
      BezierTail(-0.2576 pp -0.1962, -0.2573 pp -0.2019, -0.2573 pp -0.2023), LineTail(-0.2573 pp -0.2023),
      BezierTail(-0.2573 pp -0.2023, -0.2604 pp -0.2026, -0.2652 pp -0.2026), LineTail(-0.2652 pp -0.2026)).fill(Colour(0xFF4e5b31))
    
    val leaf17 =  ShapeGenOld(LineTail(-0.2421 pp -0.1796), BezierTail(-0.2422 pp -0.1795, -0.2447 pp -0.1764, -0.2472 pp -0.1725),
      BezierTail(-0.2498 pp -0.1684, -0.2522 pp -0.1642, -0.2522 pp -0.1607), BezierTail(-0.2522 pp -0.1573, -0.253 pp -0.1503, -0.2535 pp -0.1463),
      BezierTail(-0.2541 pp -0.1414, -0.2547 pp -0.1371, -0.2547 pp -0.1367), LineTail(-0.2547 pp -0.1368),
      BezierTail(-0.2542 pp -0.137, -0.2409 pp -0.1419, -0.2409 pp -0.1544), BezierTail(-0.2409 pp -0.1669, -0.2421 pp -0.1794, -0.2421 pp -0.1796),
      LineTail(-0.2421 pp -0.1796), LineTail(-0.2421 pp -0.1796), LineTail(-0.2421 pp -0.1796)).fill(Colour(0xFF4e5b31))
    
    val leaf18 =  ShapeGenOld(LineTail(-0.2593 pp -0.1784), BezierTail(-0.2595 pp -0.1784, -0.2596 pp -0.1784, -0.2598 pp -0.1783),
      BezierTail(-0.2637 pp -0.1777, -0.2706 pp -0.1747, -0.2782 pp -0.1703), BezierTail(-0.2867 pp -0.1654, -0.2936 pp -0.1602, -0.2976 pp -0.1557),
      BezierTail(-0.3012 pp -0.1515, -0.3034 pp -0.1449, -0.304 pp -0.1361), BezierTail(-0.3045 pp -0.1299, -0.304 pp -0.1247, -0.3039 pp -0.1242),
      BezierTail(-0.3032 pp -0.1239, -0.3025 pp -0.1238, -0.3017 pp -0.1238), BezierTail(-0.2988 pp -0.1239, -0.2956 pp -0.1252, -0.2914 pp -0.1281),
      BezierTail(-0.2881 pp -0.1304, -0.2844 pp -0.1335, -0.2806 pp -0.1373), BezierTail(-0.2739 pp -0.1439, -0.2686 pp -0.1505, -0.2686 pp -0.1506),
      LineTail(-0.2685 pp -0.1507), BezierTail(-0.2681 pp -0.1515, -0.2643 pp -0.1583, -0.2613 pp -0.1649),
      BezierTail(-0.2595 pp -0.1691, -0.2584 pp -0.1722, -0.258 pp -0.1745), BezierTail(-0.2577 pp -0.1758, -0.2577 pp -0.1767, -0.258 pp -0.1775),
      BezierTail(-0.2581 pp -0.1778, -0.2584 pp -0.1781, -0.2586 pp -0.1782), BezierTail(-0.2589 pp -0.1783, -0.2591 pp -0.1784, -0.2594 pp -0.1784),
      LineTail(-0.2593 pp -0.1784)).fill(Colour(0xFF4e5b31))

    RArr[GraphicElem](background, cMap,
      leaf01, leaf02, leaf03, leaf04, leaf05, leaf06, leaf07, leaf08, leaf09, leaf10, leaf11, leaf12, leaf13, leaf14, leaf15, leaf16, leaf17, leaf18,
      leaf01.negX, leaf02.negX, leaf03.negX, leaf04.negX, leaf05.negX, leaf06.negX, leaf07.negX, leaf08.negX, leaf09.negX, leaf10.negX,
      leaf11.negX, leaf12.negX, leaf13.negX, leaf14.negX, leaf15.negX, leaf16.negX, leaf17.negX, leaf18.negX
    )
  }
}