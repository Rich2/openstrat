/* Copyright 2018-20 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, Colour._

/** The flag trait is a builder for Graphic Elements and sequences of Graphic Elements, representing the flag, it is not itself. */
trait Flag
{ def name: String
  def ratio: Double
  def apply(): GraphicElems
  def rect: Rect = Rect(ratio, 1)

  def compoundStr: RectCompound = rect.activeChildren(name + " flag", apply())

  def compound(evObj: Any = this): PolygonCompound =
  { val rect = Rect(ratio, 1)
    PolygonCompound(rect, Arr(), apply() +- rect.active(evObj))
  }

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): GraphicElems = colours.iMap((colour, i) => Rect.tl(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottom(colours: Colour*): GraphicElems = colours.iMap((colour, i) => Rect.tl(ratio,
     1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour))

  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottomRepeat(numBands: Int, colours: Colour*): GraphicElems = iUntilMap(0, numBands){ i =>
    val r1 = Rect.tl(ratio, 1.0 / numBands, -ratio / 2 vv + 0.5)
    val r2 = r1.slate(0, - i.toDouble / numBands)
    r2.fill(colours(i %% colours.length))
  }
}

object PlainFlagMaker
{
  def apply(colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = colour.str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): GraphicElems = Arr(rect.fill(colour))
  }
}

object TextFlagMaker
{
  def apply(str: String, colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): GraphicElems = Arr(rect.fill(colour), TextGraphic(str, 40))
  }
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  val apply: GraphicElems = leftToRight(Red, Blue, Gold)
}

object Chad extends Flag
{ val ratio = 1.5
  val name = "Chad"
  def apply(): GraphicElems = leftToRight(Blue, Yellow, Red)
}

object China extends Flag
{ val name = "China"
  val ratio = 1.5
  val apply: GraphicElems = Arr(Rect(1.5, 1).fill(Red),
    Rect.tl(0.75, 0.5, - 0.75 vv 0.5).fill(DarkBlue))
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  val apply: GraphicElems =
  { val rw = rect.fill(White)
    val circ = Circle(0.6).fill(Colour.fromInts(188, 0,45))
    Arr(rw, circ)
  }
}

object WhiteFlag extends Flag
{ val name = "White"
  val ratio = 1.5
  val apply: GraphicElems = Arr(Rect(1.5, 1).fill(White))
}
  
object CommonShapesInFlags extends Flag
{ val name = "CommonShapesInFlags"
  val ratio = 1.5

  val apply: GraphicElems = Arr(
    Rect(1.5, 1).fill(White),

    //off centre cross
    Rect(ratio, 0.25).fill(Green),
    Rect(0.25, 1).fill(Green).slate(-0.3 vv 0),

    Star5().scale(0.1).slate(-0.6 vv 0.3).fill(Magenta),

    Star7(0.382).scale(0.1).slate(-0.3 vv 0.3).fill(Red),

    Star5().scale(0.1).slate(0.3 vv 0.3).draw(1, Lime),

    //hexagram
    Star3().scale(0.15).slate(0.6 vv 0.3).draw(1.5, Blue),
    Star3().scale(0.15).rotate(Deg180).slate(0.6 vv 0.3).draw(1.5, Blue),

    //crescent
    Circle(0.225, -0.6, -0.3).fill(Red),
    Circle(0.2, -0.6, -0.3).slate(0.04 vv 0).fill(White),

    //composite star ()
    Star5().scale(0.15).slate(-0.3 vv 0).fill(Gold),
    Star5().scale(0.1).slate(-0.3 vv 0).fill(Magenta),

    Pentagram().scale(0.1).slate(0 vv 0.3).draw(2, Colour(0xFF006233)),
  )
}

object Iraq extends Flag
{ val name = "Iraq"
  val ratio = 1.5
  val apply: GraphicElems =
  { topToBottom(Colour(0xFFce1126), White, Black) ++ Arr(
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
      Circle(0.0068, -0.5091, 0.2311).fill(Colour(0xFF007a3d)),
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
  val apply: GraphicElems =
  { 
    val spoke = PolyCurve(LineTail(-0.75 vv 0.3833), LineTail(-0.746 vv 0.4533), BezierTail(-0.746 vv 0.4533, -0.75 vv 0.4867, -0.75 vv 0.4867),
    BezierTail(-0.75 vv 0.4867, -0.754 vv 0.4533, -0.754 vv 0.4533), LineTail(-0.75 vv 0.3833),
    LineTail(-0.75 vv 0.3833)).slate(0.75, -0.5).fill(Colour(0xFF000080))
    
    val spokes = iToMap(0,23){i => spoke.rotate(deg30/2*i)}
    val rimNotch = Circle(0.875/75, 0, -17.5/150).rotate(deg30/4).fill(Colour(0xFF000080))
    val rimNotches = iToMap(0,23){i => rimNotch.rotate(deg30/2*i)}
    val outerCircle = Circle(20.0/75).fill(Colour(0xFF000080))
    val middleCircle = Circle(17.5/75).fill(Colour(0xFFFFFFFF))
    val innerCircle = Circle(3.5/75).fill(Colour(0xFF000080))
    topToBottom(Colour(0xFFFF9933), White, Colour(0xFF138808)) ++ Arr(outerCircle, middleCircle, innerCircle) ++ spokes ++ rimNotches
  }
}