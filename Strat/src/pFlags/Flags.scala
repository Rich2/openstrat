/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, Colour._

/** The flag trait is a builder for Graphic Elements and sequences of Graphic Elements, representing the flag, it is not itself. */
trait Flag
{ def name: String
  def ratio: Double
  def apply(): Arr[DisplayElem]
  def rect: PolygonClass = Rectangle(ratio)
  def parentStr: PolygonParent = Rectangle(ratio).parentElems(name + " flag", apply)
  def parent(evObj: Any = this): PolygonParent = Rectangle(ratio).parentElems(evObj, apply)

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): Arr[DisplayAffineElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottom(colours: Colour*): Arr[DisplayAffineElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio,
     1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour))

  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottomRepeat(numBands: Int, colours: Colour*): Arr[DisplayAffineElem] = iUntilMap(0, numBands){ i =>
    val r1 = Rectangle.fromTL(ratio, 1.0 / numBands, -ratio / 2 vv + 0.5)
    val r2 = r1.slate(0, - i.toDouble / numBands)
    r2.fill(colours(i %% colours.length))
  }
}

object PlainFlagMaker
{
  def apply(colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = colour.str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): Arr[DisplayElem] = Arr(rect.fill(colour))
  }
}

object TextFlagMaker
{
  def apply(str: String, colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): Arr[DisplayElem] = Arr(rect.fill(colour), TextGraphic(str, 40))
  }
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  val apply: Arr[DisplayElem] = leftToRight(Red, Blue, Gold)
}

object Chad extends Flag
{ val ratio = 1.5
  val name = "Chad"
  def apply(): Arr[DisplayElem] = leftToRight(Blue, Yellow, Red)
}

object China extends Flag
{ val name = "China"
  val ratio = 1.5
  val apply: Arr[DisplayElem] =Arr[DisplayElem](Rectangle(1.5, 1).fill(Red),
    Rectangle.fromTL(0.75, 0.5, - 0.75 vv 0.5).fill(DarkBlue))  
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  val apply: Arr[DisplayElem] =
  { val rw = rect.fill(White)
    val circ = Circle(Vec2Z, 0.6).fill(Colour.fromInts(188, 0,45))
    Arr(rw, circ)
  }
}

object WhiteFlag extends Flag
{ val name = "White"
  val ratio = 1.5
  val apply: Arr[DisplayElem] = Arr[DisplayElem](Rectangle(1.5, 1).fill(White))
}
  
object CommonShapesInFlags extends Flag
{ val name = "CommonShapesInFlags"
  val ratio = 1.5

  val apply: Arr[DisplayElem] =
  {
    Arr[DisplayElem](
      Rectangle(1.5, 1).fill(White),

      //off centre cross
      Rectangle(ratio, 0.25).fill(Green),
      Rectangle(0.25, 1).fill(Green).slate(-0.3 vv 0),

      Star5().scale(0.1).slate(-0.6 vv 0.3).fill(Magenta),

      Star7(0.382).scale(0.1).slate(-0.3 vv 0.3).fill(Red),

      Star5().scale(0.1).slate(0.3 vv 0.3).draw(1, Lime),
      
      //hexagram
      Star3().scale(0.15).slate(0.6 vv 0.3).draw(1.5, Blue),
      Star3().scale(0.15).rotate(deg180).slate(0.6 vv 0.3).draw(1.5, Blue),

      //crescent
      CircleOld.segs(0.225).slate(-0.6 vv -0.3).fill(Red),
      CircleOld.segs(0.2).slate(-0.6 vv -0.3).slate(0.04 vv 0).fill(White),

      //composite star ()
      Star5().scale(0.15).slate(-0.3 vv 0).fill(Gold),
      Star5().scale(0.1).slate(-0.3 vv 0).fill(Magenta),
      
      Pentagram().scale(0.1).slate(0 vv 0.3).draw(2, Colour(0xFF006233)),
    )
  }
}

