/* Copyright 2018-22 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, Colour._

/** The flag trait is a builder for Graphic Elements and sequences of Graphic Elements, representing the flag, it is not itself. */
trait Flag
{ def name: String
  def ratio: Double
  def apply(): GraphicElems
  def rect: Rect = Rect(ratio, 1)

  def compoundStr: RectCompound = rect.activeChildren(name + " flag", apply())

  def compound(evObj: AnyRef = this): PolygonCompound =
  { val rect = Rect(ratio, 1)
    PolygonCompound(rect, RArr(), apply() +% rect.active(evObj))
  }

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): GraphicElems = colours.iMap{ (i, colour) => Rect.tl(ratio / colours.length, 1,
    -ratio / 2 pp + 0.5).slateXY(i * ratio / colours.length, 0).fill(colour) }
         
  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottom(colours: Colour*): GraphicElems = colours.iMap{ (i, colour) => Rect.tl(ratio,
     1.0 / colours.length, -ratio / 2 pp + 0.5).slateXY(0,
       - i.toDouble / colours.length).fill(colour) }

  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottomRepeat(numBands: Int, colours: Colour*): GraphicElems = iUntilMap(numBands){ i =>
    val r1 = Rect.tl(ratio, 1.0 / numBands, -ratio / 2 pp + 0.5)
    val r2 = r1.slateXY(0, - i.toDouble / numBands)
    r2.fill(colours(i %% colours.length))
  }
}

object PlainFlagMaker
{
  def apply(colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = colour.str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): GraphicElems = RArr(rect.fill(colour))
  }
}

object TextFlagMaker
{
  def apply(str: String, colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): GraphicElems = RArr(rect.fill(colour), TextGraphic(str, 40, Pt2Z))
  }
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  def apply(): GraphicElems = leftToRight(Red, Blue, Gold)
}

object Chad extends Flag
{ val ratio = 1.5
  val name = "Chad"
  def apply(): GraphicElems = leftToRight(Blue, Yellow, Red)
}

object China extends Flag
{ val name = "China"
  val ratio = 1.5
  def apply(): GraphicElems = RArr(Rect(1.5, 1).fill(Red),
    Rect.tl(0.75, 0.5, - 0.75 pp 0.5).fill(DarkBlue))
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  def apply(): GraphicElems =
  { val rw = rect.fill(White)
    val circ = Circle(0.6).fill(Colour.fromInts(188, 0,45))
    RArr(rw, circ)
  }
}

object WhiteFlag extends Flag
{ val name = "White"
  val ratio = 1.5
  def apply(): GraphicElems = RArr(Rect(1.5, 1).fill(White))
}
  
object CommonShapesInFlags extends Flag
{ val name = "CommonShapesInFlags"
  val ratio = 1.5

  def apply(): GraphicElems = RArr(
    Rect(1.5, 1).fill(White),

    //off centre cross
    Rect(ratio, 0.25).fill(Green),
    Rect(0.25, 1).fill(Green).slate(-0.3 pp 0),

    Star5().scale(0.1).slate(-0.6 pp 0.3).fill(Magenta),

    Star7(0.382).scale(0.1).slate(-0.3 pp 0.3).fill(Red),

    Star5().scale(0.1).slate(0.3 pp 0.3).draw(1, Lime),

    //hexagram
    Star3().scale(0.15).slate(0.6 pp 0.3).draw(1.5, Blue),
    Star3().scale(0.15).rotate(DegVec180).slate(0.6 pp 0.3).draw(1.5, Blue),

    //crescent
    Circle(0.225, -0.6, -0.3).fill(Red),
    Circle(0.2, -0.6, -0.3).slate(0.04 pp 0).fill(White),

    //composite star ()
    Star5().scale(0.15).slate(-0.3 pp 0).fill(Gold),
    Star5().scale(0.1).slate(-0.3 pp 0).fill(Magenta),

    Pentagram().scale(0.1).slate(0 pp 0.3).draw(2, Colour(0xFF006233)),
  )
}

