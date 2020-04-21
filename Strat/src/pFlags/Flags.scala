/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, Colour._

/** The flag trait is a builder for Graphic Elements and sequences of Graphic Elements, representing the flag, it is not itself. */
trait Flag
{ def name: String
  def ratio: Double
  def apply(): Arr[PaintFullElem]
  def rect: Polygon = Rectangle(ratio)
  def parentStr: PolyParent = Rectangle(ratio).parentElems(name + " flag", apply)
  def parent(evObj: Any = this): PolyParent = Rectangle(ratio).parentElems(evObj, apply)

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): Arr[PaintFullElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottom(colours: Colour*): Arr[PaintFullElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio,
     1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour))

  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottomRepeat(numBands: Int, colours: Colour*): Arr[PaintFullElem] = iUntilMap(0, numBands){ i =>
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
    override def apply(): Arr[PaintFullElem] = Arr(rect.fill(colour))
  }
}

object TextFlagMaker
{
  def apply(str: String, colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): Arr[PaintFullElem] = Arr(rect.fill(colour), TextGraphic(str, 40))
  }
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  val apply: Arr[PaintFullElem] = leftToRight(Red, Blue, Gold)
}

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] = topToBottom(Black, Yellow)
}

object Belgium extends Flag
{ val name = "Belgium"
  val ratio = 15.0 / 13.0
  def apply: Arr[PaintFullElem] = leftToRight(Black, Yellow, Red)
}

object Chad extends Flag
{ val ratio = 1.5
  val name = "Chad"
  def apply: Arr[PaintFullElem] = leftToRight(Blue, Yellow, Red)
}

object China extends Flag
{ val name = "China"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
      Rectangle(1.5, 1).fill(Red),
      Rectangle.fromTL(0.75, 0.5, - 0.75 vv 0.5).fill(DarkBlue)
    )
  }
}

trait EnglandLike extends Flag
{ def ratio = 2
  def englishRed: Colour = Colour.fromInts(204, 0, 0)
  def redCross: Arr[PolyFill] = Rectangle.cross(2, 1, 0.2).map(_.fill(englishRed))
  def common = rect.fill(White) +: redCross
}

object England extends EnglandLike
{ val name = "England"
  val apply = common
}

object UnitedKingdom extends EnglandLike
{ val name = "United Kingdom"

  val apply: Arr[PaintFullElem] =
  { val xd = math.sqrt(5) / 30.0 //hypotenuse sqrt(2 * 2 + 1 * 1)
    val yd = math.sqrt(1.25) / 30.0 //hypotenuse Sqrt(1 * 1 + 0.5 * 0.5)
    val ywc = 5.0 / 30 //top of White cross bar
    val xDiag = 10.0 / 30.0 //ywc * 2 where diag crosses ywc
    val b1 = Polygon(
      5.0 / 30 vv 0.5, 1 - xd * 3 vv 0.5,
      1.0 / 6.0 vv ywc + yd)

    val b2 = Polygon(
      xDiag + 3 * xd vv ywc,
      1 vv 0.5 - yd * 3,
      1 vv ywc)

    val r1: Polygon = Polygon(
      -1 vv 0.5,
      -xDiag vv ywc,
      -(xDiag + xd * 2) vv ywc,
      -1 vv 0.5 - (yd * 2))

    val r2: Polygon = Polygon(
      xDiag - xd * 2 vv ywc,
      1 - xd * 2 vv 0.5,
      1 vv 0.5,
      xDiag vv ywc)

    val reds1 = Polygons(r1, r2).map(_.fill(englishRed))
    val reds = reds1.flatMap(e => Arr(e, e.negXY))

    val blues = {
      val l1 = Polygons(b1, b2).map(_.fill(Colour.fromInts(0, 0, 102)))
      l1.flatMap(b => Arr(b, b.negX, b.negY, b.negXY))
    }
     common ++ blues ++ reds
  }
}