object Iraq extends Flag
{ val name = "Iraq"
  val ratio = 1.5
  val apply: Arr[DisplayElem] =
  { topToBottom(Colour(0xFFce1126), White, Black) ++ Arr[DisplayElem](
      PolyCurve(LineTail(-0.34 vv 0.2997), BezierTail(-0.3409 vv 0.3002, -0.3419 vv 0.301, -0.3423 vv 0.3015),
        BezierTail(-0.3428 vv 0.3022, -0.3425 vv 0.3022, -0.3403 vv 0.3016), BezierTail(-0.3365 vv 0.3006, -0.334 vv 0.301, -0.3315 vv 0.3031),
        LineTail(-0.3293 vv 0.3049), LineTail(-0.3268 vv 0.3036), BezierTail(-0.3254 vv 0.3029, -0.3239 vv 0.3024, -0.3234 vv 0.3025),
        BezierTail(-0.3223 vv 0.3028, -0.32 vv 0.3058, -0.3201 vv 0.3068), BezierTail(-0.3202 vv 0.3081, -0.3191 vv 0.3077, -0.3186 vv 0.3064),
        BezierTail(-0.3176 vv 0.3036, -0.3191 vv 0.3005, -0.3217 vv 0.2998), BezierTail(-0.323 vv 0.2995, -0.3242 vv 0.2996, -0.3261 vv 0.3003),
        BezierTail(-0.3285 vv 0.3011, -0.3289 vv 0.3011, -0.3301 vv 0.3002), BezierTail(-0.3328 vv 0.2981, -0.3366 vv 0.2979, -0.34 vv 0.2997),
        LineTail(-0.34 vv 0.2997)).fill(Colour(0xFF007a3d)),
      
    PolyCurve(LineTail(-0.3304 vv 0.3084), BezierTail(-0.3313 vv 0.3096, -0.3325 vv 0.3141, -0.3321 vv 0.3153),
      BezierTail(-0.3318 vv 0.3162, -0.3314 vv 0.3164, -0.3306 vv 0.3161), BezierTail(-0.329 vv 0.3157, -0.3287 vv 0.3146, -0.3289 vv 0.311),
      BezierTail(-0.3291 vv 0.3081, -0.3295 vv 0.3073, -0.3304 vv 0.3084), LineTail(-0.3304 vv 0.3084)).fill(Colour(0xFF007a3d)),
      PolyCurve(LineTail(-0.4429 vv 0.3117), BezierTail(-0.4432 vv 0.3095, -0.4391 vv 0.3041, -0.4372 vv 0.3031),
        BezierTail(-0.4384 vv 0.3025, -0.44 vv 0.3028, -0.4412 vv 0.3021), BezierTail(-0.4478 vv 0.2955, -0.4718 vv 0.2721, -0.4762 vv 0.2665),
        BezierTail(-0.4632 vv 0.2662, -0.4488 vv 0.2667, -0.4366 vv 0.2672), BezierTail(-0.4366 vv 0.2761, -0.4283 vv 0.2765, -0.4227 vv 0.2797),
        BezierTail(-0.4198 vv 0.2752, -0.4125 vv 0.2755, -0.4116 vv 0.2687), LineTail(-0.4116 vv 0.2393), LineTail(-0.5227 vv 0.2393),
        BezierTail(-0.5246 vv 0.2307, -0.5324 vv 0.2241, -0.5433 vv 0.2268), BezierTail(-0.5399 vv 0.2303, -0.5342 vv 0.2315, -0.5322 vv 0.2364),
        BezierTail(-0.5305 vv 0.247, -0.5356 vv 0.2535, -0.5389 vv 0.2595), BezierTail(-0.5335 vv 0.2615, -0.5326 vv 0.262, -0.5271 vv 0.2658),
        BezierTail(-0.531 vv 0.2539, -0.5169 vv 0.2552, -0.5065 vv 0.2555), BezierTail(-0.5062 vv 0.2595, -0.5063 vv 0.2643, -0.5094 vv 0.2648),
        BezierTail(-0.5054 vv 0.2663, -0.5048 vv 0.2668, -0.4984 vv 0.2722), LineTail(-0.4984 vv 0.2562), LineTail(-0.4215 vv 0.2564),
        BezierTail(-0.4215 vv 0.2614, -0.4202 vv 0.2694, -0.4241 vv 0.2694), BezierTail(-0.4279 vv 0.2694, -0.4243 vv 0.2591, -0.4272 vv 0.2591),
        LineTail(-0.4866 vv 0.2591), LineTail(-0.4867 vv 0.2693), BezierTail(-0.4842 vv 0.2718, -0.4844 vv 0.2716, -0.4673 vv 0.2888),
        BezierTail(-0.4655 vv 0.2905, -0.4535 vv 0.3014, -0.4429 vv 0.3117), LineTail(-0.4429 vv 0.3117)).fill(Colour(0xFF007a3d)),
      PolyCurve(LineTail(-0.2945 vv 0.3121), BezierTail(-0.2903 vv 0.3099, -0.2871 vv 0.3068, -0.282 vv 0.3055),
        BezierTail(-0.2826 vv 0.3034, -0.2845 vv 0.3026, -0.2849 vv 0.3003), LineTail(-0.2849 vv 0.2555),
        BezierTail(-0.2793 vv 0.2543, -0.2781 vv 0.2575, -0.2754 vv 0.2592), BezierTail(-0.2746 vv 0.252, -0.2701 vv 0.245, -0.2702 vv 0.2394),
        LineTail(-0.2945 vv 0.2394), LineTail(-0.2945 vv 0.3121), LineTail(-0.2945 vv 0.3121)).fill(Colour(0xFF007a3d)),
      PolyCurve(LineTail(-0.3268 vv 0.2881), LineTail(-0.318 vv 0.2958), LineTail(-0.318 vv 0.2567), LineTail(-0.3117 vv 0.2567),
        LineTail(-0.3119 vv 0.3006), BezierTail(-0.3093 vv 0.3032, -0.3042 vv 0.3069, -0.303 vv 0.3095), LineTail(-0.303 vv 0.2394),
        LineTail(-0.3587 vv 0.2394), BezierTail(-0.3595 vv 0.254, -0.3597 vv 0.269, -0.3427 vv 0.2658), LineTail(-0.3427 vv 0.2717),
        BezierTail(-0.3432 vv 0.2727, -0.3441 vv 0.2715, -0.3444 vv 0.2728), BezierTail(-0.3417 vv 0.2755, -0.3408 vv 0.2762, -0.3335 vv 0.2825),
        LineTail(-0.3333 vv 0.2567), LineTail(-0.3269 vv 0.2567), BezierTail(-0.3269 vv 0.2567, -0.3268 vv 0.2871, -0.3268 vv 0.2881),
        LineTail(-0.3268 vv 0.2881)).fill(Colour(0xFF007a3d)),
      PolyCurve(LineTail(-0.3478 vv 0.2571), BezierTail(-0.3466 vv 0.2553, -0.3425 vv 0.2553, -0.3427 vv 0.2583),
        BezierTail(-0.3434 vv 0.2608, -0.3487 vv 0.2599, -0.3478 vv 0.2571), LineTail(-0.3478 vv 0.2571)).fill(Colour(0xFFFFFFFF)),
      CircleOld.segs(0.0068).slate(-0.5091 vv 0.2311).fill(Colour(0xFF007a3d)),
      PolyCurve(LineTail(-0.4041 vv 0.312), BezierTail(-0.3999 vv 0.3098, -0.3967 vv 0.3067, -0.3916 vv 0.3054),
        BezierTail(-0.3922 vv 0.3033, -0.394 vv 0.3025, -0.3945 vv 0.3003), LineTail(-0.3945 vv 0.2554),
        BezierTail(-0.3889 vv 0.2542, -0.3877 vv 0.2574, -0.385 vv 0.2591), BezierTail(-0.3842 vv 0.2519, -0.3797 vv 0.2449, -0.3798 vv 0.2393),
        LineTail(-0.4041 vv 0.2393), LineTail(-0.4041 vv 0.3121), LineTail(-0.4041 vv 0.312)).fill(Colour(0xFF007a3d))
    ).scale(2.18978).slate(.892 vv -.595)
  }
}