object Iraq extends Flag
{ val name = "Iraq"
  val ratio = 1.5
  def apply(): GraphicElems =
  { topToBottom(Colour(0xFFce1126), White, Black) ++ RArr(
      ShapeGenOld(LineTail(-0.34 pp 0.2997), BezierTail(-0.3409 pp 0.3002, -0.3419 pp 0.301, -0.3423 pp 0.3015),
        BezierTail(-0.3428 pp 0.3022, -0.3425 pp 0.3022, -0.3403 pp 0.3016), BezierTail(-0.3365 pp 0.3006, -0.334 pp 0.301, -0.3315 pp 0.3031),
        LineTail(-0.3293 pp 0.3049), LineTail(-0.3268 pp 0.3036), BezierTail(-0.3254 pp 0.3029, -0.3239 pp 0.3024, -0.3234 pp 0.3025),
        BezierTail(-0.3223 pp 0.3028, -0.32 pp 0.3058, -0.3201 pp 0.3068), BezierTail(-0.3202 pp 0.3081, -0.3191 pp 0.3077, -0.3186 pp 0.3064),
        BezierTail(-0.3176 pp 0.3036, -0.3191 pp 0.3005, -0.3217 pp 0.2998), BezierTail(-0.323 pp 0.2995, -0.3242 pp 0.2996, -0.3261 pp 0.3003),
        BezierTail(-0.3285 pp 0.3011, -0.3289 pp 0.3011, -0.3301 pp 0.3002), BezierTail(-0.3328 pp 0.2981, -0.3366 pp 0.2979, -0.34 pp 0.2997),
        LineTail(-0.34 pp 0.2997)).fill(Colour(0xFF007a3d)),
      
    ShapeGenOld(LineTail(-0.3304 pp 0.3084), BezierTail(-0.3313 pp 0.3096, -0.3325 pp 0.3141, -0.3321 pp 0.3153),
      BezierTail(-0.3318 pp 0.3162, -0.3314 pp 0.3164, -0.3306 pp 0.3161), BezierTail(-0.329 pp 0.3157, -0.3287 pp 0.3146, -0.3289 pp 0.311),
      BezierTail(-0.3291 pp 0.3081, -0.3295 pp 0.3073, -0.3304 pp 0.3084), LineTail(-0.3304 pp 0.3084)).fill(Colour(0xFF007a3d)),
      ShapeGenOld(LineTail(-0.4429 pp 0.3117), BezierTail(-0.4432 pp 0.3095, -0.4391 pp 0.3041, -0.4372 pp 0.3031),
        BezierTail(-0.4384 pp 0.3025, -0.44 pp 0.3028, -0.4412 pp 0.3021), BezierTail(-0.4478 pp 0.2955, -0.4718 pp 0.2721, -0.4762 pp 0.2665),
        BezierTail(-0.4632 pp 0.2662, -0.4488 pp 0.2667, -0.4366 pp 0.2672), BezierTail(-0.4366 pp 0.2761, -0.4283 pp 0.2765, -0.4227 pp 0.2797),
        BezierTail(-0.4198 pp 0.2752, -0.4125 pp 0.2755, -0.4116 pp 0.2687), LineTail(-0.4116 pp 0.2393), LineTail(-0.5227 pp 0.2393),
        BezierTail(-0.5246 pp 0.2307, -0.5324 pp 0.2241, -0.5433 pp 0.2268), BezierTail(-0.5399 pp 0.2303, -0.5342 pp 0.2315, -0.5322 pp 0.2364),
        BezierTail(-0.5305 pp 0.247, -0.5356 pp 0.2535, -0.5389 pp 0.2595), BezierTail(-0.5335 pp 0.2615, -0.5326 pp 0.262, -0.5271 pp 0.2658),
        BezierTail(-0.531 pp 0.2539, -0.5169 pp 0.2552, -0.5065 pp 0.2555), BezierTail(-0.5062 pp 0.2595, -0.5063 pp 0.2643, -0.5094 pp 0.2648),
        BezierTail(-0.5054 pp 0.2663, -0.5048 pp 0.2668, -0.4984 pp 0.2722), LineTail(-0.4984 pp 0.2562), LineTail(-0.4215 pp 0.2564),
        BezierTail(-0.4215 pp 0.2614, -0.4202 pp 0.2694, -0.4241 pp 0.2694), BezierTail(-0.4279 pp 0.2694, -0.4243 pp 0.2591, -0.4272 pp 0.2591),
        LineTail(-0.4866 pp 0.2591), LineTail(-0.4867 pp 0.2693), BezierTail(-0.4842 pp 0.2718, -0.4844 pp 0.2716, -0.4673 pp 0.2888),
        BezierTail(-0.4655 pp 0.2905, -0.4535 pp 0.3014, -0.4429 pp 0.3117), LineTail(-0.4429 pp 0.3117)).fill(Colour(0xFF007a3d)),
      ShapeGenOld(LineTail(-0.2945 pp 0.3121), BezierTail(-0.2903 pp 0.3099, -0.2871 pp 0.3068, -0.282 pp 0.3055),
        BezierTail(-0.2826 pp 0.3034, -0.2845 pp 0.3026, -0.2849 pp 0.3003), LineTail(-0.2849 pp 0.2555),
        BezierTail(-0.2793 pp 0.2543, -0.2781 pp 0.2575, -0.2754 pp 0.2592), BezierTail(-0.2746 pp 0.252, -0.2701 pp 0.245, -0.2702 pp 0.2394),
        LineTail(-0.2945 pp 0.2394), LineTail(-0.2945 pp 0.3121), LineTail(-0.2945 pp 0.3121)).fill(Colour(0xFF007a3d)),
      ShapeGenOld(LineTail(-0.3268 pp 0.2881), LineTail(-0.318 pp 0.2958), LineTail(-0.318 pp 0.2567), LineTail(-0.3117 pp 0.2567),
        LineTail(-0.3119 pp 0.3006), BezierTail(-0.3093 pp 0.3032, -0.3042 pp 0.3069, -0.303 pp 0.3095), LineTail(-0.303 pp 0.2394),
        LineTail(-0.3587 pp 0.2394), BezierTail(-0.3595 pp 0.254, -0.3597 pp 0.269, -0.3427 pp 0.2658), LineTail(-0.3427 pp 0.2717),
        BezierTail(-0.3432 pp 0.2727, -0.3441 pp 0.2715, -0.3444 pp 0.2728), BezierTail(-0.3417 pp 0.2755, -0.3408 pp 0.2762, -0.3335 pp 0.2825),
        LineTail(-0.3333 pp 0.2567), LineTail(-0.3269 pp 0.2567), BezierTail(-0.3269 pp 0.2567, -0.3268 pp 0.2871, -0.3268 pp 0.2881),
        LineTail(-0.3268 pp 0.2881)).fill(Colour(0xFF007a3d)),
      ShapeGenOld(LineTail(-0.3478 pp 0.2571), BezierTail(-0.3466 pp 0.2553, -0.3425 pp 0.2553, -0.3427 pp 0.2583),
        BezierTail(-0.3434 pp 0.2608, -0.3487 pp 0.2599, -0.3478 pp 0.2571), LineTail(-0.3478 pp 0.2571)).fill(Colour(0xFFFFFFFF)),
      Circle(0.0068, -0.5091, 0.2311).fill(Colour(0xFF007a3d)),
      ShapeGenOld(LineTail(-0.4041 pp 0.312), BezierTail(-0.3999 pp 0.3098, -0.3967 pp 0.3067, -0.3916 pp 0.3054),
        BezierTail(-0.3922 pp 0.3033, -0.394 pp 0.3025, -0.3945 pp 0.3003), LineTail(-0.3945 pp 0.2554),
        BezierTail(-0.3889 pp 0.2542, -0.3877 pp 0.2574, -0.385 pp 0.2591), BezierTail(-0.3842 pp 0.2519, -0.3797 pp 0.2449, -0.3798 pp 0.2393),
        LineTail(-0.4041 pp 0.2393), LineTail(-0.4041 pp 0.3121), LineTail(-0.4041 pp 0.312)).fill(Colour(0xFF007a3d))
    ).scale(2.18978).slate(.892 pp -.595)
  }
}

