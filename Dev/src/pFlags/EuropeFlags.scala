/* Copyright 2018-21 Richard Oliver, w0d. Licensed under Apache Licence version 2.0. */
package ostrat; package pFlags
import geom._, Colour._

object Austria extends Flag
{ def name = "Austria"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Black, Yellow)
}

object Belgium extends Flag
{ val name = "Belgium"
  val ratio = 15.0 / 13.0
  def apply(): GraphicElems = leftToRight(Black, Yellow, Red)
}

trait EnglandLike extends Flag
{ def ratio = 2
  def englishRed: Colour = Colour.fromInts(204, 0, 0)
  def redCross: Arr[PolygonFill] = Rect.cross(2, 1, 0.2).map(_.fill(englishRed))
  def common = rect.fill(White) %: redCross
}

object England extends EnglandLike
{ val name = "England"
  def apply(): GraphicElems = common
}

object UnitedKingdom extends EnglandLike
{ val name = "United Kingdom"

  def apply(): GraphicElems =
  { val xd = hypotenuse(2,1) / 30.0
    val yd = hypotenuse(1,0.5) / 30.0

    /** Top of White cross bar */
    val ywc = 5.0 / 30

    /** ywc * 2 where diag crosses ywc */
    val xDiag = 10.0 / 30.0

    val b1 = PolygonGen(
      5.0 / 30 pp 0.5, 1 - xd * 3 pp 0.5,
      1.0 / 6.0 pp ywc + yd)

    val b2 = PolygonGen(
      xDiag + 3 * xd pp ywc,
      1 pp 0.5 - yd * 3,
      1 pp ywc)

    val r1: PolygonGen = PolygonGen(
      -1 pp 0.5,
      -xDiag pp ywc,
      -(xDiag + xd * 2) pp ywc,
      -1 pp 0.5 - (yd * 2))

    val r2: PolygonGen = PolygonGen(
      xDiag - xd * 2 pp ywc,
      1 - xd * 2 pp 0.5,
      1 pp 0.5,
      xDiag pp ywc)

    val reds1 = Arr(r1, r2).map(_.fill(englishRed))
    val reds = reds1.flatMap(e => Arr(e, e.negY.negX))

    val blues = {
      val l1 = Arr(b1, b2).map(_.fill(Colour.fromInts(0, 0, 102)))
      l1.flatMap(b => Arr(b, b.negY, b.negX, b.negY.negX))
    }
    common ++ blues ++ reds
  }
}

object France extends Flag
{ val name = "France"
  val ratio = 1.5
  def apply(): GraphicElems = leftToRight(Colour(0xFF0055A4) , White, Colour(0xFFEF4135))
}

object Germany extends Flag
{ val name = "Germany"
  val ratio = 5 / 3.0
  def apply(): GraphicElems = topToBottom(Black, Red, Gold)
}

object Germany1871 extends Flag
{ val name = "Germany (1871)"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Black, White, Red)
}

object Italy extends Flag
{ val name = "Italy"
  val ratio = 1.5
  def apply(): GraphicElems = topToBottom(Green, White, Red)
}

object Ireland extends Flag
{ val name = "Ireland"
  val ratio = 2
  def apply(): GraphicElems = topToBottom(Green, White, Orange)
}

object Russia extends Flag
{ val ratio = 1.5
  val name = "Russia"
  def apply(): GraphicElems = topToBottom(White, Blue, Red)
}

object USSR extends Flag
{ val name = "USSR"
  val ratio = 1.5
  def apply(): GraphicElems = Arr(
    Rect(ratio, 1).fill(Red),
    Star5().scale(0.4).fill(Gold)
  )
}

object Swastika extends Flag
{ val name = "Swastika"
  val ratio = 5 / 3.0
  def apply(): GraphicElems =
  { val poly = Rect(ratio, 1)
    val bar = Rect.bCen(0.1, 0.2).fill(Black)
    val arm = Rect.tl(6.0 / 20, 0.1, -1.0 / 20 pp 0.25).fill(Black)
    val cross = Arr(bar, arm).rotate45.flatMap(_.rotateQuadrants)
    Arr(poly.fill(Red), Circle(6.0 / 8).fill(White)) ++ cross
  }
}

object CzechRepublic extends Flag
{ val name = "Czech Republic"
  val ratio = 1.5
  def apply(): GraphicElems = Arr(
    Rect(ratio, 1).fill(White),
    Rect(ratio, 0.5).slate(0 pp -0.25).fill(Colour(0xFFD7141A)),
    Triangle(-ratio / 2 pp 0.5, -ratio / 2 pp -0.5, 0 pp 0).fill(Colour(0xFF11457E))
  )
}