object India extends Flag
{ val name = "India"
  val ratio = 1.5
  val apply: Arr[DisplayElem] =
  { val spoke = PolyCurve(LineTail(-0.75 vv 0.3833), LineTail(-0.746 vv 0.4533), BezierTail(-0.746 vv 0.4533, -0.75 vv 0.4867, -0.75 vv 0.4867), BezierTail(-0.75 vv 0.4867, -0.754 vv 0.4533, -0.754 vv 0.4533), LineTail(-0.75 vv 0.3833), LineTail(-0.75 vv 0.3833)).slate(0.75, -0.5).fill(Colour(0xFF000080))
    val spokes = iToMap(0,23){i => spoke.rotate(deg30/2*i)}
    val rimNotch = CircleOld.segs(0.875/75).slate(0, -17.5/150).rotate(deg30/4).fill(Colour(0xFF000080))
    val rimNotches = iToMap(0,23){i => rimNotch.rotate(deg30/2*i)}
    val outerCircle = CircleOld.segs(20.0/75).fill(Colour(0xFF000080))
    val middleCircle = CircleOld.segs(17.5/75).fill(Colour(0xFFFFFFFF))
    val innerCircle = CircleOld.segs(3.5/75).fill(Colour(0xFF000080))
    topToBottom(Colour(0xFFFF9933), White, Colour(0xFF138808)) ++ Arr(outerCircle, middleCircle, innerCircle) ++ spokes ++ rimNotches
  }
}

