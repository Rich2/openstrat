/* Copyright 2018-20 Richard Oliver. Licensed under Apache Licence version 2.0. */
package ostrat
package pFlags
import geom._, Colour._

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  val apply: Arr[DisplayAffineElem] = topToBottom(Black, Yellow)
}

object Belgium extends Flag
{ val name = "Belgium"
  val ratio = 15.0 / 13.0
  def apply(): Arr[DisplayAffineElem] = leftToRight(Black, Yellow, Red)
}

trait EnglandLike extends Flag
{ def ratio = 2
  def englishRed: Colour = Colour.fromInts(204, 0, 0)
  def redCross: Arr[PolygonFill] = Rectangle.cross(2, 1, 0.2).map(_.fill(englishRed))
  def common = rect.fill(White) +: redCross
}

object England extends EnglandLike
{ val name = "England"
  val apply = common
}

object UnitedKingdom extends EnglandLike
{ val name = "United Kingdom"

  val apply: Arr[DisplayAffineElem] =
  { val xd = math.sqrt(5) / 30.0 //hypotenuse sqrt(2 * 2 + 1 * 1)
    val yd = math.sqrt(1.25) / 30.0 //hypotenuse Sqrt(1 * 1 + 0.5 * 0.5)
    val ywc = 5.0 / 30 //top of White cross bar
    val xDiag = 10.0 / 30.0 //ywc * 2 where diag crosses ywc
    val b1 = PolygonClass(
      5.0 / 30 vv 0.5, 1 - xd * 3 vv 0.5,
      1.0 / 6.0 vv ywc + yd)

    val b2 = PolygonClass(
      xDiag + 3 * xd vv ywc,
      1 vv 0.5 - yd * 3,
      1 vv ywc)

    val r1: PolygonClass = PolygonClass(
      -1 vv 0.5,
      -xDiag vv ywc,
      -(xDiag + xd * 2) vv ywc,
      -1 vv 0.5 - (yd * 2))

    val r2: PolygonClass = PolygonClass(
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
  val apply: Arr[DisplayAffineElem] = leftToRight(Colour(0xFF0055A4) , White, Colour(0xFFEF4135))
}

object Germany extends Flag
{ val name = "Germany"
  val ratio = 5 / 3.0
  val apply: Arr[DisplayAffineElem] = topToBottom(Black, Red, Gold)
}

object Germany1871 extends Flag
{ val name = "Germany (1871)"
  val ratio = 1.5
  val apply: Arr[DisplayAffineElem] = topToBottom(Black, White, Red)
}

object Italy extends Flag
{ val name = "Italy"
  val ratio = 1.5
  val apply: Arr[DisplayAffineElem] = topToBottom(Green, White, Red)
}

object Ireland extends Flag
{ val name = "Ireland"
  val ratio = 2
  val apply: Arr[DisplayAffineElem] = topToBottom(Green, White, Orange)
}

object Russia extends Flag
{ val ratio = 1.5
  val name = "Russia"
  val apply: Arr[DisplayAffineElem] = topToBottom(White, Blue, Red)
}

object USSR extends Flag
{ val name = "USSR"
  val ratio = 1.5
  val apply: Arr[DisplayAffineElem] =
  {
    Arr[DisplayAffineElem](
      Rectangle(ratio, 1).fill(Red),
      Star5().scale(0.4).fill(Gold)
    )
  }
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  val apply =
  { val poly = Rectangle(ratio, 1)
    val bar = Rectangle.fromBC(0.1, 0.2).fill(Black)
    val arm = Rectangle.fromTL(6.0 / 20, 0.1, -1.0 / 20 vv 0.25).fill(Black)
    val cross = Arr(bar, arm).rotate45.flatMap(_.rCrossArr)
    Arr(poly.fill(Red), Circle(6.0 / 8).fill(White)) ++ cross
  }
}

object CzechRepublic extends Flag
{ val name = "Czech Republic"
  val ratio = 1.5
  val apply: Arr[DisplayAffineElem] =
  {
    Arr[DisplayAffineElem](
      Rectangle(ratio, 1).fill(White),
      Rectangle(ratio, 0.5).slate(0 vv -0.25).fill(Colour(0xFFD7141A)),
      Triangle.fill(-ratio/2 vv 0.5, -ratio/2 vv -0.5, 0 vv 0, Colour(0xFF11457E))
    )
  }
}

object CCCP extends Flag
{ val name = "CCCP"
  val ratio = 2.0
  val apply: Arr[DisplayAffineElem] =
  { Arr[DisplayAffineElem](
    //background
    Rectangle(ratio, 1).fill(Colour(0xFFCC0000)),
    //hammer
    PolyCurve(LineTail(-0.7709 vv 0.2138), LineTail(-0.7395 vv 0.1822), LineTail(-0.7099 vv 0.2116), BezierTail(-0.6648 vv 0.1633, -0.6175 vv 0.1166, -0.5727 vv 0.06808), BezierTail(-0.566 vv 0.06131, -0.555 vv 0.06128, -0.5483 vv 0.068), BezierTail(-0.5415 vv 0.07472, -0.5415 vv 0.08566, -0.5482 vv 0.09243), BezierTail(-0.5962 vv 0.1378, -0.6444 vv 0.1834, -0.6924 vv 0.2289), LineTail(-0.6525 vv 0.2686), LineTail(-0.7081 vv 0.2763), LineTail(-0.7709 vv 0.2138)).fill(Colour(0xFFFFD700)),
    //sickle
    PolyCurve(LineTail(-0.6695 vv 0.3163), BezierTail(-0.6437 vv 0.3018, -0.624 vv 0.2809, -0.6124 vv 0.259), BezierTail(-0.6007 vv 0.2369, -0.5955 vv 0.2137, -0.5954 vv 0.1953), BezierTail(-0.5952 vv 0.1574, -0.6262 vv 0.1266, -0.6641 vv 0.1266), BezierTail(-0.6843 vv 0.1266, -0.7025 vv 0.1354, -0.715 vv 0.1493), LineTail(-0.722 vv 0.1434), BezierTail(-0.7232 vv 0.1439, -0.7244 vv 0.1441, -0.7257 vv 0.1441), BezierTail(-0.7287 vv 0.1441, -0.7316 vv 0.1428, -0.7336 vv 0.1405), BezierTail(-0.7386 vv 0.1398, -0.7427 vv 0.1364, -0.7443 vv 0.1316), BezierTail(-0.7495 vv 0.1212, -0.7587 vv 0.1129, -0.7698 vv 0.1092), BezierTail(-0.7699 vv 0.1091, -0.77 vv 0.1091, -0.7701 vv 0.109), BezierTail(-0.7752 vv 0.1072, -0.7803 vv 0.1038, -0.7847 vv 0.09938), BezierTail(-0.7934 vv 0.09065, -0.7976 vv 0.07948, -0.7952 vv 0.07165), BezierTail(-0.7954 vv 0.07097, -0.7956 vv 0.07025, -0.7956 vv 0.06953), BezierTail(-0.7956 vv 0.06591, -0.7926 vv 0.06298, -0.789 vv 0.06298), BezierTail(-0.7881 vv 0.06298, -0.7872 vv 0.06317, -0.7864 vv 0.06354), BezierTail(-0.7786 vv 0.06178, -0.7679 vv 0.06612, -0.7596 vv 0.07447), BezierTail(-0.7549 vv 0.07922, -0.7514 vv 0.08485, -0.7497 vv 0.09036), BezierTail(-0.7458 vv 0.1014, -0.7375 vv 0.1104, -0.727 vv 0.1154), BezierTail(-0.7267 vv 0.1156, -0.7265 vv 0.1157, -0.7263 vv 0.1158), BezierTail(-0.7228 vv 0.1175, -0.7202 vv 0.1207, -0.7193 vv 0.1245), BezierTail(-0.7031 vv 0.1054, -0.6789 vv 0.09325, -0.6517 vv 0.09262), BezierTail(-0.602 vv 0.09145, -0.5649 vv 0.128, -0.5631 vv 0.1807), BezierTail(-0.5623 vv 0.2072, -0.5725 vv 0.2413, -0.5959 vv 0.2693), BezierTail(-0.6137 vv 0.2907, -0.6399 vv 0.3085, -0.6695 vv 0.3163), LineTail(-0.6695 vv 0.3163)).fill(Colour(0xFFFFD700)),
    //outer star
    Star5().scale(1.0/16).slate(-2.0/3 vv 0.75/2).fill(Colour(0xFFFFD700)),
    //inner star
    Star5().scale(1.0/25).slate(-2.0/3 vv 0.75/2).fill(Colour(0xFFCC0000)),
  )
  }
}