object India extends Flag
{ val name = "India"
  val ratio = 1.5
  def apply(): GraphicElems =
  { 
    val spoke = ShapeGenOld(LineTail(-0.75 pp 0.3833), LineTail(-0.746 pp 0.4533), BezierTail(-0.746 pp 0.4533, -0.75 pp 0.4867, -0.75 pp 0.4867),
    BezierTail(-0.75 pp 0.4867, -0.754 pp 0.4533, -0.754 pp 0.4533), LineTail(-0.75 pp 0.3833),
    LineTail(-0.75 pp 0.3833)).slateXY(0.75, -0.5).fill(Colour(0xFF000080))
    
    val spokes = iToMap(23){i => spoke.rotate(DegVec30/2*i)}
    val rimNotch = Circle(0.875/75, 0, -17.5/150).rotate(DegVec30/4).fill(Colour(0xFF000080))
    val rimNotches = iToMap(23){i => rimNotch.rotate(DegVec30/2*i)}
    val outerCircle = Circle(20.0/75).fill(Colour(0xFF000080))
    val middleCircle = Circle(17.5/75).fill(Colour(0xFFFFFFFF))
    val innerCircle = Circle(3.5/75).fill(Colour(0xFF000080))
    topToBottom(Colour(0xFFFF9933), White, Colour(0xFF138808)) ++ RArr(outerCircle, middleCircle, innerCircle) ++ spokes ++ rimNotches
  }
}