object France extends Flag
{ val name = "France"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] = leftToRight(Colour(0xFF0055A4) , White, Colour(0xFFEF4135))
}

object Germany extends Flag
{ val name = "Germany"
  val ratio = 5 / 3.0
  val apply: Arr[PaintFullElem] = topToBottom(Black, Red, Gold)
}

object Germany1871 extends Flag
{ val name = "Germany (1871)"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] = topToBottom(Black, White, Red)
}

object Italy extends Flag
{ val name = "Italy"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] = topToBottom(Green, White, Red)
}

object Ireland extends Flag
{ val name = "Ireland"
  val ratio = 2
  val apply: Arr[PaintFullElem] = topToBottom(Green, White, Orange)
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  { val rw = rect.fill(White)
    val circ = Circle.segs(0.6).fill(Colour.fromInts(188, 0,45))
    Arr(rw, circ)
  }
}

object Russia extends Flag
{ val ratio = 1.5
  val name = "Russia"
  val apply: Arr[PaintFullElem] = topToBottom(White, Blue, Red)
}

object USSR extends Flag
{ val name = "USSR"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
      Rectangle(ratio, 1).fill(Red),
      Star5().scale(0.4).fill(Gold)
    )
  }
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  val apply: Arr[PaintFullElem] =
  { val poly = Rectangle(ratio, 1)
    val bar = Rectangle.fromBC(0.1, 0.2).fill(Black)
    val arm = Rectangle.fromTL(6.0 / 20, 0.1, -1.0 / 20 vv 0.25).fill(Black)
    val cross = Arr(bar, arm).anti45.flatMap(_.rCrossArr)
    Arr[PaintFullElem](
      poly.fill(Red),
      Circle.segs(6.0 / 8).fill(White)
    ) ++ cross
  }
}


object WhiteFlag extends Flag
{ val name = "White"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
      Rectangle(1.5, 1).fill(White)
    )
  }
}
  
object CommonShapesInFlags extends Flag
{ val name = "CommonShapesInFlags"
  val ratio = 1.5

  val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
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
      Circle.segs(0.225).slate(-0.6 vv -0.3).fill(Red),
      Circle.segs(0.2).slate(-0.6 vv -0.3).slate(0.04 vv 0).fill(White),

      //composite star ()
      Star5().scale(0.15).slate(-0.3 vv 0).fill(Gold),
      Star5().scale(0.1).slate(-0.3 vv 0).fill(Magenta),
      
      Pentagram().scale(0.1).slate(0 vv 0.3).draw(2, Colour(0xFF006233)),
    )
  }
}
  
object CzechRepublic extends Flag
{ val name = "Czech Republic"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  {
    Arr[PaintFullElem](
      Rectangle(ratio, 1).fill(White),
      Rectangle(ratio, 0.5).slate(0 vv -0.25).fill(Colour(0xFFD7141A)),
      Triangle.fill(-ratio/2 vv 0.5, -ratio/2 vv -0.5, 0 vv 0, Colour(0xFF11457E))
    )
  }
}

