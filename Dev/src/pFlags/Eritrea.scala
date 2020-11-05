/* Copyright 2018-20 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._

object Eritrea extends Flag
{ val name = "Eritrea"
  val ratio = 2.0
  
  val apply: GraphicElems =
  { 
    val blueRectangle = Rect(2, 0.5, 0 pp 0.25).fillHex(0xFF4189dd)
    val greenRectangle = Rect(2, 0.5, 0 pp -0.25).fillHex(0xFF12ad2b)
    val redTriangle: TriangleFill = Triangle(-1 pp 0.5, -1 pp -0.5, 1 pp 0).fillHex(0xFFea0437)
    
    val olive = PolyCurve(LineTail(-0.5395 pp -0.2383), BezierTail(-0.5611 pp -0.2443, -0.5735 pp -0.2611, -0.5732 pp -0.2769),
      LineTail(-0.4668 pp -0.2765), BezierTail(-0.4658 pp -0.2595, -0.4798 pp -0.2437, -0.5011 pp -0.2375),
      BezierTail(-0.3969 pp -0.2354, -0.3064 pp -0.1964, -0.2899 pp -0.1713), BezierTail(-0.3062 pp -0.1642, -0.325 pp -0.1756, -0.3385 pp -0.173),
      BezierTail(-0.3066 pp -0.1583, -0.2112 pp -0.09651, -0.2267 pp -0.03028),
      BezierTail(-0.2387 pp -0.06707, -0.2753 pp -0.0975, -0.2908 pp -0.1057),
      BezierTail(-0.2552 pp -0.05154, -0.2065 pp 0.004824, -0.2488 pp 0.04847),
      BezierTail(-0.2466 pp 0.02317, -0.2648 pp -0.004666, -0.2729 pp -0.006758),
      BezierTail(-0.2522 pp 0.05044, -0.2325 pp 0.1223, -0.2773 pp 0.1696), BezierTail(-0.2714 pp 0.1525, -0.2738 pp 0.1042, -0.2819 pp 0.102),
      BezierTail(-0.2843 pp 0.1409, -0.291 pp 0.2228, -0.3319 pp 0.2216), BezierTail(-0.3189 pp 0.21, -0.3132 pp 0.1784, -0.313 pp 0.1465),
      BezierTail(-0.3223 pp 0.1683, -0.3329 pp 0.1848, -0.3556 pp 0.201), BezierTail(-0.3739 pp 0.2362, -0.4066 pp 0.2653, -0.4393 pp 0.2862),
      BezierTail(-0.4356 pp 0.2606, -0.4326 pp 0.2404, -0.3968 pp 0.2138), BezierTail(-0.4154 pp 0.215, -0.4341 pp 0.2503, -0.454 pp 0.2513),
      BezierTail(-0.4699 pp 0.2521, -0.4823 pp 0.2369, -0.5082 pp 0.2457), BezierTail(-0.5054 pp 0.2371, -0.4933 pp 0.2333, -0.4907 pp 0.227),
      BezierTail(-0.4963 pp 0.2232, -0.5095 pp 0.2276, -0.5204 pp 0.2333), BezierTail(-0.5054 pp 0.2128, -0.482 pp 0.2007, -0.4622 pp 0.2048),
      BezierTail(-0.4386 pp 0.2093, -0.4133 pp 0.2069, -0.3892 pp 0.1932), BezierTail(-0.3954 pp 0.19, -0.4193 pp 0.19, -0.4347 pp 0.192),
      BezierTail(-0.4207 pp 0.1778, -0.4114 pp 0.1684, -0.3871 pp 0.1686), BezierTail(-0.3654 pp 0.1687, -0.354 pp 0.1802, -0.3484 pp 0.1731),
      BezierTail(-0.3348 pp 0.157, -0.3256 pp 0.1408, -0.3142 pp 0.1218), BezierTail(-0.3394 pp 0.1191, -0.3318 pp 0.1503, -0.3598 pp 0.1663),
      BezierTail(-0.3756 pp 0.1337, -0.3417 pp 0.09505, -0.3187 pp 0.07899), BezierTail(-0.3184 pp 0.05449, -0.3148 pp 0.034, -0.3044 pp 0.01535),
      BezierTail(-0.2974 pp 0.002203, -0.2883 pp -0.01125, -0.2917 pp -0.04093),
      BezierTail(-0.3055 pp -0.03088, -0.319 pp 0.003005, -0.3141 pp 0.0299),
      BezierTail(-0.3313 pp 0.02519, -0.3381 pp -0.005166, -0.3299 pp -0.02045),
      BezierTail(-0.3239 pp -0.03209, -0.3198 pp -0.05435, -0.3268 pp -0.06431),
      BezierTail(-0.3336 pp -0.07358, -0.3344 pp -0.07254, -0.3343 pp -0.09245),
      BezierTail(-0.334 pp -0.1044, -0.3407 pp -0.1184, -0.3515 pp -0.1283), BezierTail(-0.3493 pp -0.1199, -0.3467 pp -0.1054, -0.3492 pp -0.09641),
      BezierTail(-0.3578 pp -0.1109, -0.3792 pp -0.126, -0.386 pp -0.1416), BezierTail(-0.3927 pp -0.1573, -0.3943 pp -0.1844, -0.4265 pp -0.1906),
      BezierTail(-0.4679 pp -0.1988, -0.4822 pp -0.206, -0.5088 pp -0.2169), BezierTail(-0.5117 pp -0.1967, -0.5029 pp -0.1545, -0.486 pp -0.1569),
      BezierTail(-0.4695 pp -0.1599, -0.4194 pp -0.1397, -0.4373 pp -0.09751), BezierTail(-0.4409 pp -0.1108, -0.4527 pp -0.1238, -0.4654 pp -0.1243),
      BezierTail(-0.4516 pp -0.1065, -0.427 pp -0.08796, -0.439 pp -0.058), BezierTail(-0.4457 pp -0.07074, -0.4561 pp -0.08602, -0.472 pp -0.09461),
      BezierTail(-0.455 pp -0.06199, -0.47 pp -0.05224, -0.4903 pp -0.0791), BezierTail(-0.4979 pp -0.08947, -0.5025 pp -0.1102, -0.5073 pp -0.1366),
      BezierTail(-0.5153 pp -0.1152, -0.5148 pp -0.08692, -0.5242 pp -0.06399),
      BezierTail(-0.534 pp -0.03919, -0.5111 pp -0.03264, -0.5004 pp -0.03463),
      BezierTail(-0.474 pp -0.04167, -0.43 pp -0.04166, -0.4332 pp 0.001873), BezierTail(-0.4446 pp -0.01285, -0.4646 pp -0.0174, -0.4861 pp -0.0121),
      BezierTail(-0.4619 pp 0.005577, -0.4428 pp 0.03883, -0.4698 pp 0.05619), BezierTail(-0.4707 pp 0.03778, -0.4849 pp 0.01714, -0.504 pp 0.007632),
      BezierTail(-0.5084 pp 0.02316, -0.5084 pp 0.03965, -0.5046 pp 0.05772), BezierTail(-0.5152 pp 0.04654, -0.523 pp 0.02328, -0.5291 pp -0.00316),
      BezierTail(-0.5296 pp 0.02298, -0.5247 pp 0.04181, -0.521 pp 0.05587), BezierTail(-0.5154 pp 0.07641, -0.5016 pp 0.06304, -0.4805 pp 0.06153),
      BezierTail(-0.4601 pp 0.06037, -0.4321 pp 0.07163, -0.4374 pp 0.09925), BezierTail(-0.4444 pp 0.08832, -0.4587 pp 0.08407, -0.4732 pp 0.08523),
      BezierTail(-0.4557 pp 0.09586, -0.4251 pp 0.1148, -0.4419 pp 0.1438), BezierTail(-0.4489 pp 0.1328, -0.4512 pp 0.1234, -0.4716 pp 0.12),
      BezierTail(-0.4662 pp 0.1323, -0.4654 pp 0.1497, -0.4495 pp 0.1566), BezierTail(-0.4778 pp 0.1622, -0.4939 pp 0.1437, -0.5022 pp 0.1146),
      BezierTail(-0.5055 pp 0.1348, -0.5095 pp 0.1422, -0.5103 pp 0.1569), BezierTail(-0.495 pp 0.1739, -0.4934 pp 0.207, -0.5264 pp 0.2144),
      BezierTail(-0.5284 pp 0.1973, -0.5278 pp 0.1933, -0.5241 pp 0.1793), BezierTail(-0.5396 pp 0.1885, -0.5614 pp 0.1936, -0.576 pp 0.1806),
      BezierTail(-0.5661 pp 0.1699, -0.5509 pp 0.1606, -0.5273 pp 0.1722), BezierTail(-0.5329 pp 0.154, -0.5474 pp 0.1571, -0.5672 pp 0.164),
      BezierTail(-0.5551 pp 0.1412, -0.5398 pp 0.1372, -0.5228 pp 0.1398), BezierTail(-0.514 pp 0.1165, -0.5136 pp 0.09873, -0.5395 pp 0.06473),
      BezierTail(-0.5383 pp 0.08586, -0.5398 pp 0.1016, -0.5566 pp 0.1187), BezierTail(-0.571 pp 0.1328, -0.5826 pp 0.1181, -0.5602 pp 0.08676),
      BezierTail(-0.5738 pp 0.0968, -0.5892 pp 0.1172, -0.5939 pp 0.1376), BezierTail(-0.5984 pp 0.1124, -0.5943 pp 0.08271, -0.5804 pp 0.06608),
      BezierTail(-0.587 pp 0.05904, -0.5945 pp 0.06683, -0.6056 pp 0.08406), BezierTail(-0.6014 pp 0.02891, -0.5778 pp 0.01827, -0.5462 pp 0.03056),
      BezierTail(-0.5453 pp 0.0002905, -0.5453 pp -0.02774, -0.5435 pp -0.06431),
      BezierTail(-0.562 pp -0.03778, -0.5853 pp -0.018, -0.5984 pp -0.01305),
      BezierTail(-0.6024 pp -0.02789, -0.5871 pp -0.04722, -0.5786 pp -0.05801),
      BezierTail(-0.5916 pp -0.05531, -0.62 pp -0.03373, -0.62 pp -0.03373), BezierTail(-0.6228 pp -0.05831, -0.591 pp -0.08109, -0.5705 pp -0.09128),
      BezierTail(-0.5946 pp -0.09023, -0.6053 pp -0.08109, -0.6209 pp -0.0661), BezierTail(-0.6206 pp -0.1343, -0.547 pp -0.1225, -0.5332 pp -0.112),
      BezierTail(-0.5314 pp -0.1455, -0.5288 pp -0.1843, -0.527 pp -0.2179), BezierTail(-0.5478 pp -0.2143, -0.5462 pp -0.208, -0.564 pp -0.2064),
      BezierTail(-0.6134 pp -0.2045, -0.6526 pp -0.1471, -0.6656 pp -0.1049), BezierTail(-0.6693 pp -0.1118, -0.6663 pp -0.1191, -0.6697 pp -0.1276),
      BezierTail(-0.6778 pp -0.1069, -0.688 pp -0.08015, -0.7017 pp -0.0677),
      BezierTail(-0.6982 pp -0.07977, -0.6979 pp -0.09205, -0.6991 pp -0.1146),
      BezierTail(-0.7038 pp -0.09999, -0.7082 pp -0.09551, -0.7085 pp -0.07834),
      BezierTail(-0.7082 pp -0.06516, -0.6958 pp -0.05544, -0.6965 pp -0.0369),
      BezierTail(-0.697 pp -0.02336, -0.7093 pp 0.005975, -0.7111 pp 0.02869), BezierTail(-0.7171 pp 0.005312, -0.7209 pp -0.01933, -0.73 pp -0.0338),
      BezierTail(-0.7255 pp -0.008837, -0.7269 pp 0.008388, -0.7192 pp 0.02519),
      BezierTail(-0.7104 pp 0.04287, -0.7028 pp 0.05866, -0.7087 pp 0.07651), BezierTail(-0.7144 pp 0.06962, -0.7124 pp 0.06335, -0.7267 pp 0.0466),
      BezierTail(-0.7298 pp 0.06482, -0.7083 pp 0.09397, -0.6874 pp 0.1057), BezierTail(-0.6726 pp 0.1135, -0.654 pp 0.1412, -0.6661 pp 0.1603),
      BezierTail(-0.68 pp 0.1503, -0.6862 pp 0.1369, -0.7058 pp 0.1139), BezierTail(-0.6918 pp 0.1685, -0.6555 pp 0.1828, -0.6121 pp 0.183),
      BezierTail(-0.6025 pp 0.183, -0.5831 pp 0.1865, -0.5776 pp 0.1992), BezierTail(-0.5899 pp 0.1945, -0.6044 pp 0.1939, -0.6173 pp 0.1965),
      BezierTail(-0.6079 pp 0.2102, -0.5881 pp 0.2084, -0.5697 pp 0.2085), BezierTail(-0.5553 pp 0.2086, -0.5327 pp 0.2106, -0.5236 pp 0.2311),
      BezierTail(-0.5412 pp 0.2235, -0.5687 pp 0.2219, -0.586 pp 0.2273), BezierTail(-0.5585 pp 0.2416, -0.5154 pp 0.2431, -0.4933 pp 0.2618),
      BezierTail(-0.5185 pp 0.2806, -0.5815 pp 0.2574, -0.6213 pp 0.2301), BezierTail(-0.6102 pp 0.2403, -0.5926 pp 0.2583, -0.5829 pp 0.2729),
      BezierTail(-0.6047 pp 0.2833, -0.6598 pp 0.2223, -0.6787 pp 0.1861), BezierTail(-0.6967 pp 0.1759, -0.7039 pp 0.16, -0.7109 pp 0.1488),
      BezierTail(-0.7013 pp 0.1813, -0.7003 pp 0.2048, -0.6923 pp 0.2315), BezierTail(-0.7541 pp 0.2103, -0.7284 pp 0.09618, -0.7418 pp 0.06888),
      BezierTail(-0.7403 pp 0.09902, -0.7415 pp 0.1377, -0.7539 pp 0.1577), BezierTail(-0.773 pp 0.1432, -0.7745 pp 0.05778, -0.7566 pp -0.01333),
      BezierTail(-0.7631 pp 0.005703, -0.7753 pp 0.02347, -0.7792 pp 0.04662),
      BezierTail(-0.8073 pp -0.004719, -0.7627 pp -0.06529, -0.7253 pp -0.1131),
      BezierTail(-0.7534 pp -0.0985, -0.7812 pp -0.067, -0.7995 pp -0.0406), BezierTail(-0.7944 pp -0.1325, -0.6985 pp -0.1516, -0.6836 pp -0.1742),
      BezierTail(-0.7038 pp -0.1648, -0.7425 pp -0.1461, -0.7589 pp -0.1657), BezierTail(-0.7322 pp -0.1718, -0.7109 pp -0.1789, -0.6937 pp -0.1902),
      BezierTail(-0.6688 pp -0.2213, -0.6216 pp -0.2349, -0.5395 pp -0.2383), LineTail(-0.5395 pp -0.2383)).fill(Colour(0xFFffc726))
    
    Arr(greenRectangle, blueRectangle, redTriangle, olive)
  }
}