object Eritrea extends Flag
{ val name = "Eritrea"
  val ratio = 2.0
  val apply: Arr[DisplayElem] =
  { val blueRectangle = Rectangle(2, 0.5, 0 vv 0.25).fill(Colour(0xFF4189dd))
    val greenRectangle =Rectangle(2, 0.5, 0 vv -0.25).fill(Colour(0xFF12ad2b))
    val redTriangle = Triangle.fill(-1 vv 0.5, -1 vv -0.5, 1 vv 0, Colour(0xFFea0437))
    val olive = PolyCurve(LineTail(-0.5395 vv -0.2383), BezierTail(-0.5611 vv -0.2443, -0.5735 vv -0.2611, -0.5732 vv -0.2769), LineTail(-0.4668 vv -0.2765), BezierTail(-0.4658 vv -0.2595, -0.4798 vv -0.2437, -0.5011 vv -0.2375), BezierTail(-0.3969 vv -0.2354, -0.3064 vv -0.1964, -0.2899 vv -0.1713), BezierTail(-0.3062 vv -0.1642, -0.325 vv -0.1756, -0.3385 vv -0.173), BezierTail(-0.3066 vv -0.1583, -0.2112 vv -0.09651, -0.2267 vv -0.03028), BezierTail(-0.2387 vv -0.06707, -0.2753 vv -0.0975, -0.2908 vv -0.1057), BezierTail(-0.2552 vv -0.05154, -0.2065 vv 0.004824, -0.2488 vv 0.04847), BezierTail(-0.2466 vv 0.02317, -0.2648 vv -0.004666, -0.2729 vv -0.006758), BezierTail(-0.2522 vv 0.05044, -0.2325 vv 0.1223, -0.2773 vv 0.1696), BezierTail(-0.2714 vv 0.1525, -0.2738 vv 0.1042, -0.2819 vv 0.102), BezierTail(-0.2843 vv 0.1409, -0.291 vv 0.2228, -0.3319 vv 0.2216), BezierTail(-0.3189 vv 0.21, -0.3132 vv 0.1784, -0.313 vv 0.1465), BezierTail(-0.3223 vv 0.1683, -0.3329 vv 0.1848, -0.3556 vv 0.201), BezierTail(-0.3739 vv 0.2362, -0.4066 vv 0.2653, -0.4393 vv 0.2862), BezierTail(-0.4356 vv 0.2606, -0.4326 vv 0.2404, -0.3968 vv 0.2138), BezierTail(-0.4154 vv 0.215, -0.4341 vv 0.2503, -0.454 vv 0.2513), BezierTail(-0.4699 vv 0.2521, -0.4823 vv 0.2369, -0.5082 vv 0.2457), BezierTail(-0.5054 vv 0.2371, -0.4933 vv 0.2333, -0.4907 vv 0.227), BezierTail(-0.4963 vv 0.2232, -0.5095 vv 0.2276, -0.5204 vv 0.2333), BezierTail(-0.5054 vv 0.2128, -0.482 vv 0.2007, -0.4622 vv 0.2048), BezierTail(-0.4386 vv 0.2093, -0.4133 vv 0.2069, -0.3892 vv 0.1932), BezierTail(-0.3954 vv 0.19, -0.4193 vv 0.19, -0.4347 vv 0.192), BezierTail(-0.4207 vv 0.1778, -0.4114 vv 0.1684, -0.3871 vv 0.1686), BezierTail(-0.3654 vv 0.1687, -0.354 vv 0.1802, -0.3484 vv 0.1731), BezierTail(-0.3348 vv 0.157, -0.3256 vv 0.1408, -0.3142 vv 0.1218), BezierTail(-0.3394 vv 0.1191, -0.3318 vv 0.1503, -0.3598 vv 0.1663), BezierTail(-0.3756 vv 0.1337, -0.3417 vv 0.09505, -0.3187 vv 0.07899), BezierTail(-0.3184 vv 0.05449, -0.3148 vv 0.034, -0.3044 vv 0.01535), BezierTail(-0.2974 vv 0.002203, -0.2883 vv -0.01125, -0.2917 vv -0.04093), BezierTail(-0.3055 vv -0.03088, -0.319 vv 0.003005, -0.3141 vv 0.0299), BezierTail(-0.3313 vv 0.02519, -0.3381 vv -0.005166, -0.3299 vv -0.02045), BezierTail(-0.3239 vv -0.03209, -0.3198 vv -0.05435, -0.3268 vv -0.06431), BezierTail(-0.3336 vv -0.07358, -0.3344 vv -0.07254, -0.3343 vv -0.09245), BezierTail(-0.334 vv -0.1044, -0.3407 vv -0.1184, -0.3515 vv -0.1283), BezierTail(-0.3493 vv -0.1199, -0.3467 vv -0.1054, -0.3492 vv -0.09641), BezierTail(-0.3578 vv -0.1109, -0.3792 vv -0.126, -0.386 vv -0.1416), BezierTail(-0.3927 vv -0.1573, -0.3943 vv -0.1844, -0.4265 vv -0.1906), BezierTail(-0.4679 vv -0.1988, -0.4822 vv -0.206, -0.5088 vv -0.2169), BezierTail(-0.5117 vv -0.1967, -0.5029 vv -0.1545, -0.486 vv -0.1569), BezierTail(-0.4695 vv -0.1599, -0.4194 vv -0.1397, -0.4373 vv -0.09751), BezierTail(-0.4409 vv -0.1108, -0.4527 vv -0.1238, -0.4654 vv -0.1243), BezierTail(-0.4516 vv -0.1065, -0.427 vv -0.08796, -0.439 vv -0.058), BezierTail(-0.4457 vv -0.07074, -0.4561 vv -0.08602, -0.472 vv -0.09461), BezierTail(-0.455 vv -0.06199, -0.47 vv -0.05224, -0.4903 vv -0.0791), BezierTail(-0.4979 vv -0.08947, -0.5025 vv -0.1102, -0.5073 vv -0.1366), BezierTail(-0.5153 vv -0.1152, -0.5148 vv -0.08692, -0.5242 vv -0.06399), BezierTail(-0.534 vv -0.03919, -0.5111 vv -0.03264, -0.5004 vv -0.03463), BezierTail(-0.474 vv -0.04167, -0.43 vv -0.04166, -0.4332 vv 0.001873), BezierTail(-0.4446 vv -0.01285, -0.4646 vv -0.0174, -0.4861 vv -0.0121), BezierTail(-0.4619 vv 0.005577, -0.4428 vv 0.03883, -0.4698 vv 0.05619), BezierTail(-0.4707 vv 0.03778, -0.4849 vv 0.01714, -0.504 vv 0.007632), BezierTail(-0.5084 vv 0.02316, -0.5084 vv 0.03965, -0.5046 vv 0.05772), BezierTail(-0.5152 vv 0.04654, -0.523 vv 0.02328, -0.5291 vv -0.00316), BezierTail(-0.5296 vv 0.02298, -0.5247 vv 0.04181, -0.521 vv 0.05587), BezierTail(-0.5154 vv 0.07641, -0.5016 vv 0.06304, -0.4805 vv 0.06153), BezierTail(-0.4601 vv 0.06037, -0.4321 vv 0.07163, -0.4374 vv 0.09925), BezierTail(-0.4444 vv 0.08832, -0.4587 vv 0.08407, -0.4732 vv 0.08523), BezierTail(-0.4557 vv 0.09586, -0.4251 vv 0.1148, -0.4419 vv 0.1438), BezierTail(-0.4489 vv 0.1328, -0.4512 vv 0.1234, -0.4716 vv 0.12), BezierTail(-0.4662 vv 0.1323, -0.4654 vv 0.1497, -0.4495 vv 0.1566), BezierTail(-0.4778 vv 0.1622, -0.4939 vv 0.1437, -0.5022 vv 0.1146), BezierTail(-0.5055 vv 0.1348, -0.5095 vv 0.1422, -0.5103 vv 0.1569), BezierTail(-0.495 vv 0.1739, -0.4934 vv 0.207, -0.5264 vv 0.2144), BezierTail(-0.5284 vv 0.1973, -0.5278 vv 0.1933, -0.5241 vv 0.1793), BezierTail(-0.5396 vv 0.1885, -0.5614 vv 0.1936, -0.576 vv 0.1806), BezierTail(-0.5661 vv 0.1699, -0.5509 vv 0.1606, -0.5273 vv 0.1722), BezierTail(-0.5329 vv 0.154, -0.5474 vv 0.1571, -0.5672 vv 0.164), BezierTail(-0.5551 vv 0.1412, -0.5398 vv 0.1372, -0.5228 vv 0.1398), BezierTail(-0.514 vv 0.1165, -0.5136 vv 0.09873, -0.5395 vv 0.06473), BezierTail(-0.5383 vv 0.08586, -0.5398 vv 0.1016, -0.5566 vv 0.1187), BezierTail(-0.571 vv 0.1328, -0.5826 vv 0.1181, -0.5602 vv 0.08676), BezierTail(-0.5738 vv 0.0968, -0.5892 vv 0.1172, -0.5939 vv 0.1376), BezierTail(-0.5984 vv 0.1124, -0.5943 vv 0.08271, -0.5804 vv 0.06608), BezierTail(-0.587 vv 0.05904, -0.5945 vv 0.06683, -0.6056 vv 0.08406), BezierTail(-0.6014 vv 0.02891, -0.5778 vv 0.01827, -0.5462 vv 0.03056), BezierTail(-0.5453 vv 0.0002905, -0.5453 vv -0.02774, -0.5435 vv -0.06431), BezierTail(-0.562 vv -0.03778, -0.5853 vv -0.018, -0.5984 vv -0.01305), BezierTail(-0.6024 vv -0.02789, -0.5871 vv -0.04722, -0.5786 vv -0.05801), BezierTail(-0.5916 vv -0.05531, -0.62 vv -0.03373, -0.62 vv -0.03373), BezierTail(-0.6228 vv -0.05831, -0.591 vv -0.08109, -0.5705 vv -0.09128), BezierTail(-0.5946 vv -0.09023, -0.6053 vv -0.08109, -0.6209 vv -0.0661), BezierTail(-0.6206 vv -0.1343, -0.547 vv -0.1225, -0.5332 vv -0.112), BezierTail(-0.5314 vv -0.1455, -0.5288 vv -0.1843, -0.527 vv -0.2179), BezierTail(-0.5478 vv -0.2143, -0.5462 vv -0.208, -0.564 vv -0.2064), BezierTail(-0.6134 vv -0.2045, -0.6526 vv -0.1471, -0.6656 vv -0.1049), BezierTail(-0.6693 vv -0.1118, -0.6663 vv -0.1191, -0.6697 vv -0.1276), BezierTail(-0.6778 vv -0.1069, -0.688 vv -0.08015, -0.7017 vv -0.0677), BezierTail(-0.6982 vv -0.07977, -0.6979 vv -0.09205, -0.6991 vv -0.1146), BezierTail(-0.7038 vv -0.09999, -0.7082 vv -0.09551, -0.7085 vv -0.07834), BezierTail(-0.7082 vv -0.06516, -0.6958 vv -0.05544, -0.6965 vv -0.0369), BezierTail(-0.697 vv -0.02336, -0.7093 vv 0.005975, -0.7111 vv 0.02869), BezierTail(-0.7171 vv 0.005312, -0.7209 vv -0.01933, -0.73 vv -0.0338), BezierTail(-0.7255 vv -0.008837, -0.7269 vv 0.008388, -0.7192 vv 0.02519), BezierTail(-0.7104 vv 0.04287, -0.7028 vv 0.05866, -0.7087 vv 0.07651), BezierTail(-0.7144 vv 0.06962, -0.7124 vv 0.06335, -0.7267 vv 0.0466), BezierTail(-0.7298 vv 0.06482, -0.7083 vv 0.09397, -0.6874 vv 0.1057), BezierTail(-0.6726 vv 0.1135, -0.654 vv 0.1412, -0.6661 vv 0.1603), BezierTail(-0.68 vv 0.1503, -0.6862 vv 0.1369, -0.7058 vv 0.1139), BezierTail(-0.6918 vv 0.1685, -0.6555 vv 0.1828, -0.6121 vv 0.183), BezierTail(-0.6025 vv 0.183, -0.5831 vv 0.1865, -0.5776 vv 0.1992), BezierTail(-0.5899 vv 0.1945, -0.6044 vv 0.1939, -0.6173 vv 0.1965), BezierTail(-0.6079 vv 0.2102, -0.5881 vv 0.2084, -0.5697 vv 0.2085), BezierTail(-0.5553 vv 0.2086, -0.5327 vv 0.2106, -0.5236 vv 0.2311), BezierTail(-0.5412 vv 0.2235, -0.5687 vv 0.2219, -0.586 vv 0.2273), BezierTail(-0.5585 vv 0.2416, -0.5154 vv 0.2431, -0.4933 vv 0.2618), BezierTail(-0.5185 vv 0.2806, -0.5815 vv 0.2574, -0.6213 vv 0.2301), BezierTail(-0.6102 vv 0.2403, -0.5926 vv 0.2583, -0.5829 vv 0.2729), BezierTail(-0.6047 vv 0.2833, -0.6598 vv 0.2223, -0.6787 vv 0.1861), BezierTail(-0.6967 vv 0.1759, -0.7039 vv 0.16, -0.7109 vv 0.1488), BezierTail(-0.7013 vv 0.1813, -0.7003 vv 0.2048, -0.6923 vv 0.2315), BezierTail(-0.7541 vv 0.2103, -0.7284 vv 0.09618, -0.7418 vv 0.06888), BezierTail(-0.7403 vv 0.09902, -0.7415 vv 0.1377, -0.7539 vv 0.1577), BezierTail(-0.773 vv 0.1432, -0.7745 vv 0.05778, -0.7566 vv -0.01333), BezierTail(-0.7631 vv 0.005703, -0.7753 vv 0.02347, -0.7792 vv 0.04662), BezierTail(-0.8073 vv -0.004719, -0.7627 vv -0.06529, -0.7253 vv -0.1131), BezierTail(-0.7534 vv -0.0985, -0.7812 vv -0.067, -0.7995 vv -0.0406), BezierTail(-0.7944 vv -0.1325, -0.6985 vv -0.1516, -0.6836 vv -0.1742), BezierTail(-0.7038 vv -0.1648, -0.7425 vv -0.1461, -0.7589 vv -0.1657), BezierTail(-0.7322 vv -0.1718, -0.7109 vv -0.1789, -0.6937 vv -0.1902), BezierTail(-0.6688 vv -0.2213, -0.6216 vv -0.2349, -0.5395 vv -0.2383), LineTail(-0.5395 vv -0.2383)).fill(Colour(0xFFffc726))
    Arr[DisplayElem](
      greenRectangle,
      blueRectangle,
      redTriangle,
      olive
    )
  }
}