object CCCP extends Flag
{ val name = "CCCP"
  val ratio = 2.0
  val apply: Arr[PaintFullElem] =
  { Arr[PaintFullElem](
      //background
      Rectangle(ratio, 1).fill(Colour(0xFFCC0000)),
      //hammer
      Shape(LineSeg(-0.7709 vv 0.2138), LineSeg(-0.7395 vv 0.1822), LineSeg(-0.7099 vv 0.2116), BezierSeg(-0.6648 vv 0.1633, -0.6175 vv 0.1166, -0.5727 vv 0.06808), BezierSeg(-0.566 vv 0.06131, -0.555 vv 0.06128, -0.5483 vv 0.068), BezierSeg(-0.5415 vv 0.07472, -0.5415 vv 0.08566, -0.5482 vv 0.09243), BezierSeg(-0.5962 vv 0.1378, -0.6444 vv 0.1834, -0.6924 vv 0.2289), LineSeg(-0.6525 vv 0.2686), LineSeg(-0.7081 vv 0.2763), LineSeg(-0.7709 vv 0.2138)).fill(Colour(0xFFFFD700)),
      //sickle
      Shape(LineSeg(-0.6695 vv 0.3163), BezierSeg(-0.6437 vv 0.3018, -0.624 vv 0.2809, -0.6124 vv 0.259), BezierSeg(-0.6007 vv 0.2369, -0.5955 vv 0.2137, -0.5954 vv 0.1953), BezierSeg(-0.5952 vv 0.1574, -0.6262 vv 0.1266, -0.6641 vv 0.1266), BezierSeg(-0.6843 vv 0.1266, -0.7025 vv 0.1354, -0.715 vv 0.1493), LineSeg(-0.722 vv 0.1434), BezierSeg(-0.7232 vv 0.1439, -0.7244 vv 0.1441, -0.7257 vv 0.1441), BezierSeg(-0.7287 vv 0.1441, -0.7316 vv 0.1428, -0.7336 vv 0.1405), BezierSeg(-0.7386 vv 0.1398, -0.7427 vv 0.1364, -0.7443 vv 0.1316), BezierSeg(-0.7495 vv 0.1212, -0.7587 vv 0.1129, -0.7698 vv 0.1092), BezierSeg(-0.7699 vv 0.1091, -0.77 vv 0.1091, -0.7701 vv 0.109), BezierSeg(-0.7752 vv 0.1072, -0.7803 vv 0.1038, -0.7847 vv 0.09938), BezierSeg(-0.7934 vv 0.09065, -0.7976 vv 0.07948, -0.7952 vv 0.07165), BezierSeg(-0.7954 vv 0.07097, -0.7956 vv 0.07025, -0.7956 vv 0.06953), BezierSeg(-0.7956 vv 0.06591, -0.7926 vv 0.06298, -0.789 vv 0.06298), BezierSeg(-0.7881 vv 0.06298, -0.7872 vv 0.06317, -0.7864 vv 0.06354), BezierSeg(-0.7786 vv 0.06178, -0.7679 vv 0.06612, -0.7596 vv 0.07447), BezierSeg(-0.7549 vv 0.07922, -0.7514 vv 0.08485, -0.7497 vv 0.09036), BezierSeg(-0.7458 vv 0.1014, -0.7375 vv 0.1104, -0.727 vv 0.1154), BezierSeg(-0.7267 vv 0.1156, -0.7265 vv 0.1157, -0.7263 vv 0.1158), BezierSeg(-0.7228 vv 0.1175, -0.7202 vv 0.1207, -0.7193 vv 0.1245), BezierSeg(-0.7031 vv 0.1054, -0.6789 vv 0.09325, -0.6517 vv 0.09262), BezierSeg(-0.602 vv 0.09145, -0.5649 vv 0.128, -0.5631 vv 0.1807), BezierSeg(-0.5623 vv 0.2072, -0.5725 vv 0.2413, -0.5959 vv 0.2693), BezierSeg(-0.6137 vv 0.2907, -0.6399 vv 0.3085, -0.6695 vv 0.3163), LineSeg(-0.6695 vv 0.3163)).fill(Colour(0xFFFFD700)),
      //outer star
      Star5().scale(1.0/16).slate(-2.0/3 vv 0.75/2).fill(Colour(0xFFFFD700)),
      //inner star
      Star5().scale(1.0/25).slate(-2.0/3 vv 0.75/2).fill(Colour(0xFFCC0000)),
    )
  }
}