object CCCP extends Flag
{ val name = "CCCP"
  val ratio = 2.0
  def apply(): GraphicElems = Arr(
    //background
    Rect(ratio, 1).fill(Colour(0xFFCC0000)),
    //hammer
    ShapeGenOld(LineTail(-0.7709 pp 0.2138), LineTail(-0.7395 pp 0.1822), LineTail(-0.7099 pp 0.2116),
      BezierTail(-0.6648 pp 0.1633, -0.6175 pp 0.1166, -0.5727 pp 0.06808), BezierTail(-0.566 pp 0.06131, -0.555 pp 0.06128, -0.5483 pp 0.068),
      BezierTail(-0.5415 pp 0.07472, -0.5415 pp 0.08566, -0.5482 pp 0.09243), BezierTail(-0.5962 pp 0.1378, -0.6444 pp 0.1834, -0.6924 pp 0.2289),
      LineTail(-0.6525 pp 0.2686), LineTail(-0.7081 pp 0.2763), LineTail(-0.7709 pp 0.2138)).fill(Colour(0xFFFFD700)),
    //sickle
    ShapeGenOld(LineTail(-0.6695 pp 0.3163), BezierTail(-0.6437 pp 0.3018, -0.624 pp 0.2809, -0.6124 pp 0.259),
      BezierTail(-0.6007 pp 0.2369, -0.5955 pp 0.2137, -0.5954 pp 0.1953), BezierTail(-0.5952 pp 0.1574, -0.6262 pp 0.1266, -0.6641 pp 0.1266),
      BezierTail(-0.6843 pp 0.1266, -0.7025 pp 0.1354, -0.715 pp 0.1493), LineTail(-0.722 pp 0.1434),
      BezierTail(-0.7232 pp 0.1439, -0.7244 pp 0.1441, -0.7257 pp 0.1441), BezierTail(-0.7287 pp 0.1441, -0.7316 pp 0.1428, -0.7336 pp 0.1405),
      BezierTail(-0.7386 pp 0.1398, -0.7427 pp 0.1364, -0.7443 pp 0.1316), BezierTail(-0.7495 pp 0.1212, -0.7587 pp 0.1129, -0.7698 pp 0.1092),
      BezierTail(-0.7699 pp 0.1091, -0.77 pp 0.1091, -0.7701 pp 0.109), BezierTail(-0.7752 pp 0.1072, -0.7803 pp 0.1038, -0.7847 pp 0.09938),
      BezierTail(-0.7934 pp 0.09065, -0.7976 pp 0.07948, -0.7952 pp 0.07165), BezierTail(-0.7954 pp 0.07097, -0.7956 pp 0.07025, -0.7956 pp 0.06953),
      BezierTail(-0.7956 pp 0.06591, -0.7926 pp 0.06298, -0.789 pp 0.06298), BezierTail(-0.7881 pp 0.06298, -0.7872 pp 0.06317, -0.7864 pp 0.06354),
      BezierTail(-0.7786 pp 0.06178, -0.7679 pp 0.06612, -0.7596 pp 0.07447), BezierTail(-0.7549 pp 0.07922, -0.7514 pp 0.08485, -0.7497 pp 0.09036),
      BezierTail(-0.7458 pp 0.1014, -0.7375 pp 0.1104, -0.727 pp 0.1154), BezierTail(-0.7267 pp 0.1156, -0.7265 pp 0.1157, -0.7263 pp 0.1158),
      BezierTail(-0.7228 pp 0.1175, -0.7202 pp 0.1207, -0.7193 pp 0.1245), BezierTail(-0.7031 pp 0.1054, -0.6789 pp 0.09325, -0.6517 pp 0.09262),
      BezierTail(-0.602 pp 0.09145, -0.5649 pp 0.128, -0.5631 pp 0.1807), BezierTail(-0.5623 pp 0.2072, -0.5725 pp 0.2413, -0.5959 pp 0.2693),
      BezierTail(-0.6137 pp 0.2907, -0.6399 pp 0.3085, -0.6695 pp 0.3163), LineTail(-0.6695 pp 0.3163)).fill(Colour(0xFFFFD700)),
    //outer star
    Star5().scale(1.0/16).slate(-2.0/3 pp 0.75/2).fill(Colour(0xFFFFD700)),
    //inner star
    Star5().scale(1.0/25).slate(-2.0/3 pp 0.75/2).fill(Colour(0xFFCC0000)),
  )
}