object PapuaNewGuinea extends Flag
{ val name = "Papua New Guinea"
  val ratio = 4.0/3
  val apply: Arr[DisplayElem] =
  { val base = Rectangle(ratio, 1).fill(Black)
    val topRighttriangle = Triangle.fill(-2.0/3 vv 0.5, 2.0/3 vv 0.5, 2.0/3 vv -0.5, Colour(0xFFce1126))
    val bird = PolyCurve(LineTail(0.04247 vv 0.2211), BezierTail(0.03302 vv 0.2089, 0.01065 vv 0.2334, -0.01615 vv 0.2339), BezierTail(-0.04295 vv 0.2345, -0.06194 vv 0.2082, -0.06975 vv 0.1993), BezierTail(-0.07757 vv 0.1904, -0.1027 vv 0.1435, -0.1021 vv 0.1384), BezierTail(-0.1016 vv 0.1334, -0.0865 vv 0.1524, -0.08092 vv 0.1558), BezierTail(-0.08036 vv 0.1491, -0.08706 vv 0.1412, -0.08538 vv 0.139), BezierTail(-0.08371 vv 0.1368, -0.06584 vv 0.1524, -0.0597 vv 0.153), BezierTail(-0.05803 vv 0.1485, -0.06305 vv 0.144, -0.06138 vv 0.1373), BezierTail(-0.05914 vv 0.1351, -0.04909 vv 0.1485, -0.04239 vv 0.1513), BezierTail(-0.04128 vv 0.1463, -0.04853 vv 0.1368, -0.04463 vv 0.1306), BezierTail(-0.04183 vv 0.1301, -0.02508 vv 0.1496, -0.0195 vv 0.1518), BezierTail(-0.01392 vv 0.1541, -0.02229 vv 0.1334, -0.0195 vv 0.1317), BezierTail(-0.01448 vv 0.1351, 0.001159 vv 0.153, 0.006184 vv 0.1552), BezierTail(0.01121 vv 0.1574, 0.005626 vv 0.1323, 0.007859 vv 0.1323), BezierTail(0.014 vv 0.1368, 0.02963 vv 0.158, 0.03131 vv 0.1613), BezierTail(0.03298 vv 0.1647, 0.02963 vv 0.129, 0.04694 vv 0.11), BezierTail(0.06425 vv 0.09098, 0.09049 vv 0.06921, 0.09328 vv 0.06921), BezierTail(0.09663 vv 0.07535, 0.09217 vv 0.08819, 0.09496 vv 0.08764), BezierTail(0.1028 vv 0.07926, 0.1173 vv 0.06698, 0.1206 vv 0.06698), BezierTail(0.124 vv 0.06698, 0.1184 vv 0.08038, 0.1229 vv 0.08038), BezierTail(0.1318 vv 0.072, 0.1575 vv 0.03906, 0.2077 vv 0.02399), BezierTail(0.2044 vv 0.03348, 0.1971 vv 0.04185, 0.1966 vv 0.04632), BezierTail(0.1999 vv 0.04576, 0.2117 vv 0.04018, 0.2139 vv 0.04074), BezierTail(0.2161 vv 0.04129, 0.1893 vv 0.07424, 0.191 vv 0.0787), BezierTail(0.1971 vv 0.07926, 0.1999 vv 0.07535, 0.2022 vv 0.07814), BezierTail(0.1994 vv 0.08261, 0.1759 vv 0.1089, 0.1759 vv 0.115), BezierTail(0.1759 vv 0.1211, 0.1832 vv 0.1139, 0.1854 vv 0.115), BezierTail(0.1876 vv 0.1161, 0.1765 vv 0.1396, 0.1787 vv 0.1424), BezierTail(0.1809 vv 0.1451, 0.2055 vv 0.1273, 0.2116 vv 0.1273), BezierTail(0.2178 vv 0.1273, 0.2133 vv 0.1491, 0.2161 vv 0.1502), BezierTail(0.2189 vv 0.1513, 0.2396 vv 0.1384, 0.244 vv 0.1384), BezierTail(0.2485 vv 0.1384, 0.2479 vv 0.1479, 0.2496 vv 0.1468), BezierTail(0.2513 vv 0.1457, 0.2999 vv 0.09713, 0.2831 vv 0.04018), BezierTail(0.2664 vv -0.000582, 0.2329 vv -0.02236, 0.1837 vv -0.00449), BezierTail(0.1865 vv -0.008957, 0.2128 vv -0.03073, 0.2412 vv -0.02291), BezierTail(0.2697 vv -0.0151, 0.3043 vv 0.007235, 0.3004 vv 0.06307), BezierTail(0.2965 vv 0.1189, 0.2781 vv 0.1267, 0.2753 vv 0.1351), BezierTail(0.2725 vv 0.1435, 0.2798 vv 0.1329, 0.2859 vv 0.1306), BezierTail(0.292 vv 0.1284, 0.2848 vv 0.1424, 0.2831 vv 0.1485), BezierTail(0.2814 vv 0.1546, 0.3015 vv 0.1357, 0.3066 vv 0.134), BezierTail(0.3116 vv 0.1323, 0.2948 vv 0.1798, 0.2898 vv 0.1831), BezierTail(0.2848 vv 0.1865, 0.2948 vv 0.182, 0.2982 vv 0.1809), BezierTail(0.3015 vv 0.1798, 0.2675 vv 0.2328, 0.2602 vv 0.2328), BezierTail(0.253 vv 0.2328, 0.2664 vv 0.2356, 0.2703 vv 0.2356), BezierTail(0.2742 vv 0.2356, 0.2312 vv 0.2702, 0.2239 vv 0.2702), BezierTail(0.2167 vv 0.2702, 0.2396 vv 0.2791, 0.2418 vv 0.2797), BezierTail(0.2457 vv 0.2791, 0.1921 vv 0.3082, 0.1441 vv 0.2791), BezierTail(0.1413 vv 0.2814, 0.1667 vv 0.299, 0.1675 vv 0.3065), BezierTail(0.1678 vv 0.3085, 0.1592 vv 0.3126, 0.1586 vv 0.3149), BezierTail(0.158 vv 0.3171, 0.172 vv 0.3238, 0.1726 vv 0.3288), BezierTail(0.1692 vv 0.3333, 0.1631 vv 0.3316, 0.1597 vv 0.3327), BezierTail(0.1589 vv 0.3369, 0.1715 vv 0.3473, 0.1703 vv 0.3517), BezierTail(0.1695 vv 0.3542, 0.1603 vv 0.3551, 0.158 vv 0.3534), BezierTail(0.1558 vv 0.3517, 0.1639 vv 0.3651, 0.1625 vv 0.369), BezierTail(0.1614 vv 0.3707, 0.1569 vv 0.3696, 0.1525 vv 0.3685), BezierTail(0.1497 vv 0.369, 0.167 vv 0.393, 0.1636 vv 0.3964), BezierTail(0.1603 vv 0.3997, 0.1134 vv 0.3718, 0.09607 vv 0.3406), BezierTail(0.07877 vv 0.3093, 0.09217 vv 0.2842, 0.08993 vv 0.2736), BezierTail(0.0877 vv 0.263, 0.08491 vv 0.2523, 0.07765 vv 0.2523), BezierTail(0.07039 vv 0.2523, 0.05364 vv 0.2635, 0.04806 vv 0.2691), BezierTail(0.04247 vv 0.2747, 0.04136 vv 0.2786, 0.03354 vv 0.2808), BezierTail(0.02572 vv 0.2831, 0.007859 vv 0.2764, 0.001159 vv 0.2758), BezierTail(-0.005541 vv 0.2752, -0.03178 vv 0.2808, -0.03346 vv 0.2791), BezierTail(-0.03513 vv 0.2775, -0.007776 vv 0.2674, -0.001633 vv 0.2641), BezierTail(0.004509 vv 0.2607, 0.005068 vv 0.2563, 0.009534 vv 0.2535), BezierTail(0.014 vv 0.2507, 0.05587 vv 0.2384, 0.04247 vv 0.2211), LineTail(0.04247 vv 0.2211)).fill(Colour(0xFFfcd116))
    val tail = PolyCurve(LineTail(0.2061 vv 0.1424), BezierTail(0.2195 vv 0.129, 0.3099 vv 0.06865, 0.3099 vv -0.005048), BezierTail(0.3099 vv -0.07875, 0.244 vv -0.07652, 0.225 vv -0.07428), BezierTail(0.2463 vv -0.09215, 0.3267 vv -0.0888, 0.3267 vv -0.005048), BezierTail(0.3267 vv 0.06865, 0.2228 vv 0.1424, 0.2094 vv 0.1491), BezierTail(0.196 vv 0.1558, 0.1927 vv 0.1558, 0.2061 vv 0.1424), LineTail(0.2061 vv 0.1424)).fill(Colour(0xFFfcd116))
    val starSmall = PolyCurve(LineTail(-0.2843 vv -0.2267), LineTail(-0.2644 vv -0.1628), LineTail(-0.2445 vv -0.2274), LineTail(-0.2969 vv -0.1878), LineTail(-0.2319 vv -0.1878), LineTail(-0.2843 vv -0.2267)).fill(Colour(0xFFFFFFFF))
    val starLarge = PolyCurve(LineTail(-0.3326 vv 0.1901), LineTail(-0.3724 vv 0.06216), LineTail(-0.2676 vv 0.14), LineTail(-0.3976 vv 0.14), LineTail(-0.2928 vv 0.06076), LineTail(-0.3326 vv 0.1901)).fill(Colour(0xFFFFFFFF))
    
    Arr[DisplayElem](
      base,
      topRighttriangle, 
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