object Iraq extends Flag
{ val name = "Iraq"
  val ratio = 1.5
  val apply: Arr[PaintFullElem] =
  { topToBottom(Colour(0xFFce1126), White, Black) ++ Arr[PaintFullElem](
      Shape(LineSeg(-0.34 vv 0.2997), BezierSeg(-0.3409 vv 0.3002, -0.3419 vv 0.301, -0.3423 vv 0.3015), BezierSeg(-0.3428 vv 0.3022, -0.3425 vv 0.3022, -0.3403 vv 0.3016), BezierSeg(-0.3365 vv 0.3006, -0.334 vv 0.301, -0.3315 vv 0.3031), LineSeg(-0.3293 vv 0.3049), LineSeg(-0.3268 vv 0.3036), BezierSeg(-0.3254 vv 0.3029, -0.3239 vv 0.3024, -0.3234 vv 0.3025), BezierSeg(-0.3223 vv 0.3028, -0.32 vv 0.3058, -0.3201 vv 0.3068), BezierSeg(-0.3202 vv 0.3081, -0.3191 vv 0.3077, -0.3186 vv 0.3064), BezierSeg(-0.3176 vv 0.3036, -0.3191 vv 0.3005, -0.3217 vv 0.2998), BezierSeg(-0.323 vv 0.2995, -0.3242 vv 0.2996, -0.3261 vv 0.3003), BezierSeg(-0.3285 vv 0.3011, -0.3289 vv 0.3011, -0.3301 vv 0.3002), BezierSeg(-0.3328 vv 0.2981, -0.3366 vv 0.2979, -0.34 vv 0.2997), LineSeg(-0.34 vv 0.2997)).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.3304 vv 0.3084), BezierSeg(-0.3313 vv 0.3096, -0.3325 vv 0.3141, -0.3321 vv 0.3153), BezierSeg(-0.3318 vv 0.3162, -0.3314 vv 0.3164, -0.3306 vv 0.3161), BezierSeg(-0.329 vv 0.3157, -0.3287 vv 0.3146, -0.3289 vv 0.311), BezierSeg(-0.3291 vv 0.3081, -0.3295 vv 0.3073, -0.3304 vv 0.3084), LineSeg(-0.3304 vv 0.3084)).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.4429 vv 0.3117), BezierSeg(-0.4432 vv 0.3095, -0.4391 vv 0.3041, -0.4372 vv 0.3031), BezierSeg(-0.4384 vv 0.3025, -0.44 vv 0.3028, -0.4412 vv 0.3021), BezierSeg(-0.4478 vv 0.2955, -0.4718 vv 0.2721, -0.4762 vv 0.2665), BezierSeg(-0.4632 vv 0.2662, -0.4488 vv 0.2667, -0.4366 vv 0.2672), BezierSeg(-0.4366 vv 0.2761, -0.4283 vv 0.2765, -0.4227 vv 0.2797), BezierSeg(-0.4198 vv 0.2752, -0.4125 vv 0.2755, -0.4116 vv 0.2687), LineSeg(-0.4116 vv 0.2393), LineSeg(-0.5227 vv 0.2393), BezierSeg(-0.5246 vv 0.2307, -0.5324 vv 0.2241, -0.5433 vv 0.2268), BezierSeg(-0.5399 vv 0.2303, -0.5342 vv 0.2315, -0.5322 vv 0.2364), BezierSeg(-0.5305 vv 0.247, -0.5356 vv 0.2535, -0.5389 vv 0.2595), BezierSeg(-0.5335 vv 0.2615, -0.5326 vv 0.262, -0.5271 vv 0.2658), BezierSeg(-0.531 vv 0.2539, -0.5169 vv 0.2552, -0.5065 vv 0.2555), BezierSeg(-0.5062 vv 0.2595, -0.5063 vv 0.2643, -0.5094 vv 0.2648), BezierSeg(-0.5054 vv 0.2663, -0.5048 vv 0.2668, -0.4984 vv 0.2722), LineSeg(-0.4984 vv 0.2562), LineSeg(-0.4215 vv 0.2564), BezierSeg(-0.4215 vv 0.2614, -0.4202 vv 0.2694, -0.4241 vv 0.2694), BezierSeg(-0.4279 vv 0.2694, -0.4243 vv 0.2591, -0.4272 vv 0.2591), LineSeg(-0.4866 vv 0.2591), LineSeg(-0.4867 vv 0.2693), BezierSeg(-0.4842 vv 0.2718, -0.4844 vv 0.2716, -0.4673 vv 0.2888), BezierSeg(-0.4655 vv 0.2905, -0.4535 vv 0.3014, -0.4429 vv 0.3117), LineSeg(-0.4429 vv 0.3117)).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.2945 vv 0.3121), BezierSeg(-0.2903 vv 0.3099, -0.2871 vv 0.3068, -0.282 vv 0.3055), BezierSeg(-0.2826 vv 0.3034, -0.2845 vv 0.3026, -0.2849 vv 0.3003), LineSeg(-0.2849 vv 0.2555), BezierSeg(-0.2793 vv 0.2543, -0.2781 vv 0.2575, -0.2754 vv 0.2592), BezierSeg(-0.2746 vv 0.252, -0.2701 vv 0.245, -0.2702 vv 0.2394), LineSeg(-0.2945 vv 0.2394), LineSeg(-0.2945 vv 0.3121), LineSeg(-0.2945 vv 0.3121)).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.3268 vv 0.2881), LineSeg(-0.318 vv 0.2958), LineSeg(-0.318 vv 0.2567), LineSeg(-0.3117 vv 0.2567), LineSeg(-0.3119 vv 0.3006), BezierSeg(-0.3093 vv 0.3032, -0.3042 vv 0.3069, -0.303 vv 0.3095), LineSeg(-0.303 vv 0.2394), LineSeg(-0.3587 vv 0.2394), BezierSeg(-0.3595 vv 0.254, -0.3597 vv 0.269, -0.3427 vv 0.2658), LineSeg(-0.3427 vv 0.2717), BezierSeg(-0.3432 vv 0.2727, -0.3441 vv 0.2715, -0.3444 vv 0.2728), BezierSeg(-0.3417 vv 0.2755, -0.3408 vv 0.2762, -0.3335 vv 0.2825), LineSeg(-0.3333 vv 0.2567), LineSeg(-0.3269 vv 0.2567), BezierSeg(-0.3269 vv 0.2567, -0.3268 vv 0.2871, -0.3268 vv 0.2881), LineSeg(-0.3268 vv 0.2881)).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.3478 vv 0.2571), BezierSeg(-0.3466 vv 0.2553, -0.3425 vv 0.2553, -0.3427 vv 0.2583), BezierSeg(-0.3434 vv 0.2608, -0.3487 vv 0.2599, -0.3478 vv 0.2571), LineSeg(-0.3478 vv 0.2571)).fill(Colour(0xFFFFFFFF)),
      Circle.segs(0.0068).slate(-0.5091 vv 0.2311).fill(Colour(0xFF007a3d)),
      Shape(LineSeg(-0.4041 vv 0.312), BezierSeg(-0.3999 vv 0.3098, -0.3967 vv 0.3067, -0.3916 vv 0.3054), BezierSeg(-0.3922 vv 0.3033, -0.394 vv 0.3025, -0.3945 vv 0.3003), LineSeg(-0.3945 vv 0.2554), BezierSeg(-0.3889 vv 0.2542, -0.3877 vv 0.2574, -0.385 vv 0.2591), BezierSeg(-0.3842 vv 0.2519, -0.3797 vv 0.2449, -0.3798 vv 0.2393), LineSeg(-0.4041 vv 0.2393), LineSeg(-0.4041 vv 0.3121), LineSeg(-0.4041 vv 0.312)).fill(Colour(0xFF007a3d))
    ).scale(2.18978).slate(.892 vv -.595)
  }
}
