/* Copyright 2018 Richard Oliver. Licensed under Apache Licence version 2.0 */
package ostrat
package pFlags
import geom._, Colour._

/** The flag trait is a builder for Graphic Elements and sequences of Graphic Elements, representing the flag, it is not itself. */
trait Flag
{ def name: String
  def ratio: Double
  def apply(): Arr[PaintElem]
  def rect: Polygon = Rectangle(ratio)
  def parentStr: PolyParent = Rectangle(ratio).parentElems(name + " flag", apply)
  def parent(evObj: Any = this): PolyParent = Rectangle(ratio).parentElems(evObj, apply)

  /** Equal width vertical bands. width ratio should normally be greater than 1.0 */
  def leftToRight(colours: Colour*): Arr[PaintElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio / colours.length, 1,
    -ratio / 2 vv + 0.5).slate(i * ratio / colours.length, 0).fill(colour))
         
  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottom(colours: Colour*): Arr[PaintElem] = colours.iMap((colour, i) => Rectangle.fromTL(ratio,
     1.0 / colours.length, -ratio / 2 vv + 0.5).slate(0,
       - i.toDouble / colours.length).fill(colour))

  /** Equal height horizontal bands. width ratio should normally be greater than 1.0 */
  def topToBottomRepeat(numBands: Int, colours: Colour*): Arr[PaintElem] = iUntilMap(0, numBands){ i =>
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
    override def apply(): Arr[PaintElem] = Arr(rect.fill(colour))
  }
}

object TextFlagMaker
{
  def apply(str: String, colour: Colour, ratioIn: Double = 1.5): Flag = new Flag
  { override def name: String = str + " Flag"
    override def ratio: Double = ratioIn
    override def apply(): Arr[PaintElem] = Arr(rect.fill(colour), TextGraphic(str, 40))
  }
}

object Armenia extends Flag
{ val name = "Armenia"
  val ratio = 2
  val apply: Arr[PaintElem] = leftToRight(Red, Blue, Gold)
}

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  val apply: Arr[PaintElem] = topToBottom(Black, Yellow)
}

object Belgium extends Flag
{ val name = "Belgium"
  val ratio = 15.0 / 13.0
  def apply: Arr[PaintElem] = leftToRight(Black, Yellow, Red)
}

object Chad extends Flag
{ val ratio = 1.5
  val name = "Chad"
  def apply: Arr[PaintElem] = leftToRight(Blue, Yellow, Red)
}

object China extends Flag
{ val name = "China"
  val ratio = 1.5
  val apply: Arr[PaintElem] =
  {
    Arr[PaintElem](
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

  val apply: Arr[PaintElem] =
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
  val apply: Arr[PaintElem] = leftToRight(Colour(0xFF0055A4) , White, Colour(0xFFEF4135))
}

object Germany extends Flag
{ val name = "Germany"
  val ratio = 5 / 3.0
  val apply: Arr[PaintElem] = topToBottom(Black, Red, Gold)
}

object Germany1871 extends Flag
{ val name = "Germany (1871)"
  val ratio = 1.5
  val apply: Arr[PaintElem] = topToBottom(Black, White, Red)
}

object Italy extends Flag
{ val name = "Italy"
  val ratio = 1.5
  val apply: Arr[PaintElem] = topToBottom(Green, White, Red)
}

object Ireland extends Flag
{ val name = "Ireland"
  val ratio = 2
  val apply: Arr[PaintElem] = topToBottom(Green, White, Orange)
}

object Japan extends Flag
{ val name = "Japan"
  val ratio = 1.5
  val apply: Arr[PaintElem] =
  { val rw = rect.fill(White)
    val circ = Circle.segs(0.6).fill(Colour.fromInts(188, 0,45))
    Arr(rw, circ)
  }
}

object Russia extends Flag
{ val ratio = 1.5
  val name = "Russia"
  val apply: Arr[PaintElem] = topToBottom(White, Blue, Red)
}

object USSR extends Flag
{ val name = "USSR"
  val ratio = 1.5
  val apply: Arr[PaintElem] =
  {
    Arr[PaintElem](
      Rectangle(ratio, 1).fill(Red),
      Star5().scale(0.4).fill(Gold)
    )
  }
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  val apply: Arr[PaintElem] =
  { val poly = Rectangle(ratio, 1)
    val bar = Rectangle.fromBC(0.1, 0.2).fill(Black)
    val arm = Rectangle.fromTL(6.0 / 20, 0.1, -1.0 / 20 vv 0.25).fill(Black)
    val cross = Arr(bar, arm).anti45.flatMap(_.rCrossArr)
    Arr[PaintElem](
      poly.fill(Red),
      Circle.segs(6.0 / 8).fill(White)
    ) ++ cross
  }
}


object WhiteFlag extends Flag
{ val name = "White"
  val ratio = 1.5
  val apply: Arr[PaintElem] =
  {
    Arr[PaintElem](
      Rectangle(1.5, 1).fill(White)
    )
  }
}
  
object CommonShapesInFlags extends Flag
{ val name = "CommonShapesInFlags"
  val ratio = 1.5

  val apply: Arr[PaintElem] =
  {
    Arr[PaintElem](
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
  val apply: Arr[PaintElem] = 
  {
    Arr[PaintElem](
      Rectangle(ratio, 1).fill(White),
      Rectangle(ratio, 0.5).slate(0 vv -0.25).fill(Colour(0xFFD7141A)),
      Triangle.fill(-ratio/2 vv 0.5, -ratio/2 vv -0.5, 0 vv 0, Colour(0xFF11457E))
    )
  }
}

object CCCP extends Flag
{ val name = "CCCP"
  val ratio = 2.0
  val apply: Arr[PaintElem] = 
  { Arr[PaintElem](
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
